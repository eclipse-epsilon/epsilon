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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.common.util.ListSet;
import org.eclipse.epsilon.eol.dom.AbortStatement;
import org.eclipse.epsilon.eol.dom.AnnotationBlock;
import org.eclipse.epsilon.eol.dom.AssignmentStatement;
import org.eclipse.epsilon.eol.dom.BooleanLiteral;
import org.eclipse.epsilon.eol.dom.BreakStatement;
import org.eclipse.epsilon.eol.dom.Case;
import org.eclipse.epsilon.eol.dom.CollectionLiteralExpression;
import org.eclipse.epsilon.eol.dom.ComplexOperationCallExpression;
import org.eclipse.epsilon.eol.dom.ContinueStatement;
import org.eclipse.epsilon.eol.dom.DeleteStatement;
import org.eclipse.epsilon.eol.dom.EnumerationLiteralExpression;
import org.eclipse.epsilon.eol.dom.ExecutableAnnotation;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.ExpressionInBrackets;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.FirstOrderOperationCallExpression;
import org.eclipse.epsilon.eol.dom.ForStatement;
import org.eclipse.epsilon.eol.dom.IfStatement;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.IntegerLiteral;
import org.eclipse.epsilon.eol.dom.ItemSelectorExpression;
import org.eclipse.epsilon.eol.dom.MapLiteralExpression;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.ModelDeclarationParameter;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.NewInstanceExpression;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.OperationList;
import org.eclipse.epsilon.eol.dom.OperatorExpressionFactory;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.RealLiteral;
import org.eclipse.epsilon.eol.dom.ReturnStatement;
import org.eclipse.epsilon.eol.dom.SimpleAnnotation;
import org.eclipse.epsilon.eol.dom.SpecialAssignmentStatement;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.dom.SwitchStatement;
import org.eclipse.epsilon.eol.dom.TernaryExpression;
import org.eclipse.epsilon.eol.dom.ThrowStatement;
import org.eclipse.epsilon.eol.dom.TransactionStatement;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.dom.VariableDeclaration;
import org.eclipse.epsilon.eol.dom.WhileStatement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.parse.EolLexer;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.tools.EolSystem;

public class EolModule extends AbstractModule implements IEolModule {
	
	protected StatementBlock main;
	protected IEolContext context;
	protected List<Statement> postOperationStatements = new ArrayList<>();
	protected OperationList declaredOperations = new OperationList();
	protected List<Import> imports = new ArrayList<>();
	protected OperationList operations = new OperationList();
	protected List<ModelDeclaration> declaredModelDeclarations;
	protected Set<ModelDeclaration> modelDeclarations;
	private IEolModule parent;
	private IImportManager importManager;
	
	/**
	 * The type of {@link #context} when using {@link #getContext()} and {@link #setContext(IEolContext)}.
	 * @since 1.6
	 */
	Class<? extends IEolContext> expectedContextType = IEolContext.class;
	
