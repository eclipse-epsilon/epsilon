package org.eclipse.epsilon.eol.engine.test.acceptance.plugged;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.BeforeClass;
import org.junit.Test;

public class PluggedInUpImportsTest {

	private static final String PROJECT_NAME = "pluggedin.resources";
	private static final String FOLDER_NAME = "resources";

	@BeforeClass
	public static void setUpProject() throws Exception {
		IProgressMonitor monitor = new NullProgressMonitor();
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(PROJECT_NAME);
		if (!project.exists()) {
			project.create(monitor);
		}
		project.open(monitor);

		File sourceDir = new File(
			new File("../org.eclipse.epsilon.eol.engine.test.acceptance.plugged"),
			FOLDER_NAME
		);
		File targetDir = new File(project.getLocation().toFile(), sourceDir.getName());
		if (targetDir.exists()) {
			FileUtils.deleteDirectory(targetDir);
		}
		FileUtils.copyDirectory(sourceDir, targetDir);

		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	@Test
	public void directRoot() throws Exception {
		EolModule module = parse("level1b/directRoot.eol");
		assertEquals("i am root", module.execute());
	}

	@Test
	public void level1OnlyName() throws Exception {
		EolModule module = parse("level1b/useLevel1.eol");
		assertEquals("level 1", module.execute());
	}

	@Test
	public void level2OnlyName() throws Exception {
		EolModule module = parse("level1b/useLevel2.eol");
		assertEquals("level 2", module.execute());
	}

	@Test
	public void importMissing() throws Exception {
		EolModule module = new EolModule();
		module.parse(computePlatformURI("level1b/importMissing.eol"));
		assertEquals(1, module.getParseProblems().size());
		assertTrue(module.getParseProblems().get(0).getReason().contains("not found"));
	}

	protected EolModule parse(String path) throws Exception {
		EolModule module = new EolModule();
		module.parse(computePlatformURI(path));
		assertEquals("There should not be any parse problems",
			Collections.emptyList(), module.getParseProblems());
		return module;
	}

	protected URI computePlatformURI(String path) throws URISyntaxException {
		return new URI(String.format(
			"platform:/resource/%s/%s/upImports/%s",
			PROJECT_NAME, FOLDER_NAME, path));
	}
}
