/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.egl.test.acceptance.extensibility;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;

public class CountingTemplateFactory extends EglFileGeneratingTemplateFactory {

	@Override
	protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
		return new CountingTemplate(spec, getContextForNewTemplate(), getOutputRootOrRoot());
	}	
}
