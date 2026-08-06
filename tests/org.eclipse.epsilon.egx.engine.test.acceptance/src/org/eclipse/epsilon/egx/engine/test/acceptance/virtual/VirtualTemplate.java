/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.virtual;

import java.io.File;
import java.net.URI;

import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;

public class VirtualTemplate extends EglPersistentTemplate {

	private final VirtualTemplateLedger ledger;
	
	public VirtualTemplate(VirtualTemplateLedger ledger, EglTemplateSpecification spec, IEglContext context, URI outputRoot, String outputRootPath) throws Exception {
		super(spec, context, outputRoot, outputRootPath);
		this.ledger = ledger;
	}

	@Override
	protected void doGenerate(File file, String targetName, boolean overwrite, boolean protectRegions) throws EglRuntimeException {
		this.ledger.add(targetName, getContents());
	}
}
