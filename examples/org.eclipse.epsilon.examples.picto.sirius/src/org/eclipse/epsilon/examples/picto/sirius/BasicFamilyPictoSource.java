package org.eclipse.epsilon.examples.picto.sirius;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.epsilon.picto.dom.PictoFactory;
import org.eclipse.epsilon.picto.source.SimpleSource;
import org.eclipse.sirius.diagram.impl.DSemanticDiagramImpl;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.ui.IEditorPart;

public class BasicFamilyPictoSource extends SimpleSource {

	private static final String EGX_SCRIPT = "platform:/plugin/org.eclipse.epsilon.examples.picto.sirius/resources/basicfamily.egx";
	
	@Override
	protected String getFormat() {
		return "egx";
	}

	@Override
	protected String getFileExtension() {
		return "basicfamily";
	}
	
	@Override
	protected Picto getRenderingMetadata(IEditorPart editorPart) {
		Picto metadata = PictoFactory.eINSTANCE.createPicto();
		metadata.setFormat(getFormat());
		metadata.setTransformation(EGX_SCRIPT);
		// pass the root element of the Sirius diagram to EGX via currentElement variable
		metadata.getParameters().add(createParameter("currentElement", getRootOfDiagram(editorPart)));
		return metadata;
	}
	
	@Override
	public boolean supports(IEditorPart editorPart) {
		return isSiriusEditor(editorPart);
	}
	
	@Override
	protected boolean supportsEditorType(IEditorPart editorPart) {
		return isSiriusEditor(editorPart);
	}
	
	@Override
	protected IFile getFile(IEditorPart editorPart) {
		if (isSiriusEditor(editorPart)) {
			Resource r = getRootOfDiagram(editorPart).eResource();
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(r.getURI().toPlatformString(true)));
		}
		return null;
	}
	
	@Override
	protected Resource getResource(IEditorPart editorPart) {
		if (isSiriusEditor(editorPart)) {
			return getRootOfDiagram(editorPart).eResource();
		}
		return null;
	}
	
	protected EObject getRootOfDiagram(IEditorPart editorPart) {
		if (isSiriusEditor(editorPart)) {
			DDiagramEditor editor = (DDiagramEditor) editorPart;
			DSemanticDiagramImpl diagram = (DSemanticDiagramImpl) editor.getRepresentation();
			return diagram.getTarget();
		}
		return null;
	}
	
	protected boolean isSiriusEditor(IEditorPart editorPart) {
		return editorPart instanceof DDiagramEditor;
	}

}
