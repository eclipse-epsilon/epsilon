/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.eunit.dt.ui.EUnitRunnerView;
import org.eclipse.epsilon.internal.eunit.dt.history.EUnitHistory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class EUnitPlugin extends AbstractUIPlugin implements EpsilonPlugin, ILaunchesListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.epsilon.eunit.dt"; //$NON-NLS-1$

	// The shared instance
	private static EUnitPlugin plugin;

	private final static String ICON_BASE_PATH = "$nl$/icons/";

	private ILaunch lastLaunch;

	private ILaunchManager launchManager;

	private EUnitHistory history;

	// Stores a reference to the last EUnit view created, for the commands
	private EUnitRunnerView lastView = null;

	// The attribute name to be used to set test filters (only use it on working copies!)
	private static final String LAUNCH_ATTR_SELECTED_TESTS = "org.eclipse.epsilon.eunit.dt.selected_tests";

	@SuppressWarnings("rawtypes")
	private static final List LAUNCH_ATTR_SELECTED_TESTS_DEFAULT = null;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		history = new EUnitHistory();

		launchManager = DebugPlugin.getDefault().getLaunchManager();
		launchManager.addLaunchListener(this);
		launchesAdded(launchManager.getLaunches());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EUnitPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public Image createImage(String path) {
		try {
			URL BASE_URL = getDefault().getBundle().getEntry("/");
			URL url = new URL(BASE_URL, path);
			return ImageDescriptor.createFromURL(url).createImage();
		}
		catch(MalformedURLException e) {}
		return null;
	}

	/**
	 * Produces an Image object from its path inside the plugin.
	 */
	public Image getIconImage(final String iconPath) {
		URL urlImgTest = FileLocator.find(getBundle(),
				new Path(EUnitPlugin.ICON_BASE_PATH + iconPath), null);
		return ImageDescriptor.createFromURL(urlImgTest).createImage();
	}

	/**
	 * Logs an exception as an error in the Error Log view.
	 * @param e Exception to be logged.
	 */
	public void logException(Exception e) {
		getLog().log(
			new Status(IStatus.ERROR, PLUGIN_ID, e.getLocalizedMessage(), e));
	}

	/**
	 * Returns the last Ant launch that has been performed, or <code>null</code> if
	 * none has been performed yet.
	 */
	public ILaunch getLastLaunch() {
		return lastLaunch;
	}

	@Override
	public void launchesRemoved(ILaunch[] launches) {
		// nothing to do
	}

	@Override
	public void launchesAdded(ILaunch[] launches) {
		updateLastLaunch(launches);
	}

	@Override
	public void launchesChanged(ILaunch[] launches) {
		updateLastLaunch(launches);
	}

	/**
	 * Returns the history of all EUnit launches since Eclipse was started.
	 */
	public EUnitHistory getHistory() {
		return history;
	}

	/**
	 * Returns the list of the operations to be run in a launch.
	 */
	@SuppressWarnings("rawtypes")
	public List getSelectedOperations(ILaunch launch) {
		List selOps = LAUNCH_ATTR_SELECTED_TESTS_DEFAULT;
		try {
			if (launch != null) {
				final ILaunchConfiguration lastLaunchConfig = launch.getLaunchConfiguration();
				selOps = lastLaunchConfig.getAttribute(
						LAUNCH_ATTR_SELECTED_TESTS,
						LAUNCH_ATTR_SELECTED_TESTS_DEFAULT);
			}
		} catch (CoreException e) {
			logException(e);
		}
		return selOps;
	}

	/**
	 * Sets the list of operations to be run in the launch with configuration <code>launchConfig</code>.
	 */
	@SuppressWarnings("rawtypes")
	public void setSelectedOperations(ILaunchConfigurationWorkingCopy launchConfig, List selOps) {
		launchConfig.setAttribute(EUnitPlugin.LAUNCH_ATTR_SELECTED_TESTS, selOps);
	}

	/**
	 * Updates the reference to the last view opened by the user. By default, it is a <code>null</code>
	 * reference. This method should only be called by the constructor of the {@link EUnitRunnerView} class.
	 */
	public void setLastView(EUnitRunnerView lastView) {
		this.lastView = lastView;
	}

	/**
	 * Obtains the reference to the last EUnit view opened by the user. If the EUnit view has not been
	 * opened yet since Eclipse was started, returns <code>null</code>.
	 */
	public EUnitRunnerView getLastView() {
		return lastView;
	}

	private void updateLastLaunch(ILaunch[] launches) {
		//FIXME: Changed IAntLaunchConstants.ID_ANT_LAUNCH_CONFIGURATION_TYPE with literal value to avoid unsatisfied dependency
		ILaunchConfigurationType antConfigType
			= launchManager.getLaunchConfigurationType("org.eclipse.ant.AntLaunchConfigurationType");

		for (ILaunch launch : launches) {
			if (launch.getLaunchConfiguration() == null) continue;

			// We're only interested in Ant launch configurations. Among them,
			// we will only pick those which have used the EUnit Ant task, by
			// later using the ShowEUnitViewTestListener class.
			ILaunchConfiguration config = launch.getLaunchConfiguration();
			try {
				if (!config.getType().equals(antConfigType)) continue;
				lastLaunch = launch;
			} catch (CoreException e) {
				logException(e);
			}
		}
	}
}
