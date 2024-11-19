/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.test;

import java.io.File;
import java.util.UUID;

import org.eclipse.epsilon.emc.simulink.common.test.AbstractCommonSimulinkTest;
import org.eclipse.epsilon.emc.simulink.common.test.MatlabEngineSetupEnum;
import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class AbstractSimulinkDictionaryTest extends AbstractCommonSimulinkTest {

	@Override
	public IGenericSimulinkModel loadSimulinkModel(File file, boolean activeCaching) throws Exception {
		SimulinkDictionaryModel model = getModel();
		model.setName("M");
		if (file != null) {
			model.setFile(file);
			model.setReadOnLoad(true);
		} else {
			model.setFile(new File("model" + String.valueOf(UUID.randomUUID()).replace("-", "") + ".slx"));
			model.setReadOnLoad(false);
		}
		model.setStoredOnDisposal(false);
		model.setCachingEnabled(activeCaching);
		String version = installation.getVersion();
		try {
			String engine = MatlabEngineSetupEnum.ENGINE_JAR.path(version);
			System.out.println(engine);
			model.setEngineJarPath(engine);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		try {
			model.load();
		} catch (EolModelLoadingException e) {
			e.printStackTrace();
			throw e;
		}
		return model;
	}

	@Override
	public SimulinkDictionaryModel getModel() {
		return new SimulinkDictionaryModel();
	}

	@Override
	public void dispose(IGenericSimulinkModel model) {
		// TODO Auto-generated method stub
		
	}


}
