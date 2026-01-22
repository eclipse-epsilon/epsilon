package org.eclipse.epsilon.picto.browser;

import org.eclipse.epsilon.picto.PictoView;

public class SysoutBrowserFunction implements PictoBrowserFunction {

	@Override
	public Object run(PictoView pictoView, Object[] parameters) {
		for (Object paramerter : parameters) {
			System.out.print(paramerter);
		}
		System.out.println();
		return null;
	}

	@Override
	public String getName() {
		return "sysout";
	}

}
