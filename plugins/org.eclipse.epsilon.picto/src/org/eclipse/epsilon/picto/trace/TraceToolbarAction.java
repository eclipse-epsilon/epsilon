package org.eclipse.epsilon.picto.trace;

import org.eclipse.epsilon.picto.browser.PictoBrowserFunction;

public abstract class TraceToolbarAction implements PictoBrowserFunction {
	
	public abstract String getId();
	
	@Override
	public String getName() {
		return "picto_toolbar_" + getId();
	}
}
