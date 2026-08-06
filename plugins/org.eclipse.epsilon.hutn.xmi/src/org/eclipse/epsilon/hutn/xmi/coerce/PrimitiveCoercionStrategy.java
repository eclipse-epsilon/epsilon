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
package org.eclipse.epsilon.hutn.xmi.coerce;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.emc.emf.util.EListUtil;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;

public class PrimitiveCoercionStrategy extends AbstractCoercionStrategy {

	public PrimitiveCoercionStrategy(AttributeSlot slot) {
		super(slot);
	}
	
	public boolean isApplicable() {
		return true;
	}
	
	public AttributeSlot coerce() {
		if (EListUtil.elementsAreAllOfSameType(coercedValues()))
			slot.setValues(coercedValues());
			
		return slot;
	}
	
	private EList<Object> coercedValues() {
		final EList<Object> coercedValues = new BasicEList<>();
			 
		for (String value : values) {
			coercedValues.add(new SerialisedValue(value).coerce());
		}
		
		return coercedValues;
	}

	
	private static class SerialisedValue {
		
		private final String value;
		
		public SerialisedValue(String value) {
			this.value = value;
		}
		
		public Object coerce() {
			if (isFloat(value)) {
				return toFloat(value);
			
			} else if (isInteger(value)) {
				return toInteger(value);
				
			} else if (isBoolean(value)) {
				return toBoolean(value);
			
			} else {
				return value;
			}
		}
		
		private boolean isBoolean(String value) {
			return value.matches("true|false");
		}
		
		private boolean toBoolean(String value) {
			return Boolean.parseBoolean(value);
		}
		
		private boolean isInteger(String value) {
			return value.matches("-?\\d+");
		}
		
		private int toInteger(String value) {
			return Integer.parseInt(value);
		}
		
		private boolean isFloat(String value) {
			//                    -? \d* (. \d+)?
			return value.matches("-?\\d*\\.\\d+");
		}
		
		private float toFloat(String value) {
			return Float.parseFloat(value);
		}	
	}
}
