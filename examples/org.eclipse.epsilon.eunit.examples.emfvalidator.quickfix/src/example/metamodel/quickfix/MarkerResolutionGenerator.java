package example.metamodel.quickfix;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.epsilon.eunit.examples.emfvalidator.Model;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Example of a Java-based resolution generator for an EObject.
 */
public class MarkerResolutionGenerator implements IMarkerResolutionGenerator {

	protected class SetToHighValueResolution implements EObjectResolution {
		/**
		 * Simple command for fixing the issue. Ideally, we'd use the EMF
		 * ChangeDescription and ChangeRecorder to allow for undo/redo.
		 * For an example of this, check the ExecuteEvlFixCommand class
		 * in the Epsilon source code. 
		 */
		protected class SetToHighValueCommand implements Command {
			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			public void execute() {
				target.setPositiveValue(999);
			}

			@Override
			public boolean canUndo() {
				return false;
			}

			@Override
			public void undo() {
				// stub
			}

			@Override
			public void redo() {
				// stub
			}

			@Override
			public Collection<?> getResult() {
				return null;
			}

			@Override
			public Collection<?> getAffectedObjects() {
				return Collections.singleton(target);
			}

			@Override
			public String getLabel() {
				return "Set to 999";
			}

			@Override
			public String getDescription() {
				return getLabel();
			}

			@Override
			public void dispose() {
				// nothing to do
			}

			@Override
			public Command chain(Command command) {
				return null;
			}
		}

		private Model target;

		@Override
		public String getLabel() {
			return "Set to 999";
		}

		@Override
		public void run(IMarker marker) {
			target = (Model) resolve(marker);
			getEditingDomain(marker).getCommandStack().execute(new SetToHighValueCommand());
			target.eResource().setModified(true);
			try {
				marker.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public EObject getTarget() {
			return target;
		}
	}

	interface EObjectResolution extends IMarkerResolution {
		/**
		 * Returns the EObject that this resolution fixed. Should be
		 * called only after running the resolution. Needed for testing,
		 * as there may be a delay between the fix being applied and the
		 * fixed model being saved to disk, for reloading by EUnit. 
		 */
		EObject getTarget();
	}

	protected boolean canResolve(IMarker marker) {
		try {
			if (EValidator.MARKER.equals(marker.getType()) || marker.isSubtypeOf(EValidator.MARKER)) {
				// Check the constraint name, to limit the scope of this resolution generator
				return marker.getAttribute("message", "").contains("PositiveValue");
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (!canResolve(marker)) return new IMarkerResolution[0];

		if (resolve(marker) == null) {
			return new IMarkerResolution[0];
		}

		return new IMarkerResolution[] {
			new SetToHighValueResolution()
		};
	}

	protected EObject resolve(IMarker marker) {
		EObject self = null;
		ResourceSet resourceSet = getEditingDomain(marker).getResourceSet();
		for (Resource resource : resourceSet.getResources()) {
			String relativeElementId = getRelativeElementId(marker);
			EObject temp = resource.getEObject(relativeElementId);
			if (temp != null) {
				self = temp;
				break;
			}
		}
		return self;
	}

	protected EditingDomain getEditingDomain(IMarker marker) {	
		final String filePath = getElementResourceLocation(marker);
		
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
		String editorId = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filePath).getId();

		// The Display call is needed for EUnit, as it does not run from the UI thread.
		return Display.getDefault().syncCall(() -> {
			try {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IEditorPart part = window.getActivePage().openEditor(new FileEditorInput(file), editorId, false);
				return getEditingDomain(part);
			} catch (PartInitException e1) {
				return null;
			}
		});
	}
	
	protected EditingDomain getEditingDomain(IEditorPart editor) {
		if (editor instanceof IEditingDomainProvider) {
			return ((IEditingDomainProvider) editor).getEditingDomain();
		}
		else {
			return null;
		}		
	}

    protected String getElementResourceLocation(IMarker marker) {
        String absoluteElementId = getAbsoluteElementId(marker);
		final String location = absoluteElementId.split("#")[0];

        URI uri = URI.createURI(location);
        if (uri.isPlatform()) {
               return uri.toPlatformString(true);
        } else {
               return location;
        }
    }

    protected String getRelativeElementId(IMarker marker) {
		String[] parts = getAbsoluteElementId(marker).split("#");
		return parts[1];
	}

	protected String getAbsoluteElementId(IMarker marker) {
		return marker.getAttribute("uri", "");
	}

	/*
	 * We need to use this to produce the markers, so it has the EMF diagnostics
	 * type that we use from regular resolution generators.
	 */
	public MarkerHelper getMarkerGenerator() {
		return new ValidateAction.EclipseResourcesUtil();
	}

}
