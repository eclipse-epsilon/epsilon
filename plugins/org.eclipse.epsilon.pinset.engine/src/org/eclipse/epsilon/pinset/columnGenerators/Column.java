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

import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.dom.AnnotatableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.pinset.PinsetModule;
import org.eclipse.epsilon.pinset.ReturnValueParser;

/**
 * Column.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class Column extends AnnotatableModuleElement implements ColumnGenerator {

	protected String name;
	protected IExecutableModuleElement block;
	protected boolean isSilent = false;
	protected IEolContext context;

	public void setName(String name) {
		this.name = name;
	}

	public IExecutableModuleElement getBlock() {
		return block;
	}

	public void setBlock(IExecutableModuleElement block) {
		this.block = block;
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		name = cst.getFirstChild().getText();
		block = (IExecutableModuleElement) module.createAst(cst.getSecondChild(), this);
		isSilent = this.hasAnnotation(PinsetModule.SILENT_ANNOTATION);
	}

	@Override
	public List<String> getNames() {
		return Arrays.asList(getName());
	}

	public String getName() {
		return name;
	}

	@Override
	public List<Object> getValues(Object elem) throws EolRuntimeException {
		return Arrays.asList(getValue(elem));
	}

	private Object getValue(Object obj) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		frameStack.enterLocal(FrameType.UNPROTECTED, block);
		Object res = null;
		try {
			res = context.getExecutorFactory().execute(block, context);
		}
		catch (EolRuntimeException e) {
			if (!isSilent) {
				throw e;
			}
		}
		frameStack.leaveLocal(block);
		return ReturnValueParser.obtainValue(res);
	}

	public void setContext(IEolContext context) {
		this.context = context;
	}

	public void setSilent(boolean isSilent) {
		this.isSilent |= isSilent;
	}
}
