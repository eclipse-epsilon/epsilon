/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;

public class EvlMarkerResolverManager implements IEvlMarkerResolver {
	
	public static EvlMarkerResolverManager INSTANCE = new EvlMarkerResolverManager();
	
	protected Collection<IEvlMarkerResolver> delegates = new ArrayList<>();
	
	private EvlMarkerResolverManager() {
		delegates.add(new XtextMarkerResolver());
		delegates.add(new EmfMarkerResolver());
		delegates.add(new SiriusMarkerResolver());
		try {
			Class.forName("org.eclipse.gmf.runtime.notation.View");
			delegates.add(new GmfMarkerResolver());
		} catch (ClassNotFoundException ex) {
			// GMF is not available - do not add the marker resolver, but log it somewhere (just in case)
			Activator.getDefault().getLog().log(
				new Status(IStatus.WARNING, Activator.PLUGIN_ID,
						"GMF is not available: disabling the GMF marker resolver", ex));
		}
	}
	
	@Override
	public boolean canResolve(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return true;
		}
		return false;
	}

	@Override
	public void run(IMarker marker, EvlMarkerResolution resolution) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker)) {
				delegate.run(marker, resolution);
				return;
			}
		}
	}
	
	@Override
	public EObject resolve(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.resolve(marker);
		}
		return null;
	}
	
	@Override
	public String getAbsoluteElementId(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getAbsoluteElementId(marker);
		}
		return null;
	}

	@Override
	public String getMessage(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getMessage(marker);
		}
		return null;
	}

	@Override
	public EditingDomain getEditingDomain(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getEditingDomain(marker);
		}
		return null;
	}

	public EditingDomain getEditingDomain(IEditorPart editor) {
		return null;
	}

}
