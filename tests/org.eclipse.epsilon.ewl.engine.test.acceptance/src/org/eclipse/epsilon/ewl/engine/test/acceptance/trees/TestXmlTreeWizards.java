/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ewl.engine.test.acceptance.trees;

import java.util.HashMap;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.ewl.EwlModule;
import org.eclipse.epsilon.ewl.IEwlModule;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestXmlTreeWizards {
	
	protected IEwlModule module;
	protected HashMap<String, Object> info;
	protected PlainXmlModel model;
	
	@Before
	public void setup() throws Exception {
		module = new EwlModule();
		module.parse(FileUtil.getFileStandalone("trees.ewl", TestXmlTreeWizards.class));
		info = new HashMap<>();
		model = loadXmlModel("Tree", "tree.xml");
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("info", info));
		module.getContext().getModelRepository().addModel(model);
	}
	
	@Test
	public void testCorrectNumberOfWizards() throws Exception {
		Object t1 = model.getAllOfType("t_tree").iterator().next();
		assertEquals(1, module.getWizardsFor(t1).size());
		assertEquals(0, module.getWizardsFor(null).size());
	}

	@Test
	public void testCorrectTitle() throws Exception {
		Object t1 = model.getAllOfType("t_tree").iterator().next();
		assertEquals("Delete t1", module.getWizardsFor(t1).get(0).getTitle());
	}
	
	@Test
	public void testExecute() throws Exception {
		Object t1 = model.getAllOfType("t_tree").iterator().next();
		module.getWizardsFor(t1).get(0).process();
		assertEquals(true, info.get("deleted"));
	}
	
	@Test
	public void testLoopWizards() throws Exception {
		assertEquals(1, module.getWizardsFor(model.getAllOfType("t_tree")).size());
	}
	
	protected PlainXmlModel loadXmlModel(String name, String fileName) throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setName(name);
		model.setFile(FileUtil.getFileStandalone(fileName, getClass()));
		model.load();
		return model;
	}
	
}
