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

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.common.dt.util.LogUtil;

/**
 * An implementation of {@link IncrementalProjectBuilder} that visits every child
 * of the project (in a full build) and every child of the changed resource (in
 * an incremental build). Each resource to be built is passed to 
 * {@link ResourceBuildingIncrementalProjectBuilder#buildResource(IResource, IProgressMonitor)}.
 */
public abstract class ResourceBuildingIncrementalProjectBuilder extends IncrementalProjectBuilder {
	
	private void refreshProject(IProgressMonitor monitor) {
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			reportError(e);
			e.printStackTrace();
		}
	}
	
	@Override
	protected IProject[] build(int kind, Map<String,String> args, IProgressMonitor monitor) {
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		
		refreshProject(monitor);
		
		return null;
	}

	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor) {
		try {
			delta.accept(new IResourceDeltaVisitor() {
				
				@Override
				public boolean visit(IResourceDelta delta) {
					if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.CHANGED)
						buildResource(delta.getResource(), monitor);
					
					return true; // visit children too
				}
			});
			
		} catch (CoreException e) {
			reportError(e);
		}
	}

	private void fullBuild(final IProgressMonitor monitor) {
		try {
			getProject().accept(new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource) throws CoreException {
					buildResource(resource, monitor);
					return true; // visit children too
				}
				
			});
			
		} catch (CoreException e) {
			reportError(e);
		}
	}
	
	protected void reportError(CoreException e) {
		LogUtil.log("Error encountered during build.", e);
	}

	protected abstract void buildResource(IResource resource, IProgressMonitor monitor);	
}
