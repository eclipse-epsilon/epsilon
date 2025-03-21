package org.eclipse.epsilon.examples.eol.dap.bundleprogram;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class DebugHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EpsilonJob job = new EpsilonJob("Debug Hello World");
		job.setDebug(true);
		job.schedule();
		return null;
	}

}
