/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.dt.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epsilon.common.dt.extensions.ClassBasedExtension;
import org.eclipse.epsilon.common.dt.extensions.IllegalExtensionException;
import org.eclipse.epsilon.common.dt.locators.IModelElementLocator;
import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.dt.EvlPlugin;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.ViewPart;

public class ValidationView extends ViewPart {
	
	protected IEvlModule module;
	protected TableViewer viewer;
	protected Action stopAction;
	protected Action clearAction;
	protected ValidationViewFixer fixer;
	
	class UnsatisfiedConstraintLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public String getColumnText(Object obj, int index) {
			return obj instanceof UnsatisfiedConstraint ? ((UnsatisfiedConstraint) obj).getMessage() : obj.toString();
		}
		
		@Override
		public Image getColumnImage(Object obj, int index) {
			UnsatisfiedConstraint unsatisfiedConstraint = (UnsatisfiedConstraint) obj;
			if (unsatisfiedConstraint.isFixed()) {
				return EvlPlugin.getDefault().createImage("icons/fix.gif");
			}
			else if (unsatisfiedConstraint.getConstraint().isInfo()) {
				return EvlPlugin.getDefault().createImage("icons/info.gif");
			}
			else if (unsatisfiedConstraint.getConstraint().isCritique()) {
				return EvlPlugin.getDefault().createImage("icons/critique.gif");
			}
			else {
				return EvlPlugin.getDefault().createImage("icons/error.gif");
			}
		}
	}

	protected boolean existUnsatisfiedConstraintsToFix() {
		for (UnsatisfiedConstraint constraint : module.getContext().getUnsatisfiedConstraints()) {
			if (constraint.getFixes().size() > 0 && !constraint.isFixed()) {
				return true;
			}
		}
		return false;
	}
	
	public void fix(final IEvlModule module, ValidationViewFixer fixer) {
		
		if (this.fixer != null) {
			setDone(true);
		}
		
		this.fixer = fixer;
		this.module = module;
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			// Needs to be a list, otherwise validation view won't get populated!
			viewer.setInput(new ArrayList<>(module.getContext().getUnsatisfiedConstraints()));
			setDone(!existUnsatisfiedConstraintsToFix());
		});
	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ListContentProvider());
		viewer.setLabelProvider(new UnsatisfiedConstraintLabelProvider());
		// Sort by name alphabetically
		viewer.setComparator(new ViewerComparator((s1, s2) -> s1.compareTo(s2)));
		
		try {
			final List<IModelElementLocator> modelElementLocators = ClassBasedExtension.getImplementations(IModelElementLocator.EXTENSION_POINT, IModelElementLocator.class);
		
			viewer.addDoubleClickListener(event -> {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				final UnsatisfiedConstraint constraint = (UnsatisfiedConstraint) selection.getFirstElement();
				for (final IModelElementLocator modelElementLocator : modelElementLocators) {
					if (modelElementLocator.canLocate(constraint.getInstance())) {
						Job job = new Job("Locating model element") {
					        @Override
					        protected IStatus run(IProgressMonitor monitor) {
					        	modelElementLocator.locate(constraint.getInstance());
					            return Status.OK_STATUS;
					        }
						};
						// Start the Job
						job.schedule();
					}
				}
			});
		}
		catch (IllegalExtensionException ex) {
			LogUtil.log(ex);
		}
		
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(ValidationView.this::fillContextMenu);
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(stopAction);
		manager.add(clearAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		StructuredSelection selection = (StructuredSelection) viewer.getSelection();
		UnsatisfiedConstraint unsatisfiedConstraint = (UnsatisfiedConstraint) selection.getFirstElement();

		if (unsatisfiedConstraint != null && !unsatisfiedConstraint.getFixes().isEmpty()) {
			manager.add(new QuickFixAction(unsatisfiedConstraint));
		}
	}

	protected class QuickFixAction extends Action {
		protected class ComputeFixTitlesJob extends Job {
			protected ComputeFixTitlesJob(String name) {
				super(name);
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final Map<String, FixInstance> fixesByTitle = new TreeMap<>();
				for (FixInstance fixInstance : unsatisfiedConstraint.getFixes()) {
					try {
						fixesByTitle.put(fixInstance.getTitle(), fixInstance);
					} catch (EolRuntimeException e) {
						return Status.error(e.getMessage(), e);
					}
				}
				PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
					showQuickFixDialog(unsatisfiedConstraint, fixesByTitle);
				});
				return Status.OK_STATUS;
			}

			@SuppressWarnings("unchecked")
			protected void showQuickFixDialog(UnsatisfiedConstraint unsatisfiedConstraint, final Map<String, FixInstance> fixesByTitle) {
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(
					getViewSite().getShell(), new MapEntryLabelProvider());

				dialog.setElements(fixesByTitle.entrySet().toArray());
				dialog.setTitle("Select a quick fix");
				dialog.setMultipleSelection(false);
				if (dialog.open() == Window.OK) {
					Entry<String, FixInstance> selected = (Map.Entry<String, FixInstance>) dialog.getFirstResult();
					if (selected != null) {
						// Need to run fix from a non-UI background job (in case it is debugged)
						new RunFixJob("Run fix", selected).schedule();
					}
				}
			}
		}

		protected class RunFixJob extends Job {
			private final Entry<String, FixInstance> selected;

			protected RunFixJob(String name, Entry<String, FixInstance> selected) {
				super(name);
				this.selected = selected;
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					selected.getValue().perform();
					unsatisfiedConstraint.setFixed(true);
					PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
						setDone(!existUnsatisfiedConstraintsToFix());
						viewer.refresh();
					});
				} catch (Exception e) {
					module.getContext().getErrorStream().println(e.toString());
					return Status.error(e.getMessage());
				}
				return Status.OK_STATUS;
			}
		}

		protected class MapEntryLabelProvider extends LabelProvider {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				if (element instanceof Map.Entry) {
					return ((Map.Entry<String, Object>) element).getKey();
				}
				return super.getText(element);
			}
		}

		private final UnsatisfiedConstraint unsatisfiedConstraint;

		public QuickFixAction(UnsatisfiedConstraint unsatisfiedConstraint) {
			super("Quick Fix...");
			this.unsatisfiedConstraint = unsatisfiedConstraint;
		}

		@Override
		public void run() {
			// Need to compute fix titles from a background job (could have a breakpoint)
			new ComputeFixTitlesJob("Compute fix titles").schedule();
		}
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(stopAction);
		manager.add(clearAction);
	}

	private void makeActions() {
		stopAction = new Action() {
			@Override
			public void run() {
				setDone(true);
			}
		};
		stopAction.setText("Stop");
		stopAction.setToolTipText("Finish with performing fixes and resume to the post section of the EVL specification");
		stopAction.setImageDescriptor(EvlPlugin.getDefault().getImageDescriptor("icons/stop.gif"));
		
		clearAction = new Action() {
			@Override
			public void run() {
				Display.getDefault().asyncExec(() -> {
					setDone(true);
					viewer.setInput(Collections.emptyList());
				});
			}
		};
		
		clearAction.setText("Clear");
		clearAction.setToolTipText("Clear the validation view");
		clearAction.setImageDescriptor(EvlPlugin.getDefault().getImageDescriptor("icons/clear.gif"));
		
		stopAction.setEnabled(false);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public boolean isDone() {
		if (fixer == null) return true;
		return fixer.isDone();
	}
	
	protected void setDone(boolean done) {
		stopAction.setEnabled(!done);
		if (fixer != null) fixer.setDone(done);
	}
}
