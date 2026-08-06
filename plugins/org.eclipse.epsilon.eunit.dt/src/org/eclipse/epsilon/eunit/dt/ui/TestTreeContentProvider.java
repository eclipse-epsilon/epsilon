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

import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

/**
 * Content provider for the test tree shown in the EUnit view.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
class TestTreeContentProvider implements ITreeContentProvider {
	private EUnitTest rootTest = null;
	private final IViewSite viewSite;

	TestTreeContentProvider(IViewSite viewSite) {
		this.viewSite = viewSite;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		if (newInput instanceof EUnitTest) {
			rootTest = (EUnitTest)newInput;
		}
	}

	public void dispose() {}

	public Object[] getElements(Object parent) {
		if (parent.equals(viewSite)) {
			return getChildren(rootTest);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof EUnitTest) {
			return ((EUnitTest)child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof EUnitTest) {
			return ((EUnitTest)parent).getChildren().toArray();
		}
		else if (parent instanceof List) {
			return ((List<?>)parent).toArray();
		}
		else if (parent instanceof EUnitModule) {
			EUnitModule module = (EUnitModule)parent;
			try {
				return module.getSuiteRoot().getChildren().toArray();
			} catch (EolRuntimeException e) {
				EUnitPlugin.getDefault().logException(e);
			}
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		return getChildren(parent).length > 0;
	}
}
