/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.jdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * This class contains a set of static methods that are able to access Java
 * source code based Eclipse JDT. It supports accessing major Java model
 * elements and type-specific query of all AST nodes
 * 
 * @author Cheng Yun
 * 
 */
public class JdtUtil {

	public static List<IProject> getIProjects() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// get all projects in the workspace
		IProject[] projects = root.getProjects();

		return Arrays.asList(projects);
	}

	public static List<IProject> getIProjects(String[] projectNames) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// get all projects in the workspace
		IProject[] projects = root.getProjects();
		List<IProject> projectsSelected = new ArrayList<IProject>();
		for (IProject project : projects) {
			for (String str : projectNames) {
				if (project.getName().equals(str)) {
					projectsSelected.add(project);
				}
			}
		}
		return projectsSelected;
	}

	public static IJavaProject getIJavaProject(IProject project)
			throws CoreException {
		IJavaProject javaProject;
		// the project should be a open Java project
		if (project.isOpen() && project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			javaProject = JavaCore.create(project);
			return javaProject;
		}
		return null;
	}

	public static List<IJavaProject> getIJavaProjects(List<IProject> proejcts)
			throws CoreException {
		List<IJavaProject> sourceProjects = new ArrayList<IJavaProject>();
		for (IProject project : proejcts) {
			if (getIJavaProject(project) != null) {
				sourceProjects.add(getIJavaProject(project));
			}
		}
		return sourceProjects;
	}
	
}
