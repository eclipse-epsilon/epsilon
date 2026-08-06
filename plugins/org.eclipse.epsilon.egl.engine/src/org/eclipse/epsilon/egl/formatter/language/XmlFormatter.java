/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.formatter.language;

public class XmlFormatter extends LanguageFormatter {

	/*
	 * A start tag begins with < and ends with >
	 * The middle contains either a single character (not /)   -- [^/]
	 * OR a string that does not contain a close angle bracket -- [^>]*
	 *    AND does not start with / nor ! nor ?                -- [^?!/][^>]*
	 *    AND does not end with /                              -- [^!/][^>]*[^/]
	 * 
	 * This pattern matches tags such as: <html>, <p>, <body class="main">
	 * But not: <br/>, </html>
	 */
	private static final String startTagPattern = "<([^/]|([^?!/][^>]*[^/]))>";
	
	/*
	 * An end tag begins with </ and ends with >
	 * The middle contains any string that does not contain a close angle bracket
	 * 
	 * This pattern matches tags such as: </html>, </p>, </body>
	 */
	private static final String endTagPattern = "</[^>]*>";
	
	
	public XmlFormatter() {
		super(startTagPattern, endTagPattern);
	}
}
