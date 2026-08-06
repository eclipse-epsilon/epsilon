/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.dt.util;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.IHutnModule;
import org.eclipse.epsilon.hutn.dt.markers.MarkerManager;

public class HutnBuilderHelper {
	
	private final HutnBuildReporter reporter;
	private final IFile file;
	private final IProgressMonitor monitor;
	
	private IHutnModule hutnModule;
	private IPath base;
	private IResource parent;
	
	public HutnBuilderHelper(IFile file, HutnBuildReporter reporter) {
		this(file, reporter, null);
	}
	
	public HutnBuilderHelper(IFile file, HutnBuildReporter reporter, IProgressMonitor monitor) {
		this.file     = file;
		this.reporter = reporter;
		this.monitor  = monitor;
	}

	public static IHutnModule initialiseHutnModule(IFile file) {
		final IHutnModule hutnModule = new HutnModule();
				
		hutnModule.setConfigFileDirectory(WorkspaceUtil.getAbsolutePath(file.getParent()));
		
		return hutnModule;
	}
	
	public void buildHutn() {
		try {
			hutnModule = initialiseHutnModule(file);
			
			if (hutnModule.parse(file.getRawLocationURI())) {
				base   = file.getLocation().removeFileExtension();
				parent = file.getParent();
				
				final List<File> generatedFiles = hutnModule.storeEmfModel(parent.getLocation().toFile(), // eg: /../ScratchPad/
				                                                           getFileName(base, "model"),    // eg: Output.model
				                                                           getFileName(base, "ecore"));   // eg: Inferred.metamodel
				
				for (File generatedFile : generatedFiles) {
					LogUtil.logInfo("Generated " + generatedFile);
				}
				
				parent.refreshLocal(IResource.DEPTH_ONE, monitor);
				
				removeMarkersFromModelFile();
				parent.refreshLocal(IResource.DEPTH_ONE, monitor);

			} else {
				reporter.reportFailure(file, "Cannot build model because HUTN cannot be parsed.");
			}	
		} catch (Exception e) {
			LogUtil.log("Error while building HUTN " + file.getLocation(), e);
		}
	}
	
	private void removeMarkersFromModelFile() throws CoreException {
		final String modelFile = hutnModule.getModelFile() == null ? 
				                         getFileName(base, "model") :
				                         hutnModule.getModelFile();
				
				                
		final IPath modelPathFromProject = parent.getFullPath().removeFirstSegments(1).append(modelFile);
		new MarkerManager(file.getProject().getFile(modelPathFromProject)).removeMarkers();
	}
	
	private static String getFileName(IPath base, String extension) {
		return base.addFileExtension(extension).toFile().getName();
	}
	
	
	public static interface HutnBuildReporter {
		public void reportFailure(IFile source, String message);
	}
}
