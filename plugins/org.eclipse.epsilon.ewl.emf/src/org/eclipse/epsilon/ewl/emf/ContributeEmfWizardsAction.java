/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

public class ContributeEmfWizardsAction extends AbstractContributeWizardsAction {
	
	@Override
	protected EditingDomain getEditingDomain() {
		if (targetPart instanceof IEditingDomainProvider) {
			return ((IEditingDomainProvider) targetPart).getEditingDomain();
		} else {
			return null;
		}
	}

	@Override
	protected EObject getEObject(Object selected) {
		if (selected instanceof EObject) {
			return (EObject) selected;
		}
		else {
			return null;
		}
	}

	@Override
	protected WorkbenchPartRefresher getWorkbenchPartRefresher() {
		return new WorkbenchPartRefresher() {
			@Override
			public void refresh() {
				// Do nothing
			}
		};
	}

}
