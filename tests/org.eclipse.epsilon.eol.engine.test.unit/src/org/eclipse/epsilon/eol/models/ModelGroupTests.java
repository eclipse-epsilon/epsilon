/*******************************************************************************
 * Copyright (c) 2009-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - refactored to Mockito
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.eol.models;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;

public class ModelGroupTests {
	
	private static final ModelRepository repository = new ModelRepository();
	private static IModel first, second;
	
	// TODO: Refactored from easymock to Mockito.
	// Someone that knows about mocking frameworks:
	// Please check that the refactoring is correct!
	// Specifically, the verify calls have been removed.
	
	@BeforeClass
	public static void setup() {
		first  = mock(IModel.class);
		second = mock(IModel.class);
		
		repository.addModel(first);
		repository.addModel(second);
	}
	
	@Before
	public void resetMocks() {
		reset(first, second);
		when(first.getAliases()).thenReturn(Arrays.asList("first",  "Ecore"));
		when(second.getAliases()).thenReturn(Arrays.asList("second", "Ecore"));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void allContentsDelegatesToGroupedModels() throws EolModelNotFoundException {		
		final Object pkg = EcoreFactory.eINSTANCE.createEPackage();
		final Object cls = EcoreFactory.eINSTANCE.createEClass();

		when(first.allContents()).thenReturn((Collection)Arrays.asList(pkg));	
		when(second.allContents()).thenReturn((Collection)Arrays.asList(cls));
		
		final ModelGroup group = new ModelGroup(repository, "Ecore");
		assertEquals(Arrays.asList(pkg, cls), group.allContents());
	}
	
	
	@Test
	public void typeNameOfDelegatesToGroupedModels() throws EolModelNotFoundException {		
		final EObject pkg = EcoreFactory.eINSTANCE.createEPackage();
		
		when(first.isModelElement(pkg)).thenReturn(false);	
		when(second.isModelElement(pkg)).thenReturn(true);		
		when(second.getTypeNameOf(pkg)).thenReturn("EPackage");
		
		final ModelGroup group = new ModelGroup(repository, "Ecore");
		assertEquals("EPackage", group.getTypeNameOf(pkg));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void exceptionWhenNoGroupedModelKnowsTypeNameOfInstance() throws EolModelNotFoundException {		
		when(first.isModelElement("foo")).thenReturn(false);		
		when(second.isModelElement("foo")).thenReturn(false);	
		
		final ModelGroup group = new ModelGroup(repository, "Ecore");
		group.getTypeNameOf("foo");
	}
	
	@Test
	public void testProperties() throws Exception {
		ModelRepository repository = new ModelRepository();
		repository.addModel(createReadOnlyPlainXmlModel("Foo", "Xml", "<a p=\"\"/>"));
		repository.addModel(createReadOnlyPlainXmlModel("Bar", "Xml", "<a p=\"\"/>"));
		IModel modelGroup = repository.getModelByName("Xml");
		
		EolModule module = new EolModule();
		module.getContext().getModelRepository().addModel(modelGroup);
		module.parse("for (a in Xml!t_a.all) { a.a_p = 'something'; } "
				+ "assertEquals(Xml!t_a.all.first().a_p, 'something');"
				+ "assertEquals(Xml!t_a.all.second().a_p, 'something');"
				);
		module.execute();
	}
	
	@Test
	public void testModelGroupOperationContributor() throws Exception {
		
		PlainXmlModel m1 = new PlainXmlModelExt();
		m1.setName("M1");
		m1.getAliases().add("Xml");
		m1.getAliases().add("Alias1");
		m1.setXml("<?xml version=\"1.0\"?>" + "<foo/>");
		m1.setReadOnLoad(true);
		m1.load();
		
		PlainXmlModel m2 = new PlainXmlModelExt();
		m2.setName("M1");
		m2.getAliases().add("Xml");
		m2.getAliases().add("Alias2");
		m2.setXml("<?xml version=\"1.0\"?>" + "<foo/>");
		m2.setReadOnLoad(true);
		m2.load();
		
		EolModule module = new EolModule();
		module.getContext().getModelRepository().addModels(m1, m2);
		
		module.parse("return Sequence{"
				+ "Xml!t_foo.all.class.simpleName, "
				+ "Xml!t_foo.getAllOfType().class.simpleName, "
				+ "Xml.allContents().class.simpleName, "
				+ "Alias1!t_foo.all.class.simpleName, "
				+ "Alias1!t_foo.getAllOfType().class.simpleName, "
				+ "Alias1.allContents().class.simpleName"
				+ "};");
		assertEquals(Arrays.asList("ArrayList", "ArrayList", "ArrayList", "LinkedList", "LinkedList", "LinkedList"), module.execute());
		
	}
	
	protected PlainXmlModel createReadOnlyPlainXmlModel(String name, String alias, String xml) {
		PlainXmlModel m = new PlainXmlModel();
		m.setName(name);	
		m.getAliases().add(alias);
		m.setXml("<?xml version=\"1.0\"?>" + xml);
		try {
			m.load();
		} catch (EolModelLoadingException e) {
			throw new RuntimeException(e);
		}
		return m;
	}
	
	class PlainXmlModelExt extends PlainXmlModel {
		
		@Override
		public Collection<Element> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
			return new LinkedList<>(super.getAllOfType(type));
		}
		
		@Override
		public Collection<Element> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
			return new LinkedList<>(super.getAllOfKind(type));
		}
		
		@Override
		public Collection<Element> allContents() {
			return new LinkedList<>(super.allContents());
		}
	}
}
