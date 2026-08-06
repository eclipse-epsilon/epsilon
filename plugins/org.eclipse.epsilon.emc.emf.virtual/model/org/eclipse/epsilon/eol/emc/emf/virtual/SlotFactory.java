/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.emc.emf.virtual;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.emc.emf.virtual.BooleanSlot;
import org.eclipse.epsilon.emc.emf.virtual.FloatSlot;
import org.eclipse.epsilon.emc.emf.virtual.IntegerSlot;
import org.eclipse.epsilon.emc.emf.virtual.ReferenceSlot;
import org.eclipse.epsilon.emc.emf.virtual.Slot;
import org.eclipse.epsilon.emc.emf.virtual.StringSlot;
import org.eclipse.epsilon.emc.emf.virtual.VirtualFactory;
import org.eclipse.epsilon.emc.emf.virtual.VirtualObject;

class SlotFactory {
	
	private static final SlotFactory instance = new SlotFactory();
	
	private SlotFactory() {}
	
	public static SlotFactory getInstance() {
		return instance;
	}
	
	private Slot createSlot(Object referenceValue, Object value) {
		final List<Object> values = new ArrayList<>();
		values.add(value);
		return createSlot(referenceValue, values);
	}
	
	private Slot createSlot(Object referenceValue, List<?> values) {
		if (referenceValue instanceof VirtualObject) {
			final ReferenceSlot slot = VirtualFactory.eINSTANCE.createReferenceSlot();
			
			for (Object value : values)
				slot.getValues().add((VirtualObject)value);
			
			return slot;
			
		} else if (referenceValue instanceof String) {
			final StringSlot slot = VirtualFactory.eINSTANCE.createStringSlot();
			
			for (Object value : values)
				slot.getValues().add((String)value);
			
			return slot;
		
		} else if (referenceValue instanceof Integer) {
			final IntegerSlot slot = VirtualFactory.eINSTANCE.createIntegerSlot();
			
			for (Object value : values)
				slot.getValues().add((Integer)value);
			
			return slot;
		
		} else if (referenceValue instanceof Boolean) {
			final BooleanSlot slot = VirtualFactory.eINSTANCE.createBooleanSlot();
			
			for (Object value : values)
				slot.getValues().add((Boolean)value);
			
			return slot;
			
		} else if (referenceValue instanceof Float) {
			final FloatSlot slot = VirtualFactory.eINSTANCE.createFloatSlot();
			
			for (Object value : values)
				slot.getValues().add((Float)value);
			
			return slot;	
		}
		
		return null;
	}
	
	public Slot createSlotFor(Object value) {
		
		if (value instanceof List<?>) {
			final List<?> list = (List<?>)value;
			
			// Empty lists not supported yet (can't infer type of Slot required)
			if (list.isEmpty())
				return null;
			
			return createSlot(list.get(0), list);
			
		} else {
			return createSlot(value, value);
		}
	}

	private Object getValue(EList<?> values) {
		if (values.size() > 1) {
			final List<Object> list = new ArrayList<>();
			
			for (Object value : values) {
				list.add(value);
			}
			
			return list;
		
		} else {
			return values.get(0);
		}
	}
	
	public Object getValueFrom(Slot slot) {
		final Object value;
		
		if (slot instanceof ReferenceSlot) {		
			value = getValue(((ReferenceSlot)slot).getValues());
		
		} else if (slot instanceof StringSlot) {		
			value = getValue(((StringSlot)slot).getValues());
		
		} else if (slot instanceof IntegerSlot) {
			value = getValue(((IntegerSlot)slot).getValues());
		
		} else if (slot instanceof BooleanSlot) {
			value = getValue(((BooleanSlot)slot).getValues());
		
		} else if (slot instanceof FloatSlot) {
			value = getValue(((FloatSlot)slot).getValues());
			
		} else {
			return null;
		}
		
		return value;
	}

}
