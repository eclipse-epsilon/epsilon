/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;
import org.eclipse.epsilon.internal.eunit.dt.history.EUnitHistory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchActionConstants;

/**
 * Action which displays a drop down menu listing the history of all EUnit launches
 * since Eclipse was started. When the user picks one, the input of the provided viewer
 * will be changed to it.
 *
 * @author Antonio García-Domínguez
 */
class HistoryDropDownAction extends Action implements IMenuCreator, IMenuListener {
	private Menu menu;
	private EUnitHistory history;
	private final EUnitRunnerView view;

	private final class HistoryAction extends Action {
		private ILaunch launch;

		public HistoryAction(ILaunch launch) throws EolRuntimeException {
			this.launch = launch;
			setText(launch);
			setChecked(launch == history.getCurrentLaunch());
		}

		private void setText(ILaunch launch) throws EolRuntimeException {
			final List<EUnitModule> modules = history.getModules(launch);
			final EUnitTestResultType result = history.getResult(launch);
			final EUnitModule firstModule = modules.get(0);

			final Date testStartTime
				= new Date(firstModule.getSuiteRoot().getStartWallclockTime());
			final DateFormat dateFormat = new SimpleDateFormat();
			final StringBuffer sbuf = new StringBuffer();

			sbuf.append(result.toString());
			sbuf.append(": ");
			sbuf.append(EUnitModule.getBasename(firstModule));
			sbuf.append(' ');
			sbuf.append(dateFormat.format(testStartTime));

			setText(sbuf.toString());
		}

		public void run() {
			history.setCurrentLaunch(launch);
			view.setCurrentModules(history.getModules(launch));
		}
	}

	public HistoryDropDownAction(EUnitHistory history, EUnitRunnerView view) {
		super("View History", Action.AS_DROP_DOWN_MENU);
		this.history = history;
		this.view = view;

		setImageDescriptor(EUnitPlugin.getImageDescriptor("icons/elcl16/history_list.gif"));
		setMenuCreator(this);
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	@Override
	public Menu getMenu(Control parent) {
		MenuManager manager = new MenuManager();
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(this);
		menu = manager.createContextMenu(parent);
		return menu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		try {
			for (ILaunch launch : history.getAllLaunches().keySet()) {
				HistoryAction historyAction = new HistoryAction(launch);
				manager.add(historyAction);
			}
		} catch (EolRuntimeException e) {
			EUnitPlugin.getDefault().logException(e);
		}
		manager.add(new Separator());
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	@Override
	public void runWithEvent(Event ev) {
		if (ev.type == SWT.Selection && ev.detail != SWT.ARROW && ev.widget instanceof ToolItem) {
			// Act as if it was a click on the drop down part
			// Ugly, but saves us from repeating the internal menu display code
			final ToolItem toolItem = (ToolItem)ev.widget;
			final ToolBar toolBar = toolItem.getParent();
			ev.x = toolItem.getBounds().x;
			ev.y = toolBar.getBounds().height;
			ev.detail = SWT.ARROW;

			for (Object o : ev.widget.getListeners(SWT.Selection)) {
				if (o instanceof Listener) {
					((Listener) o).handleEvent(ev);
				}
			}
		}
		else {
			super.runWithEvent(ev);
		}
	}
}
