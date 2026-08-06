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
package org.eclipse.epsilon.common.dt.test.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;

/**
 * Helper class that provides some basic project/workspace related
 * functionality. A clean project called 'sample' is provided for each test.
 */
public class TestThatUsesAProject {

	/** Default project, available to the tests. It is cleaned between tests */
	protected IProject project;
	
	@Before
	public void setupProject() throws CoreException {
		// Make sure the old project is not residing in the workspace anymore
		deleteResource(project);

        // Create default project
		project = createProject("sample");
	}
	
	@After
	public void teardownProject() throws CoreException {
        // Remove the default project
		deleteResource(project);
	}
	
	/** @return the newly created project */
	protected static IProject createProject(String name) throws CoreException {
		final IProject base = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		
		base.create(new NullProgressMonitor());
		base.open(new NullProgressMonitor());
		return base;
	}
	
	/**
	 * Creates a new folder in the given project with the given name
	 * @return the folder object
	 */
	protected static IFolder createFolder(IProject project, String name) throws CoreException {
		final IFolder folder = project.getFolder(name);
		folder.create(true, true, null);
		return folder;
	}
	
	/**
	 * Creates an empty file in the given project
	 * @return the file object
	 */
	protected static IFile createEmptyFile(IProject project, String name) throws CoreException {
		final IFile file = project.getFile(name);
		file.create(new ByteArrayInputStream(new byte[0]), true, new NullProgressMonitor());
		return file;
	}

	/**
	 * Creates a file in the given project with the provided contents
	 * @return the file object
	 */
	protected static IFile createFile(IProject project, String name, String contents) throws CoreException {
		final IFile file = project.getFile(name);
		createFile(file, contents);
		return file;
	}
	
	/**
	 * Creates a file in the given project with the contents of the given resource
	 * @return the file object
	 */
	protected static IFile createFile(IProject project, String name, URI resourceToCopy) throws CoreException, IOException {
		return createFile(project, name, new URIReader(resourceToCopy).getContents());
	}
	
	/** Changes the content of the given file */
	protected static void changeFileContents(IFile file, String newContents) throws CoreException {
		if (file.exists())
			file.setContents(new ByteArrayInputStream(newContents.getBytes()), true, false, new NullProgressMonitor());
		else
			createFile(file, newContents);
	}
	
	/**
	 * Refreshes the given resource (and all its child resources) in the
	 * workspace (and sleep for 1 second)
	 */
	protected static void refreshContents(IResource resource) throws CoreException, InterruptedException {
		resource.refreshLocal(IResource.DEPTH_INFINITE, null);
		Thread.sleep(1000);
	}
	
	private static void createFile(IFile file, String contents) throws CoreException {
		file.create(new ByteArrayInputStream(contents.getBytes()), true, new NullProgressMonitor());
	}

	/**
	 * Moves the given file to newPath
	 * @return the file object of the moved file
	 */
	protected static IFile moveFile(IFile file, String newPath) throws CoreException {
		if (file == null) return null;
		
		final IPath destination = file.getProject().getFullPath().append(newPath);
		file.move(destination, true, new NullProgressMonitor());
		return (IFile)ResourcesPlugin.getWorkspace().getRoot().findMember(destination);
	}
	
	/** Delete the given resource */
	protected static void deleteResource(IResource resource) throws CoreException {
		if (resource != null && resource.exists())
			resource.delete(true, new NullProgressMonitor());
	}

	/** Print the contents of the given file to {@link System.err} */
	protected static void printFileContents(IFile file) throws IOException, CoreException {
		try (InputStreamReader reader = new InputStreamReader(file.getContents())) {
			int c;
			while ((c = reader.read()) != -1) {
				System.err.print((char) c);
			}
		}
	}

	/** Adds a nature of the given id to the given project */
	protected static void addNature(IProject project, String natureId) throws CoreException {
		applyNaturesToProject(project, addNature(project.getDescription().getNatureIds(), natureId));
	}
	
	private static void applyNaturesToProject(IProject project, Collection<String> natureIds) throws CoreException {
		final IProjectDescription desc = project.getDescription();
				
		desc.setNatureIds(natureIds.toArray(new String[0]));
		
		project.setDescription(desc, null);
	}

	private static final Collection<String> addNature(final String[] natureIds, String natureId) {
		final Collection<String> newNatureIds = new LinkedList<>();
		newNatureIds.add(natureId);
		newNatureIds.addAll(Arrays.asList(natureIds));
		return newNatureIds;
	}
}
