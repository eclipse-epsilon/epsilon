/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.examples.etl.api;

import java.io.File;
import java.util.Collection;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.dom.NewInstanceExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public class App {
	
	public static void main(String[] args) throws Exception {
		new App().run();
	}

	protected void run() throws Exception {
		
		EtlModule module = new EtlModule() {
			@Override
			public ModuleElement adapt(AST cst, ModuleElement parentAst) {
				ModuleElement ast = super.adapt(cst, parentAst);
				if (ast instanceof TransformationRule) {
					// Return a sub-class of TransformationRule that we control
					return new CustomTransformationRule();
				}
				else {
					return ast;
				}
			}
		};
		
		PlainXmlModel in = new PlainXmlModel();
		in.setName("In");
		in.setFile(new File("in.xml"));
		in.setReadOnLoad(true);
		in.setStoredOnDisposal(false);
		in.load();
		
		PlainXmlModel out = new PlainXmlModel();
		out.setName("Out");
		out.setFile(new File("out.xml"));
		out.setReadOnLoad(false);
		out.setStoredOnDisposal(true);
		out.load();
		
		module.parse(new File("in2out.etl"));
		module.getContext().getModelRepository().addModel(in);
		module.getContext().getModelRepository().addModel(out);
		
		// Add a listener to monitor for invocations of new t_n expressions
		module.getContext().getExecutorFactory().addExecutionListener(new IExecutionListener() {
			
			@Override
			public void finishedExecutingWithException(ModuleElement ast,
					EolRuntimeException exception, IEolContext context) {}
			
			@Override
			public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {
				if (ast instanceof NewInstanceExpression) {
					System.out.println("Created " + result);
				}
			}
			
			@Override
			public void aboutToExecute(ModuleElement ast, IEolContext context) {}
		});
		
		module.execute();
		
		module.getContext().getModelRepository().dispose();
		module.getContext().dispose();
		
	}
	
	class CustomTransformationRule extends TransformationRule {
		
		@Override
		public Collection<?> transform(Object source,
				Collection<Object> targets, IEtlContext context)
				throws EolRuntimeException {

			System.out.println("Transforming " + source + " to " + targets);
			return super.transform(source, targets, context);
		}
		
	}
	
}
