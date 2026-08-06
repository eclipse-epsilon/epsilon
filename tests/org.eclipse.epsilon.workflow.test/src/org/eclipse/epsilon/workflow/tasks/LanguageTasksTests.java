/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import java.io.File;
import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Task;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.workflow.tasks.ExecutableModuleTask.ModuleProperty;
import org.junit.Rule;
import org.junit.Test;

/**
 * We are only interested in what properties where used by the language task, not the outcome of executing
 * the script.
 * @author Horacio Hoyos Rodriguez
 *
 */
public class LanguageTasksTests {
	
	protected static final File BASE_DIR = new File("../org.eclipse.epsilon.workflow.test/resources/epsilon/");
	
	@Rule
    public final BuildFileRule buildRule = new BuildFileRule();

	@Test
	public void eolModuleInLine() throws Exception {
		final String eolExp = "      \"Hello world\".println();";
		final String initTask = createTaskDefTarget("Eol");
		final StringBuilder mainBuilder = new StringBuilder();
		mainBuilder.append("<target name=\"main\" depends=\"eol.init\">");
		mainBuilder.append("  <epsilon.eol id=\"eol\">");		// Use id so its not disposed
		mainBuilder.append("    <![CDATA[");
		mainBuilder.append(eolExp);
        mainBuilder.append("    ]]>");
        mainBuilder.append("  </epsilon.eol>");
        mainBuilder.append("</target>");
        //builder.append('\n');
        File buildFile = createBuildFile("main", initTask, mainBuilder.toString());
        buildRule.configureProject(buildFile.getAbsolutePath());   
        buildRule.executeTarget("main");
        
        Task task = buildRule.getProject().getTargets().get("main").getTasks()[0];
        assertTrue(task instanceof EolTask);
        EolTask eolTask = (EolTask)task;
        assertEquals(eolExp.trim(), eolTask.code.trim());
        assertEquals(null, eolTask.src);
        buildFile.delete();
	}
	
	@Test
	public void eolModuleFromSource() throws Exception {
		final String eolExp = "\"Hello world\".println();";
		final File eolSrc = File.createTempFile("eolTest", ".eol");
		FileUtil.setFileContents(eolExp, eolSrc);		
		
		final String initTask = createTaskDefTarget("Eol");
		final StringBuilder mainBuilder = new StringBuilder();
		mainBuilder.append("<target name=\"main\" depends=\"eol.init\">");
		mainBuilder.append(String.format("  <epsilon.eol id=\"eol\" src=\"%s\"/>", eolSrc.getAbsolutePath()));	// Use id so its not disposed
		mainBuilder.append("</target>");
        //builder.append('\n');
        File buildFile = createBuildFile("main", initTask, mainBuilder.toString());
        buildRule.configureProject(buildFile.getAbsolutePath());   
        buildRule.executeTarget("main");
        
        Task task = buildRule.getProject().getTargets().get("main").getTasks()[0];
        assertTrue(task instanceof EolTask);
        EolTask eolTask = (EolTask)task;
        assertTrue(StringUtil.isEmpty(eolTask.code));
        assertEquals(eolSrc.getAbsolutePath(), eolTask.src.getAbsolutePath());
        buildFile.delete();
	}
	
	@Test
	public void eclModuleFromSource() throws Exception {
		File basedir = new File("resources/");
		File eclFile = new File("resources/epsilon/ecl/tree.ecl");
		
		String modelTarget = createModelLoadTarget(basedir.getAbsolutePath());
		final String initTask = createTaskDefTarget("Ecl");
		final StringBuilder mainBuilder = new StringBuilder();
		mainBuilder.append("<target name=\"main\" depends=\"ecl.init, loadModels\">");
		mainBuilder.append(String.format("  <epsilon.ecl id=\"ecl\" src=\"%s\">", eclFile.getAbsolutePath()));	// Use id so its not disposed
		mainBuilder.append("test<model ref=\"T1\"/>");
		mainBuilder.append("<model ref=\"T2\"/>");
		mainBuilder.append("</epsilon.ecl>");
		mainBuilder.append("</target>");
		
		File buildFile = createBuildFile("main", modelTarget, initTask, mainBuilder.toString());
        buildRule.configureProject(buildFile.getAbsolutePath());   
        buildRule.executeTarget("main");
        
        Task task = buildRule.getProject().getTargets().get("main").getTasks()[0];
        assertTrue(task instanceof EclTask);
        EclTask eclTask = (EclTask)task;
        assertTrue(StringUtil.isEmpty(eclTask.code));
        assertEquals(eclFile.getAbsolutePath(), eclTask.src.getAbsolutePath());
        buildFile.delete();
	}
	
