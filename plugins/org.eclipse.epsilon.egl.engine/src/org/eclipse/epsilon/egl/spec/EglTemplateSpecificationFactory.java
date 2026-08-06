/*******************************************************************************
 * Copyright (c) 2011-2024 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Antonio Garcia-Dominguez - add import managers
 ******************************************************************************/
package org.eclipse.epsilon.egl.spec;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.egl.execute.control.ITemplateExecutionListener;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.incremental.IncrementalitySettings;
import org.eclipse.epsilon.eol.IImportManager;

public class EglTemplateSpecificationFactory {

	private final Formatter defaultFormatter;
	private final IncrementalitySettings incrementalitySettings;
	private final Collection<ITemplateExecutionListener> listeners;
	private final IImportManager importManager;
	
	public EglTemplateSpecificationFactory(Formatter defaultFormatter, IncrementalitySettings incrementalitySettings, IImportManager importManager, ITemplateExecutionListener... listeners) {
		this.defaultFormatter = defaultFormatter;
		this.incrementalitySettings = incrementalitySettings;
		this.listeners = Arrays.asList(listeners);
		this.importManager = importManager;
	}
	
	public EglTemplateSpecification fromCode(String code) {
		return new CodeBackedTemplateSpecification(code, defaultFormatter, incrementalitySettings, importManager, listeners);
	}

	public EglTemplateSpecification fromResource(String name, URI resource) {
		return new ResourceBackedTemplateSpecification(name, resource, defaultFormatter, incrementalitySettings, importManager, listeners);
	}

	public EglTemplateSpecification fromDirtyResource(String name, String latestCode, URI resource) {
		return new DirtyResourceBackedTemplateSpecification(name, latestCode, resource, defaultFormatter, incrementalitySettings, importManager, listeners);
	}
}
