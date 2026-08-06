package org.eclipse.epsilon.examples.picto.headless;

import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewRenderer;
import org.eclipse.epsilon.picto.ViewTree;
import org.eclipse.epsilon.picto.source.StandalonePictoSource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ExportPictoViewsAction implements IObjectActionDelegate {
	
	protected IStructuredSelection selection = null;
	
	@Override
	public void run(IAction action) {
		System.out.println(selection.getFirstElement());
		
		final IFile selectedFile = (IFile) selection.getFirstElement();
		PictoView picto = new PictoView();
		picto.setViewRenderer(new ViewRenderer(null));
		
		StandalonePictoSource source = new StandalonePictoSource() {
			@Override
			protected IFile getFile(IEditorPart editorPart) {
				return selectedFile;
			}
		};
		
		try {
			ViewTree viewTree = source.getViewTree(null);
			visit(viewTree, picto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void visit(ViewTree viewTree, PictoView picto) {
		EpsilonConsole.getInstance().getDebugStream().println(viewTree.getName());
		EpsilonConsole.getInstance().getDebugStream().println("---");
		EpsilonConsole.getInstance().getDebugStream().println(viewTree.getContent().getFinal(picto));
		
		viewTree.getChildren().forEach(child -> { visit(child, picto); });
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

}
