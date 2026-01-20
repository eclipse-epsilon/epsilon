package org.eclipse.epsilon.examples.flock.crossresource;

import java.util.Map;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.TypeBasedEquivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.context.FlockContext;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;

public class CrossResourceRetyping extends Retyping {
	
protected Map<IReflectiveModel, IReflectiveModel> modelMap = null;
	
	public CrossResourceRetyping(Map<IReflectiveModel, IReflectiveModel> modelMap) {
		this.modelMap = modelMap;
	}
	
	@Override
	public Equivalence createEquivalence(IEolContext context, FlockExecution execution, ModelElement original,
			EquivalentFactory factory) throws FlockRuntimeException {
		
		Object originalElement = original.unwrap();
		Model migratedModel = ((FlockContext) context).getMigratedModel();
		
		for (IReflectiveModel originalModel : modelMap.keySet()) {
			if (originalModel.owns(originalElement)) {
				try {
					ModelElement equivalent = (ModelElement) migratedModel.wrap(modelMap.get(originalModel).createInstance(getEvolvedType()));
					return new TypeBasedEquivalence(context, execution, original, equivalent);
				} catch (EolRuntimeException e) {
					throw new FlockRuntimeException("Could not create in the migrated model a model element of type: " + original.getTypeName(), e);
				}
			}
		}
		
		return super.createEquivalence(context, execution, original, factory);
	}
	
}
