package org.eclipse.epsilon.egx.engine.test.acceptance.extendedproperties;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.junit.Test;

public class ExtendedPropertiesTests {
	
	@Test
	public void testExtendedProperties() throws Exception {
		EgxModule module = new EgxModule();
		FileUtil.getFileStandalone("exp.egl", ExtendedPropertiesTests.class);
		module.parse(FileUtil.getFileStandalone("exp.egx", ExtendedPropertiesTests.class));
		
		HashMap<String, Object> values = new HashMap<>();
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("values", values));
		
		PlainXmlModel model = new PlainXmlModel();
		model.setXml("<?xml version=\"1.0\"?><_><person name=\"Alice\"/><person name=\"Bob\"/></_>");
		model.load();
		
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		
		assertEquals(1, values.get("aliceIndexRule"));
		assertEquals(2, values.get("bobIndexRule"));
		
		assertEquals(1, values.get("aliceIndexPost"));
		assertEquals(2, values.get("bobIndexPost"));
	}
	
}
