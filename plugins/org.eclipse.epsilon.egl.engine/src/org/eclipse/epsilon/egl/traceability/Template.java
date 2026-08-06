/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.traceability;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.util.UriUtil;

public class Template extends Container {
	
	public Template() {
		this(null, "", null);
	}
	
	public Template(URI uri) {
		this(null, uri == null ? "" : UriUtil.getName(uri), uri);
	}
	
	public Template(String name) {
		this(name, null);
	}
	
	public Template(String name, URI uri) {
		this(null, name, uri);
	}
	
	protected Template(Template parent, String name, URI uri) {
		super(parent, name, uri);
	}
	
	public OutputFile addOutputFile(String name, URI uri) {
		final OutputFile outputFile = new OutputFile(this, name, uri);
		add(outputFile);
		return outputFile;
	}
	
	public OutputFile addOutputFile(URI uri) {
		if (uri == null)
			throw new NullPointerException("uri cannot be null");
		
		return addOutputFile(UriUtil.getName(uri), uri);
	}
	
	public Template addTemplate(String name, URI uri) {
		final Template template = new Template(this, name, uri);
		add(template);
		return template;
	}
	
	public Template addTemplate(URI uri) {
		if (uri == null)
			throw new NullPointerException("uri cannot be null");
		
		return addTemplate(UriUtil.getName(uri), uri);
	}
	
	public Variable addVariable(String name, Object value) {
		final Variable variable = new Variable(this, name, value);
		add(variable);
		return variable;
	}
	
	public Collection<Variable> getVariables() {
		return contents.stream()
			.filter(Variable.class::isInstance)
			.map(Variable.class::cast)
			.collect(Collectors.toList());
	}
	
	public Collection<OutputFile> getOutputFiles() {
		return contents.stream()
			.filter(OutputFile.class::isInstance)
			.map(OutputFile.class::cast)
			.collect(Collectors.toList());
	}

	public void reset() {
		contents.clear();
	}
}
