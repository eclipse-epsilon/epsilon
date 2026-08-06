/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.ui;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Viewer filter which only selects tests that have failed. This viewer can be
 * enabled and disabled at will, using {@link #setEnabled(boolean)}, but the
 * filtered viewer must be refreshed elsewhere.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class ShowOnlyFailedTestsViewerFilter extends ViewerFilter {

	boolean isEnabled = false;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (isEnabled) {
			if (element instanceof EUnitTest) {
				EUnitTest t = (EUnitTest)element;
				return t.getResult().equals(EUnitTestResultType.ERROR)
					|| t.getResult().equals(EUnitTestResultType.FAILURE);
			}
			else if (element instanceof EUnitModule) {
				EUnitModule m = (EUnitModule)element;
				try {
					return select(viewer, m, m.getSuiteRoot());
				} catch (EolRuntimeException e) {
					EUnitPlugin.getDefault().logException(e);
					// fall back on the default case below
				}
			}
		}
		return true;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

}
