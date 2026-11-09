/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class CoverageDtPlugin extends AbstractUIPlugin {
	
	public static final String PLUGIN_ID = "org.eclipse.epsilon.coverage.dt";
	
	/** The id of the coverage launch mode */
	public static final String COVERAGE_MODE = "coverage";
	
	public static final String LAUNCH_GROUP_ID = PLUGIN_ID + ".launchGroup";
	
	public static final String VIEW_ID = PLUGIN_ID + ".CoverageView";
	
	public static final String IMG_COVERAGE = "icons/coverage.png";
	public static final String IMG_COVERED = "icons/full_obj.png";
	public static final String IMG_PARTIAL = "icons/partial_obj.png";
	public static final String IMG_MISSED = "icons/missed_obj.png";
	
	private static CoverageDtPlugin plugin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	public static CoverageDtPlugin getDefault() {
		return plugin;
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		for (String path : new String[] {IMG_COVERAGE, IMG_COVERED, IMG_PARTIAL, IMG_MISSED}) {
			registry.put(path, imageDescriptorFromPlugin(PLUGIN_ID, path));
		}
	}
	
	public static Image getImage(String path) {
		return getDefault().getImageRegistry().get(path);
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return getDefault().getImageRegistry().getDescriptor(path);
	}
	
	public static void log(Throwable t) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, t.getMessage() == null ? t.toString() : t.getMessage(), t));
	}
}
