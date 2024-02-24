/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.spec;

import java.io.File;
import java.net.URI;
import java.util.Collection;

import org.eclipse.epsilon.egl.execute.control.ITemplateExecutionListener;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.incremental.IncrementalitySettings;
import org.eclipse.epsilon.egl.internal.IEglModule;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.eol.IImportManager;

class DirtyResourceBackedTemplateSpecification extends EglTemplateSpecification {

	private final String latestCode;
	private final URI resource;
	
	protected DirtyResourceBackedTemplateSpecification(String name, String latestCode, URI resource, Formatter defaultFormatter, IncrementalitySettings incrementalitySettings, IImportManager importManager, Collection<ITemplateExecutionListener> listeners) {
		super(name, defaultFormatter, incrementalitySettings, importManager, listeners);
		
		this.latestCode = latestCode;
		this.resource = resource;
	}

	@Override
	public Template createTemplate() {
		return new Template(getName(), resource);
	}
	
	@Override
	public void parseInto(IEglModule module) throws Exception {
		module.parse(latestCode, new File(resource));
	}

	@Override
	public URI getURI() {
		return resource;
	}	
}
