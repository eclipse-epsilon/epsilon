/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.flock.debug.FlockDebugger;
import org.eclipse.epsilon.flock.execute.FlockResult;
import org.eclipse.epsilon.flock.execute.context.FlockContext;
import org.eclipse.epsilon.flock.execute.context.IFlockContext;
import org.eclipse.epsilon.flock.model.domain.EquivalentAssignmentStatement;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRule;
import org.eclipse.epsilon.flock.model.domain.typemappings.Deletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageDeletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageRetyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstruct;
import org.eclipse.epsilon.flock.parse.FlockLexer;
import org.eclipse.epsilon.flock.parse.FlockParser;

public class FlockModule extends ErlModule implements IFlockModule {
	
	protected MigrationStrategy strategy;
	
	public FlockModule() {
		this(null);
	}
	
	public FlockModule(IFlockContext context) {
		super(context != null ? context : new FlockContext());
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new FlockLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new FlockParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
		return "flockModule";
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch(cst.getType()) {
			case FlockParser.GUARD:
				return new ExecutableBlock<>(Boolean.class);
			case FlockParser.DELETE:
				return new Deletion();
			case FlockParser.RETYPE:
				return new Retyping();
			case FlockParser.MIGRATE:
				return new MigrateRule();
			case FlockParser.RETYPEPACKAGE:
				return new PackageRetyping();
			case FlockParser.DELETEPACKAGE:
				return new PackageDeletion();
			case FlockParser.SPECIAL_ASSIGNMENT:
				return new EquivalentAssignmentStatement();
			case FlockParser.BLOCK:
				if (cst.getParent() != null && cst.getParent().getType() == FlockParser.MIGRATE)
					return new ExecutableBlock<>(Void.class);
			default:
				return super.adapt(cst, parentAst);
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		strategy = createMigrationStrategy();
		
		for (AST childAst : cst.getChildren()) {
			
			ModuleElement moduleElement = module.createAst(childAst, module);
			
			if (moduleElement instanceof MigrateRule) {
				strategy.addRule((MigrateRule) moduleElement);
			}
			else if (moduleElement instanceof Retyping || moduleElement instanceof PackageRetyping || moduleElement instanceof Deletion || moduleElement instanceof PackageDeletion) {
				strategy.addTypeMappingConstruct((TypeMappingConstruct) moduleElement);
			}
			
		}
	}
	
	protected MigrationStrategy createMigrationStrategy() {
		return new MigrationStrategy();
	}
	
	@Override
	public FlockResult execute() throws EolRuntimeException {
		return (FlockResult) super.execute();
	}
	
	@Override
	public FlockResult execute(IModel original, IModel migrated) throws EolRuntimeException {
		IFlockContext context = getContext();
		
		context.setOriginalModel(original);
		context.setMigratedModel(migrated);
			
		return execute();
	}
	
	@Override
	protected FlockResult processRules() throws EolRuntimeException {
		return getContext().execute(strategy);
	}
	
	@Override
	public MigrationStrategy getStrategy() {
		return strategy;
	}
	
	@Override
	public IFlockContext getContext() {
		return (IFlockContext) super.getContext();
	}

	@Override
	public EolDebugger createDebugger() {
		return new FlockDebugger();
	}

	
}
