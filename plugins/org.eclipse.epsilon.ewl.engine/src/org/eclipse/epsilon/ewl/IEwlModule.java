/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl;

import java.util.List;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.ewl.dom.Wizard;
import org.eclipse.epsilon.ewl.execute.WizardInstance;
import org.eclipse.epsilon.ewl.execute.context.IEwlContext;

public interface IEwlModule extends IEolModule {
	
	List<WizardInstance> getWizardsFor(Object self) throws EolRuntimeException;
	
	@Override
	default IEwlContext getContext() {
		return (IEwlContext) ((IEolModule)this).getContext();
	}
	
	/**
	 * 
	 * @return
	 * @since 2.2
	 */
	List<Wizard> getWizards();
	
}
