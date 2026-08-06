/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simuink.dictionary.dt;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.jface.preference.IPreferenceStore;

public class SimulinkDictionaryModelInEclipse extends SimulinkDictionaryModel {
	
	public SimulinkDictionaryModelInEclipse() {
		IPreferenceStore prefs = EpsilonCommonsPlugin.getDefault().getPreferenceStore();
		this.matlabPath = prefs.getString(PROPERTY_MATLAB_PATH);
		this.libraryPath = prefs.getString(PROPERTY_LIBRARY_PATH);
		this.engineJarPath = prefs.getString(PROPERTY_ENGINE_JAR_PATH);
	}
	
}