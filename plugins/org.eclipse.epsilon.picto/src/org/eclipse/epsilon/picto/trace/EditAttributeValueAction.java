package org.eclipse.epsilon.picto.trace;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.picto.PictoView;

//TODO: Create an extension point specifically for toolbar actions. The class should also return a tooltip and an icon.
public class EditAttributeValueAction extends TraceToolbarAction {

	@Override
	public void accept(PictoView view, Object[] parameters) {
		try {
			if (view.getEditor() instanceof IEditingDomainProvider) {
				EditingDomain editingDomain = ((IEditingDomainProvider) view.getEditor()).getEditingDomain();
				Trace trace = view.getTraceMarkerManager().getTrace(parameters[0].toString());
				String value = new JFaceUserInput(trace.getContext().getPrettyPrinterManager()).prompt(trace.getProperty());
				editingDomain.getCommandStack().execute(new SetAttributeValueCommand((EObject) trace.getElement(), trace.getProperty(), value));
				view.render(view.getEditor()); // Refresh
			}
		}
		catch (Exception ex) {
			LogUtil.log(ex);
		}
	}
	
	@Override
	public String getId() {
		return "edit";
	}
	
}
