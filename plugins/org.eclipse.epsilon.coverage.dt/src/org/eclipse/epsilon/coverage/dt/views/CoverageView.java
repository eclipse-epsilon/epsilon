/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.views;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.CoverageStatistics;
import org.eclipse.epsilon.coverage.CoverageStatistics.Counter;
import org.eclipse.epsilon.coverage.dt.CoverageDtPlugin;
import org.eclipse.epsilon.coverage.dt.CoverageFiles;
import org.eclipse.epsilon.coverage.dt.CoverageSessionManager;
import org.eclipse.epsilon.coverage.dt.ICoverageSessionListener;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Shows the coverage information of the active coverage session.
 */
public class CoverageView extends ViewPart implements ICoverageSessionListener {

	protected TreeViewer viewer;
	protected ResourceManager resources;
	protected final Map<CoverageSession, CoverageStatistics> statistics = new WeakHashMap<>();
	protected IAction clearAction, saveAction, collapseAllAction, openAction;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		viewer = new TreeViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(false);
		viewer.setUseHashlookup(true);
		resources = new LocalResourceManager(JFaceResources.getResources(), viewer.getControl());
		viewer.setContentProvider(new CoverageContentProvider());

		createColumn("Element", 350, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return getLabel(element);
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof SourceFile) {
					return getFileImage((SourceFile) element);
				}
				Counter counter = getCounter(element);
				if (counter == null) return null;
				if (counter.getTotal() > 0 && counter.getCovered() == counter.getTotal()) return CoverageDtPlugin.getImage(CoverageDtPlugin.IMG_COVERED);
				if (counter.getCovered() == 0) return CoverageDtPlugin.getImage(CoverageDtPlugin.IMG_MISSED);
				return CoverageDtPlugin.getImage(CoverageDtPlugin.IMG_PARTIAL);
			}
		});
		createColumn("Coverage", 90, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Counter counter = getCounter(element);
				if (counter == null || counter.getTotal() == 0) return "";
				return String.format("%.1f %%", counter.getRatio() * 100);
			}
		});
		createColumn("Covered Probes", 110, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Counter counter = getCounter(element);
				return counter == null ? "" : String.valueOf(counter.getCovered());
			}
		});
		createColumn("Missed Probes", 110, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Counter counter = getCounter(element);
				return counter == null ? "" : String.valueOf(counter.getMissed());
			}
		});
		createColumn("Total Probes", 100, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Counter counter = getCounter(element);
				return counter == null ? "" : String.valueOf(counter.getTotal());
			}
		});

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(org.eclipse.jface.viewers.Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof SourceFile && e2 instanceof SourceFile) {
					return getLabel(e1).compareToIgnoreCase(getLabel(e2));
				}
				if (e1 instanceof CoverageElement && e2 instanceof CoverageElement) {
					return Integer.compare(((CoverageElement) e1).getStartLine(), ((CoverageElement) e2).getStartLine());
				}
				return 0;
			}
		});

		viewer.addDoubleClickListener(event -> {
			Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
			open(selected);
		});

		getSite().setSelectionProvider(viewer);
		createActions();

		CoverageSessionManager.getInstance().addListener(this);
		sessionChanged(CoverageSessionManager.getInstance().getSession());
	}

	protected void createColumn(String title, int width, ColumnLabelProvider labelProvider) {
		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(title);
		column.getColumn().setWidth(width);
		column.getColumn().setAlignment(title.equals("Element") ? SWT.LEFT : SWT.RIGHT);
		column.setLabelProvider(labelProvider);
	}

	protected CoverageStatistics getStatistics(CoverageSession session) {
		return statistics.computeIfAbsent(session, s -> new CoverageStatistics());
	}

	protected Counter getCounter(Object element) {
		CoverageSession session = CoverageSessionManager.getInstance().getSession();
		if (session == null || !(element instanceof SourceFile || element instanceof CoverageElement)) return null;
		return getStatistics(session).get((org.eclipse.emf.ecore.EObject) element);
	}

	/**
	 * @return The icon of the editor associated with the file's type (e.g. the EVL icon for
	 * .evl files), or the generic file icon if there is none
	 */
	protected Image getFileImage(SourceFile file) {
		String uri = file.getUri();
		if (uri != null) {
			try {
				String name = URI.createURI(uri).lastSegment();
				ImageDescriptor descriptor = name == null ? null : PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(name);
				if (descriptor != null) return resources.create(descriptor);
			}
			catch (Exception ex) {
				// Fall back to the generic file icon
			}
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	}

	protected String getLabel(Object element) {
		if (element instanceof SourceFile) {
			SourceFile file = (SourceFile) element;
			String name = CoverageFiles.getDisplayName(file);
			return CoverageFiles.isStale(file) ? name + " (modified since coverage was recorded)" : name;
		}
		else if (element instanceof CoverageElement) {
			CoverageElement coverageElement = (CoverageElement) element;
			String label = coverageElement.getLabel();
			if ("StatementBlock".equals(coverageElement.getKind()) && coverageElement.getParent() == null) {
				// The main block of the program (its recorded label is a snippet of its first statement)
				label = "(main)";
			}
			else if (label == null) {
				label = coverageElement.getKind();
			}
			return label + " [line " + coverageElement.getStartLine() + "]";
		}
		return String.valueOf(element);
	}

	protected void open(Object selected) {
		SourceFile sourceFile = null;
		CoverageElement element = null;
		if (selected instanceof SourceFile) {
			sourceFile = (SourceFile) selected;
		}
		else if (selected instanceof CoverageElement) {
			element = (CoverageElement) selected;
			org.eclipse.emf.ecore.EObject container = element;
			while (container != null && !(container instanceof SourceFile)) container = container.eContainer();
			sourceFile = (SourceFile) container;
		}
		if (sourceFile == null) return;

		IFile file = CoverageFiles.getFile(sourceFile);
		if (file == null) {
			MessageDialog.openInformation(getSite().getShell(), "Coverage", "Cannot find " + sourceFile.getUri() + " in the workspace.");
			return;
		}
		try {
			IWorkbenchPage page = getSite().getPage();
			Object editor = IDE.openEditor(page, file, true);
			if (element != null && editor instanceof ITextEditor) {
				ITextEditor textEditor = (ITextEditor) editor;
				IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
				int start = offset(document, element.getStartLine(), element.getStartColumn());
				textEditor.selectAndReveal(start, 0);
			}
		}
		catch (Exception ex) {
			CoverageDtPlugin.log(ex);
		}
	}

	protected int offset(IDocument document, int line, int column) throws BadLocationException {
		int lineOffset = document.getLineOffset(Math.max(0, line - 1));
		int lineLength = document.getLineLength(Math.max(0, line - 1));
		return lineOffset + Math.max(0, Math.min(column, lineLength));
	}

	protected void createActions() {
		CoverageSessionManager manager = CoverageSessionManager.getInstance();
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();

		openAction = new Action("Open Coverage File...") {
			@Override
			public void run() {
				FileDialog dialog = new FileDialog(getSite().getShell(), SWT.OPEN);
				dialog.setFilterExtensions(new String[] {"*." + CoverageResources.FILE_EXTENSION, "*.*"});
				String path = dialog.open();
				if (path == null) return;
				try {
					manager.load(new File(path));
				}
				catch (Exception ex) {
					MessageDialog.openError(getSite().getShell(), "Coverage", "Could not load " + path + ": " + ex.getMessage());
				}
			}
		};
		openAction.setImageDescriptor(CoverageDtPlugin.getImageDescriptor(CoverageDtPlugin.IMG_IMPORT));

		saveAction = new Action("Save Coverage As...") {
			@Override
			public void run() {
				CoverageSession session = manager.getSession();
				if (session == null) return;
				FileDialog dialog = new FileDialog(getSite().getShell(), SWT.SAVE);
				dialog.setFilterExtensions(new String[] {"*." + CoverageResources.FILE_EXTENSION});
				dialog.setOverwrite(true);
				dialog.setFileName((session.getName() == null ? "coverage" : session.getName().replaceAll("[^\\w\\-\\.]", "_")) + "." + CoverageResources.FILE_EXTENSION);
				String path = dialog.open();
				if (path == null) return;
				try {
					CoverageResources.save(session, URI.createFileURI(new File(path).getAbsolutePath()));
					CoverageFiles.refresh(new File(path));
				}
				catch (Exception ex) {
					MessageDialog.openError(getSite().getShell(), "Coverage", "Could not save " + path + ": " + ex.getMessage());
				}
			}
		};
		saveAction.setImageDescriptor(CoverageDtPlugin.getImageDescriptor(CoverageDtPlugin.IMG_EXPORT));

		clearAction = new Action("Clear Coverage") {
			@Override
			public void run() {
				manager.clear();
			}
		};
		clearAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE));

		collapseAllAction = new Action("Collapse All") {
			@Override
			public void run() {
				viewer.collapseAll();
			}
		};
		collapseAllAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));

		IToolBarManager toolbar = getViewSite().getActionBars().getToolBarManager();
		toolbar.add(openAction);
		toolbar.add(saveAction);
		toolbar.add(new Separator());
		toolbar.add(clearAction);
		toolbar.add(new Separator());
		toolbar.add(collapseAllAction);
		updateActions();
	}

	protected void updateActions() {
		boolean available = CoverageSessionManager.getInstance().getSession() != null;
		clearAction.setEnabled(available);
		saveAction.setEnabled(available);
	}

	protected String getSessionLabel(CoverageSession session) {
		StringBuilder label = new StringBuilder(session.getName() == null ? "Coverage session" : session.getName());
		if (session.getTimestamp() != null) {
			label.append(" (").append(DateFormat.getDateTimeInstance().format(session.getTimestamp())).append(")");
		}
		return label.toString();
	}

	@Override
	public void sessionChanged(CoverageSession session) {
		if (viewer == null || viewer.getControl().isDisposed()) return;
		viewer.setInput(session);
		if (session == null) {
			setContentDescription("No coverage session available. Launch a run configuration in Coverage mode or open an ." + CoverageResources.FILE_EXTENSION + " file.");
		}
		else {
			Counter counter = getStatistics(session).get(session);
			setContentDescription(getSessionLabel(session) + " - " +
				String.format("%.1f %%", counter.getRatio() * 100) + " (" + counter + " probes)");
		}
		updateActions();
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void dispose() {
		CoverageSessionManager.getInstance().removeListener(this);
		super.dispose();
	}

	/**
	 * Tree structure: session -> files -> named elements (operations, rules, constraints etc.)
	 * and the main block of the program. Individual statements and expressions are not shown
	 * (so that the tree remains manageable for large programs); their coverage is highlighted
	 * in editors instead.
	 */
	protected static class CoverageContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object input) {
			if (input instanceof CoverageSession) return ((CoverageSession) input).getFiles().toArray();
			return new Object[0];
		}

		@Override
		public Object[] getChildren(Object parent) {
			List<CoverageElement> children = new ArrayList<>();
			if (parent instanceof SourceFile) {
				for (CoverageElement element : ((SourceFile) parent).getElements()) {
					// The main block of the program is shown as "(main)"
					if ("StatementBlock".equals(element.getKind())) children.add(element);
					else collect(element, children);
				}
			}
			else if (parent instanceof CoverageElement) {
				for (CoverageElement child : ((CoverageElement) parent).getChildren()) {
					collect(child, children);
				}
			}
			return children.toArray();
		}

		/**
		 * Adds the element to the result if it is a named element,
		 * or otherwise looks for named elements among its children.
		 */
		protected void collect(CoverageElement element, List<CoverageElement> result) {
			if (isNamed(element)) {
				result.add(element);
			}
			else {
				for (CoverageElement child : element.getChildren()) {
					collect(child, result);
				}
			}
		}

		/**
		 * Named elements (operations, rules, constraints etc.) are labelled
		 * "Kind name" (or just "Kind" if they have no name) by the coverage recorder.
		 */
		protected boolean isNamed(CoverageElement element) {
			String label = element.getLabel(), kind = element.getKind();
			return !element.isProbe() && label != null && kind != null && (label.equals(kind) || label.startsWith(kind + " "));
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof CoverageElement) {
				CoverageElement coverageElement = (CoverageElement) element;
				return coverageElement.getParent() != null ? coverageElement.getParent() : coverageElement.eContainer();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}
}