	public EolModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	@SuppressWarnings("unchecked")
	public EolModule(IEolContext context) {
		// Ensure that setContext is consistent with getContext
		try {
			expectedContextType = (Class<? extends IEolContext>) getClass().getMethod("getContext").getReturnType();
		}
		catch (NoSuchMethodException | SecurityException | ClassCastException ex) {
			// Use the default - no need to do anything here.
		}
		setContext(context != null ? context : new EolContext());
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		checkImports(cst);
		
		for (Map.Entry<String, Class<?  extends IModule>> entry : getImportConfiguration().entrySet()) {
			imports.addAll(getImportsByExtension(cst, entry.getKey(), entry.getValue()));
		}
		
		for (AST operationAst : AstUtil.getChildren(cst, EolParser.HELPERMETHOD)) {
			declaredOperations.add((Operation) createAst(operationAst, this));
		}
		
		List<AST> modelDeclarationAsts = AstUtil.getChildren(cst, EolParser.MODELDECLARATION);
		declaredModelDeclarations = new ArrayList<>(modelDeclarationAsts.size());
		for (AST modelDeclarationAst : modelDeclarationAsts) {
			declaredModelDeclarations.add((ModelDeclaration) createAst(modelDeclarationAst, this));
		}
		
		if (AstUtil.getChild(cst, EolParser.BLOCK) != null) {
			main = (StatementBlock) createAst(AstUtil.getChild(cst, EolParser.BLOCK), this);
			
			for (AST child : cst.getChildren()) {
				int type = child.getType();
				if (type == EolParser.BLOCK ||
					type == EolParser.ANNOTATIONBLOCK ||
					type == EolParser.HELPERMETHOD ||
					type == EolParser.MODELDECLARATION ||
					type == EolParser.IMPORT) {
						continue;
				}
				
				ModuleElement exprAst = module.createAst(child, this);
				final Expression expression;
				if (exprAst instanceof ReturnStatement) {
					expression = ((ReturnStatement) exprAst).getReturnedExpression();
				}
				else expression = (Expression) exprAst;
				
				ExpressionStatement expressionStatement = new ExpressionStatement(expression);
				expressionStatement.setParent(this);
				postOperationStatements.add(expressionStatement);
			}
		}
		
		operations.addAll(this.getDeclaredOperations());
		for (Import import_ : imports) {
			import_.setContext(context);
			if (import_.isLoaded() && import_.getModule() instanceof IEolModule) {
				operations.addAll(((IEolModule)import_.getModule()).getOperations());
			}
		}
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		if (cst == null) return null;
		
		// Delegate adaptation to the parent (importing) module if one exists
		if (getParentModule() != null && getParentModule() instanceof AbstractModule && cst.getParent() != null) {
			return ((AbstractModule) getParentModule()).adapt(cst, parentAst);
		}
		
		AST cstParent = cst.getParent();
		
		if (cstParent != null && cstParent.getType() == EolParser.EOLMODULE && cst.getType() == EolParser.BLOCK) {
			return new StatementBlock();
		}
		
		OperatorExpressionFactory operatorExpressionFactory = new OperatorExpressionFactory();
		
		if (parentAst == null) return this;
		
		switch (cst.getType()) {
			case EolParser.FOR: return new ForStatement();
			case EolParser.WHILE: return new WhileStatement();
			case EolParser.DEFAULT:
			case EolParser.CASE: return new Case();
			case EolParser.SWITCH: return new SwitchStatement();
			case EolParser.IF: return new IfStatement();
			case EolParser.ITEMSELECTOR: return new ItemSelectorExpression();
			case EolParser.ARROW: case EolParser.NAVIGATION: case EolParser.POINT: {
				AST secondChild = cst.getSecondChild();
				if (!secondChild.hasChildren()) {
					return new PropertyCallExpression();
				}
				else if (secondChild.getExtraTokens().size() >= 2) {
					if (secondChild.getChildren().stream().anyMatch(ast -> ast.getType() == EolParser.LAMBDAEXPR)) {
						return new ComplexOperationCallExpression();
					}
					else {
						return new FirstOrderOperationCallExpression();
					}
				}
				else {
					return new OperationCallExpression();
				}
			}
			case EolParser.NAME: {
				if (cst.hasChildren()) {
					AST firstChild = cst.getFirstChild();
					if (firstChild.getType() == EolParser.PARAMLIST) {
						return new FirstOrderOperationCallExpression();
					}
					else if (firstChild.getExtraTokens().size() >= 1) {
						if (firstChild.getChildren().stream().anyMatch(ast -> ast.getType() == EolParser.LAMBDAEXPR)) {
							return new ComplexOperationCallExpression();
						}
						else return new FirstOrderOperationCallExpression();
					}
				}
				return new NameExpression();
			}
			case EolParser.FORMAL: return new Parameter();
			case EolParser.BLOCK: return new StatementBlock();
			case EolParser.FEATURECALL: {
				int parentType = cstParent.getType();
				if (cst.hasChildren() && cst.getFirstChild().getType() == EolParser.PARAMETERS && 
					(
						(parentType != EolParser.ARROW && parentType != EolParser.POINT && parentType != EolParser.NAVIGATION) ||
						(parentType == EolParser.ARROW || parentType == EolParser.POINT || parentType == EolParser.NAVIGATION) &&
							cstParent.getFirstChild() == cst)
					) {
						return new OperationCallExpression(true);
				}
				else {
					return new NameExpression();
				}
			}
			case EolParser.STRING: return new StringLiteral();
			case EolParser.INT: return new IntegerLiteral();
			case EolParser.BOOLEAN: return new BooleanLiteral();
			case EolParser.FLOAT: return new RealLiteral();
			case EolParser.ASSIGNMENT: return new AssignmentStatement();
			case EolParser.SPECIAL_ASSIGNMENT: return new SpecialAssignmentStatement();
			case EolParser.EXPRESSIONINBRACKETS: return new ExpressionInBrackets();
			case EolParser.VAR: return new VariableDeclaration();
			case EolParser.NEW: {
				if (cst.getFirstChild().getType() == EolParser.TYPE) {
					return new NewInstanceExpression();
				}
				else {
					return new VariableDeclaration();
				}
			}
			case EolParser.IMPORT: return new Import();
			case EolParser.OPERATOR: {
				if (cst.getText().equals("=") && ((
										(parentAst instanceof IfStatement || parentAst instanceof ForStatement || parentAst instanceof WhileStatement) 
										&& (cstParent.getFirstChild() != cst)) || 
										parentAst instanceof StatementBlock /*|| 
										"BLOCK".equals(parentAst.getText())*/)) {
					return new AssignmentStatement();
				}
				else {
					return operatorExpressionFactory.createOperatorExpression(cst);
				}
			}
			case EolParser.TERNARY: return new TernaryExpression();
			case EolParser.CONTINUE: return new ContinueStatement();
			case EolParser.DELETE: return new DeleteStatement();
			case EolParser.HELPERMETHOD: return new Operation();
			case EolParser.RETURN: return new ReturnStatement();
			case EolParser.ENUMERATION_VALUE: return new EnumerationLiteralExpression();
			case EolParser.Annotation: return new SimpleAnnotation();
			case EolParser.EXECUTABLEANNOTATION: return new ExecutableAnnotation();
			case EolParser.ANNOTATIONBLOCK: return new AnnotationBlock();
			case EolParser.COLLECTION: case EolParser.MAP: {
				boolean isMap = cst.getType() == EolParser.MAP;
				String typeName = cst.getText();
				if (isMap && MapLiteralExpression.createMap(typeName) != null) {
					return new MapLiteralExpression<>();
				}
				else if (CollectionLiteralExpression.createCollection(typeName) != null) {
					return new CollectionLiteralExpression<>();
				}
				else {
					getParseProblems().add(new ParseProblem("Unknown "+(isMap ? "collection" : "map")+" type: "+typeName, this));
				}
			}
			case EolParser.TYPE: return new TypeExpression();
			case EolParser.BREAK: return new BreakStatement(false);
			case EolParser.BREAKALL: return new BreakStatement(true);
			case EolParser.THROW: return new ThrowStatement();
			case EolParser.ABORT: return new AbortStatement();
			case EolParser.TRANSACTION: return new TransactionStatement();
			case EolParser.MODELDECLARATION: return new ModelDeclaration();
			case EolParser.MODELDECLARATIONPARAMETER: return new ModelDeclarationParameter();
			
			default: return null;
		}
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EolLexer(inputStream);
	}
	
	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EolParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
		return "eolModule";
	}
	
	@Override
	public OperationList getDeclaredOperations() {
		return declaredOperations;
	}
	
	protected HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = new HashMap<>(4);
		importConfiguration.put("eol", EolModule.class);
		return importConfiguration;
	}

	protected void prepareContext() throws EolRuntimeException {
		IEolContext context = getContext();
		
		EolSystem system = new EolSystem();
		system.setContext(context);
		context.setModule(this);
		
		context.getFrameStack().putGlobal(
			Variable.createReadOnlyVariable("null", null),
			Variable.createReadOnlyVariable("System", system)
		);
	}

	@Override
	public List<Import> getImports() {
		return imports;
	}
	
	@Override
	public String toString() {
		if (this.sourceFile != null) {
			return this.sourceFile.getAbsolutePath();
		}
		else return super.toString();
	}

	@Override
	public Set<ModelDeclaration> getModelDeclarations() {
		if (modelDeclarations == null) {
			modelDeclarations = new ListSet<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && import_.getModule() instanceof IEolModule){
					modelDeclarations.addAll(((IEolModule)import_.getModule()).getModelDeclarations());
				}
			}
			modelDeclarations.addAll(this.getDeclaredModelDeclarations());
		}
		return modelDeclarations;
	}
	
	@Override
	public OperationList getOperations() {
		return operations;
	}
	
	protected Collection<Import> getImportsByExtension(AST cst, String extension, Class<? extends IModule> moduleImplClass) {
		List<AST> importAsts = AstUtil.getChildren(cst, EolParser.IMPORT);
		List<Import> imports = new ArrayList<>(importAsts.size());
		
		
		for (AST importAst : importAsts) {
			Import import_ = (Import) createAst(importAst, this);
			if (!import_.getPath().endsWith("." + extension)) continue;
			
			import_.setParentModule(this);
			
			URI uri = null;
			if (sourceUri != null) {
				uri = sourceUri;
			} else if (sourceFile != null) {
				uri = sourceFile.toURI();
			}

			try {
				getImportManager().loadModuleForImport(import_, moduleImplClass, uri);

				if (!import_.isLoaded()) {
					ParseProblem problem = new ParseProblem();
					problem.setLine(import_.getRegion().getStart().getLine());

					String reason = import_.isFound()
							? String.format("File %s not found", import_.getPath())
							: String.format("File %s contains errors: %s", import_.getPath(),
									import_.getModule().getParseProblems());

					problem.setReason(reason);
					getParseProblems().add(problem);
				}
			} catch (URISyntaxException ex) {
				ParseProblem problem = new ParseProblem();
				problem.setLine(import_.getRegion().getStart().getLine());
				problem.setReason("Imported URI is invalid: " + uri);
				getParseProblems().add(problem);
			}

			imports.add(import_);
		}

		return imports;
	}
	
	protected void checkImports(AST cst) {
		for (AST importAst : AstUtil.getChildren(cst, EolParser.IMPORT)) {
			String importedFile = importAst.getFirstChild().getText();
			boolean validExtension = false;
			for (String extension : getImportConfiguration().keySet()) {
				if (importedFile.endsWith("." + extension)) {
					validExtension = true;
				}
			}
			if (!validExtension) {
				ParseProblem problem = new ParseProblem();
				problem.setLine(importAst.getLine());
				problem.setReason("Importing " + importAst.getFirstChild().getText() + " is not supported in this language");
				problem.setSeverity(ParseProblem.WARNING);
				getParseProblems().add(problem);
			}
		}		
	}

	@Override
	public List<ModelDeclaration> getDeclaredModelDeclarations() {
		return declaredModelDeclarations;
	}

	@Override
	public IEolModule getParentModule() {
		return parent;
	}

	@Override
	public void setParentModule(IEolModule parent) {
		this.parent = parent;
	}
	
	@Override
	public Object execute() throws EolRuntimeException {
		IEolContext context = getContext();
		prepareContext();
		return context.getExecutorFactory().execute(this, context);
	}
	
	public Object executeImpl() throws EolRuntimeException {
		IEolContext context = getContext();
		return Return.getValue(context.getExecutorFactory().execute(main, context));
	}
	
	@Override
	public List<Statement> getPostOperationStatements() {
		return postOperationStatements;
	}
	
	@Override
	public StatementBlock getMain() {
		return main;
	}

	public void setMain(StatementBlock main) {
		this.main = main;
	}
	
	@Override
	public IEolContext getContext() {
		return context;
	}

	@Override
	public void setContext(IEolContext context) {
		if (context != null && !expectedContextType.isInstance(context)) {
			throw new IllegalArgumentException(
				"Invalid context type: expected "+expectedContextType.getName()
				+ " but got "+context.getClass().getName()
			);
		}
		if (this.context != context) {
			this.context = context;
			
			if (context.getModule() == null) {
				context.setModule(this);
			}
			
			for (Import import_ : getImports()) {
				import_.setContext(context);
			}
		}
	}

	/**
	 * Clear all cached results and type information, and all extended
	 * properties. Useful for rerunning the same EolModule with different sets
	 * of models, without having to parse it again.
	 */
	public void clearCache() {
		for (Operation op : getOperations()) {
			op.clearCache();
		}
		getContext().getExtendedProperties().clear();
	}
	
	@Override
	public IImportManager getImportManager() {
		if (importManager == null) {
			importManager = new ImportManager();
		}
		return importManager;
	}
	
	@Override
	public void setImportManager(IImportManager importManager) {
		this.importManager = importManager;
	}
}
