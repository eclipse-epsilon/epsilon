/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.picto.ViewRenderer.ZoomType;
import org.eclipse.epsilon.picto.actions.BackAction;
import org.eclipse.epsilon.picto.actions.CopyToClipboardAction;
import org.eclipse.epsilon.picto.actions.ForwardAction;
import org.eclipse.epsilon.picto.actions.LayersMenuAction;
import org.eclipse.epsilon.picto.actions.LockAction;
import org.eclipse.epsilon.picto.actions.NewPictoViewAction;
import org.eclipse.epsilon.picto.actions.PinAction;
import org.eclipse.epsilon.picto.actions.PrintAction;
import org.eclipse.epsilon.picto.actions.RefreshAction;
import org.eclipse.epsilon.picto.actions.ViewContentsMenuAction;
import org.eclipse.epsilon.picto.actions.ZoomAction;
import org.eclipse.epsilon.picto.browser.BrowserContainer;
import org.eclipse.epsilon.picto.browser.BrowserFindTarget;
import org.eclipse.epsilon.picto.browser.BrowserFunctionExtensionPointManager;
import org.eclipse.epsilon.picto.browser.BrowserScriptExtensionPointManager;
import org.eclipse.epsilon.picto.browser.PictoBrowserFunction;
import org.eclipse.epsilon.picto.browser.PictoBrowserScript;
import org.eclipse.epsilon.picto.preferences.PictoPreferencePage;
import org.eclipse.epsilon.picto.source.PictoSource;
import org.eclipse.epsilon.picto.source.PictoSourceExtensionPointManager;
import org.eclipse.epsilon.picto.source.VerbatimSource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;

public class PictoView extends ViewPart {
	
	public static final String ID = "org.eclipse.epsilon.picto.PictoView";
	
	protected ViewRenderer viewRenderer;
	protected BrowserContainer browserContainer;
	protected IEditorPart editor;
	protected EditorPropertyListener listener = new EditorPropertyListener();
	protected TreeViewer treeViewer;
	protected SashForm sashForm;
	protected int[] sashFormWeights = null;
	protected IEditorPart renderedEditor = null;
	protected boolean locked = false;
	protected boolean pinned = false;
	protected ToggleTreeViewerAction hideTreeAction;
	protected HashMap<IEditorPart, ViewTree> activeViewHistory = new HashMap<>();
	protected ViewTree activeView = null;
	protected PictoSource source = null;
	protected Collection<PictoSource> sources = new PictoSourceExtensionPointManager().getExtensions();
	protected List<PictoBrowserFunction> browserFunctions = new BrowserFunctionExtensionPointManager().getExtensions();
	protected List<PictoBrowserScript> browserScripts = new BrowserScriptExtensionPointManager().getExtensions();
	protected ViewTreeLabelProvider viewTreeLabelProvider;
	protected FilteredViewTree filteredTree;
	protected boolean renderVerbatimSources = false;
	protected ViewTreeSelectionHistory viewTreeSelectionHistory = new ViewTreeSelectionHistory();
	protected int treePosition = SWT.LEFT;
	
