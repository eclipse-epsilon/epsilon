package org.eclipse.epsilon.picto.watermarking;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.browser.PictoBrowserFunction;

public class EditBrowserFunction implements PictoBrowserFunction {

	@Override
	public void accept(PictoView view, Object[] parameters) {
		try {
			if (view.getEditor() instanceof IEditingDomainProvider) {
				EditingDomain editingDomain = ((IEditingDomainProvider) view.getEditor()).getEditingDomain();
				WatermarkTrace trace = view.getWatermarkTracer().getTrace(parameters[0].toString());
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
	public String getName() {
		return "picto_toolbar_edit";
	}

}
