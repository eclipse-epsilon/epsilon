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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.coverage.CoverageElementTypes;
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
import org.eclipse.epsilon.eol.dom.StatementBlock;
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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
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
	protected IAction clearAction, saveAction, collapseAllAction;

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
			if (CoverageElementTypes.isOfType(coverageElement, StatementBlock.class) && coverageElement.getParent() == null) {
				// The main block of the program (its recorded label is a snippet of its first statement)
				label = "(main)";
			}
			else if (label == null) {
				label = CoverageElementTypes.getSimpleName(coverageElement);
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
			LogUtil.log(ex);
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

		saveAction = new Action("Save Coverage in Workspace...") {
			@Override
			public void run() {
				CoverageSession session = manager.getSession();
				if (session != null) saveInWorkspace(session);
			}
		};
		saveAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT));

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
		toolbar.add(clearAction);
		toolbar.add(new Separator());
		toolbar.add(saveAction);
		toolbar.add(new Separator());
		toolbar.add(collapseAllAction);
		updateActions();
	}

	protected void updateActions() {
		boolean available = CoverageSessionManager.getInstance().getSession() != null;
		clearAction.setEnabled(available);
		saveAction.setEnabled(available);
	}

	/**
	 * Asks for a file in the workspace and saves the session to it
	 */
	protected void saveInWorkspace(CoverageSession session) {
		String extension = "." + CoverageResources.FILE_EXTENSION;
		SaveAsDialog dialog = new SaveAsDialog(getSite().getShell());
		dialog.setTitle("Save Coverage");
		String fileName = getProposedFileName(session) + extension;
		IContainer container = getDefaultContainer(session);
		if (container != null) {
			dialog.setOriginalFile(container.getFile(IPath.fromOSString(fileName)));
		}
		else {
			dialog.setOriginalName(fileName);
		}
		if (dialog.open() != Window.OK || dialog.getResult() == null) return;

		IPath path = dialog.getResult();
		if (!extension.substring(1).equalsIgnoreCase(path.getFileExtension())) {
			path = path.addFileExtension(extension.substring(1));
		}
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		try {
			if (file.getLocation() == null) {
				throw new IllegalStateException(path + " is not stored in the local file system");
			}
			File target = file.getLocation().toFile();
			target.getParentFile().mkdirs();
			// Saved through the file system location (not a platform:/resource URI)
			// so that the URIs of the source files can be stored relative to it
			CoverageResources.save(session, URI.createFileURI(target.getAbsolutePath()));
			// Refresh the nearest existing ancestor so that any new folders appear too
			IContainer parent = file.getParent();
			while (!parent.exists() && parent.getParent() != null) parent = parent.getParent();
			parent.refreshLocal(IResource.DEPTH_INFINITE, null);
		}
		catch (Exception ex) {
			LogUtil.log("Could not save " + path + ": " + ex.getMessage(), ex, true);
		}
	}

	/**
	 * @return The project of the first source file of the session that is in the workspace,
	 * or null if there is none
	 */
	protected IContainer getDefaultContainer(CoverageSession session) {
		for (SourceFile sourceFile : session.getFiles()) {
			IFile file = CoverageFiles.getFile(sourceFile);
			if (file != null) return file.getProject();
		}
		return null;
	}

	/**
	 * @return A file name for saving the session, without an extension. Dots are replaced
	 * too, so that sessions named after launch configurations (e.g. "program.egx") don't
	 * produce file names that look like they have a different extension.
	 */
	protected String getProposedFileName(CoverageSession session) {
		String name = session.getName() == null ? "" : session.getName().replaceAll("[^\\w\\-]", "_");
		if (name.isEmpty()) name = "coverage";
		return name;
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
					if (CoverageElementTypes.isOfType(element, StatementBlock.class)) children.add(element);
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
			String label = element.getLabel(), kind = CoverageElementTypes.getSimpleName(element);
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
