/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.util;

import java.io.File;
import java.util.UUID;

import org.eclipse.epsilon.emc.simulink.common.test.AbstractCommonSimulinkTest;
import org.eclipse.epsilon.emc.simulink.common.test.MatlabEngineSetupEnum;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.eol.exceptions.EolAssertionException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class AbstractSimulinkTest extends AbstractCommonSimulinkTest {

	@Override
	public IGenericSimulinkModel loadSimulinkModel(File file, boolean activeCaching) throws Exception {
		SimulinkModel model = getModel();
		model.setName("M");
		if (file != null) {
			model.setFile(file);
			model.setReadOnLoad(true);
			model.setWorkingDir(file.getParentFile());
		} else {
			model.setFile(new File("model" + String.valueOf(UUID.randomUUID()).replace("-", "") + ".slx"));
			model.setWorkingDir(new File(System.getProperty("user.dir")));
			model.setReadOnLoad(false);
		}
		model.setStoredOnDisposal(false);
		model.setOpenOnLoad(false);
		model.setCloseOnDispose(true);
		model.setCachingEnabled(activeCaching);
		String version = installation.getVersion();
		try {
			String engine = MatlabEngineSetupEnum.ENGINE_JAR.path(version);
			System.out.println(engine);
			model.setEngineJarPath(engine);
		} catch (EolAssertionException e) {
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e) {
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
	public SimulinkModel getModel() {
		return new SimulinkModel();
	}

	@Override
	public void dispose(IGenericSimulinkModel model) {
		model.dispose();
	}

}
