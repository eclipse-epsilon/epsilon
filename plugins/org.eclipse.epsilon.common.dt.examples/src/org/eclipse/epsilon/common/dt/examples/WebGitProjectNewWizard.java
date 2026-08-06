/*******************************************************************************
 * Copyright (c) 2006, 2010 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.epsilon.common.dt.examples;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

abstract public class WebGitProjectNewWizard extends Wizard implements
		INewWizard, IExecutableExtension {

	private WizardNewProjectCreationPage wizardNewProjectCreationPage;
	private String pageName = "page";
	private String pageTitle;
	private String pageDescription;
	private String pageProjectName;
	private String projectServer;
	private String projectUrl;
	
	public WebGitProjectNewWizard(String pageTitle, String pageDescription, 
			String pageProjectName, String projectServer, String projectUrl) {
		
		super();

		this.pageTitle = pageTitle;
		this.pageDescription = pageDescription;
		this.pageProjectName = pageProjectName;
		this.projectUrl = projectUrl;
		this.projectServer = projectServer;
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean performFinish() {

		try {
			IRunnableWithProgress operation = new WorkspaceModifyOperation() {

				@Override
				public void execute(IProgressMonitor monitor)
						throws InterruptedException {
					try {
						monitor.beginTask("Creating project", 120);

						String projectName = wizardNewProjectCreationPage.getProjectName();

						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IProject project = workspace.getRoot().getProject(projectName);

						// If the project does not exist, we will create it
						// and populate it.
						if (!project.exists()) {
							monitor.worked(10);

							WebGitFolder exampleFolder = new WebGitFolder(projectServer, projectUrl);
							WebGitImportStructureProvider structureProvider = new WebGitImportStructureProvider();
							
							ImportOperation op = new ImportOperation(project.getFullPath(), exampleFolder, 
									structureProvider,
									pathString -> pathString, structureProvider.getChildren(exampleFolder));
							
							op.setContext(getShell());
							try {
								op.run(monitor);
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
							
						}

						// Now, we ensure that the project is open.
						project.open(monitor);
						monitor.worked(10);
						
						if (monitor.isCanceled()) {
							throw new InterruptedException();
						}

					} catch (CoreException e) {
						throw new RuntimeException(e);
					} finally {
						monitor.done();
					}
				}
			};

			getContainer().run(false, true, operation);

		} catch (InterruptedException e) {
			return false;

		} catch (Exception e) {
			LogUtil.log(e);
			return false;
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		wizardNewProjectCreationPage = new WizardNewImmuatableProjectCreationPage(getPageName());
		wizardNewProjectCreationPage.setTitle(getPageTitle());
		wizardNewProjectCreationPage.setDescription(getPageDescription());
		wizardNewProjectCreationPage.setInitialProjectName(getPageProjectName());

		this.addPage(wizardNewProjectCreationPage);
	}

	private String getPageName() {
		return pageName;
	}

	private String getPageTitle() {
		return pageTitle;
	}

	private String getPageDescription() {
		return pageDescription;
	}

	private String getPageProjectName() {
		return pageProjectName;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {}
	
}
