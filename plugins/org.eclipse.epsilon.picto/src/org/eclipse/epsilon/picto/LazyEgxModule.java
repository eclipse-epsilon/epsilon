/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.common.dt.launching.EclipseExecutionController;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.pinset.DatasetRule;
import org.eclipse.epsilon.pinset.PinsetModule;

public class LazyEgxModule extends EgxModule {
	
	public LazyEgxModule() {
		super();
	}
	
	public LazyEgxModule(EglTemplateFactory templateFactory) {
		super(templateFactory);
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		ModuleElement ast = super.adapt(cst, parentAst);
		if (ast instanceof GenerationRule) return new LazyGenerationRule();
		else return ast;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object processRules() throws EolRuntimeException {
		IEgxContext context = getContext();
		ExecutorFactory ef = context.getExecutorFactory();
		context.getOperationContributorRegistry().add(new PictoOperationContributor(this));
		Collection<? extends GenerationRule> rules = getGenerationRules();
		Collection<LazyGenerationRuleContentPromise> promises = new ArrayList<>(rules.size());
		for (GenerationRule rule : rules) {
			if (!rule.isLazy(context)) {
				promises.addAll((Collection<? extends LazyGenerationRuleContentPromise>) ef.execute(rule, context));
			}
		}
		return promises;
	}
	
	public class LazyGenerationRule extends GenerationRule {
		
		@Override
		public Object execute(IEolContext context_, Object element) throws EolRuntimeException {
			IEgxContext context = (IEgxContext) context_;
			FrameStack frameStack = context.getFrameStack();
			
			if (sourceParameter != null) {
				frameStack.enterLocal(FrameType.PROTECTED, this, Variable.createReadOnlyVariable(sourceParameter.getName(), element));
			}
			else {
				frameStack.enterLocal(FrameType.PROTECTED, this);
			}
			
			if (guardBlock != null && !guardBlock.execute(context, false)) {
				frameStack.leaveLocal(this);
				return null;
			}
			
			if (preBlock != null) {
				preBlock.execute(context, false);
			}
			
			final String templateName = (templateBlock == null) ? null : templateBlock.execute(context, false);
			final EglTemplateFactory templateFactory = context.getTemplateFactory();
			final Map<URI, EglTemplate> templateCache = context.getTemplateCache();
			List<Variable> variables = new ArrayList<>();
			URI templateUri = null; 
			
			if (templateName != null) {
				templateUri = templateFactory.resolveTemplate(templateName);
			}
			
			if (sourceParameter != null) {
				variables.add(Variable.createReadOnlyVariable(sourceParameter.getName(), element));
			}
			
			if (parametersBlock != null) {
				for (Map.Entry<String, ?> entry : parametersBlock.execute(context, false).entrySet()) {
					variables.add(new Variable(entry.getKey(), entry.getValue(), EolAnyType.Instance, false));
				}
			}
			
			frameStack.leaveLocal(this);
			return new LazyGenerationRuleContentPromise(templateFactory, templateCache, templateUri, variables);
		}
		
		@Override
		public Object execute(IEolContext context) throws EolRuntimeException {
			Collection<LazyGenerationRuleContentPromise> promises = new ArrayList<>();
			ExecutorFactory ef = context.getExecutorFactory();
			for (Object element : getAllElements(context)) {
				Object result = ef.execute(this, context, element);
				if (result instanceof LazyGenerationRuleContentPromise) {
					promises.add((LazyGenerationRuleContentPromise) result);
				}
			}
			return promises;
		}
		
		public Parameter getSourceParameter() {
			return sourceParameter;
		}
		
	}
	
	public class LazyGenerationRuleContentPromise implements ContentPromise {
		protected EglTemplateFactory templateFactory;
		protected Map<URI, EglTemplate> templateCache;
		protected URI templateUri;
		protected Collection<Variable> variables;
		protected IProgressMonitor progressMonitor;
		
		protected LazyGenerationRuleContentPromise() {}
		
		public LazyGenerationRuleContentPromise(EglTemplateFactory templateFactory,
				Map<URI, EglTemplate> templateCache,
				URI templateUri, Collection<Variable> variables) {
			this.templateFactory = templateFactory;
			this.templateCache = templateCache;
			this.templateUri = templateUri;
			this.variables = variables;
		}
		
		public Collection<Variable> getVariables() {
			return variables;
		}
		
		@Override
		public String getContent() throws Exception {
			if (templateUri == null) return "";

			if (templateUri.toString().endsWith(".pinset")) {
				PinsetModule module = new PinsetModule();
				module.setContext(context);
				module.persistDatasets(false);
				module.parse(templateUri);
				if (progressMonitor != null) {
					// Allow Pinset job to be automatically cancelled
					module.getContext().getExecutorFactory().setExecutionController(new EclipseExecutionController(progressMonitor));
				}

				String ruleName = null;
				for (Variable variable : variables) {
					if (variable.getName().equalsIgnoreCase("pinsetrule")) {
						ruleName = "" + variable.getValue();
						break;
					}
				}

				DatasetRule rule = null;
				if (ruleName != null) {
					rule = module.getDatasetRule(ruleName);
					if (rule == null) {
						throw new RuntimeException("Pinset rule \"" + ruleName + "\" not found");
					}
				}
				else {
					rule = module.getDatasetRules().get(0);
				}

				context.getFrameStack().enterLocal(FrameType.PROTECTED, module,
						variables.toArray(new Variable[variables.size()]));

				module.preExecution();
				rule.execute(module.getContext());

				context.getFrameStack().leaveLocal(module);

				String content = rule.getDataset().toString();
				rule.dispose();

				return content;
			}

			EglTemplate template = null;
			if (templateCache == null || (template = templateCache.get(templateUri)) == null) {
				template = templateFactory.load(templateUri);
				if (templateCache != null) {
					templateCache.put(templateUri, template);
				}
			}
			
			for (Variable variable : variables) {
				template.populate(variable.getName(), variable.getValue());
			}

			if (progressMonitor != null) {
				// Allow EGL job to be automatically cancelled
				ExecutorFactory execFactory = template.getModule().getContext().getExecutorFactory();
				execFactory.setExecutionController(new EclipseExecutionController(progressMonitor));
			}

			String result = template.process();
			template.reset();
			return result;
		}

		@Override
		public IProgressMonitor getProgressMonitor() {
			return progressMonitor;
		}

		@Override
		public void setProgressMonitor(IProgressMonitor monitor) {
			this.progressMonitor = monitor;
		}
		
	}
		
}
