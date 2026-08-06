/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eml.engine.test.acceptance.trees;

import java.io.File;
import java.util.HashMap;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestXmlTreeMerging {
	
	protected IEclModule eclModule;
	protected IEmlModule emlModule;
	protected PlainXmlModel mergedModel;
	
	protected HashMap<String, Object> info;
	
	@Before
	public void setup() throws Exception {
		// Load imported file
		FileUtil.getFileStandalone("imported.eml", TestXmlTreeMerging.class);
		
		File leftXML = FileUtil.getFileStandalone("left.xml", TestXmlTreeMerging.class);
		PlainXmlModel leftModel = loadXmlModel("Left", leftXML);
		File rightXML = FileUtil.getFileStandalone("right.xml", TestXmlTreeMerging.class);
		PlainXmlModel rightModel = loadXmlModel("Right", rightXML);
		leftModel.getAliases().add("Source");
		rightModel.getAliases().add("Target");
		
		File treesECL = FileUtil.getFileStandalone("trees.ecl", TestXmlTreeMerging.class);
		eclModule = new EclModule();
		eclModule.parse(treesECL);
		info = new HashMap<>();
		eclModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("info", info));
		eclModule.getContext().getModelRepository().addModels(leftModel, rightModel);
		eclModule.execute();
		
		File merged = FileUtil.getFileStandalone("merged.xml", TestXmlTreeMerging.class);
		mergedModel = loadXmlModel("Merged", merged, false);
		emlModule = new EmlModule();
		
		File treesEML = FileUtil.getFileStandalone("trees.eml", TestXmlTreeMerging.class);
		emlModule.parse(treesEML);
		emlModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("info", info));
		emlModule.getContext().getModelRepository().addModels(leftModel, rightModel, mergedModel);
		emlModule.getContext().setMatchTrace(eclModule.getContext().getMatchTrace().getReduced());
		emlModule.execute();
	}
	
	@Test
	public void testCorrectNumberOfElements() throws Exception {
		assertEquals(5, mergedModel.getAllOfKind("t_tree").size());
	}
	
	@Test
	public void testPreExecuted() {
		assertEquals("pre", info.get("pre"));		
	}
	
	@Test
	public void testPostExecuted() {
		assertEquals("post", info.get("post"));		
	}
	
	@Test
	public void testOverrideByName() {
		assertNotEquals(true, info.get("imported"));		
	}
	
	protected static PlainXmlModel loadXmlModel(String name, File file, boolean readOnLoad) throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setName(name);
		model.setFile(file);
		model.setReadOnLoad(readOnLoad);
		model.load();
		return model;
	}
	
	protected static PlainXmlModel loadXmlModel(String name, File file) throws Exception {
		return loadXmlModel(name, file, true);
	}
}
