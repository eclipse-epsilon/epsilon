/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emf.dt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.emf.EmfUtil;

public class EmfRegistryManager {

	protected static EmfRegistryManager instance = null;
	protected HashMap<String, List<EPackage>> managedMetamodels = new HashMap<>();

	public static EmfRegistryManager getInstance() {
		if (instance == null) {
			instance = new EmfRegistryManager();
		}
		return instance;
	}

	private EmfRegistryManager() {

		for (String metamodel : getMetamodels()) {
			try {
				registerMetamodel(metamodel);
			} catch (Exception ex) {
				LogUtil.log(ex);
			}
		}
		initChangeListener();
	}

	protected void initChangeListener() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				// we are only interested in POST_CHANGE events
				if (event.getType() != IResourceChangeEvent.POST_CHANGE)
					return;
				IResourceDelta rootDelta = event.getDelta();
				// get the delta, if any, for the documentation directory
				// IResourceDelta docDelta = rootDelta.findMember(DOC_PATH);
				// if (docDelta == null)
				// return;
				final ArrayList<IResourceDelta> changed = new ArrayList<>();
				IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta delta) {
						// only interested in changed resources (not added or
						// removed)
						/*
						 * if (delta.getKind() != IResourceDelta.CHANGED) return true; //only interested
						 * in content changes if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
						 * return true; IResource resource = delta.getResource(); //only interested in
						 * files with the "txt" extension if (resource.getType() == IResource.FILE &&
						 * "txt".equalsIgnoreCase(resource.getFileExtension())) { changed.add(resource);
						 * }
						 */
						changed.add(delta);
						return true;
					}
				};
				try {
					rootDelta.accept(visitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}

				if (changed.size() == 0)
					return;

				for (IResourceDelta delta : changed) {

					String metamodel = delta.getResource().getFullPath().toOSString();
					List<String> metamodels = getMetamodels();
					if (metamodels.contains(metamodel)) {
						if (delta.getKind() == IResourceDelta.REMOVED) {
							removeMetamodel(metamodel);
							if (delta.getMovedToPath() != null) {
								try {
									addMetamodel(delta.getMovedToPath().toOSString());
								} catch (Exception e) {
									LogUtil.log(e);
								}
							}
						} else {
							try {
								registerMetamodel(metamodel);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}

			}
		};
		workspace.addResourceChangeListener(listener);
	}

	public void addMetamodel(String fileName) throws Exception {
		registerMetamodel(fileName);
		List<String> metamodels = getMetamodels();
		if (!metamodels.contains(fileName)) {
			metamodels.add(fileName);
			setMetamodels(metamodels);
		}
	}

	private void registerMetamodel(String fileName) throws Exception {
		List<EPackage> ePackages;
		ePackages = EmfUtil.register(URI.createPlatformResourceURI(fileName, true), EPackage.Registry.INSTANCE);
		managedMetamodels.put(fileName, ePackages);
	}

	public void removeMetamodel(String fileName) {

		List<EPackage> ePackages = managedMetamodels.get(fileName);

		if (ePackages == null)
			return;

		for (EPackage ePackage : ePackages) {
			EPackage.Registry.INSTANCE.remove(ePackage.getNsURI());
		}

		managedMetamodels.remove(fileName);
		List<String> metamodels = getMetamodels();
		metamodels.remove(fileName);
		setMetamodels(metamodels);

	}

	public List<String> getMetamodels() {
		List<String> metamodels = new ArrayList<>();
		String concat = EmfUtilPlugin.getDefault().getPreferenceStore().getString("metamodels");
		for (
			StringTokenizer st = new StringTokenizer(concat, ";");
			st.hasMoreTokens();
			metamodels.add(st.nextToken())
		);
		return metamodels;
	}

	private void setMetamodels(List<String> metamodels) {
		String mmStr = metamodels.stream().collect(Collectors.joining(";"));
		EmfUtilPlugin.getDefault().getPreferenceStore().setValue("metamodels", mmStr);
	}
}
