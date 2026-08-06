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
import java.util.Objects;
import org.eclipse.epsilon.eol.exceptions.EolEvaluatorException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;

public class EolEvaluator {

	private final IEolModule module = new EolModule();
	
	private boolean statementsExecuted = false;
	
	public EolEvaluator(IModel... models) {
		module.getContext().getModelRepository().addModels(models);
	}
	
	public IEolContext getContext() {
		return module.getContext();
	}
	
	public void dispose() {
		try {
			getContext().getModelRepository().dispose();
			getContext().dispose();
		}
		catch (Exception ex) {
			
		}
	}
	
	private Object executeInteral(String statement) {
		try {
			if (module.parse(statement)) {
				return module.execute();
			}
			else {
				throw new EolEvaluatorException(module.getParseProblems());
			}
		}
		catch (EolEvaluatorException e) {
			throw e;
		}
		catch (Exception e) {
			throw new EolEvaluatorException("Could not execute '" + statement + "'", e);
		}
		finally {
			statementsExecuted = true;
		}
	}
	
	public Object evaluate(Object o) {
		return executeInteral("return " + Objects.toString(o) + ";");
	}
	
	public void execute(String statement) {
		executeInteral(statement);
	}
	
	public void setVariable(String name, String eolStatement) {
		setVariable(name, evaluate(eolStatement));
	}
	
	public void setVariable(String name, Object value) {
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable(name, value));
	}
	
	public void importFile(File file) {
		if (statementsExecuted) {
			throw new IllegalStateException("Cannot import files after calls to execute, evaluate or setVariable have occurred");
		}
		
		try {
			if (module.parse("import '" + file.getAbsolutePath() + "';") && module.getParseProblems().isEmpty()) {
				return;
			}
		} catch (Exception e) {
			throw new EolEvaluatorException("Could not import: " + file.getAbsolutePath(), e);
		}
		
		throw new EolEvaluatorException("Errors when parsing: " + file.getAbsolutePath() + "-" + module.getParseProblems());
	}
}
