/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.eol;

import static org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil.run;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.emc.emf.EmfPrettyPrinter;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinter;
import org.eclipse.epsilon.eol.models.IModel;
import org.junit.Test;

public class EglUsesEmfPrettyPrinter {

	@Test
	public void printingAnEObjectShouldBePretty() throws Exception {
		final EClass modelElement = anEClass().named("Library").build();
	
		final String actual = run(createFactoryWithPrettyPrinter(),
		                          "[%=ecore::EClass.all.first.asString()%]",
		                          createModelContaining(modelElement));
		
		assertEquals(prettyPrint(modelElement), actual);
	}

	private static EglTemplateFactory createFactoryWithPrettyPrinter() {
		final EglTemplateFactory factory = new EglTemplateFactory();
		factory.getContext().getPrettyPrinterManager().addPrettyPrinter(getPrettyPrinter());
		return factory;
	}

	private static IModel createModelContaining(EObject root) {
		final Resource resource = EmfUtil.createResource(root);
		return new InMemoryEmfModel(resource);
	}
	
	private static String prettyPrint(Object o) {
		return getPrettyPrinter().print(o);
	}

	private static PrettyPrinter getPrettyPrinter() {
		return new EmfPrettyPrinter();
	}
}
