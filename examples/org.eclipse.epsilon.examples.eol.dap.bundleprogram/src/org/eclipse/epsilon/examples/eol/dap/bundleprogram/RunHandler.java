package org.eclipse.epsilon.examples.eol.dap.bundleprogram;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RunHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new EpsilonJob("Run Hello World").schedule();
		return null;
	}

}
