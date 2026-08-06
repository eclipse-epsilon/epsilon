/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.module;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.Region;

public interface ModuleElement {
	
	File getFile();
	
	URI getUri();

	void setUri(URI uri);

	void setModule(IModule module);
	
	void build(AST cst, IModule module);
	
	Region getRegion();
	
	void setRegion(Region region);
	
	ModuleElement getParent();
	
	void setParent(ModuleElement moduleElement);
	
	List<ModuleElement> getChildren();
	
	IModule getModule();
	
	List<Comment> getComments();
	
	Map<String, Object> getData();
	
}
