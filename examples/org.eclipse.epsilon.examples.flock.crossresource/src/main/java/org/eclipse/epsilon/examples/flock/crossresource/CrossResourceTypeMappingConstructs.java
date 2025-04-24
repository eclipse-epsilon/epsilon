package org.eclipse.epsilon.examples.flock.crossresource;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstruct;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstructs;

public class CrossResourceTypeMappingConstructs extends TypeMappingConstructs {
	
	public CrossResourceTypeMappingConstructs(Map<IReflectiveModel, IReflectiveModel> modelMap, TypeMappingConstruct... typeMappings) {
		super(new CrossResourceEquivalenceFactory(modelMap));
		this.typeMappingConstructs.addAll(Arrays.asList(typeMappings));
	}
	
}
