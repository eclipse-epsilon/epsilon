/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.egl.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.dom.TemplateOperation;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.exceptions.EglStoppedException;
import org.eclipse.epsilon.egl.execute.context.EglContext;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.model.EglMarkerSection;
import org.eclipse.epsilon.egl.output.IOutputBuffer;
import org.eclipse.epsilon.egl.parse.EglLexer;
import org.eclipse.epsilon.egl.parse.EglParser;
import org.eclipse.epsilon.egl.parse.EglToken.TokenType;
import org.eclipse.epsilon.egl.parse.problem.EglParseProblem;
import org.eclipse.epsilon.egl.preprocessor.Preprocessor;
import org.eclipse.epsilon.egl.types.EglComplexType;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.types.EolType;

/**
 * Used internally by {@link EglTemplateFactory} and {@link EglTemplate}
 * to parse and execute EGL, by transforming to EOL.
 * 
 * Most extensions should subclass {@link EglTemplateFactory} and/or
 * {@link EglTemplate} rather than this class.
 *
 * @author lrose, Sina Madani
 */
public class EglModule extends EolModule implements IEglModule {

	protected EglParser eglParser;
	private final Preprocessor preprocessor = new Preprocessor();
	private final List<EglMarkerSection> markers = new LinkedList<>();
	private URI templateRoot;

	public EglModule() {
		this(null);
	}
	
	public EglModule(IEglContext context) {
		super(context != null ? context : new EglContext());
	}
	
	@Override
	public void build(AST cst, IModule module) {
		if (cst.getParent() == null) {
			preprocessor.updateASTLocations(cst);
		}
		super.build(cst, module);
	}
	
	@Override
	public boolean parse(String code, File file) throws Exception {
		if ((this.sourceFile = file) != null) try {
			templateRoot = UriUtil.fileToUri(file.getAbsoluteFile().getParentFile());
		}
		catch (URISyntaxException e) {}
		return parseAndPreprocess(new EglLexer(code));
	}

	@Override
	public boolean parse(URI uri) throws Exception {
		if (uri == null)
			throw new IllegalArgumentException("URI cannot be null");

		this.sourceUri = uri;
		this.templateRoot = uri;

		if (uri.getScheme() != null && uri.getScheme().equals("file")) {
			this.sourceFile = new File(uri);
		}

		try (Reader reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream()))) {
			return parseAndPreprocess(new EglLexer(reader));
		}
	}
	
	private boolean parseAndPreprocess(EglLexer lexer) throws Exception {
		EpsilonTreeAdaptor astFactory = new EpsilonTreeAdaptor(sourceFile, this);
		(eglParser = new EglParser(lexer, astFactory)).parse();
		AST ast = eglParser.getAST();

		if (eglParser.getParseProblems().isEmpty() && preprocess(ast)) {
			for (AST child : ast.getChildren()) {
				if (child.getType() == TokenType.START_MARKER_TAG.getIdentifier()) {
					EglMarkerSection section = (EglMarkerSection) createAst(child, this);
					markers.add(section);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		if (cst == null) {
			return this;
		}
		if (cst.getType() == TokenType.START_MARKER_TAG.getIdentifier()) {
			return new EglMarkerSection();
		}
		if (cst.getType() == EolParser.HELPERMETHOD && hasAnnotation(cst, "template")) {
			return new TemplateOperation();
		}
		
		ModuleElement ast = super.adapt(cst, parentAst);
		
		if (ast instanceof TypeExpression) {
			return new TypeExpression() {
				@Override
				public EolType execute(IEolContext context) throws EolRuntimeException {
					if ("Template".equals(getName())) {
						return EglComplexType.Template;
					}
					else {
						return super.execute(context);
					}
				}
			};
		}
		return ast;
	}
	
	@Override
	public List<EglMarkerSection> getMarkers() {
		return markers;
	}
	
	@Override
	public Object execute(EglTemplate template, Formatter postprocessor) throws EglRuntimeException {
		IEglContext context = getContext();
		context.enter(template);
		context.getTemplateFactory().initialiseRoot(templateRoot);
		
		try {
			super.execute();
		}
		catch (EolInternalException ex) {
			Throwable internal = ex.getInternal();
			if (internal instanceof EglStoppedException) {
				// Ignore exception caused by a call to out.stop()
			}
			else if (internal instanceof EglRuntimeException) {
				throw new EglRuntimeException(ex);
			}
			else {
				throw new EglRuntimeException((Throwable) ex);
			}
		}
		catch (EglRuntimeException ex) {
			throw ex;
		}
		catch (EolRuntimeException ex) {
			throw new EglRuntimeException(ex);
		}

		IOutputBuffer output = context.getOutputBuffer();
		output.formatWith(postprocessor);
		
		final List<String> problems = context.getPartitioningProblems();
		if (!problems.isEmpty()) {
			throw new EglRuntimeException(problems.get(0), this);
		}

		String result = output.toString();
		
		context.exit();
		return result;
	}

	@Override
	public List<ParseProblem> getParseProblems() {
		parseProblems.addAll(eglParser.getParseProblems());
		
		for (int index = 0; index < parseProblems.size(); index++) {
			final ParseProblem problem = parseProblems.get(index);
			
			if (!(problem instanceof EglParseProblem)) {
				parseProblems.set(index, new EglParseProblem(problem, preprocessor.getTrace()));
			}
		}
		
		return parseProblems;
	}

	@Override
	public List<ModuleElement> getChildren() {
		final List<ModuleElement> children = new ArrayList<>();
		children.addAll(getImports());
		children.addAll(getDeclaredOperations());
		return children;
	}
	
	@Override
	protected HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("egl", EglModule.class);
		return importConfiguration;
	}
	
	@Override
	public IEglContext getContext() {
		return (IEglContext) super.getContext();
	}
	
	protected boolean hasAnnotation(AST ast, String name) {
		if (ast.getAnnotationsAst() == null) return false;
		for (AST annotation : ast.getAnnotationsAst().getChildren()) {
			if (annotation.getType() == EolParser.Annotation && 
				annotation.getText().trim().equals("@" + name)) return true;
		}
		return false;
	}
	
	protected boolean preprocess(AST ast) {
		final String eol = preprocessor.convertToEol(ast);
		try {
			return super.parse(eol, sourceFile);
		}
		catch (Exception e) {
			e.printStackTrace();
			// Ignore - clients are expected to call
			// getParseProblems in order to check for problems
			return false;
		}
	}

	@Override
	public EolDebugger createDebugger() {
		return new EglDebugger();
	}

}
