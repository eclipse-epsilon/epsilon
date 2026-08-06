/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.listener;

import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestListener;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;
import org.eclipse.epsilon.eunit.dt.ui.EUnitRunnerView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * Simple test listener which is responsible for displaying the EUnit view.
 * After setting it up and activating the view, this listener delegates all
 * notifications to the view.
 *
 * This test listener is also responsible for adding launches to the EUnit
 * history: we can't add them from the EUnitPlugin launch listener, as it'd try
 * to record all Ant launches, regardless of whether EUnit was run or not.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class ShowEUnitViewTestListener implements EUnitTestListener {

	private final Display display = Display.getDefault();
	private EUnitRunnerView eunitView;

	private void showEUnitView() {
		if (PlatformUI.isWorkbenchRunning()) {
			display.syncExec(new Runnable(){
				public void run() {
					IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						eunitView = (EUnitRunnerView)activePage.showView(EUnitRunnerView.ID);
					} catch (PartInitException e) {
						EUnitPlugin.getDefault().logException(e);
					}
				}
			});
		}
	}

	@Override
	public void beforeCase(EUnitModule module, EUnitTest test) {
		if (test.getParent() == null) {
			final EUnitPlugin plugin = EUnitPlugin.getDefault();
			final ILaunch lastLaunch = plugin.getLastLaunch();
			plugin.getHistory().addLaunch(lastLaunch, module);

			@SuppressWarnings("rawtypes")
			final List selOps = plugin.getSelectedOperations(lastLaunch);
			try {
				module.setSelectedOperations(selOps);
			} catch (EolRuntimeException e) {
				plugin.logException(e);
			}

			showEUnitView();
		}

		if (eunitView != null) {
			eunitView.beforeCase(module, test);
		}
	}

	@Override
	public void afterCase(EUnitModule module, EUnitTest test) {
		if (eunitView != null) {
			eunitView.afterCase(module, test);
		}
	}

}
