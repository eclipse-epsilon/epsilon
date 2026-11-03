/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.workflow.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;
import org.eclipse.epsilon.workflow.tasks.coverage.CoverageStartTask;
import org.eclipse.epsilon.workflow.tasks.coverage.CoverageStopTask;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CoverageTaskTests extends WorkflowTestCase {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@Override
	protected void addTaskDefinitionsTo(Project project) {
		project.addTaskDefinition("epsilon.coverage.start", CoverageStartTask.class);
		project.addTaskDefinition("epsilon.coverage.stop", CoverageStopTask.class);
	}
	
	protected File write(String name, String contents) throws Exception {
		File file = new File(folder.getRoot(), name);
		Files.write(file.toPath(), contents.getBytes(StandardCharsets.UTF_8));
		return file;
	}
	
	@Test
	public void testCoverageRecordedAcrossTasks() throws Exception {
		File eol = write("program.eol", "'a'.println();\nif (false) {\n  'b'.println();\n}\n");
		File build = write("build.xml",
			"<project default='main'>\n" +
			"  <target name='main'>\n" +
			"    <epsilon.coverage.start/>\n" +
			"    <epsilon.eol src='program.eol'/>\n" +
			"    <epsilon.eol src='program.eol'/>\n" +
			"    <epsilon.coverage.stop file='out/coverage.ecv' name='ant'/>\n" +
			"    <epsilon.coverage.start/>\n" +
			"    <epsilon.eol src='program.eol'/>\n" +
			"    <epsilon.coverage.stop file='out/coverage.ecv' append='true'/>\n" +
			"  </target>\n" +
			"</project>\n");
		
		runTarget(build, "main");
		
		CoverageSession session = CoverageResources.load(new File(folder.getRoot(), "out/coverage.ecv"));
		assertEquals(1, session.getFiles().size());
		SourceFile file = session.getFiles().get(0);
		assertEquals(eol.toURI().toString(), file.getUri());
		
		boolean foundFirst = false, foundMissed = false;
		for (TreeIterator<EObject> it = file.eAllContents(); it.hasNext();) {
			EObject next = it.next();
			if (!(next instanceof CoverageElement) || !((CoverageElement) next).isProbe()) continue;
			CoverageElement element = (CoverageElement) next;
			if (element.getStartLine() == 1 && "ExpressionStatement".equals(element.getKind())) {
				// 2 runs in the first session + 1 run appended
				assertEquals(3, element.getHits());
				foundFirst = true;
			}
			if (element.getStartLine() == 3) {
				assertEquals(0, element.getHits());
				foundMissed = true;
			}
		}
		assertTrue(foundFirst && foundMissed);
	}
	
	@Test(expected = BuildException.class)
	public void testStopWithoutStart() throws Exception {
		File build = write("build.xml",
			"<project default='main'>\n" +
			"  <target name='main'>\n" +
			"    <epsilon.coverage.stop file='coverage.ecv'/>\n" +
			"  </target>\n" +
			"</project>\n");
		runTarget(build, "main");
	}
}
