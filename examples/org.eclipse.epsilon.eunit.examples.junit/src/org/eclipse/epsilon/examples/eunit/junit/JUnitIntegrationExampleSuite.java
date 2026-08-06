/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.examples.eunit.junit;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.eunit.junit.IEUnitSuite;
import org.eclipse.epsilon.eunit.junit.dt.EclipseEUnitTestRunner;
import org.junit.runner.RunWith;

/**
 * Example EUnit-based JUnit plug-in test suite which uses a custom contributor
 * to invoke an ETL transformation from an EUnit test suite. This custom
 * contributor is required since running an Ant script from a JUnit plug-in test
 * (from Tycho, for instance) is quite difficult to set up.
 */
@RunWith(EclipseEUnitTestRunner.class)
public class JUnitIntegrationExampleSuite implements IEUnitSuite {

	public static final class ExampleTestOperationContributor extends
			OperationContributor {
		@Override
		public boolean contributesTo(Object target) {
			return true;
		}

		public void transform() throws Exception {
			EtlModule etl = new EtlModule();
			etl.getContext().setModelRepository(getContext().getModelRepository());
			etl.parse(new File("resources/etl/Tree2Graph.etl"));
			etl.execute();
		}
	}

	@Override
	public OperationContributor getOperationContributor() {
		return new ExampleTestOperationContributor();
	}

	@Override
	public java.net.URI getModuleURI() throws Exception {
		return getResourceURI("tests/example.eunit");
	}

	@Override
	public List<IModel> prepareModels() throws Exception {
		final List<IModel> models = new ArrayList<IModel>();
		
		models.add(loadEmptyModel("Tree", "metamodels/tree.ecore"));
		models.add(loadEmptyModel("Graph", "metamodels/graph.ecore"));
		models.add(loadEmptyModel("GraphExpected", "metamodels/graph.ecore"));

		return models;
	}

	private EmfModel loadEmptyModel(String name, String metamodelPath) throws EolModelLoadingException {
		EmfModel model = new EmfModel();
		model.setName(name);
		model.setMetamodelFileUri(getResourceEmfURI(metamodelPath));
		model.setModelFileUri(URI.createFileURI(new File("Empty" + name + ".model").getAbsolutePath()));
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(false);
		model.load();
		return model;
	}

	private java.net.URI getResourceURI(String resourcePath) throws URISyntaxException {
		return getClass().getResource("/resources/" + resourcePath).toURI();
	}

	private URI getResourceEmfURI(String resourcePath) {
		return URI.createURI(getClass().getResource("/resources/" + resourcePath).toExternalForm());
	}

}
