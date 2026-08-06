/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml;

import org.eclipse.epsilon.emc.muddle.Feature;
import org.eclipse.epsilon.emc.muddle.MuddleFactory;
import org.eclipse.epsilon.emc.muddle.PrimitiveTypes;


public class ValuedSlotFeatureLabelParser {
	
	protected Feature feature;
	protected Object value;
	
	public static void main(String[] args) {
		new ValuedSlotFeatureLabelParser("int[] x = 5").getFeature();
	}
	
	public ValuedSlotFeatureLabelParser(String label) {
		
		String featureLabel = "";
		String valueLabel = "";
		
		// Check if there is an = anywhere in the label
		// If there is, split into a prototype and a value label
		int eq = label.indexOf('=');
		
		if (eq > -1) {
			featureLabel = label.substring(0, eq).trim();
			valueLabel = label.substring(eq+1, label.length()).trim();
		}
		else {
			featureLabel = label;
			valueLabel = "";
		}
		
		Parser typeAndMultiplicityParser = new Parser("(\\S+?)\\s*\\[.*?\\]\\s+(\\S+)");
		Parser typeParser = new Parser("(\\S+?)\\s+(\\S+)");
		
		String name = "";
		String typeLabel = null;
		boolean many = false;
		
		if (typeAndMultiplicityParser.matches(featureLabel)) {
			many = true;
			name = typeAndMultiplicityParser.getGroups().get(1);
			typeLabel = typeAndMultiplicityParser.getGroups().get(0);
		} else if (typeParser.matches(featureLabel)) {
			name = typeParser.getGroups().get(1);
			typeLabel = typeParser.getGroups().get(0);
		}
		else {
			name = featureLabel;
		}
				
		feature = MuddleFactory.eINSTANCE.createFeature();
		feature.setName(name);
		feature.setMany(many);
		
		if (typeLabel != null) {
			if (typeLabel.equalsIgnoreCase("Integer") || typeLabel.equals("int")) feature.setType(PrimitiveTypes.getIntegerType());
			else if (typeLabel.equalsIgnoreCase("Boolean") || typeLabel.equals("bool")) feature.setType(PrimitiveTypes.getBooleanType());
			else if (typeLabel.equalsIgnoreCase("Real") || typeLabel.equalsIgnoreCase("real")) feature.setType(PrimitiveTypes.getRealType());
			else feature.setType(PrimitiveTypes.getStringType());
		}
		
		value = valueLabel;
		
	}
	
	public Feature getFeature() {
		return feature;
	}
	
	public Object getValue() {
		return value;
	}
	
}
