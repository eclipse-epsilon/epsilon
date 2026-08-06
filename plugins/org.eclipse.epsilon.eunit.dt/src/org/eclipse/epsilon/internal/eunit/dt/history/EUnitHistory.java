/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.internal.eunit.dt.history;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;

/**
 * Class which tracks the results of all the EUnit tests run in this session.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class EUnitHistory {

	private final Map<ILaunch, List<EUnitModule>> launches = new LinkedHashMap<>();
	private ILaunch currentLaunch = null;

	public void addLaunch(ILaunch launch, EUnitModule module) {
		if (launches.containsKey(launch)) {
			launches.get(launch).add(module);
		}
		else {
			final List<EUnitModule> newList = new ArrayList<>();
			newList.add(module);
			launches.put(launch, newList);
		}
		setCurrentLaunch(launch);
	}

	public Map<ILaunch, List<EUnitModule>> getAllLaunches() {
		return launches;
	}

	public ILaunch getCurrentLaunch() {
		return currentLaunch;
	}

	public void setCurrentLaunch(ILaunch launch) {
		currentLaunch = launch;
	}

	public List<EUnitModule> getModules(ILaunch launch) {
		return launches.get(launch);
	}

	/**
	 * Returns the aggregated result over all EUnit modules executed in a launch.
	 * The basic order is as follows: error if one module had an error, otherwise
	 * failure if one module had a failure, otherwise skipped if all modules were
	 * skipped, otherwise success. If the launch is not in the history of this instance,
	 * it will report that the launch has not been launched yet.
	 */
	public EUnitTestResultType getResult(ILaunch launch) {
		final List<EUnitModule> modules = getModules(launch);
		if (modules == null) {
			return EUnitTestResultType.NOT_RUN_YET;
		}

		boolean bHasFailure = false, bAllSkipped = true;
		for (EUnitModule m : modules) {
			try {
				final EUnitTestResultType result = m.getSuiteRoot().getResult();
				if (result == EUnitTestResultType.ERROR) {
					return result;
				}
				bAllSkipped = bAllSkipped && result == EUnitTestResultType.SKIPPED;
				bHasFailure = bHasFailure || result == EUnitTestResultType.FAILURE;
			} catch (EolRuntimeException e) {
				EUnitPlugin.getDefault().logException(e);
				// skip this module
			}
		}

		if (bHasFailure) {
			return EUnitTestResultType.FAILURE;
		}
		else if (bAllSkipped) {
			return EUnitTestResultType.SKIPPED;
		}
		else {
			return EUnitTestResultType.SUCCESS;
		}
	}
}
