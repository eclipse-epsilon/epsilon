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
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.emf.EmfPrettyPrinter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class EvlMarkerResolutionGenerator implements IMarkerResolutionGenerator {
	
	public static EvlMarkerResolutionGenerator INSTANCE = new EvlMarkerResolutionGenerator();
	
	protected Map<String, Collection<FixInstance>> resolutions = new HashMap<>();
	
	protected Map<FixInstance, String> messages = new HashMap<>();
	
	protected Map<FixInstance, String> modelNames = new HashMap<>();
	
	protected Map<FixInstance, String> ePackageUris = new HashMap<>();
	
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		
		if (this != INSTANCE) return INSTANCE.getResolutions(marker);
		
		if (!EvlMarkerResolverManager.INSTANCE.canResolve(marker)) return new IMarkerResolution[]{};
		
		ArrayList<EvlMarkerResolution> resolutions = new ArrayList<>();
		String elementId = "";
		
		try {
			elementId = EvlMarkerResolverManager.INSTANCE.getAbsoluteElementId(marker);
			String message = EvlMarkerResolverManager.INSTANCE.getMessage(marker);
			
			if (this.resolutions.get(elementId) != null) {
				for (FixInstance fix : this.resolutions.get(elementId)) {
					// Use endsWith instead of equals as EMF prepends [platform:/...] to the
					// actual error message in elements from imported resources
					if (message.endsWith(messages.get(fix))) {
						resolutions.add(new EvlMarkerResolution(elementId, fix, modelNames.get(fix), ePackageUris.get(fix)));
					}
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		IMarkerResolution[] iMarkerResolutions = new IMarkerResolution[resolutions.size() + 1];
	
		int i = 0;
		for (IMarkerResolution resolution : resolutions) {
			iMarkerResolutions[i++] = resolution;
		}
		
		iMarkerResolutions[iMarkerResolutions.length - 1] = new IMarkerResolution() {

			public String getLabel() {
				return "Ignore";
			}

			public void run(IMarker marker) {
				// Just (try to) delete the marker
				try {
					marker.delete();
				} catch (CoreException e) {
					LogUtil.log(e);
				}
			}
			
		};
		
		return iMarkerResolutions;
	}

	protected EmfPrettyPrinter printer = new EmfPrettyPrinter();
	public void removeFixesFor(EObject eObject) {

		String eObjectId = getEObjectId(eObject);
		Collection<FixInstance> fixes = resolutions.remove(eObjectId);
		if (fixes != null) {
			for (FixInstance fix : fixes) {
				messages.remove(fix);
				modelNames.remove(fix);
			}
		}
	}
	
	public String getEObjectId(Object instance) {
		return EcoreUtil.getURI((EObject) instance).toString();
	}
	
	public void addResolution(String message, FixInstance fix, String modelName, String ePackageUri) {

		messages.put(fix, message);
		modelNames.put(fix, modelName);
		ePackageUris.put(fix, ePackageUri);
		
		String elementId = getEObjectId(fix.getSelf());
		Collection<FixInstance> fixes;
		if (resolutions.containsKey(elementId)) {
			fixes = resolutions.get(elementId);
		}
		else {
			fixes = new ArrayList<>();
			resolutions.put(elementId, fixes);
		}
		fixes.add(disconnect(fix));
	}
	
	protected FixInstance disconnect(FixInstance fix) {
		try {
			fix.getTitle();
		} catch (EolRuntimeException e) {
			LogUtil.log(e);
		} 
		fix.setSelf(null);
		fix.getContext().getModelRepository().getModels().clear();
		return fix;
	}
	
	
}
