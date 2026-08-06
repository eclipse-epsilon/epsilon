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
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.pinset.ReturnValueParser;
import org.eclipse.epsilon.pinset.parse.PinsetParser;

/**
 * Reference.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class Reference extends AnnotatableModuleElement implements ColumnGenerator {
	
	protected String name;
	protected List<String> columnNames = new ArrayList<>();
	protected List<String> properties = new ArrayList<>();
	protected IPropertyGetter getter;
	protected IEolContext context;

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		name = cst.getFirstChild().getText();
		List<AST> aliasedNames =
				AstUtil.getChild(cst, PinsetParser.NAMESLIST).getChildren();
		for (AST aliasedName : aliasedNames) {
			// fill column names: if an alias is defined, use it
			if (aliasedName.getNumberOfChildren() > 0) {
				columnNames.add(aliasedName.getFirstChild().getText());
			}
			else {
				columnNames.add(name + "_" + aliasedName.getText());
			}
			// keep property name for later access
			properties.add(aliasedName.getText());
		}
	}

	@Override
	public List<String> getNames() {
		return columnNames;
	}

	@Override
	public List<Object> getValues(Object o)
			throws EolRuntimeException {
		List<Object> res = new ArrayList<>();
		Object refObject = getter.invoke(o, name);
		if (refObject == null) {
			// No object present in reference, blank for all columns
			for (@SuppressWarnings("unused") String element : properties) {
				res.add(null);
			}
		}
		else {
			for (String prop : properties) {
				res.add(ReturnValueParser.obtainValue(
						getter.invoke(refObject, prop, context)));
			}
		}
		return res;
	}

	public void setGetter(IPropertyGetter getter) {
		this.getter = getter;
	}

	public void setContext(IEolContext context) {
		this.context = context;
	}
}
