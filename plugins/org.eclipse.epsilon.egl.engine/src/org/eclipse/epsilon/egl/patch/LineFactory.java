/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.patch;

/**
 * 
 * @since 1.6
 */
public class LineFactory {
	
	public Line createLine(String text, int number) {
		if (text.trim().contentEquals("---")) {
			return new Line(LineType.REMOVE_WILDCARD, "", number);
		}
		else if (text.startsWith("-")) {
			return new Line(LineType.REMOVE, text.substring(1), number);
		}
		else if (text.startsWith("+")) {
			return new Line(LineType.INSERT, text.substring(1), number);
		}
		else if (text.startsWith("#")) {
			return new Line(LineType.COMMENT, text.substring(1), number);
		}
		else if (text.trim().contentEquals("...")) {
			return new Line(LineType.KEEP_WILDCARD, "", number);
		}
		else return new Line(LineType.REGULAR, text, number);
	}
	
}
