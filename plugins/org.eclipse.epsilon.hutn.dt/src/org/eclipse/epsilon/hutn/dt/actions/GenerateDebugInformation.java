/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.actions;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.epsilon.common.dt.actions.AbstractObjectActionDelegate;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.IHutnModule;
import org.eclipse.epsilon.hutn.dt.util.WorkspaceUtil;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.jface.action.IAction;

public class GenerateDebugInformation extends AbstractObjectActionDelegate {

	private static final ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
	
	private IHutnModule hutnModule;
	
	private IFolder debugDir;
	
	private String debugDirName;
	private String intermediateModelName;
	private String transformationName;
	private String reusableTransformationName;
	private String launchConfigName;
	private String targetModelName;
	
	@Override
	public void run(IAction action) {
		try {
			if (getFirstElementInSelection() instanceof IFile) {
				final IFile file = (IFile)getFirstElementInSelection();
				
				hutnModule = new HutnModule();
				hutnModule.setConfigFileDirectory(WorkspaceUtil.getAbsolutePath(file.getParent()));
				
				
				if (hutnModule.parse(file.getRawLocationURI()) && hutnModule.getParseProblems().isEmpty()) {
					
					determineNames(file);
					
					// Initialise debug directory
					emptyOrCreateDebugDir(file.getProject().getFolder(debugDirName));

					// Store intermediate model and transformation
					hutnModule.storeIntermediateModel(createFileInDebugDir(intermediateModelName));
					hutnModule.storeIntermediateModelTransformation(createFileInDebugDir(transformationName));
					hutnModule.storeIntermediateModelTransformationForAllInputModels(createFileInDebugDir(reusableTransformationName));
					
					storeLaunchConfiguration();
					
					LogUtil.logInfo("Generated debug info for " + file.getLocation().toFile());
					refreshProjectContaining(file);

				} else {
					LogUtil.logInfo("Cannot generate debug info because HUTN cannot be parsed.", true);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void determineNames(IFile file) {
		final String sourceFileName = file.getLocation().removeFileExtension().lastSegment();
		
		debugDirName               = sourceFileName+"-hutn-debug";
		intermediateModelName      = sourceFileName + "-Intermediate.model";
		transformationName         = "Intermediate2" + sourceFileName + ".etl";
		reusableTransformationName = "Intermediate2" + sourceFileName + "Complete.etl";
		targetModelName            = "Debug-" + sourceFileName + ".model";
		launchConfigName           = "Intermediate2" + sourceFileName;
	}
	
	private void emptyOrCreateDebugDir(IFolder location) throws CoreException {
		debugDir = location;
		
		if (debugDir != null) {
			if (debugDir.exists()) {				
				for (IResource member : debugDir.members()) {
					if ((launchConfigName+".launch").equals(member.getName())) {
						launchManager.getLaunchConfiguration((IFile)member).delete();
					}
	
					member.delete(true, null);
				}
			} else {
				debugDir.create(true, true, null);
			}
		}
	}
	
	private File createFileInDebugDir(String name) {
		return new File(debugDir.getLocation().toFile(), name);
	}
	
	
	
	// ==================== Launch Configuration Storage ==================== \\
	
	private void storeLaunchConfiguration() throws CoreException {
		final ILaunchConfigurationWorkingCopy launchConfig = 
			launchManager.getLaunchConfigurationType("org.epsilon.etl.eclipse.dt.launching.EtlLaunchConfigurationDelegate")
				.newInstance(debugDir, launchConfigName);
		
		// Set source attribute to transformation
		launchConfig.setAttribute("source", getPathToFileInDebugDir(transformationName));
		
		// Add intermediate and target models
		final ArrayList<String> models = new ArrayList<>();
		models.add(createEmfModel("Intermediate", getPathToFileInDebugDir(intermediateModelName), HutnPackage.eNS_URI, true, false));
		models.add(createEmfModel("Model",        getPathToFileInDebugDir(targetModelName), hutnModule.getNsUris().get(0), false, true));
		launchConfig.setAttribute("models", models);
		
		launchConfig.doSave();
	}
	
	private String getPathToFileInDebugDir(String name) {
		return debugDir.getFile(name).getFullPath().toOSString();
	}
	
	private String createEmfModel(String modelName, String modelFile, String metamodelUri, boolean readOnLoad, boolean storeOnDisposal) {
		final StringProperties properties = new StringProperties();
		properties.put("type", "EMF");
		properties.put(EmfModel.PROPERTY_NAME, modelName);
		properties.put(EmfModel.PROPERTY_ALIASES, "");
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		properties.put(EmfModel.PROPERTY_MODEL_URI, EmfUtil.createFileBasedURI(modelFile) + "");
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelUri);
		return properties.toString();
	}
}
