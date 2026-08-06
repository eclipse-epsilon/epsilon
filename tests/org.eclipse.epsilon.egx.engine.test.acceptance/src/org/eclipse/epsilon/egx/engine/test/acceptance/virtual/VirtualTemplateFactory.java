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

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;

public class VirtualTemplateFactory extends EglFileGeneratingTemplateFactory {

	private final Map<String, String> virtualTemplates = new HashMap<>();
	private final VirtualTemplateLedger ledger = new VirtualTemplateLedger();
	
	public void addVirtualTemplate(String path, String code) {
		virtualTemplates.put(path, code);
	}
	
	@Override
	public EglTemplate load(URI resource) throws EglRuntimeException {
		String relativePath = root.relativize(resource).toString();
		
		if (virtualTemplates.containsKey(relativePath)) {
			return this.load(virtualTemplates.get(relativePath), resource);
		} else {
			return super.load(resource);
		}
	}
	
	@Override
	protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
		return new VirtualTemplate(ledger, spec, getContextForNewTemplate(), getOutputRootOrRoot(), outputRootPath);
	}

	public Collection<String> getOutputFiles() {
		return ledger.getOutputFiles();
	}

	public Object getContentFor(String path) {
		return ledger.getContentFor(path);
	}
}
