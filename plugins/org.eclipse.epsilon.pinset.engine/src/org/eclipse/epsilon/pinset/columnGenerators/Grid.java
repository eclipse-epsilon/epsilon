/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.columnGenerators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.AnnotatableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.pinset.PinsetModule;
import org.eclipse.epsilon.pinset.ReturnValueParser;
import org.eclipse.epsilon.pinset.parse.PinsetParser;

/**
 * Grid.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class Grid extends AnnotatableModuleElement implements ColumnGenerator {

	private static final String KEY_VARNAME = "key";

	protected IExecutableModuleElement keysBlock;
	protected IExecutableModuleElement headerBlock;
	protected IExecutableModuleElement bodyBlock;
	protected boolean isSilent = false;

	protected List<Object> keys = null;
	protected List<String> headers = null;

	protected IEolContext context;

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		AST keysAST = AstUtil.getChild(cst, PinsetParser.GRIDKEYS);
		keysBlock = (IExecutableModuleElement) module.createAst(keysAST.getFirstChild(), this);
		AST headerAST = AstUtil.getChild(cst, PinsetParser.GRIDHEADER);
		headerBlock = (IExecutableModuleElement) module.createAst(headerAST.getFirstChild(), this);
		AST bodyAST = AstUtil.getChild(cst, PinsetParser.GRIDBODY);
		bodyBlock = (IExecutableModuleElement) module.createAst(bodyAST.getFirstChild(), this);
		isSilent = this.hasAnnotation(PinsetModule.SILENT_ANNOTATION);
	}

	@Override
	public List<String> getNames() throws EolRuntimeException {
		if (headers == null) {
			initHeaders();
		}
		return headers;
	}

	private void initHeaders() throws EolRuntimeException {
		initKeys(context);
		headers = new ArrayList<>();
		FrameStack frameStack = context.getFrameStack();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		frameStack.enterLocal(FrameType.UNPROTECTED, headerBlock);
		for (Object key : keys) {
			frameStack.put(Variable.createReadOnlyVariable(KEY_VARNAME, key));
			Object result = ReturnValueParser.obtainValue(executorFactory.execute(headerBlock, context));
			if (result == null) {
				throw new EolRuntimeException(
					String.format("There has been a problem when generating a header for key %s", key)
				);
			}
			String header = "" + result;
			headers.add(header.trim().replaceAll("\\s+", "_"));
		}
		frameStack.leaveLocal(headerBlock);
	}

	@SuppressWarnings("unchecked")
	private void initKeys(IEolContext context) throws EolRuntimeException {
		if (keys == null) {
			FrameStack frameStack = context.getFrameStack();
			frameStack.enterLocal(FrameType.UNPROTECTED, keysBlock);
			keys = (List<Object>) ReturnValueParser.obtainValue(context.getExecutorFactory().execute(keysBlock, context));
			frameStack.leaveLocal(keysBlock);
		}
	}

	@Override
	public List<Object> getValues(Object obj) throws EolRuntimeException {
		initKeys(context);
		List<Object> values = new ArrayList<>();
		FrameStack frameStack = context.getFrameStack();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		frameStack.enterLocal(FrameType.UNPROTECTED, bodyBlock);
		for (Object key : keys) {
			frameStack.put(Variable.createReadOnlyVariable(KEY_VARNAME, key));
			Object value = null;
			try {
				value = executorFactory.execute(bodyBlock, context);
			}
			catch (EolRuntimeException e) {
				if (!isSilent) {
					throw e;
				}
			}
			values.add(ReturnValueParser.obtainValue(value));
		}
		frameStack.leaveLocal(bodyBlock);
		return values;
	}

	public void setContext(IEolContext context) {
		this.context = context;
	}

	public void setSilent(boolean isSilent) {
		this.isSilent |= isSilent;
	}
}
