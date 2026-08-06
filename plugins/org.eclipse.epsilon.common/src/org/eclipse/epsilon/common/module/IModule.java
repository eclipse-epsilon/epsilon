/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio Garc��a-Dom��nguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garc��a-Dom��nguez - add getSourceURI
 ******************************************************************************/
package org.eclipse.epsilon.common.module;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;

public interface IModule extends ModuleElement {
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * @since 1.6
	 */
	default boolean parse(URL url) throws Exception {
		return parse(url.toURI());
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 * @since 1.6
	 */
	default boolean parse(Path path) throws Exception {
		return parse(path.toUri());
	}
	
	default boolean parse(File file) throws Exception {
		return parse(file.toURI());
	}
	
	default boolean parse(String code) throws Exception {
		return parse(code, (File) null);
	}
	
	boolean parse(String code, File file) throws Exception;
	
	boolean parse(URI uri) throws Exception;
		
	URI getSourceUri();

	List<ParseProblem> getParseProblems();
	
	ModuleElement createAst(AST cst, ModuleElement parentAst);
}
