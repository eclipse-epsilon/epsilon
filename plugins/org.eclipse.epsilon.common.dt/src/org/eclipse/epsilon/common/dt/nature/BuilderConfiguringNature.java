/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.nature;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

public abstract class BuilderConfiguringNature extends AbstractNature {
	
	protected abstract String getBuilderID();
	
	@Override
	public void configure() throws CoreException {	
		if (!projectHasBuildSpecForBuilder()) {
			applyBuildSpecToProject(addCommandForOurBuilderToExistingBuildSpec());
		}
	}
	
	private Collection<ICommand> addCommandForOurBuilderToExistingBuildSpec() throws CoreException {
		final Collection<ICommand> newBuildSpec = new LinkedList<>();
		newBuildSpec.add(createOurBuildCommand());
		newBuildSpec.addAll(Arrays.asList(project.getDescription().getBuildSpec()));
		return newBuildSpec;
	}
	
	private ICommand createOurBuildCommand() throws CoreException {
		final ICommand buildCommand = project.getDescription().newCommand();
		buildCommand.setBuilderName(getBuilderID());
		return buildCommand;
	}
	
	private void applyBuildSpecToProject(Collection<ICommand> buildSpec) throws CoreException {
		final IProjectDescription desc = project.getDescription();
				
		desc.setBuildSpec(buildSpec.toArray(new ICommand[0]));
		
		project.setDescription(desc, null);
	}
	
	/**
	 * Clients should not remove the build command for their builder
	 * in this method, but should instead specify a nature for the 
	 * builder in the plugin.xml, which will cause the build command 
	 * to be removed automatically when the nature is removed.
	 */
	@Override
	public void deconfigure() {
		// Do nothing
	}

	private boolean projectHasBuildSpecForBuilder() throws CoreException {
		if (project != null) {
			for (ICommand buildCommand : project.getDescription().getBuildSpec()) {
				if (getBuilderID().equals(buildCommand.getBuilderName())) {
					return true;
				}
			}
		}
		
		return false;
	}
}
