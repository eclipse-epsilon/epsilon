/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id:$
 */
package org.eclipse.epsilon.test.fixtures.hutn;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.IHutnModule;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;

public abstract class AbstractEmfModelConstructor {
	
	private static IHutnModule hutnModule; 
	
	protected static boolean parse(String hutn) {
		try {
			hutnModule = new HutnModule();
			
			if (hutnModule.parse(hutn)) {
				return true;	
			}
			
			for (ParseProblem problem : hutnModule.getParseProblems()) {
				System.err.println(problem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	protected static String addPreamble(String hutn, String nsUri, String configFile) {
		return addPreamble(hutn, Collections.singletonList(nsUri), Collections.singletonList(configFile));
	}
	
	protected static String addPreamble(String hutn, List<String> nsUris, List<String> configFiles) {
		String preamble = "@Spec {";
		
		for (int index = 0; index < nsUris.size(); index++) {
			preamble += "MetaModel \"MetaModel\" {";
			preamble += "	nsUri = \"" + nsUris.get(index) + "\"";
			preamble +=     getConfigFilePreamble(configFiles.get(index));
			preamble += "}";
		}
		
		preamble += "}";
		
		return preamble + hutn;
	}

	protected static String getConfigFilePreamble(String configFile) {
		return (configFile == null) ? "" : "configFile = \"" + configFile + "\"";
	}
	
	public AbstractEmfModel constructModel(String hutn) {
		try {
			if (parse(addPreamble(hutn, getNsUris(), getConfigFiles()))) {
				return hutnModule.generateEmfModel();	
			}
		} catch (HutnGenerationException e) {
			e.printStackTrace();
		} 

		return null;
	}
	
	public AbstractEmfModel constructModel(String name, String hutn) {
		final AbstractEmfModel model = constructModel(hutn);
		model.setName(name);
		return model;
	}

	protected abstract List<String> getNsUris();
	protected abstract List<String> getConfigFiles();
}
