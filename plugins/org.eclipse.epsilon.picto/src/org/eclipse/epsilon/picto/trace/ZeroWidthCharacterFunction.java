package org.eclipse.epsilon.picto.trace;

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.browser.PictoBrowserFunction;

public class ZeroWidthCharacterFunction implements PictoBrowserFunction {
	
	@Override
	public Object run(PictoView view, Object[] parameters) {
		return view.getTraceMarkerManager().getZeroWidthCharacter();
	}
	
	@Override
	public String getName() {
		return "getZeroWidthCharacter";
	}
}
