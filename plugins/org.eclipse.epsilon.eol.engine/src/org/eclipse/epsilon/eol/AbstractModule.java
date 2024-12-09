/*******************************************************************************
 * Copyright (c) 2008-2013 The University of York, Antonio Garcia-Dominguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - refactor parse(...) methods
 ******************************************************************************/
package org.eclipse.epsilon.eol;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.Position;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.parse.EolLexer;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

public abstract class AbstractModule extends AbstractModuleElement implements IModule {
	
	protected EpsilonParser parser;
	
	protected ArrayList<ParseProblem> parseProblems = new ArrayList<>();
		
	public abstract String getMainRule();
	
	protected abstract Lexer createLexer(ANTLRInputStream inputStream);
	
	public abstract EpsilonParser createParser(TokenStream tokenStream);
	
	protected File sourceFile;
	protected URI sourceUri;
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	@Override
	public URI getSourceUri() {
		return sourceUri;
	}
	
	@Override
	public List<ParseProblem> getParseProblems() {
		return parseProblems;
	}
	
	@Override
	public boolean parse(String code, File file) throws Exception {
		this.sourceFile = file;
		if (file != null) {
			this.sourceUri = file.toURI();
		}
		return parse(sourceUri, new ByteArrayInputStream(code.getBytes()));
	}

	@Override
	public boolean parse(URI uri) throws Exception {
		this.sourceUri = uri;

		final String uriScheme = uri.getScheme();
		if ("file".equals(uriScheme)) {
			this.sourceFile = new File(uri);
		}

		return parse(uri, uri.toURL().openStream());
	}

	protected boolean invokeMainRule(List<CommonToken> comments) throws Exception {
		
		AST cst = null;
		
		try {
			cst = (AST)((ParserRuleReturnScope) ReflectionUtil.executeMethod(parser, getMainRule())).getTree();
		}
		
		catch (RecognitionException ex) {
			ParseProblem problem = new ParseProblem();
			problem.setLine(ex.line);
			problem.setColumn(ex.charPositionInLine);
			problem.setReason(ex.getMessage());
			getParseProblems().add(problem);
		}
		catch (Throwable ex) {
			ParseProblem problem = new ParseProblem();
			Token next = parser.input.LT(1);
			problem.setLine(next.getLine());
			problem.setColumn(next.getCharPositionInLine());
			problem.setReason("mismatched input: '" + next.getText() + "'");
			getParseProblems().add(problem);
		}
		
		parseProblems.addAll(parser.getParseProblems());
		
		assignAnnotations(cst);
		assignComments(cst, comments);
		createAst(cst, null);
		
		return getParseProblems().isEmpty();
	}
	
	@Override
	public ModuleElement createAst(AST cst, ModuleElement parentAst) {
		if (cst == null) return null;
		ModuleElement moduleElement = adapt(cst, parentAst);
		if (moduleElement != null) {
			moduleElement.setUri(cst.getUri());
			moduleElement.setModule(cst.getModule());
			moduleElement.setRegion(cst.getRegion());
			moduleElement.build(cst, this);
			if (parentAst != null) {
				moduleElement.setParent(parentAst);
			}
		}
		return moduleElement;
	}
	
	public abstract ModuleElement adapt(AST cst, ModuleElement parentAst);
	
	protected List<CommonToken> extractComments(CommonTokenStream stream) {
		
		List<CommonToken> comments = new ArrayList<>();
		
		// stream is automatically filled in 3.2 but not in 3.5.2
		// We'd like to be able to call stream.fill() to ensure it's
		// filled before we start processing tokens, but fill()
		// doesn't exist in 3.2. To support a wider range of ANTLR
		// versions than just 3.5.2 we're calling toString() instead
		// which has no effect in 3.2 but calls fill() in 3.5.2
		if (stream.size() == 0) stream.toString();
		
		for (Object t : stream.getTokens()) {
			CommonToken token = (CommonToken) t;
			int type = token.getType();
			if (type == EolLexer.COMMENT || type == EolParser.LINE_COMMENT) {
				comments.add(token);
			}
		}
		
		return comments;
	}
	
	private boolean parse(URI uri, final InputStream iStream) throws Exception {
		parseProblems.clear();
		
		try (Scanner s = new java.util.Scanner(iStream)) {
			// Replace tabs with spaces to get consistent column numbers in ASTs
			s.useDelimiter("\\A");
		    String contents = s.hasNext() ? s.next() : "";
		    ByteArrayInputStream noTabsStream = new ByteArrayInputStream(contents.replaceAll("\t", " ").getBytes());
		    
		    final Lexer lexer = createLexer(new ANTLRInputStream(noTabsStream));
			final CommonTokenStream stream = new CommonTokenStream(lexer);
			List<CommonToken> comments = extractComments(stream);
			final EpsilonTreeAdaptor adaptor = new EpsilonTreeAdaptor(uri, this);

			parser = createParser(stream);
			parser.setDeepTreeAdaptor(adaptor);
			parser.getParseProblems().addAll(((org.eclipse.epsilon.common.parse.Lexer) lexer).getParseProblems());
			
			return invokeMainRule(comments);
		}
		catch (Exception ex) {
			parseProblems.add(new ParseProblem("Exception during parsing: " + ex.getLocalizedMessage(), ParseProblem.ERROR));
			throw ex;
		}
	}
	
	protected void assignComments(AST root, List<CommonToken> comments) {
		for (CommonToken comment : comments) {
			assignComment(root, comment);
		}
	}
	
	protected void assignComment(AST root, CommonToken comment) {
		for (AST ast : root.getDescendants()) {
			Position start = ast.getRegion().getStart();
			if (!ast.isImaginary() && comment.getLine() <= start.getLine() && comment.getCharPositionInLine() <= start.getColumn()) {
				ast.getCommentTokens().add(comment);
				return;
			}
		}
	}
	
	protected void assignAnnotations(AST ast) {
		List<AST> children = AstUtil.getChildren(ast);
		for (AST child : children) {
			if (child.getType() == EolParser.ANNOTATIONBLOCK) {
				
				AST target = null;
				
				//HACK to support main-less EOL programs
				if (child.getNextSibling() != null) {
					if (child.getNextSibling().getType() != EolParser.BLOCK) {
						target = child.getNextSibling();
					}
					else if (child.getNextSibling().getNextSibling() != null) {
						target = child.getNextSibling().getNextSibling();
					}
				}
				
				if (target != null) {
					target.setAnnotationsAst(child);
				}
				
			}
			else {
				assignAnnotations(child);
			}
		}
	}
}
