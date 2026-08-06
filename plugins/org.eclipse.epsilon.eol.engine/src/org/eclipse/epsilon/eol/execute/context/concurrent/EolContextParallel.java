/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.context.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.concurrent.DelegatePersistentThreadLocal;
import org.eclipse.epsilon.common.concurrent.PersistentThreadLocal;
import org.eclipse.epsilon.common.function.BaseDelegate;
import org.eclipse.epsilon.common.function.BaseDelegate.MergeMode;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.concurrent.EolNestedParallelismException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.concurrent.EolThreadPoolExecutor;
import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;

/**
 * Skeletal implementation of a parallel IEolContext. This class takes care of
 * the common structures which are affected by multi-threading using
 * {@linkplain ThreadLocal}s. For use cases where these thread-local values are
 * required, a {@linkplain PersistentThreadLocal} is used, so that the
 * context will be consistent with a sequential implementation once
 * {@linkplain #endParallel()} is invoked.
 * <br/><br/>
 * For optimal performance, it is recommend that parallel tasks obtain a sequential
 * "snapshot" of this context to avoid frequent retrieval of ThreadLocal values using
 * the {@link #getShadow()} method.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolContextParallel extends EolContext implements IEolContextParallel {
	
	int numThreads;
	boolean isInParallelTask;
	boolean isInShortCircuitTask;
	protected EolThreadPoolExecutor executorService;
	
	// Data structures which will be written to and read from during parallel execution.
	// Note that the OperationContributorRegistry is shared; thread-safety is
	// handled by the OperationContributor class.
	ThreadLocal<FrameStack> concurrentFrameStacks;
	ThreadLocal<ExecutorFactory> concurrentExecutorFactories;
	
	/**
	 * Optimisation so that calls to methods like getFrameStack() don't re-fetch the ThreadLocal
	 * value every time whilst within the same parallelisation context.
	 */
	ThreadLocal<IEolContext> threadLocalShadows;
	
	public EolContextParallel() {
		this(0);
	}
	
	/**
	 * @param parallelism The number of threads to use.
	 */
	public EolContextParallel(int parallelism) {
		numThreads = parallelism > 0 ? parallelism : ConcurrencyUtils.DEFAULT_PARALLELISM;
		// This will be the "base" of others, so make it thread-safe for concurrent reads
		frameStack.setThreadSafe(true);
		asyncStatementsQueue = new ConcurrentLinkedQueue<>();
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param other The parent context to copy from. Must not be null.
	 */
	protected EolContextParallel(IEolContext other) {
		super(other);
		frameStack.setThreadSafe(true);
		
		if (other instanceof EolContextParallel) {
			executorService = ((EolContextParallel) other).getExecutorService();
		}
		
		if (other instanceof IEolContextParallel) {
			numThreads = ((IEolContextParallel) other).getParallelism();
		}
		else {
			numThreads = ConcurrencyUtils.DEFAULT_PARALLELISM;
			asyncStatementsQueue = new ConcurrentLinkedQueue<>(other.getAsyncStatementsQueue());
		}
	}
	
	protected void initThreadLocals() {
		concurrentFrameStacks = initDelegateThreadLocal(this::createThreadLocalFrameStack);
		concurrentExecutorFactories = initDelegateThreadLocal(this::createThreadLocalExecutorFactory);
		threadLocalShadows = ThreadLocal.withInitial(this::createShadowThreadLocalContext);
	}
	
	protected <T extends BaseDelegate<T>> DelegatePersistentThreadLocal<T> initDelegateThreadLocal(Supplier<? extends T> constructor) {
		return new DelegatePersistentThreadLocal<>(numThreads, constructor);
	}
	
	/**
	 * Determines whether calling {@link #parallelGet(ThreadLocal, Object)} or
	 * {@link #parallelSet(Object, ThreadLocal, Consumer)} should use the ThreadLocal
	 * value or if the alternative value should be returned.
	 * 
	 * @return <code>true</code> if the ThreadLocal should be used, <code>false</code> otherwise.
	 */
	protected boolean useThreadLocalValue() {
		return isInParallelTask;
	}
	
	/**
	 * Utility method used to appropriately return either a thread-local or the original value,
	 * depending on whether this context {@linkplain #isParallel()}.
	 * 
	 * @param <R> The value type
	 * @param threadLocal The ThreadLocal value (returned if parallel).
	 * @param originalValueGetter The non-thread-local value (returned if not parallel).
	 * @return The appropriate value for the current thread.
	 */
	protected final <R> R parallelGet(ThreadLocal<? extends R> threadLocal, Supplier<? extends R> originalValueGetter) {
		return threadLocal != null && useThreadLocalValue() ? threadLocal.get() : originalValueGetter.get();
	}
	
	/**
	 * Utility method used to appropriately return either a thread-local or the original value,
	 * depending on whether this context {@linkplain #isParallel()}.
	 * 
	 * @param <R> The value type
	 * @param threadLocal The thread-local wrapper for the value
	 * @param originalValue The main, persistent variable
	 * @return The appropriate value for the current thread.
	 */
	protected final <R> R parallelGet(ThreadLocal<? extends R> threadLocal, R originalValue) {
		return threadLocal != null && useThreadLocalValue() ? threadLocal.get() : originalValue;
	}
	
	/**
	 * Utility method used to appropriately set either a thread-local or the original value,
	 * depending on whether this context {@linkplain #isParallel()}.
	 * @param value The value to set.
	 * @param threadLocal The ThreadLocal value (will be set if parallel).
	 * @param originalValueSetter The non-thread-local value (will be set if not parallel).
	 */
	protected final <T> void parallelSet(T value, ThreadLocal<? super T> threadLocal, Consumer<? super T> originalValueSetter) {
		if (threadLocal != null && useThreadLocalValue())
			threadLocal.set(value);
		else
			originalValueSetter.accept(value);
	}
	
	protected void removeAll(ThreadLocal<?>... threadLocals) {
		if (threadLocals != null) for (ThreadLocal<?> tl : threadLocals) {
			if (tl instanceof DelegatePersistentThreadLocal && !isInShortCircuitTask) {
				((DelegatePersistentThreadLocal<?>) tl).removeAll(MergeMode.MERGE_INTO_BASE);
			}
			else if (tl instanceof PersistentThreadLocal) {
				((PersistentThreadLocal<?>) tl).removeAll();
			}
			else if (tl != null) {
				tl.remove();
			}
		}
	}
	
	protected synchronized void clearThreadLocals() {
		removeAll(concurrentFrameStacks, concurrentExecutorFactories, threadLocalShadows);
	}
	
	protected void nullifyThreadLocals() {
		concurrentFrameStacks = null;
		concurrentExecutorFactories = null;
		threadLocalShadows = null;
	}
	
	protected void clearExecutor() {
		if (executorService != null) {
			/*
			 * Note: we need to shut down the executor service no matter what, in order to
			 * avoid leaking threads. The only difference is whether we interrupt running
			 * tasks or not.
			 */
			if (this.isInShortCircuitTask) {
				// Interrupt tasks
				executorService.shutdownNow();
			} else {
				// Don't interrupt, but stop accepting new tasks
				executorService.shutdown();
			}

			executorService = null;
		}
	}
	
	protected EolThreadPoolExecutor newExecutorService() {
		return new EolThreadPoolExecutor(numThreads);
	}
	
	@Override
	public synchronized ExecutorService beginParallelTask(ModuleElement entryPoint, boolean shortCircuiting) throws EolNestedParallelismException {
		ensureNotNested(entryPoint != null ? entryPoint : getModule());
		isInShortCircuitTask = shortCircuiting;
		initThreadLocals();
		if (executorService == null || executorService.isShutdown()) {
			executorService = newExecutorService();
		}
		isInParallelTask = true;
		return executorService;
	}
	
	@Override
	public synchronized void endParallelTask() throws EolRuntimeException {
		clearExecutor();
		clearThreadLocals();
		isInParallelTask = false;
		isInShortCircuitTask = false;
	}
	
	@Override
	public synchronized void setParallelism(int threads) throws IllegalStateException, IllegalArgumentException {
		if (threads != this.numThreads) {
			if (isInParallelTask) {
				throw new IllegalStateException("Cannot change parallelism whilst execution is in progress!");
			}
			if (threads <= 0) {
				throw new IllegalArgumentException("Parallelism of "+threads+" is nonsensical!");
			}
			this.numThreads = threads;
		}
	}
	
	@Override
	public boolean isParallel() {
		return isInParallelTask;
	}
	
	@Override
	public final int getParallelism() {
		return numThreads;
	}
	
	@Override
	public synchronized final EolThreadPoolExecutor getExecutorService() {
		if (executorService == null) {
			executorService = newExecutorService();
		}
		return executorService;
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
		clearExecutor();
		nullifyThreadLocals();
	}
	
	@Override
	public FrameStack getFrameStack() {
		return parallelGet(concurrentFrameStacks, frameStack);
	}
	
	@Override
	public ExecutorFactory getExecutorFactory() {
		return parallelGet(concurrentExecutorFactories, executorFactory);
	}
	
	@Override
	public void setFrameStack(FrameStack frameStack) {
		parallelSet(frameStack, concurrentFrameStacks, fs -> this.frameStack = fs);
	}

	@Override
	public void setExecutorFactory(ExecutorFactory executorFactory) {
		parallelSet(executorFactory, concurrentExecutorFactories, ef -> this.executorFactory = ef);
	}	
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [parallelism="+getParallelism()+']';
	}
	
	protected ExecutorFactory createThreadLocalExecutorFactory() {
		return new ExecutorFactory(executorFactory);
	}
	
	protected FrameStack createThreadLocalFrameStack() {
		return new FrameStack(frameStack, false);
	}
	
	protected OperationContributorRegistry createThreadLocalOperationContributorRegistry() {
		return new OperationContributorRegistry();
	}
	
	protected IEolContext createShadowThreadLocalContext() {
		return new EolContext(this);
	}
	
	/**
	 * Can be used to obtain an optimal execution context while executing in parallel.
	 * If execution is currently not parallel, then this context itself is returned.
	 * 
	 * @return A ThreadLocal copy of this context if in parallel, or this context otherwise.
	 */
	public IEolContext getShadow() {
		return !isInParallelTask || threadLocalShadows == null ? this : threadLocalShadows.get();
	}
	
	/**
	 * Utility method for converting a sequential context to a parallel one, if it is not already parallel.
	 * 
	 * @param context The current execution context to check or convert.
	 * @return The context if it is already parallel, or a new {@link EolContextParallel} with state
	 * copied over from the context parameter.
	 * @throws EolNestedParallelismException If the context is already derived from a parallel context.
	 */
	public static IEolContextParallel convertToParallel(IEolContext context) throws EolNestedParallelismException {
		if (context instanceof IEolContextParallel) return (IEolContextParallel) context;
		Object cModule = context.getModule();
		if (cModule instanceof IEolModule && ((IEolModule)cModule).getContext() instanceof IEolContextParallel) {
			throw new EolNestedParallelismException("Attempted to create parallel context from a shadow!");
		}
		return new EolContextParallel(context);
	}
	
	/**
	 * Evaluates the job using this context's parallel execution facilities.
	 * Subclasses may override this to support additional job types, calling
	 * the super method as the last resort for unknown cases. Supported types include
	 * multi-valued types (e.g. arrays, Iterable / Iterator, Stream etc.) as well as
	 * common concurrency units such as Runnable, Callable and Future.
	 * 
	 * @param job The job (or jobs) to evaluate.
	 * @throws IllegalArgumentException If the job type is not recognised.
	 * @throws EolRuntimeException If an exception is thrown whilst evaluating the job(s).
	 * 
	 * @return The result of evaluating the job, if any.
	 */
	@SuppressWarnings("unchecked")
	public Object executeJob(Object job) throws EolRuntimeException {
		if (job == null) {
			return null;
		}
		if (job instanceof Iterable) {
			final boolean isCollection = job instanceof Collection;
			
			if (isParallelisationLegal()) {
				final Collection<Callable<?>> jobs = isCollection ?
					new ArrayList<>(((Collection<?>) job).size()) : new LinkedList<>();
				
				for (Object next : (Iterable<?>) job) {
					jobs.add(() -> executeJob(next));
				}
				return executeAll(null, jobs);
			}
			else {
				final Collection<Object> results = isCollection ?
					new ArrayList<>(((Collection<?>) job).size()) : new LinkedList<>();
				
				for (Object next : (Iterable<?>) job) {
					results.add(executeJob(next));
				}
				return results;
			}
		}
		if (job instanceof ModuleElement) {
			return getExecutorFactory().execute((ModuleElement) job, getShadow());
		}
		if (job instanceof Object[]) {
			return executeJob(Arrays.asList((Object[]) job));
		}
		if (job instanceof Stream) {
			Stream<?> stream = (Stream<?>) job;
			boolean finite = stream.spliterator().hasCharacteristics(Spliterator.SIZED);
			return executeJob(finite ? stream.collect(Collectors.toList()) : stream.iterator());
		}
		if (job instanceof BaseStream) {
			return executeJob(((BaseStream<?,?>)job).iterator());
		}
		if (job instanceof Spliterator) {
			return executeJob(StreamSupport.stream((Spliterator<?>) job, isParallelisationLegal()));
		}
		if (job instanceof Iterator) {
			Iterable<?> iter = () -> (Iterator<Object>) job;
			return executeJob(iter);
		}
		if (job instanceof Supplier) {
			return ((Supplier<?>) job).get();
		}
		try {
			if (job instanceof Future) {
				return ((Future<?>) job).get();
			}
			if (job instanceof Callable) {
				return ((Callable<?>) job).call();
			}
		}
		catch (Exception ex) {
			EolRuntimeException.propagateDetailed(ex);
		}
		if (job instanceof Runnable) {
			((Runnable) job).run();
			return null;
		}
			
		throw new IllegalArgumentException("Encountered unexpected object of type "+job.getClass().getName());
	}
}
