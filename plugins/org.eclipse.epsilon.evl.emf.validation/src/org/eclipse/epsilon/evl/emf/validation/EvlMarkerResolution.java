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

import org.eclipse.core.resources.IMarker;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.ui.IMarkerResolution;

public class EvlMarkerResolution implements IMarkerResolution {
	
	protected String label;
	protected FixInstance fix;
	protected String elementId;
	protected String modelName;
	protected String ePackageUri;
	
	public EvlMarkerResolution(String elementId, FixInstance fix, String modelName, String ePackageUri) {
		try {
			this.label = fix.getTitle();
			this.fix = fix;
			this.elementId = elementId;
			this.modelName = modelName;
			this.ePackageUri = ePackageUri;
		} catch (EolRuntimeException e) {
			e.printStackTrace();
		}
	}

	public String getLabel() {
		return label;
	}

	public void run(IMarker marker) {
		EvlMarkerResolverManager.INSTANCE.run(marker, this);
	}

	public FixInstance getFix() {
		return fix;
	}

	public String getElementId() {
		return elementId;
	}

	public String getModelName() {
		return modelName;
	}

	public String getePackageUri() {
		return ePackageUri;
	}
	
	
	
}
