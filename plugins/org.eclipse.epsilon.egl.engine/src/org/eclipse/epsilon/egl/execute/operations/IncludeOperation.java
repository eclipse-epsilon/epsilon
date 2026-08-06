/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.operations;

import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.output.IOutputBuffer;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;

public class IncludeOperation extends SimpleOperation {

	@Override
	public Object execute(Object source, List<?> parameters, IEolContext context, ModuleElement ast) throws EolRuntimeException {
				
		Variable templateFactoryVariable = context.getFrameStack().get("TemplateFactory");
		EglTemplateFactory templateFactory = (EglTemplateFactory) templateFactoryVariable.getValue();
		
		Variable outVariable = context.getFrameStack().get("out");
		
		IOutputBuffer out = (IOutputBuffer) outVariable.getValue();
		
		String templatePath = (String) parameters.get(0);
		EglTemplate template = templateFactory.load(templatePath);
		
		try {
			String output = template.process();
			out.println(output);
		}
		catch (EglRuntimeException ex) {
			throw ex;
		}

		return null;
		
	}

}
