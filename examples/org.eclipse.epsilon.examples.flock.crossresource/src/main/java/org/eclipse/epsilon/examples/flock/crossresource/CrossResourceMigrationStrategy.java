package org.eclipse.epsilon.examples.flock.crossresource;

import java.util.Map;

import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstructs;

public class CrossResourceMigrationStrategy extends MigrationStrategy {
	
	protected Map<IReflectiveModel, IReflectiveModel> modelMap = null;

	public CrossResourceMigrationStrategy(Map<IReflectiveModel, IReflectiveModel> modelMap) {
		super();
		this.modelMap = modelMap;
	}
	
	@Override
	public TypeMappingConstructs getTypeMappingConstructs() {
		if (typeMappingConstructs == null) {
			typeMappingConstructs = new CrossResourceTypeMappingConstructs(modelMap);
		}
		return typeMappingConstructs;
	}
	
}
