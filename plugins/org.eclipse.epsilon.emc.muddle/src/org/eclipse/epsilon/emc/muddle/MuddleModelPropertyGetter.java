/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.muddle;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;

public class MuddleModelPropertyGetter extends JavaPropertyGetter {
	
	protected MuddleModel model;
	
	public MuddleModelPropertyGetter(MuddleModel model) {
		this.model = model;
	}
	
	@Override
	public Object invoke(Object o, String property, IEolContext context) throws EolRuntimeException {
		
		MuddleElement element = (MuddleElement) o;
		Feature feature = getFeature(element, property);
		if (feature == null) return super.invoke(o, property, context);
		else model.getUnusedFeatures().remove(feature);
		
		Slot slot = getSlot(element, feature);
		if (slot == null) {
			slot = MuddleFactory.eINSTANCE.createSlot();
			slot.setFeature(feature);
			element.getSlots().add(slot);
		}
		
		if (feature.isMany()) {
			return slot.getValues();
		}
		else {
			if (slot.getValues().size() > 0) {
				return slot.getValues().get(0);
			}
			else {
				return getDefaultSlotValue(feature);
			}
		}
	}
	
	protected Object getDefaultSlotValue(Feature feature) {
		if (feature.getType() instanceof IntegerType) return 0;
		else if (feature.getType() instanceof StringType) return "";
		else if (feature.getType() instanceof RealType) return 0.0d;
		else if (feature.getType() instanceof BooleanType) return false;
		else return null;
	}
	
	protected Slot getSlot(MuddleElement element, Feature feature) {
		for (Slot slot : element.getSlots()) {
			if (slot.getFeature() == feature) {
				return slot;
			}
		}
		return null;
	}
	
	protected Feature getFeature(MuddleElement element, String property) {
		for (Feature feature : element.getType().getFeatures()) {
			if (feature.getName().equals(property)) {
				return feature;
			}
		}
		return null;
	}
	
}