	@Override
	public void createPartControl(Composite parent) {
		
		sashForm = new SashForm(parent, SWT.HORIZONTAL);
		
		PatternFilter filter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				ViewTree viewTree = (ViewTree) element;
				return wordMatches(viewTree.getName());
			}
		};
		filteredTree = new FilteredViewTree(sashForm,
			SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER,
			filter, true
		);
		
		treeViewer = filteredTree.getViewer();
		treeViewer.setContentProvider(new ViewTreeContentProvider());
		viewTreeLabelProvider = new ViewTreeLabelProvider();
		treeViewer.setLabelProvider(viewTreeLabelProvider);
		treeViewer.addSelectionChangedListener(event -> {
			
			ViewTree view = ((ViewTree) event.getStructuredSelection().getFirstElement());
			if (view != null) {
				activeViewHistory.put(renderedEditor, view);

				// If the selection happens as a result of undo/redo 
				// we should not execute a new command
				if (viewTreeSelectionHistory.isAutomatedSelection()) return;
				
				viewTreeSelectionHistory.execute(new Command() {
					
					protected List<String> path = null;
					
					@Override
					public void execute() {
						
						if (path == null) {
							path = view.getPath();
							try {
								renderView(view);
							}
							catch (Exception ex) {
								viewRenderer.display(ex);
							}
						}
						else {
							try {
								viewTreeSelectionHistory.setAutomatedSelection(true);
								selectViewTree(path);
								renderView(getViewTree().forPath(path));
							}
							catch (Exception ex) {
								viewRenderer.display(ex);
							}
							finally {
								viewTreeSelectionHistory.setAutomatedSelection(false);
							}
						}
					}
				});
			}
		});
		treeViewer.addDoubleClickListener(event -> filteredTree.clearFilterText());
		
		browserContainer = new BrowserContainer(sashForm, SWT.NONE);
		viewRenderer = new ViewRenderer(createBrowser(browserContainer));
		
		Browser browser = viewRenderer.getBrowser();
		for (PictoBrowserFunction pbf : browserFunctions) {
			new BrowserFunction(browser, pbf.getName()) {
				public Object function(Object[] arguments) {
					pbf.accept(PictoView.this, arguments);
					return null;
				}
			};
		}
		
		for (PictoBrowserScript pbs : browserScripts) {
			browser.execute(pbs.apply(this));
		}
		
		sashFormWeights = new int[] {20, 80};
		sashForm.setSashWidth(2);
		sashForm.setWeights(sashFormWeights);
		
		hideTreeAction = new ToggleTreeViewerAction();
		setTreeViewerVisible(false);
		
		IEditorPart activeEditor = getSite().getPage().getActiveEditor();
		render(activeEditor != null && supports(activeEditor) ? activeEditor : null);

		final PartListener partListener = new PartListener() {
			
			@Override
			public void partActivated(IWorkbenchPartReference partRef) {
				if (locked || pinned) return;
				Display.getCurrent().asyncExec(() -> {
					IWorkbenchPart part = partRef.getPart(false);
					
					if (editor != part && part instanceof IEditorPart) {
						PictoSource source = getSource((IEditorPart) part);
						if (source != null) {
							if (source instanceof VerbatimSource && !renderVerbatimSources) return;
							render((IEditorPart) part);
						}
					}
				});	
			}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				
				IWorkbenchPart workbenchPart = partRef.getPart(false);
				
				if (workbenchPart == PictoView.this) {
					getSite().getPage().removePartListener(this);
				}
				
				if (!(workbenchPart instanceof IEditorPart)) return;
				
				IEditorPart editorPart = (IEditorPart) workbenchPart;
				activeViewHistory.remove(editorPart);
				
				if (locked || pinned) {
					if (editor == editorPart) editor = null;
					return;
				}
				
				if (editor == editorPart) {
					Display.getCurrent().asyncExec(() -> render(null));
				}
			}
		};
		
		IToolBarManager toolbar = getViewSite().getActionBars().getToolBarManager();
		toolbar.add(new BackAction(this));
		toolbar.add(new ForwardAction(this));
		toolbar.add(new Separator());
		toolbar.add(new ZoomAction(ZoomType.IN, viewRenderer));
		toolbar.add(new ZoomAction(ZoomType.ACTUAL, viewRenderer));
		toolbar.add(new ZoomAction(ZoomType.OUT, viewRenderer));
		toolbar.add(new Separator());
		toolbar.add(new LayersMenuAction(this));
		toolbar.add(new Separator());
		toolbar.add(new CopyToClipboardAction(this));
		toolbar.add(new PrintAction(viewRenderer));
		toolbar.add(new RefreshAction(this));
		toolbar.add(new LockAction(this));
		toolbar.add(new Separator());
		toolbar.add(new PinAction(this));
		toolbar.add(new NewPictoViewAction());
		toolbar.add(new Separator());
		toolbar.add(hideTreeAction);
		toolbar.add(new MoveTreeAction());
		toolbar.add(new Separator());
		toolbar.add(new ViewContentsMenuAction(this));

		IMenuManager dropDownMenu = getViewSite().getActionBars().getMenuManager();
		dropDownMenu.add(new ClearViewTreeLabelProviderIconCacheAction());
		dropDownMenu.add(new ToggleVerbatimSourcesAction());
		
		this.getSite().getPage().addPartListener(partListener);

	}

	protected Browser createBrowser(BrowserContainer container) {
		int browserStyle = SWT.NONE;

		if (OperatingSystem.isWindows() &&
				EpsilonCommonsPlugin.getDefault().getPreferenceStore().getBoolean(PictoPreferencePage.INTERNAL_BROWSER_EDGE)) {
			browserStyle = SWT.EDGE;
		}

		return new Browser(browserContainer, browserStyle);
	}

	public void render(IEditorPart editor) {
		if (editor == null) {
			setTreeViewerVisible(false);
			viewRenderer.nothingToRender();
			this.editor = null;
		}
		else {
			if (this.editor != null) {
				this.editor.removePropertyListener(listener);
			}
			this.editor = editor;
			editor.addPropertyListener(listener);
			
			Job job = new TreeRenderingJob("Rendering tree in " + editor.getTitle());
			job.setUser(true);
			job.schedule();
		}
	}
	
	protected boolean treeViewerShouldBeVisible;
	
	protected boolean isTreeViewerVisible() {
		if (sashForm.isDisposed()) return false;
		return sashForm.getSashWidth() > 0;
	}
	
	protected void setTreeViewerVisible(boolean visible) {
		this.treeViewerShouldBeVisible = visible;
		
		visible = treeViewerShouldBeVisible && !hideTreeAction.isChecked();
		
		if (isTreeViewerVisible() && !visible) { // Hide
			sashFormWeights = sashForm.getWeights();
			sashForm.setSashWidth(0);
			if (treePosition == SWT.LEFT) sashForm.setWeights(new int[] {0, 100});
			else sashForm.setWeights(new int[] {100, 0});
		}
		else if (!isTreeViewerVisible() && visible) { // Show
			sashForm.setSashWidth(2);
			sashForm.setWeights(sashFormWeights);
		}
		
		browserContainer.setBorderVisible(visible);
	}
	
	public void selectViewTree(List<String> path) {
		ViewTree viewTree = getViewTree().forPath(path);
		List<ViewTree> treePath = new ArrayList<>();
		while (viewTree != null) {
			treePath.add(0, viewTree);
			viewTree = viewTree.getParent();
		}
		treeViewer.setSelection(new TreeSelection(new TreePath(treePath.toArray())));
		treeViewer.refresh();
	}
	
	public void runInUIThread(RunnableWithException runnable) throws Exception {
		Display.getDefault().syncExec(runnable);
		Exception ex = runnable.getException();
		if (ex != null) throw ex;
	}
	
	protected void setViewTree(ViewTree newViewTree, boolean rerender) throws Exception {
		
		ViewTree viewTree = getViewTree();
		if (viewTree == null || !rerender) {
			viewTree = newViewTree;
			treeViewer.setInput(viewTree);
		}
		else {
			viewTree.ingest(newViewTree);
		}
		
		treeViewer.refresh();
		
		if (rerender) {
			ViewTree selected = (ViewTree) treeViewer.getStructuredSelection().getFirstElement();
			if (selected != null) {
				renderView(selected);
			}
			else {
				viewRenderer.nothingToRender();
			}
		}
		else {
			ViewTree selection = null;
			ViewTree historicalView = activeViewHistory.get(renderedEditor);

			if (historicalView != null) {
				selection = viewTree.forPath(historicalView.getPath());
				if (selection != null)
					selection.setScrollPosition(historicalView.getScrollPosition());
			}
			
			if (selection == null) {
				selection = viewTree.getFirstWithContent();
			}
			
			if (selection != null) {
				treeViewer.setSelection(new TreeSelection(new TreePath(new Object[] {selection})), true);
				treeViewer.refresh();
			}
			else {
				viewRenderer.nothingToRender();
			}
		}
	}
	
	public void renderView(ViewTree view) throws Exception {
		ViewRenderingJob job = new ViewRenderingJob(
			String.format("Rendering view %s in %s", String.join("/", view.getPath()), editor.getTitle()),
			view
		);
		job.setUser(true);
		job.setJobGroup(viewRenderingJobs);

		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				try {
					runInUIThread(new RunnableWithException() {
						@Override
						public void runWithException() throws Exception {
							Browser browser = viewRenderer.getBrowser();
							if (activeView != null) {
								activeView.setScrollPosition(viewRenderer.getScrollPosition());
							}
							activeView = view;
							
							browser.addProgressListener(new ProgressListener() {
								@Override
								public void completed(ProgressEvent event) {
									viewRenderer.setScrollPosition(activeView.getScrollPosition());
									browser.removeProgressListener(this);
								}

								@Override
								public void changed(ProgressEvent event) {}
							});
							
							if (job.content == null) {
								viewRenderer.nothingToRender();
							} else {
								viewRenderer.display(job.content.getText());
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * Note: we want to cancel the individual jobs and not the job group itself,
		 * as we want to continue executing future jobs.
		 */
		for (Job otherJob : viewRenderingJobs.getActiveJobs()) {
			otherJob.cancel();
		}
		job.schedule();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (source != null) {
			source.dispose();
		}
		if (editor != null) {
			editor.removePropertyListener(listener);
		}
	}

	@Override
	public void setFocus() {
		viewRenderer.getBrowser().setFocus();
	}

	/**
	 * Runs the EGX script in the background, populates the tree, and shows the initial text.
	 */
	class TreeRenderingJob extends Job {
		private TreeRenderingJob(String name) {
			super(name);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				PictoSource newSource = getSource(editor);
				if (source != null) source.dispose();
				source = newSource;

				boolean rerender = renderedEditor == editor;
				renderedEditor = editor;
				if (!rerender) viewTreeSelectionHistory = new ViewTreeSelectionHistory();

				final ViewTree viewTree = source.getViewTree(editor);

				runInUIThread(new RunnableWithException() {
					@Override
					public void runWithException() throws Exception {
						if (viewTree.getChildren().isEmpty()) {
							if (rerender) viewTree.setScrollPosition(viewRenderer.getScrollPosition());
							renderView(viewTree);
						}
						else {
							setViewTree(viewTree, rerender);
						}
						setTreeViewerVisible(!viewTree.getChildren().isEmpty());
					}
				});
			}
			catch (Exception ex) {
				try { 
					runInUIThread(new RunnableWithException() {
						@Override
						public void runWithException() throws Exception {
							setTreeViewerVisible(false);
							renderView(new ViewTree(viewRenderer.getVerbatim(ex.getMessage()), "html"));
							EpsilonConsole.getInstance().getErrorStream().print(ex.getMessage());
						}
					});
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				LogUtil.log(ex);
			}

			return Status.OK_STATUS;
		}
	}

	/**
	 * Renders a specific view (whether from loading Picto, or from selecting a tree item).
	 */
	class ViewRenderingJob extends Job {
		private final ViewTree view;
		private ViewContent content;

		private ViewRenderingJob(String name, ViewTree view) {
			super(name);
			this.view = view;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			// Check if one of the source contents of the view is active
			for (Iterator<ViewContent> contentIterator = view.getContents(PictoView.this).iterator(); !monitor.isCanceled() && contentIterator.hasNext() && content == null; ) {
				ViewContent next = contentIterator.next();
				if (next.isActive()) {
					content = next.getSourceContent(PictoView.this);
				}
			}
			
			// ... if not, show the final rendered result
			if (content == null && !monitor.isCanceled()) {
				ViewContent viewContent = view.getContent();
				if (viewContent != null && !monitor.isCanceled()) {
					content = viewContent.getFinal(PictoView.this);
				}
			}

			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			} else {
				return Status.OK_STATUS;
			}
		}		
	}

	/**
	 * Ensures we only render one view at a time, and allows us to cancel pending jobs if the selection changes.
	 */
	protected JobGroup viewRenderingJobs = new JobGroup("Picto view rendering jobs", 1, 0);

	class EditorPropertyListener implements IPropertyListener {
		@Override
		public void propertyChanged(Object source, int propId) {
			if (locked) return;
			if (propId == IEditorPart.PROP_DIRTY && !editor.isDirty()) {
				render(editor);
			}
		}
	}
	
	class ToggleTreeViewerAction extends Action {
		public ToggleTreeViewerAction() {
			super("Toggle tree", AS_CHECK_BOX);
			setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/tree.png"));
		}
		
		@Override
		public void run() {
			setTreeViewerVisible(treeViewerShouldBeVisible);
		}
	}
	
	class MoveTreeAction extends Action {
		
		public MoveTreeAction() {
			setText(getText(SWT.RIGHT));
			setImageDescriptor(getImageDescriptor(SWT.RIGHT));
		}
		
		@Override
		public void run() {
			if (treePosition == SWT.LEFT) {
				browserContainer.moveAbove(filteredTree);
			}
			else {
				filteredTree.moveAbove(browserContainer);
			}
			
			sashForm.layout(true);
			
			setText(getText(treePosition));
			setImageDescriptor(getImageDescriptor(treePosition));
			treePosition = treePosition == SWT.LEFT ? SWT.RIGHT : SWT.LEFT;
		}
		
		protected String getText(int position) {
			return "Move tree to the " + (position == SWT.LEFT ? "left" : "right");
		}
		
		protected ImageDescriptor getImageDescriptor(int position) {
			return PictoPlugin.getDefault().getImageDescriptor("icons/tree_" + (position == SWT.LEFT ? "left" : "right") + ".png");
		}
	}
	
	class ClearViewTreeLabelProviderIconCacheAction extends Action {
		public ClearViewTreeLabelProviderIconCacheAction() {
			super("Clear tree icon cache");
		}
		
		@Override
		public void run() {
			viewTreeLabelProvider.clearIconCache();
		}
	}
	
	class ToggleVerbatimSourcesAction extends Action {
		public ToggleVerbatimSourcesAction() {
			super("Render verbatim sources", AS_CHECK_BOX);
			renderVerbatimSources = EpsilonCommonsPlugin.getDefault().getPreferenceStore().getBoolean(PictoPreferencePage.PROPERTY_RENDER_VERBATIM);
			setChecked(renderVerbatimSources);
		}
		
		@Override
		public void run() {
			renderVerbatimSources = this.isChecked();
		}
	}
	
	
	protected boolean supports(IEditorPart editorPart) {
		return getSource(editorPart) != null;
	}
	
	protected PictoSource getSource(IEditorPart editorPart) {
		for (PictoSource source : sources) {
			if (source.supports(editorPart)) {
				return source;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @since 2.2
	 */
	public PictoSource getSource() {
		return getSource(editor);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
	    if (IFindReplaceTarget.class.equals(adapter)) {
	    	return (T) new BrowserFindTarget(viewRenderer.getBrowser());
	    }
	    return super.getAdapter(adapter);
	}
	
	public IEditorPart getEditor() {
		return editor;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public ViewTree getActiveView() {
		return activeView;
	}
	
	public ViewTree getViewTree() {
		return (ViewTree) treeViewer.getInput();
	}
	
	public ViewRenderer getViewRenderer() {
		return viewRenderer;
	}
	
	public void setViewRenderer(ViewRenderer viewRenderer) {
		this.viewRenderer = viewRenderer;
	}
	
	public ViewTreeSelectionHistory getViewTreeSelectionHistory() {
		return viewTreeSelectionHistory;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

}
