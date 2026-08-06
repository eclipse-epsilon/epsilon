/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl;

import java.util.List;
import org.eclipse.epsilon.erl.IErlModule;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public interface IEtlModule extends IErlModule {
	
	public List<TransformationRule> getDeclaredTransformationRules();
	
	public List<TransformationRule> getTransformationRules();
	
	@Override
	public default IEtlContext getContext() {
		return (IEtlContext) ((IErlModule)this).getContext();
	}
}
