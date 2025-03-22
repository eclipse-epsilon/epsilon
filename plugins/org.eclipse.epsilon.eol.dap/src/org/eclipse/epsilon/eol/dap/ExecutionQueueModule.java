package org.eclipse.epsilon.eol.dap;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;

public class ExecutionQueueModule extends EolModule {

	private BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<>();

	public Future<Object> enqueue(IEolModule module) {
		CompletableFuture<Object> futureResult = new CompletableFuture<>();
		taskQueue.add(() -> {
			try {
				// Copy the execution listeners to the submodule
				ExecutorFactory moduleExecFactory = module.getContext().getExecutorFactory();
				ExecutorFactory queueExecFactory = getContext().getExecutorFactory();
				for (IExecutionListener l : queueExecFactory.getExecutionListeners()) {
					moduleExecFactory.addExecutionListener(l);
				}

				// Execute the module as usual
				Object result = module.execute();
				futureResult.complete(result);
			} catch (EolRuntimeException e) {
				futureResult.completeExceptionally(e);
			}
		});
		return futureResult;
	}

	@Override
	public Object executeImpl() throws EolRuntimeException {
		ExecutionController executionController = getContext().getExecutorFactory().getExecutionController();

		while (!executionController.isTerminated()) {
			try {
				taskQueue.take().run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
