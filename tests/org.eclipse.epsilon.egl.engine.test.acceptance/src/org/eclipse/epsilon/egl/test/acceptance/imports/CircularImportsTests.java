package org.eclipse.epsilon.egl.test.acceptance.imports;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.junit.Test;

public class CircularImportsTests {
	
	@Test
	public void testCircularImports() throws Exception {
		
		FileUtil.getFileStandalone("circular2.egl", CircularImportsTests.class);
		
		EglModule module = new EglModule();
		module.parse(FileUtil.getFileStandalone("circular1.egl", CircularImportsTests.class));
		
		PlainXmlModel model = new PlainXmlModel();
		model.setXml("<tree/>");
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		
		module.getContext().getModelRepository().addModel(model);
		
		module.execute();
		
		
	}
	
	
}
