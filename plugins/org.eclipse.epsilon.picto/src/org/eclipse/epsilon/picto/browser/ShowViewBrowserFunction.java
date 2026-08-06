/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.browser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.swt.widgets.Display;

public class ShowViewBrowserFunction implements PictoBrowserFunction {

	@Override
	public void accept(PictoView view, Object[] args) {
		if (args.length == 1 && args[0] instanceof Object[]) {
			Object[] pathArray = (Object[]) args[0];
			String[] pathStringArray = Arrays.copyOf(pathArray, pathArray.length, String[].class);
			List<String> path = new ArrayList<>(Arrays.asList(pathStringArray));
			path.add(0, view.getViewTree().getName());

			/*
			 * Run this in a separate thread to avoid deadlocking the browser, which is in
			 * the middle of processing an event.
			 */
			Display.getCurrent().asyncExec(() -> view.selectViewTree(path));
		}
		throw new RuntimeException();
	}

	@Override
	public String getName() {
		return "showView";
	}
}
