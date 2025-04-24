package org.eclipse.epsilon.examples.flock.crossresource;

import java.util.Map;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.NoEquivalence;
import org.eclipse.epsilon.flock.equivalences.TypeBasedEquivalence;
import org.eclipse.epsilon.flock.equivalences.factory.DefaultEquivalenceFactory;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.context.FlockContext;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;

public class CrossResourceEquivalenceFactory extends DefaultEquivalenceFactory {
	
	protected Map<IReflectiveModel, IReflectiveModel> modelMap = null;
	
	public CrossResourceEquivalenceFactory(Map<IReflectiveModel, IReflectiveModel> modelMap) {
		super();
		this.modelMap = modelMap;
	}

	@Override
	public Equivalence createEquivalence(IEolContext context, FlockExecution execution, ModelElement original, EquivalentFactory equivalentFactory) throws FlockRuntimeException {
		if (equivalentFactory.typeConformsToEvolvedMetamodel(original.getTypeName())) {
			return new TypeBasedEquivalence(context, execution, original, createEquivalent(equivalentFactory, original, context));
		} else {
			return new NoEquivalence(context, execution, original);
		}
	}
	
	protected ModelElement createEquivalent(EquivalentFactory equivalentFactory, ModelElement original, IEolContext context) throws FlockRuntimeException {
		Object originalElement = original.unwrap();
		Model migratedModel = ((FlockContext) context).getMigratedModel();
		
		for (IReflectiveModel originalModel : modelMap.keySet()) {
			if (originalModel.owns(originalElement)) {
				try {
					return (ModelElement) migratedModel.wrap(modelMap.get(originalModel).createInstance(original.getTypeName()));
				
				} catch (EolRuntimeException e) {
					throw new FlockRuntimeException("Could not create in the migrated model a model element of type: " + original.getTypeName(), e);
				}
				
			}
		}
		
		return equivalentFactory.createModelElementInMigratedModel(original.getTypeName());
	}
	
}
