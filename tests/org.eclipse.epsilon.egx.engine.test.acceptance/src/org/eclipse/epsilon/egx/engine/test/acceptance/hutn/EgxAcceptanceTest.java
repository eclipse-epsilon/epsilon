/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.hutn;

import java.io.File;
import java.io.IOException;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.execute.context.EgxContext;
import org.eclipse.epsilon.egx.engine.test.acceptance.virtual.VirtualTemplateFactory;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;

public class EgxAcceptanceTest extends HutnTestWithFamiliesMetaModel {

	protected static final VirtualTemplateFactory factory = new VirtualTemplateFactory();
	
	public static void runEgx(String source, String hutnModel, Template... templates) throws Exception {
		File egxFile = setupFactory(source, templates);
		
		EgxModule module = new EgxModule(new EgxContext(factory));
		module.getContext().getModelRepository().addModel(hutnToFamilyModel(hutnModel));
		module.parse(source, egxFile);
		
		if (!module.getParseProblems().isEmpty()) {
			for (ParseProblem p : module.getParseProblems()) {
				System.err.println(p);
			}
			throw new IllegalArgumentException("Error encountered whilst parsing EGX");
		}
		
		module.execute();
	}

	private static File setupFactory(String source, Template... templates) throws IOException {
		factory.addVirtualTemplate("base.egx", source);
		for (Template template : templates) {
			factory.addVirtualTemplate(template.name, template.content);
		}
		// FIXME We want a temp file or reuse the previous one
		//File egxFile = FileUtil.getFileStandalone("base.egx", EgxAcceptanceTest.class);
		File egxFile = FileUtil.createTempFile("base",  "egx");
		factory.setRoot(egxFile.getParentFile().toURI());
		return egxFile;
	}

	private static AbstractEmfModel hutnToFamilyModel(String hutnModel) {
		return new FamiliesModelConstructor().constructModel("MyFamily", hutnModel);
	}

	protected static Template template(String name, String content) {
		return new Template(name, content);
	}
	
	protected static class Template {
		public final String name, content;
		
		public Template(String name, String content) {
			this.name = name;
			this.content = content;
		}
	}
}