	@Test
	public void evlModuleFromSource() throws Exception {
		File basedir = new File("resources/");
		File evlFile = new File("resources/epsilon/evl/tree.evl");
		
		String modelTarget = createModelLoadTarget(basedir.getAbsolutePath());
		final String initTask = createTaskDefTarget("Evl");
		final StringBuilder mainBuilder = new StringBuilder();
		mainBuilder.append("<target name=\"main\" depends=\"evl.init, loadModels\">");
		mainBuilder.append(String.format("  <epsilon.evl id=\"evl\" src=\"%s\" ", evlFile.getAbsolutePath()));	// Use id so it's not disposed
		mainBuilder.append("moduleImplementation=\"org.eclipse.epsilon.evl.concurrent.EvlModuleParallelAnnotation\">");
		mainBuilder.append("  <moduleProperty name=\"parallelism\" value=\"6\"/>");
		mainBuilder.append("  <moduleProperty name=\""+EvlModule.OPTIMIZE_CONSTRAINTS+"\" value=\"true\"/>");
		mainBuilder.append("  <moduleProperty name=\""+IEvlContext.OPTIMIZE_CONSTRAINT_TRACE+"\" value=\"true\"/>");
		mainBuilder.append("  <moduleProperty name=\""+IEvlContext.SHORT_CIRCUIT+"\" value=\"false\"/>");
		mainBuilder.append("  <model ref=\"T1\"/>");
		mainBuilder.append("</epsilon.evl>");
		mainBuilder.append("</target>");
		
		File buildFile = createBuildFile("main", modelTarget, initTask, mainBuilder.toString());
        buildRule.configureProject(buildFile.getAbsolutePath());   
        buildRule.executeTarget("main");
        
        Task task = buildRule.getProject().getTargets().get("main").getTasks()[0];
        assertTrue(task instanceof EvlTask);
        EvlTask evlTask = (EvlTask)task;
        assertTrue(StringUtil.isEmpty(evlTask.code));
        assertEquals(evlFile.getAbsolutePath(), evlTask.src.getAbsolutePath());
        assertFalse(evlTask.properties.isEmpty());
        
        ModuleProperty parallelism = evlTask.properties.get(0);
        assertEquals("parallelism", parallelism.getName());
        assertEquals("6", parallelism.getValue());
        
        ModuleProperty oc = evlTask.properties.get(1);
        assertEquals("optimizeConstraints", oc.name);
        assertEquals("true", oc.value);
        
        buildFile.delete();
	}

	
	/**
	 * Create an ant target to init the language task definition
	 * @param language the name of the language, e.g. Eol, Etl, etc.
	 * @return
	 */
	public static String createTaskDefTarget(String language) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append(String.format("<target name=\"%s.init\"", language.toLowerCase()));
		builder.append(String.format("        description=\"Load the %s task\">", language.toLowerCase()));
		builder.append(String.format("  <taskdef name=\"epsilon.%s\"", language.toLowerCase()));
		builder.append(String.format("    classname=\"org.eclipse.epsilon.workflow.tasks.%sTask\"/>", language));
		builder.append("</target>");
		return builder.toString();
	}
	
	/**
	 * Creates 
	 */
	public static String createModelLoadTarget(String baseDir) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append(String.format("<property name=\"models.location\" location=\"%s/models\" />", baseDir));
		builder.append(String.format("<property name=\"metamodels.location\" location=\"%s/metamodels\" />", baseDir));
		builder.append("<property name=\"tree.model.location\" location=\"${models.location}/tree.model\" />");
		builder.append("<property name=\"tree.metamodel.location\" location=\"${metamodels.location}/tree.ecore\" />");
		builder.append("<property name=\"tree.metamodel.uri\" value=\"Tree\" />");
		builder.append("<target name=\"loadModels\">");
		builder.append("  <taskdef name=\"epsilon.emf.loadModel\"");
		builder.append("    classname=\"org.eclipse.epsilon.workflow.tasks.emf.LoadEmfModelTask\"/>");
		builder.append("  <epsilon.emf.loadModel name=\"T1\"");
		builder.append("    modelfile=\"${tree.model.location}\" metamodelfile=\"${tree.metamodel.location}\"");
		builder.append("    read=\"true\" store=\"false\"/>");
		builder.append("  <epsilon.emf.loadModel name=\"T2\"");
		builder.append("    modelfile=\"${tree.model.location}\" metamodelfile=\"${tree.metamodel.location}\"");
		builder.append("    read=\"true\" store=\"false\"/>");
		builder.append("</target>");
		return builder.toString();
	}
	
	/**
	 * Create a build file (xml) by adding the task definitions to a basic proejct structure
	 * @param taskDefinition
	 * @return
	 * @throws Exception If there is an error creating the file or adding the contents
	 */
	public static File createBuildFile(String defaultTarget, String... targetDefinition) throws Exception {
		final StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append('\n');
		builder.append(String.format("<project default=\"%s\">", defaultTarget));
		builder.append('\n');
		for (String task : targetDefinition) {
			builder.append(task);
			builder.append('\n');
		}
		builder.append("</project>");
		final File tmp = File.createTempFile("EpsilonWorkflowTaskTest", ".xml");
		FileUtil.setFileContents(builder.toString(), tmp);
		return tmp;
	}

}
