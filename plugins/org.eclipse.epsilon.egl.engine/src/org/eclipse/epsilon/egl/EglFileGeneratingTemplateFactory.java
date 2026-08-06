/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl;

import java.net.URI;
import java.nio.file.Path;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;

public class EglFileGeneratingTemplateFactory extends EglTemplateFactory {
	
	protected URI    outputRoot;
	protected String outputRootPath;
	
	public EglFileGeneratingTemplateFactory() {
		super();
	}
	
	public EglFileGeneratingTemplateFactory(IEglContext context) {
		super(context);
	}
	
	/**
	 * 
	 * @param outputRoot
	 * @throws EglRuntimeException
	 * @since 1.6
	 */
	public EglFileGeneratingTemplateFactory(Path outputRoot) throws EglRuntimeException {
		setOutputRoot(outputRoot.toString());
	}

	public String getOutputRoot() {
		return outputRootPath;
	}
	
	public void setOutputRoot(String path) throws EglRuntimeException {
		outputRootPath = path;
		outputRoot     = resolveRoot(path);
	}

	@Override
	protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
		return new EglFileGeneratingTemplate(spec, getContextForNewTemplate(), getOutputRootOrRoot(), outputRootPath);
	}
	
	protected URI getOutputRootOrRoot() {
		return outputRoot != null ? outputRoot : root;
	}
}
