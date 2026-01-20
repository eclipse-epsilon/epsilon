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
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.epsilon.egl.execute.control.ITemplateExecutionListener;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.incremental.IncrementalitySettings;
import org.eclipse.epsilon.egl.internal.IEglModule;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.eol.IImportManager;
import org.eclipse.epsilon.eol.ImportManager;

public abstract class EglTemplateSpecification {

	private final String name;
	private final Formatter defaultFormatter;
	private final IncrementalitySettings incrementalitySettings;
	private final Collection<ITemplateExecutionListener> listeners;
	private final IImportManager importManager;
	
	/**
	 * @deprecated From 2.5.0, switch to
	 *             {@link #EglTemplateSpecification(String, Formatter, IncrementalitySettings, IImportManager, Collection)}
	 *             for reuse of imported modules across templates.
	 */
	@Deprecated
	protected EglTemplateSpecification(String name, Formatter defaultFormatter, IncrementalitySettings incrementalitySettings, Collection<ITemplateExecutionListener> listeners) {
		this(name, defaultFormatter, incrementalitySettings, new ImportManager(), listeners);
	}

	protected EglTemplateSpecification(String name, Formatter defaultFormatter, IncrementalitySettings incrementalitySettings, IImportManager importManager, Collection<ITemplateExecutionListener> listeners) {
		this.name = name;
		this.defaultFormatter = defaultFormatter;
		this.incrementalitySettings = incrementalitySettings;
		this.listeners = listeners;
		this.importManager = importManager;
	}
	
	public String getName() {
		return name;
	}
	
	public Formatter getDefaultFormatter() {
		return defaultFormatter;
	}

	public IImportManager getImportManager() {
		return importManager;
	}
	
	public IncrementalitySettings getIncrementalitySettings() {
		// Return a defensive copy, which can be safely modified for only this template
		return new IncrementalitySettings(incrementalitySettings);
	}
	
	public Collection<ITemplateExecutionListener> getTemplateExecutionListeners() {
		// Return a defensive copy, which can be safely modified for only this template
		return new LinkedList<>(listeners);
	}

	public abstract Template createTemplate();
	public abstract void parseInto(IEglModule module) throws Exception;
	public abstract URI getURI();
}
