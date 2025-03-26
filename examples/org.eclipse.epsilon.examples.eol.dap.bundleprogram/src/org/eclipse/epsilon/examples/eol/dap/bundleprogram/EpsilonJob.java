package org.eclipse.epsilon.examples.eol.dap.bundleprogram;

import java.net.URL;
import java.nio.file.Paths;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.eol.dt.launching.EclipseContextManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class EpsilonJob extends Job {

	private boolean isDebug = false;

	public EpsilonJob(String name) {
		super(name);
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		// It's best to map the entire bundle rather than an individual script, in case there are imports
		URL bundleUrl = FileLocator.find(Activator.getDefault().getBundle(), new Path("/"));
		URL scriptUrl = FileLocator.find(Activator.getDefault().getBundle(), new Path("epsilon/main.eol"));

		EolModule module = new EolModule();
		try {
			EclipseContextManager.setup(module.getContext());
			module.parse(scriptUrl);

			if (isDebug) {
				EpsilonDebugServer server = new EpsilonDebugServer(module, 0);

				// Note: this mapping may only work when running the bundle from source
				java.nio.file.Path mappedPath = Paths.get(FileLocator.toFileURL(bundleUrl).toURI());
				server.getDebugAdapter().getUriToPathMappings().put(
					bundleUrl.toURI(),
					mappedPath);

				server.setOnStart(() -> {
					Display.getDefault().asyncExec(() -> {
						MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Debug server running", String.format(
							"Debug server running on port %d and waiting for a connection.\n"
								+ "Bundle URL is: %s\n"
								+ "Mapped path is: %s",
							server.getPort(),
							bundleUrl,
							mappedPath
						));
					});
				});

				server.run();
			} else {
				module.execute();
			}
		} catch (Exception e) {
			return Status.error(e.getMessage(), e);
		}
		return Status.OK_STATUS;
	}

}
