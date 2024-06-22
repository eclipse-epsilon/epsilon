/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dom;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.parse.EgxParser;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElementParameter;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.types.EolMap;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;

public class GenerationRule extends ExtensibleNamedRule implements IExecutableModuleElementParameter, IExecutableModuleElement {
	
	protected Parameter sourceParameter;
	protected ExecutableBlock<Collection<?>> domainBlock;
	protected ExecutableBlock<String> targetBlock, templateBlock;
	protected ExecutableBlock<Boolean> guardBlock, overwriteBlock, mergeBlock, appendBlock, patchBlock;
	protected ExecutableBlock<?> preBlock, postBlock;
	protected ExecutableBlock<EolMap<String, ?>> parametersBlock;
	protected ExecutableBlock<Formatter> formatterBlock;

	@SuppressWarnings("unchecked")
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		validateConstructs(cst, module.getParseProblems());
		
		AST sourceParameterAst = cst.getFirstChild().getNextSibling();
		if (sourceParameterAst != null && sourceParameterAst.getType() == EolParser.FORMAL) {
			sourceParameter = (Parameter) module.createAst(sourceParameterAst, this);
		}
		domainBlock = (ExecutableBlock<Collection<?>>) module.createAst(AstUtil.getChild(cst, EgxParser.DOMAIN), this);
		guardBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EgxParser.GUARD), this);
		preBlock = (ExecutableBlock<?>) module.createAst(AstUtil.getChild(cst, EgxParser.PRE), this);
		overwriteBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EgxParser.OVERWRITE), this);
		mergeBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EgxParser.MERGE), this);
		appendBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EgxParser.APPEND), this);
		patchBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EgxParser.PATCH), this);
		templateBlock = (ExecutableBlock<String>) module.createAst(AstUtil.getChild(cst, EgxParser.TEMPLATE), this);
		parametersBlock = (ExecutableBlock<EolMap<String, ?>>) module.createAst(AstUtil.getChild(cst, EgxParser.PARAMETERS), this);
		targetBlock = (ExecutableBlock<String>) module.createAst(AstUtil.getChild(cst, EgxParser.TARGET), this);
		postBlock = (ExecutableBlock<?>) module.createAst(AstUtil.getChild(cst, EgxParser.POST), this);
		formatterBlock = (ExecutableBlock<Formatter>) module.createAst(AstUtil.getChild(cst, EgxParser.FORMATTER), this);
	}
	
	/**
	 * 
	 * @param parent
	 * @param problems
	 * @since 1.6
	 */
	private static void validateConstructs(AST parent, Collection<ParseProblem> problems) {
		int[] types = {
			EgxParser.DOMAIN,
			EgxParser.GUARD,
			EgxParser.PRE,
			EgxParser.OVERWRITE,
			EgxParser.MERGE,
			EgxParser.APPEND,
			EgxParser.PATCH,
			EgxParser.TEMPLATE,
			EgxParser.PARAMETERS,
			EgxParser.TARGET,
			EgxParser.POST
		};
		String[] names = {
			"domain",
			"guard",
			"pre",
			"overwrite",
			"merge",
			"append",
			"patch",
			"template",
			"parameters",
			"target",
			"post"
		};
		for (int i = 0; i < types.length; i++) {
			if (!AstUtil.hasAtMostNChildrenOfTypes(1, parent, types[i])) {
				problems.add(new ParseProblem(parent.getLine(), parent.getColumn(),
					"At most one '"+names[i]+"' block is permitted in each rule",
					ParseProblem.ERROR));
			}
		}
	}
	
	public Collection<?> getAllElements(IEolContext context) throws EolRuntimeException {
		if (sourceParameter != null) {
			if (domainBlock == null) {
				return getAllInstances(sourceParameter, context, !isGreedy(context));
			}
			else {
				return domainBlock.execute(context, true);
			}
		}
		else {
			return Collections.singleton(null);
		}
	}
	
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
		
		final boolean overwrite = (overwriteBlock == null) ? true : overwriteBlock.execute(context, false);
		final boolean merge = (mergeBlock == null) ? true : mergeBlock.execute(context, false);			
		final boolean append = (appendBlock == null) ? false : appendBlock.execute(context, false);			
		final boolean patch = (patchBlock == null) ? false : patchBlock.execute(context, false);			
		final String templateName = (templateBlock == null) ? "" : templateBlock.execute(context, false);
		final EglTemplateFactory templateFactory = context.getTemplateFactory();
		final Map<URI, EglTemplate> templateCache = context.getTemplateCache();
		
		URI templateUri = templateFactory.resolveTemplate(templateName);
		EglTemplate eglTemplate;
		
		if (templateCache == null || (eglTemplate = templateCache.get(templateUri)) == null) {
			eglTemplate = templateFactory.load(templateUri);

			List<ParseProblem> problems = eglTemplate.getParseProblems();
			if (!problems.isEmpty()) {
				ParseProblem problem = problems.get(0);
				String reason = "Parse error(s) in " + templateUri + ": " + problem.toString();
				throw new EolRuntimeException(reason, templateBlock);
			}
				
			if (templateCache != null) {
				templateCache.put(templateUri, eglTemplate);
			}
		}

		if (sourceParameter != null) {
			eglTemplate.populate(sourceParameter.getName(), element);
		}
		
		if (parametersBlock != null) {
			for (Map.Entry<String, ?> entry : parametersBlock.execute(context, false).entrySet()) {
				eglTemplate.populate(entry.getKey(), entry.getValue());
			}
		}
		
		final String target = targetBlock != null ? targetBlock.execute(context, false) : null;

		if (formatterBlock != null) {
			final Formatter formatter = formatterBlock.execute(context, false);
			eglTemplate.setFormatter(formatter);
		}

		Object generated;
		if (eglTemplate instanceof EglPersistentTemplate && target != null) {
			if ((getBooleanAnnotationValue("patch", context) || patch) && eglTemplate instanceof EglFileGeneratingTemplate) {
				generated = ((EglFileGeneratingTemplate) eglTemplate).patch(target);
			}
			else if ((getBooleanAnnotationValue("append", context) || append) && eglTemplate instanceof EglFileGeneratingTemplate) {
				generated = ((EglFileGeneratingTemplate) eglTemplate).append(target);
			}
			else {
				generated = ((EglPersistentTemplate) eglTemplate).generate(target, overwrite, merge);
			}
		}
		else {
			generated = eglTemplate.process();
		}
		
		context.getInvokedTemplates().add(eglTemplate.getTemplate());
		
		
		if (postBlock != null) {
			frameStack.enterLocal(FrameType.UNPROTECTED, postBlock, Variable.createReadOnlyVariable("generated", generated));
			postBlock.execute(context, false);
			frameStack.leaveLocal(postBlock);
		}
		
		frameStack.leaveLocal(this);
		eglTemplate.reset();
		return generated;
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		for (Object element : getAllElements(context)) {
			executorFactory.execute(this, context, element);
		}
		return null;
	}

	/**
	 * Gets the model which the "transform" parameter type expression belongs to.
	 * @param context
	 * @return The model for the parameter.
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public IModel getOwningModelForType(IEolContext context) throws EolRuntimeException {
		if (sourceParameter == null) return null;
		EolType parameterType = sourceParameter.getType(context);
		if (parameterType instanceof EolModelElementType) 
			return ((EolModelElementType)parameterType).getModel();
		else
			return null;
	}
	
	@Override
	public String toString() {
		String label = getName();
		if (sourceParameter != null) {
			label += " (" + sourceParameter.getTypeName() + ")";
		}
		return label;
	}

	@Override
	public AST getSuperRulesAst(AST cst) {
		return null;
	}
	
	public void accept(IEgxVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Gets the parameter bound to the "transform" part, if {@link #hasTransformSource()} == true.
	 * 
	 * @return The part following "transform", or <code>null</code> if absent.
	 * @since 2.3
	 */
	public Parameter getTransformSource() {
		return sourceParameter;
	}
	
	/**
	 * Used to determine whether this rule operates over a collection of model elements.
	 * 
	 * @return <code>true</code> if the rule has a "transform" part.
	 * @since 2.3
	 */
	public boolean hasTransformSource() {
		return sourceParameter != null;
	}
	
	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<? extends Collection<?>> getDomainBlock() {
		return domainBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<String> getTargetBlock() {
		return targetBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<String> getTemplateBlock() {
		return templateBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<Boolean> getGuardBlock() {
		return guardBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<Boolean> getOverwriteBlock() {
		return overwriteBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<Boolean> getMergeBlock() {
		return mergeBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<?> getPreBlock() {
		return preBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<?> getPostBlock() {
		return postBlock;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public ExecutableBlock<EolMap<String, ?>> getParametersBlock() {
		return parametersBlock;
	}

	/**
	 *
	 * @return
	 * @since 2.5
	 */
	public ExecutableBlock<Formatter> getFormatterBlock() {
		return formatterBlock;
	}
}
