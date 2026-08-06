/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.suite;

import java.util.Collections;
import java.util.List;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEnginePool;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Don't run {@link SimulinkTestSuite} if MATLAB is not available.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ConditionalMatlabSuite extends Suite {
	
	private static Boolean isMatlabWorking;
	
	public static boolean isMatlabWorking() {
		if (isMatlabWorking != null) return isMatlabWorking;
		try {
			if (MatlabEnginePool.resolveFromEnv()) {
				MatlabEnginePool.getInstance();
				MatlabEngine.startMatlab();
				// Deliberate assignment of the boolean, not accidental
				return isMatlabWorking = true;
			}
		}
		catch (Exception ex) {
			System.err.println("MATLAB was found on the system but is not working!");
			ex.printStackTrace();
		}
		// Deliberate assignment of the boolean, not accidental
		return isMatlabWorking = false;
	}
	
	@Override
	protected boolean isIgnored(Runner child) {
		return super.isIgnored(child) || !isMatlabWorking();
	}
	
	@Override
	protected List<Runner> getChildren() {
		if (!isMatlabWorking()) return Collections.emptyList();
		return super.getChildren();
	}
	
	public ConditionalMatlabSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
		// TODO Auto-generated constructor stub
	}

	public ConditionalMatlabSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
		super(builder, classes);
		// TODO Auto-generated constructor stub
	}

	public ConditionalMatlabSuite(Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
		super(klass, suiteClasses);
		// TODO Auto-generated constructor stub
	}

	public ConditionalMatlabSuite(Class<?> klass, List<Runner> runners) throws InitializationError {
		super(klass, runners);
		// TODO Auto-generated constructor stub
	}

	public ConditionalMatlabSuite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
		super(builder, klass, suiteClasses);
		// TODO Auto-generated constructor stub
	}
}
