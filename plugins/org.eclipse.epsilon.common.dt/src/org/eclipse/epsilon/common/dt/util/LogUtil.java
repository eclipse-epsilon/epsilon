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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class LogUtil {
	
	public static void log(int severity, int code, String message, Throwable ex) {
		EpsilonCommonsPlugin.getDefault().getLog().log(new Status(severity, "org.eclipse.epsilon.common.dt", code, message, ex));
	}
	
	public static void logInfo(Object message) {
		logInfo(message.toString(), false);
	}
	
	public static void logInfo(final Object message, final boolean openInfoDialog) {
		Display.getDefault().asyncExec(() -> {
			EpsilonCommonsPlugin.getDefault().getLog().log(new Status(IStatus.INFO, "org.eclipse.epsilon.common.dt", message.toString()));
			if (openInfoDialog) {
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Information", message.toString());
			}
		});
	}
	
	public static void log(String message, Throwable ex) {
		log(IStatus.ERROR, 0, message, ex);
	}
	
	public static void log(final String message, final Throwable ex, final boolean openErrorDialog) {
		Display.getDefault().asyncExec(() -> {
			log(message, ex);
			if (openErrorDialog) {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", message + "\r\nPlease check the error log for further information");
			}
		});
	}
	
	public static void log(Throwable ex) {
		log(ex.getMessage(), ex);
	}
}
