/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.validation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParseProblemManager;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.Position;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateContext;
import org.eclipse.epsilon.ecore.delegates.execution.EvlProgram;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateResourceAdapter;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.parse.EolLexer;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.tools.EolSystem;
import org.eclipse.epsilon.eol.util.ReflectionUtil;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.parse.EvlLexer;
import org.eclipse.epsilon.evl.parse.EvlParser;

/**
 * A delegate context for Evl Delegates
 * 
 * @since 2.5
 */
public class EvlDelegateContext implements DelegateContext {
	
	public EvlDelegateContext(String delegateURI, EPackage ePackage, DelegateResourceAdapter delegateRA) {
		this.uri = delegateURI;
		this.ePackage = ePackage;
		this.delegateRA = delegateRA;
		Resource res = ePackage.eResource();
		EPackage.Registry packageRegistry = null;
		if (res != null) {
			this.delegateRA.adapt(res); // Ensure we get unloaded when the resource is unloaded
			ResourceSet resourceSet = res.getResourceSet();
			if (resourceSet != null) {
				// it's a dynamic package. Use the local package registry
				packageRegistry = resourceSet.getPackageRegistry();
			}
		}
		if (packageRegistry == null) { // Deprecated compatibility
			packageRegistry = EPackage.Registry.INSTANCE;
		}
		this.module = new EvlModule();
		this.prepareContext();
	}

	public void dispose() {
		this.module.getContext().dispose();
	}


	public String getURI() {
		return uri;
	}

	public String toString() {
		return ePackage.getName() + " : " + getURI(); //$NON-NLS-1$
	}
	
	public EvlProgram parse(String code) {
		String contents = code.trim();
		ByteArrayInputStream noTabsStream = new ByteArrayInputStream(contents.replaceAll("\t", " ").getBytes());
		Lexer lexer;
		try {
			lexer = new EvlLexer(new ANTLRInputStream(noTabsStream));
		} catch (IOException e) {
			return (new EvlProgram()).error(e);
		}
		this.module.getDeclaredConstraintContexts().clear();
		final CommonTokenStream stream = new CommonTokenStream(lexer);
		List<CommonToken> comments = extractComments(stream);
		EpsilonTreeAdaptor adaptor = new EpsilonTreeAdaptor((File) null, module);
		EvlParser parser = new EvlParser(stream);
		parser.setDeepTreeAdaptor(adaptor);
		return invokeMainRule(parser, comments);
	}
	

	private final String uri;
	private final EPackage ePackage;
	private final DelegateResourceAdapter delegateRA;
	private EvlModule module;

	private EvlProgram invokeMainRule(EvlParser parser, List<CommonToken> comments) {
		EpsilonParseProblemManager.INSTANCE.reset();
		AST cst = null;
		try {
			cst = (AST) ((ParserRuleReturnScope) ReflectionUtil.executeMethod(parser, "evlModule")).getTree();
		} catch (RecognitionException ex) {
			return (new EvlProgram()).error(ex);
		} catch (Throwable ex) {
			return (new EvlProgram()).error(ex, parser.input.LT(1));
		}
		var parseProblems = new ArrayList<>(EpsilonParseProblemManager.INSTANCE.getParseProblems());
		EpsilonParseProblemManager.INSTANCE.reset();
		assignAnnotations(cst);
		assignComments(cst, comments);
		createAst(cst);
		if (this.module.getDeclaredConstraintContexts().isEmpty()) {
			return (new EvlProgram()).error(new IllegalStateException("Error parsing EVL, context is malformed."));
		};
		ConstraintContext contexts = this.module.getDeclaredConstraintContexts().get(0);
		if (contexts.getConstraints().isEmpty()) {
			return (new EvlProgram()).error(new IllegalStateException("Error parsing EVL, constraint is malformed."));
		}
		return new EvlProgram(contexts.getConstraints().get(0), parseProblems, this.module.getContext());
	}

	private void createAst(AST cst) {
		if (cst == null)
			return;
		ModuleElement moduleElement = this.module.adapt(cst, null);
		if (moduleElement != null) {
			moduleElement.setUri(cst.getUri());
			moduleElement.setModule(cst.getModule());
			moduleElement.setRegion(cst.getRegion());
			moduleElement.build(cst, cst.getModule());
		}
	}

	private void assignComments(AST root, List<CommonToken> comments) {
		for (CommonToken comment : comments) {
			assignComment(root, comment);
		}
	}

	private void assignComment(AST root, CommonToken comment) {
		for (AST ast : root.getDescendants()) {
			Position start = ast.getRegion().getStart();
			if (!ast.isImaginary() && comment.getLine() <= start.getLine()
					&& comment.getCharPositionInLine() <= start.getColumn()) {
				ast.getCommentTokens().add(comment);
				return;
			}
		}
	}

	private void assignAnnotations(AST ast) {
		List<AST> children = AstUtil.getChildren(ast);
		for (AST child : children) {
			if (child.getType() == EolParser.ANNOTATIONBLOCK) {

				AST target = null;

				// HACK to support main-less EOL programs
				if (child.getNextSibling() != null) {
					if (child.getNextSibling().getType() != EolParser.BLOCK) {
						target = child.getNextSibling();
					} else if (child.getNextSibling().getNextSibling() != null) {
						target = child.getNextSibling().getNextSibling();
					}
				}

				if (target != null) {
					target.setAnnotationsAst(child);
				}

			} else {
				assignAnnotations(child);
			}
		}
	}
	
	private List<CommonToken> extractComments(CommonTokenStream stream) {

		List<CommonToken> comments = new ArrayList<>();

		// stream is automatically filled in 3.2 but not in 3.5.2
		// We'd like to be able to call stream.fill() to ensure it's
		// filled before we start processing tokens, but fill()
		// doesn't exist in 3.2. To support a wider range of ANTLR
		// versions than just 3.5.2 we're calling toString() instead
		// which has no effect in 3.2 but calls fill() in 3.5.2
		if (stream.size() == 0)
			stream.toString();

		for (Object t : stream.getTokens()) {
			CommonToken token = (CommonToken) t;
			int type = token.getType();
			if (type == EolLexer.COMMENT || type == EolParser.LINE_COMMENT) {
				comments.add(token);
			}
		}

		return comments;
	}
	
	private void prepareContext() {
		IEolContext context = this.module.getContext();
		EolSystem system = new EolSystem();
		system.setContext(context);
		context.setModule(this.module);
		context.getFrameStack().putGlobal(
			Variable.createReadOnlyVariable("null", null),
			Variable.createReadOnlyVariable("System", system)
		);
	}
}
