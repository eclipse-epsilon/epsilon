package org.eclipse.epsilon.examples.flock.crossresource;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;

public class CrossResourceFlockModule extends FlockModule {
	
	protected Map<IReflectiveModel, IReflectiveModel> modelMap = new HashMap<IReflectiveModel, IReflectiveModel>();
	
	@Override
	protected MigrationStrategy createMigrationStrategy() {
		return new CrossResourceMigrationStrategy(modelMap);
	}
	
	public Map<IReflectiveModel, IReflectiveModel> getModelMap() {
		return modelMap;
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		ModuleElement moduleElement = super.adapt(cst, parentAst);
		if (moduleElement instanceof Retyping) return new CrossResourceRetyping(modelMap);
		else return moduleElement;
	}
	
}
