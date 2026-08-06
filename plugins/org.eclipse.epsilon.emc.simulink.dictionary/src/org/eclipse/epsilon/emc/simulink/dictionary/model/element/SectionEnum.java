/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.model.element;

import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;

public enum SectionEnum {

	DESIGN_DATA("Design Data", "DesignDataEntry"), 
	OTHER_DATA("Other Data", "OtherDataEntry"), 
	CONFIGURATIONS("Configurations", "ConfigurationsEntry"),
	//EMBEDDED_CODER("EmbeddedCoder", "EmbeddedCoderEntry") // this returns either null or requires specific ways of configuring see coder.Section coder.Dictionary etc
	;

	private String MatlabKey;
	private String epsilonEntryName;

	private SectionEnum(String MatlabKey, String epsilonEntryName) {
		this.MatlabKey = MatlabKey;
		this.epsilonEntryName = epsilonEntryName;
	}

	public SimulinkSection getFromModel(SimulinkDictionaryModel model){
		try {
			HandleObject sectionHandle = (HandleObject) model.getEngine().fevalWithResult("getSection", model.getHandle().getHandle(), this.MatlabKey);
			return new SimulinkSection(model, model.getEngine(), sectionHandle); 
		} catch (MatlabException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getMatlabKey() {
		return MatlabKey;
	}
	
	public String getEpsilonEntryName() {
		return epsilonEntryName;
	}

	public static SectionEnum forEpsilonEntryType(String type) {
		switch (type) {
		case "DesignDataEntry":
			return DESIGN_DATA;
		case "OtherDataEntry":
			return OTHER_DATA;
		case "ConfigurationsEntry":
			return CONFIGURATIONS;
		/*case "EmbeddedCoderEntry":
			return EMBEDDER_CODER;*/
		default:
			return null;
		}
	}

	public static SectionEnum fromMatlab(String property) {
		for (SectionEnum s : SectionEnum.values()) {
			if (s.MatlabKey.equals(property)) {
				return s;
			}
		}
		return null;
	}
}
