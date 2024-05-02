/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.OperationList;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface IEolModule extends IModule {
	
	Object execute() throws EolRuntimeException;
	
	StatementBlock getMain();
	
	List<Statement> getPostOperationStatements();
	
	@Override
	boolean parse(String code, File file) throws Exception;
	
	OperationList getDeclaredOperations();
	
	OperationList getOperations();
	
	List<ModelDeclaration> getDeclaredModelDeclarations();
	
	Set<ModelDeclaration> getModelDeclarations();
	
	IEolModule getParentModule();
	
	void setParentModule(IEolModule parent);
	
	List<Import> getImports();
	
	IEolContext getContext();
	
	void setContext(IEolContext context);
	
	@Override
	List<ParseProblem> getParseProblems();
	
	/**
	 * Configure the IEolModule with the given properties
	 * @param properties a map of property:value 
	 * @since 1.6
	 */
	default void configure(Map<String, ?> properties) {
		
	}
	
	/**
	 * Get the set of property names that should be retrieved from the ILaunchConfiguration in order
	 * to configure this module.
	 * @return
	 * @since 1.6
	 */
	default Set<String> getConfigurationProperties() {
		return Collections.emptySet();
	}
	
	IImportManager getImportManager();
	
	void setImportManager(IImportManager importManager);

	EolDebugger createDebugger();
}
