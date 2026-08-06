/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtil {

	public static void copyToClipboard(String str, boolean asHtml){
		
		 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		 if (asHtml) {
			 clipboard.setContents(new HtmlTransferable(str),null);
		 }
		 else {
			 clipboard.setContents(new StringSelection(str), null);
		 }
	}

}
