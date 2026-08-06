/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.examples.eolqueries.queries;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import library.Author;
import library.Book;
import library.Library;
import library.LibraryPackage;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.Operation;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		String root = "D:/Projects/Eclipse/3.5.1/demo-runtime/org.eclipse.epsilon.examples.eolqueries.queries/src/org/eclipse/epsilon/examples/eolqueries/queries/";
		
		// Load the model using EMF
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(LibraryPackage.eNS_URI, LibraryPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createFileURI(root + "library.model"));
		resource.load(new HashMap<Object, Object>());
		
		// Get hold of the root library object
		
		Library library = (Library) resource.getContents().get(0);
		
		// Prepare the EOL module
		EolModule module = new EolModule();
		module.parse(new File(root + "queries.eol"));
		
		if (module.getParseProblems().size() > 0) {
			throw new Exception("Syntax errors in EOL module");
		}
		
		InMemoryEmfModel model = new InMemoryEmfModel("Library", resource, LibraryPackage.eINSTANCE);
		module.getContext().getModelRepository().addModel(model);
		
		// Invoke the getAuthorNames() operation
		Book book = library.getBooks().get(0);
		Operation getAuthorNames = module.getOperations().getOperation("getAuthorNames");
		String names = (String) getAuthorNames.execute(book, Collections.emptyList(), module.getContext());
		System.err.println(names);
		
		// Invoke the getAuthors() EOL operation
		Operation getAuthors = module.getOperations().getOperation("getAuthors");
		Collection<?> authors = (Collection<?>) getAuthors.execute(book, Collections.emptyList(), module.getContext());
		
		// Get the results of getAuthors() and unwrap them
		@SuppressWarnings("unchecked")
		Collection<Author> casted = (Collection<Author>) authors;
		
		for (Author author : casted) {
			System.err.println(author.getName());
		}
		
		
	}
	
}
