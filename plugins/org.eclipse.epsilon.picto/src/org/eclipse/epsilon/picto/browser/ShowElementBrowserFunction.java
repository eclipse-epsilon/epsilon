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

import org.eclipse.epsilon.picto.PictoView;

public class ShowElementBrowserFunction implements PictoBrowserFunction {

	@Override
	public void accept(PictoView view, Object[] args) {
		if (args.length == 2) {
			String id = args[0] + "";
			String uri = args[1] + "";
			view.getSource().showElement(id, uri, view.getEditor());
		}
		throw new RuntimeException();
	}

	@Override
	public String getName() {
		return "showElement";
	}
}
