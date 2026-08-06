/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl;

import java.util.List;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.erl.IErlModule;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public interface IEgxModule extends IErlModule {
	
	List<GenerationRule> getDeclaredGenerationRules();
	
	List<GenerationRule> getGenerationRules();
	
	default EglTemplateFactory getTemplateFactory() {
		return getContext().getTemplateFactory();
	}
	
	default void setTemplateFactory(EglTemplateFactory templateFactory) {
		getContext().setTemplateFactory(templateFactory);
	}
	
	@Override
	default IEgxContext getContext() {
		return (IEgxContext) ((IErlModule)this).getContext();
	}
}
