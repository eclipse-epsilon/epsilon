/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eunit.dt.diff.emf.v3;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.domain.ICompareEditingDomain;
import org.eclipse.emf.compare.domain.impl.EMFCompareEditingDomain;
import org.eclipse.emf.compare.ide.ui.internal.configuration.EMFCompareConfiguration;
import org.eclipse.emf.compare.ide.ui.internal.editor.ComparisonEditorInput;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.epsilon.eunit.dt.diff.IDifferenceViewer;

public class EMFDifferenceViewer implements IDifferenceViewer {

	@Override
	public boolean canCompare(Object expected, Object actual, Object delta) {
		return delta instanceof Comparison;
	}

	@Override
	public void compare(Object expected, Object actual, Object delta) {
		final Comparison snap = (Comparison) delta;
		final Resource left = snap.getMatchedResources().get(0).getLeft();
		final Resource right = snap.getMatchedResources().get(0).getRight();

		final ICompareEditingDomain editingDomain = EMFCompareEditingDomain.create(left, right, null);
		final AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final ComparisonEditorInput input = new ComparisonEditorInput(
			new EMFCompareConfiguration(new CompareConfiguration()), snap, editingDomain, adapterFactory);
		
		CompareUI.openCompareDialog(input);
	}


}
