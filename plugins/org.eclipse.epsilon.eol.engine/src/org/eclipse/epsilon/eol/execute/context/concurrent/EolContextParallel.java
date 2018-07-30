package org.eclipse.epsilon.eol.execute.context.concurrent;

import java.util.function.Supplier;
import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.concurrent.ConcurrentBaseDelegate;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolExecutorService;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolThreadPoolExecutor;
import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.concurrent.DelegatePersistentThreadLocal;
import org.eclipse.epsilon.eol.execute.concurrent.PersistentThreadLocal;

/**
 * Skeletal implementation of a parallel IEolContext. This class takes care of
 * the common structures which are affected by multi-threading using
 * {@linkplain ThreadLocal}s. For use cases where these thread-local values are
 * required, a {@linkplain DelegatePersistentThreadLocal} is used, so that the
 * context will be consistent with a sequential implementation once
 * {@linkplain #endParallel()} is invoked. The default behaviour of this class is
 * to NOT persist thread-local values to improve performance and save memory.
 * 
 * @author Sina Madani
 */
public class EolContextParallel extends EolContext implements IEolContextParallel {

	protected int numThreads;
	protected boolean isParallel = false;
	protected final boolean isPersistent;
	private EolExecutorService executorService;
	
	// Data strcutures which will be written to and read from during parallel execution:
	protected ThreadLocal<FrameStack> concurrentFrameStacks;
	protected ThreadLocal<OperationContributorRegistry> concurrentMethodContributors;
	protected ThreadLocal<ExecutorFactory> concurrentExecutors;
	
	public EolContextParallel() {
		this(0);
	}

	public EolContextParallel(int parallelism) {
		this(parallelism, false);
	}
	
	/**
	 * @param parallelism The number of threads to use.
	 * @param persistThreadLocals Whether to save the state of thread-local values
	 * (such as variable declarations) so that they can be merged into the main thread later.
	 */
	public EolContextParallel(int parallelism, boolean persistThreadLocals) {
		setNumThreads(parallelism);
		initMainThreadStructures();
		this.isPersistent = persistThreadLocals;
	}

	/**
	 * @see #EolContextParallel(IEolContext, int)
	 */
	public EolContextParallel(IEolContext other) {
		this(other, 0);
	}
	
	public EolContextParallel(IEolContext other, int parallelism) {
		this(other, parallelism, false);
	}
	
	/**
	 * Copy constructor.
	 * NOTE: The context parameter may be modified.
	 * 
	 * @param other The context to copy from. Structures
	 * in this parameter may be modified to be thread-safe.
	 * @param parallelism The number of threads to use.
	 * @param persistThreadLocals Whether to save the state of thread-local values
	 * (such as variable declarations) so that they can be merged into the main thread later.
	 */
	public EolContextParallel(IEolContext other, int parallelism, boolean persistThreadLocals) {
		super(other);
		setNumThreads(parallelism);
		setBaseThreadSafety(true);
		this.isPersistent = persistThreadLocals;
	}
	
	protected int setNumThreads(int parallelism) {
		return (numThreads = parallelism > 0 ? parallelism : ConcurrencyUtils.DEFAULT_PARALLELISM);
	}
	
	protected void initMainThreadStructures() {
		// This will be the "base" of others, so make it thread-safe for concurrent reads
		frameStack = new FrameStack(null, true);
		methodContributorRegistry = new OperationContributorRegistry(null, true);
		executorFactory = new ExecutorFactory(null, true);
	}
	
	protected void initThreadLocals() {
		concurrentFrameStacks = initThreadLocal(() -> new FrameStack(frameStack, false));
		concurrentMethodContributors = initThreadLocal(() -> new OperationContributorRegistry(methodContributorRegistry, false));
		concurrentExecutors = initThreadLocal(() -> new ExecutorFactory(executorFactory, false));
	}
	
	protected <T extends ConcurrentBaseDelegate<T>> ThreadLocal<T> initThreadLocal(Supplier<? extends T> constructor) {
		return isPersistent ?
			new DelegatePersistentThreadLocal<>(numThreads, constructor) :
			ThreadLocal.withInitial(constructor);
	}
	
	protected void setBaseThreadSafety(boolean concurrent) {
		frameStack.setThreadSafe(concurrent);
		methodContributorRegistry.setThreadSafe(concurrent);
		executorFactory.setThreadSafe(concurrent);
	}
	
	protected static void removeAllIfPersistent(ThreadLocal<?> threadLocal) {
		if (threadLocal instanceof PersistentThreadLocal) {
			((PersistentThreadLocal<?>) threadLocal).removeAll();
		}
	}
	
	@Override
	public void goParallel() {
		if (!isParallel) {
			initThreadLocals();
			isParallel = true;
		}
	}
	
	@Override
	public void endParallel() {
		isParallel = false;
		
		removeAllIfPersistent(concurrentFrameStacks);
		removeAllIfPersistent(concurrentMethodContributors);
		removeAllIfPersistent(concurrentExecutors);
		
		if (executorService != null) {
			executorService.shutdown();
		}
	}
	
	public boolean isPersistent() {
		return isPersistent;
	}
	
	@Override
	public final boolean isParallel() {
		return isParallel;
	}
	
	@Override
	public final int getParallelism() {
		return numThreads;
	}
	
	/**
	 * If no value is set, this method will create a new ExecutorService
	 * by calling {@linkplain #newExecutorService()} and caching the reuslt
	 * for future invocations of this method.
	 */
	@Override
	public EolExecutorService getExecutorService() {
		if (executorService == null) {
			executorService = newExecutorService();
		}
		return executorService;
	}
	
	@Override
	public synchronized void setExecutorService(EolExecutorService exector) {
		this.executorService = exector;
	}
	
	@Override
	public EolExecutorService newExecutorService() {
		return EolThreadPoolExecutor.fixedPoolExecutor(numThreads);
	}
	
	@Override
	public FrameStack getFrameStack() {
		return parallelGet(concurrentFrameStacks, super::getFrameStack);
	}
	
	@Override
	public void setFrameStack(FrameStack frameStack) {
		parallelSet(frameStack, concurrentFrameStacks, super::setFrameStack);
	}
	
	@Override
	public ExecutorFactory getExecutorFactory() {
		return parallelGet(concurrentExecutors, super::getExecutorFactory);
	}

	@Override
	public void setExecutorFactory(ExecutorFactory executorFactory) {
		parallelSet(executorFactory, concurrentExecutors, super::setExecutorFactory);
	}
	
	@Override
	public OperationContributorRegistry getOperationContributorRegistry() {
		return parallelGet(concurrentMethodContributors, super::getOperationContributorRegistry);
	}
	
	@Override
	public IEolModule getModule() {
		return (IEolModule) super.getModule();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [parallelism="+getParallelism()+']';
	}
	
	public static IEolContextParallel convertToParallel(IEolContext context_) {
		IEolContextParallel context = context_ instanceof IEolContextParallel ?
			(IEolContextParallel) context_ : new EolContextParallel(context_);

		context.goParallel();
		return context;
	}
}