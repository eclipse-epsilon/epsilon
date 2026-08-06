/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.epsilon.egl.doc.egl;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class Egl {

	private Egl() {}
	
	private static URI BASE    = null;
	private static URI TO_DOT  = null;
	private static URI TO_HTML = null;
	
	private static URI SCREEN_CSS   = null;
	private static URI UP_ARROW_IMG = null;
	private static URI CONTROLS_JS  = null;
	
	
	public static URI getBase() throws URISyntaxException {
		if (BASE == null) {
			final String clazz = Egl.class.getResource("Egl.class").toURI().toString();
			BASE = new URI(clazz.substring(0, clazz.lastIndexOf('/')+1));
		}
			
		return BASE;
	}
	
	public static URI getToDotProgram() throws URISyntaxException {
		if (TO_DOT == null) {
			TO_DOT = Egl.class.getResource("Emf2Dot.egl").toURI();
		}
			
		return TO_DOT;
	}
	
	public static URI getToHtmlProgram() throws URISyntaxException {
		if (TO_HTML == null) {
			TO_HTML = Egl.class.getResource("Emf2Html.egl").toURI();
		}
			
		return TO_HTML;
	}

	
	public static URI getScreenCss() throws URISyntaxException {
		if (SCREEN_CSS == null) {
			SCREEN_CSS = Egl.class.getResource("common/css/screen.css").toURI();
		}
			
		return SCREEN_CSS;
	}
	
	public static URI getUpArrow() throws URISyntaxException {
		if (UP_ARROW_IMG == null) {
			UP_ARROW_IMG = Egl.class.getResource("common/img/arrow_up.png").toURI();
		}
			
		return UP_ARROW_IMG;
	}
	
	public static URI getControls() throws URISyntaxException {
		if (CONTROLS_JS == null) {
			CONTROLS_JS = Egl.class.getResource("common/scripts/controls.js").toURI();
		}
			
		return CONTROLS_JS;
	}
}
