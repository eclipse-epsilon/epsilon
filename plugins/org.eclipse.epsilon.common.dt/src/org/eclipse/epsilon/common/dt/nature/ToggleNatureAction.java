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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.common.dt.actions.AbstractObjectActionDelegate;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

public abstract class ToggleNatureAction extends AbstractObjectActionDelegate {

	// FIXME - does not check item on startup. Would using the command platform framework fix this?
	
	protected abstract String getNatureId();
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		
		try {
			action.setChecked(containsNature(selection));
		
		} catch (CoreException e) {
			LogUtil.log("Error encountered will trying to check or uncheck a ToggleNatureAction", e);
		}
	}
	
	@Override
	public void run(IAction action) {
		try {
			if (getFirstElementInSelection() instanceof IProject) {
				final IProject project = (IProject)getFirstElementInSelection();
				
				applyNaturesToProject(project, toggleNature(project));
			}
			
		} catch (CoreException e) {
			LogUtil.log("Error encountered will trying toggle a nature", e);
		}
	}

	private Collection<String> toggleNature(final IProject project) throws CoreException {
		final Collection<String> newNatureIds;
		
		final String[] currentNatureIds = project.getDescription().getNatureIds();
		
		if (containsOurNature(currentNatureIds)) {
			newNatureIds = removeOurNature(currentNatureIds);
			
		} else {
			newNatureIds = addOurNature(currentNatureIds);
		}
		
		return newNatureIds;
	}
	
	private static void applyNaturesToProject(IProject project, Collection<String> natureIds) throws CoreException {
		final IProjectDescription desc = project.getDescription();
		desc.setNatureIds(natureIds.toArray(new String[0]));
		project.setDescription(desc, null);
	}

	private Collection<String> addOurNature(final String[] natureIds) {
		final Collection<String> newNatureIds = new LinkedList<>();
		newNatureIds.add(getNatureId());
		newNatureIds.addAll(Arrays.asList(natureIds));
		return newNatureIds;
	}

	private Collection<String> removeOurNature(final String[] natureIds) {
		final Collection<String> newNatureIds = new LinkedList<>(Arrays.asList(natureIds));
		newNatureIds.remove(getNatureId());
		return newNatureIds;
	}

	private boolean containsNature(ISelection selection) throws CoreException {
		if (getFirstElementOf(selection) instanceof IProject) {
			final IProject project = (IProject)getFirstElementOf(selection);
			if (project.isOpen()) {
				final String[] natureIds = project.getDescription()
						.getNatureIds();

				return containsOurNature(natureIds);
			}
		}

		return false;
	}
	
	private boolean containsOurNature(String[] natureIds) {
		for (String natureId : natureIds) {
			if (getNatureId().equals(natureId))
				return true;
		}
		
		return false;
	}
}
