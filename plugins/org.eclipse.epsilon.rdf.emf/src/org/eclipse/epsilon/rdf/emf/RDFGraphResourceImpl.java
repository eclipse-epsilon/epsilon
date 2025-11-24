/********************************************************************************
 * Copyright (c) 2025 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.epsilon.rdf.emf.config.RDFResourceConfiguration;
import org.eclipse.epsilon.rdf.emf.config.RDFResourceConfigurationIO;
import org.eclipse.epsilon.rdf.validation.RDFValidation.ValidationMode.RDFModelValidationReport;
import org.eclipse.epsilon.rdf.validation.RDFValidationException;

public class RDFGraphResourceImpl extends ResourceImpl {

	private static final Optional<Method> GET_FILE_LOCATOR;
	private static final boolean NOTIFICATION_TRACE = false;

	static {
		/*
		 * We use the Eclipse Core Resources FileLocator class through reflection, so
		 * we can keep this as an optional dependency (to simplify reuse from plain Java).
		 */
		Optional<Method> result = Optional.empty();
		try {
			Class<?> fileLocatorKlazz = Class.forName("org.eclipse.core.runtime.FileLocator");
			result = Optional.of(fileLocatorKlazz.getMethod("toFileURL", URL.class));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			if (NOTIFICATION_TRACE) {
				System.err.println("FileLocator not found: only file URLs are supported for saving");
			}
		}
		GET_FILE_LOCATOR = result;
	}

	private RDFResourceConfiguration config = new RDFResourceConfiguration();
	private boolean isConfigSaved = false;

	private RDFDeserializer deserializer;
	private RDFGraphResourceUpdate rdfGraphUpdater;
	private Dataset dataModelSet;

	public RDFGraphResourceImpl() {
		// Apply eAdapters for notifications of changes, and setup the Graph Resource updater
		if (NOTIFICATION_TRACE) {
			// Produce a console trace for debugging and development
			this.eAdapters().add(new RDFGraphResourceNotificationAdapterTrace(this));
		}
		this.eAdapters().add(new RDFGraphResourceNotificationAdapterChangeRDF(this));
	}

	/**
	 * Returns whether the {@code .rdfres} configuration will also be saved.
	 * It is false by default, but it will automatically be set to true after
	 * the first change on a resource that has not been loaded.
	 */
	public boolean isConfigSaved() {
		return isConfigSaved;
	}

	/**
	 * Changes whether the {@code .rdfres} configuration will also be saved.
	 *
	 * @see #isConfigSaved()
	 */
	public void setConfigSaved(boolean isConfigSaved) {
		this.isConfigSaved = isConfigSaved;
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		if (this.getURI().isRelative()) {
			throw new IllegalArgumentException("URI must be absolute");
		}

		this.config = RDFResourceConfigurationIO.load(inputStream);
		OntModel rdfOntModel = loadRDFModels();
		initializeFromRDFModel(rdfOntModel);
	}

	protected void initializeFromRDFModel(Model rdfOntModel) {
		try {
			setDisabledForAdapters(true);
			deserializer = new RDFDeserializer(() -> {
				if (this.getResourceSet() != null) {
					// Prefer the resource set's package registry
					return this.getResourceSet().getPackageRegistry();
				} else {
					// Fall back to the global package registry
					return EPackage.Registry.INSTANCE;
				}
			});
			deserializer.deserialize(rdfOntModel);
			for (EObject eob : deserializer.getEObjectToResourceMap().keySet()) {
				if (eob.eContainer() == null) {
					getContents().add(eob);
				}
			}
		} finally {
			setDisabledForAdapters(false);
		}

		rdfGraphUpdater = new RDFGraphResourceUpdate(deserializer, this);
	}

	@Override
	protected void doUnload() {
		/*
		 * Disable adapters prior to unloading - no need to sync removal of EMF contents
		 * into RDF graph.
		 */
		setDisabledForAdapters(true);

		this.deserializer = null;
		this.rdfGraphUpdater = null;
		this.dataModelSet = null;

		super.doUnload();
	}

	protected void setDisabledForAdapters(boolean isDisabled) {
		this.eAdapters().forEach(a -> {
			if (a instanceof IDisableable d) {
				d.setDisabled(isDisabled);
			}
		});
	}

	protected RDFGraphResourceUpdate getRDFGraphUpdater() {
		return rdfGraphUpdater;
	}
	
	// Save the Graph resource
	private void storeDatasetNamedModels(Dataset dataset, String namedModelURI, URL targetURL) throws IOException {
		// NamedModelURI is a model in the provided dataset and saveLocationURI is the file system path to save it too.
		if (dataset.containsNamedModel(namedModelURI))
		{
			Model modelToSave = dataset.getNamedModel(namedModelURI);

			/*
			 * Note: in Jena 5.x, hint takes priority over guessing by file extension. Best
			 * to try guessing by file extension and only fall back to Turtle if needed
			 */
			Lang lang = RDFDataMgr.determineLang(namedModelURI, null, null);
			if (lang == null) {
				lang = Lang.TTL;
			}

			if ("file".equals(targetURL.getProtocol()) || targetURL.getProtocol() == null) {
				try (OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(targetURL.toURI())))) {
					RDFDataMgr.write(out, modelToSave, lang);
					out.close();
				} catch (URISyntaxException e) {
					throw new IOException(e);
				}
			} else {
				throw new IOException("Protocol " + targetURL.getProtocol() + " is not supported");
			}
		}
		else {
			System.err.printf("Cannot find named model URI: %s\n", namedModelURI);
		}
	}
	
	@Override
	public void save(Map<?, ?> options) throws IOException {
		if (isConfigSaved) {
			String pathRdfRes = getFilePathForURI();
			if (pathRdfRes == null) {
				throw new IllegalStateException("Cannot save configuration to a non-file URI");
			}
			File fRdfRes = new File(pathRdfRes);
			try (FileWriter w = new FileWriter(fRdfRes)) {
				RDFResourceConfigurationIO.save(config, w);
			}
		}

		// TODO need some way to work out which of the Named models we want to write out, for now dump them all.
		for (Iterator<Resource> namedModels = dataModelSet.listModelNames(); namedModels.hasNext(); ) {
			Resource m = namedModels.next();
			URL url = new URL(m.getURI());

			try {
				storeDatasetNamedModels(dataModelSet, m.getURI(), convertToFileURL(url));
			} catch (URISyntaxException e) {
				throw new IOException(e);
			}
		}
	}

	public Resource getRDFResource(EObject eob) {
		return deserializer.getRDFResource(eob);
	}

	public EObject createInstanceAt(EClass eClass, String iri) {
		EObject eob = eClass.getEPackage().getEFactoryInstance().create(eClass);
		ensureGraphExists();

		List<Resource> resNamedModels = this.getResourcesForAllNamedModels();
		if (resNamedModels.isEmpty()) {
			throw new IllegalStateException("No named model in which to create the RDF resource");
		}

		for (Resource resNamedModel : resNamedModels) {
			rdfGraphUpdater.createNewEObjectResource(getNamedModel(resNamedModel), eob, iri);
		}
		getContents().add(eob);
		return eob;
	}

	protected OntModel loadRDFModels() throws IOException {
		Dataset schemaModelSet = loadRDFModels(config.getSchemaModels());
		Model rdfSchemaModel = schemaModelSet.getUnionModel();

		this.dataModelSet = loadRDFModels(config.getDataModels());
		Model rdfDataModel = dataModelSet.getUnionModel();

		InfModel infModel = ModelFactory.createRDFSModel(rdfSchemaModel, rdfDataModel);
		OntModel rdfOntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF, infModel);

		RDFModelValidationReport result = getConfig().getRawValidationMode().validate(rdfOntModel);
		if (!result.isValid()) {
			throw new RDFValidationException(result.getText());
		}
		return rdfOntModel;
	}

	protected Dataset loadRDFModels(Collection<String> uris) throws IOException, MalformedURLException {
		Dataset newDataset = null;
		List<String> namedModelSources = new ArrayList<>();

		for (String sURI : uris) {
			URI uri = URI.createURI(sURI);
			if (uri.isRelative()) {
				uri = uri.resolve(this.getURI());
			}

			/*
			 * NOTE: ideally we'd use a Jena StreamManager to handle this, but we can't
			 * because we would run into a conflict between the SLF4J used in Eclipse and
			 * the SLF4J bundled within Jena. It's simpler to just preprocess the platform
			 * URI here.
			 */
			if ("platform".equals(uri.scheme()) && GET_FILE_LOCATOR.isPresent()) {
				try {
					URL convertedURL = convertToFileURL(new URL(uri.toString()));
					namedModelSources.add(convertedURL.toString());
				} catch (URISyntaxException e) {
					throw new IOException(e);
				}
			} else {
				namedModelSources.add(uri.toString());
			}
		}

		// create a dataset with all the named models
		if (!namedModelSources.isEmpty()) {
			newDataset = DatasetFactory.createNamed(namedModelSources);
		} else {
			newDataset = DatasetFactory.create(); // create an empty one
		}
		return newDataset;
	}

	public RDFResourceConfiguration getConfig() {
		return config;
	}

	public void setConfig(RDFResourceConfiguration config) {
		this.config = config;
	}

	public List<Resource> getResourcesForNamedModelsContaining(EObject eObject) {
		Resource res = this.getRDFResource(eObject);
		return getResourcesForNamedModelsContaining(res);
	}

	public List<Resource> getResourcesForNamedModelsContaining(Resource res) {
		List<Resource> resources = new ArrayList<Resource>();
		if (null != dataModelSet && null != res) {
			Iterator<Resource> namedModels = dataModelSet.listModelNames();
			namedModels.forEachRemaining(m -> {
				Model model = dataModelSet.getNamedModel(m);
				if (model.containsResource(res)) {
					resources.add(m);
				}
			});
		}
		return resources;
	}

	public Model getNamedModel(Resource model) {
		return dataModelSet.getNamedModel(model);
	}
	
	public Model getFirstNamedModel() {
		return dataModelSet.getNamedModel(getResourcesForAllNamedModels().get(0));
	}

	public Resource getFirstNamedModelResource() {
		return getResourcesForAllNamedModels().get(0);
	}

	public List<Model> getNamedModels(List<Resource> namedModelURIs) {
		List<Model> namedModels = new ArrayList<Model>();
		for (Resource model : namedModelURIs) {
			namedModels.add(dataModelSet.getNamedModel(model));
		}
		return namedModels;
	}

	public List<Resource> getResourcesForAllNamedModels() {
		List<Resource> modelResourceList = new ArrayList<Resource>();
		dataModelSet.listModelNames().forEachRemaining(m->modelResourceList.add(m));
		return modelResourceList;
	}

	protected URL convertToFileURL(URL url) throws URISyntaxException {
		if (GET_FILE_LOCATOR.isPresent()) {
			try {
				return (URL) GET_FILE_LOCATOR.get().invoke(null, url);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.err.println("Failed to use FileLocator with exception below, using URL as is: ");
				e.printStackTrace();
			}
		}

		// Leave URL as is if we don't have access to the Eclipse FileLocator
		return url;
	}

	public void ensureGraphExists() {
		if (dataModelSet != null) {
			// There is an RDF graph set up: good to go
			return;
		}

		// RDF graph is not available: user wants to change the resource without loading it
		setConfigSaved(true);

		// Ensure there is at least one data model in the configuration
		if (config.getDataModels().isEmpty()) {
			if (getURI() == null) {
				throw new IllegalStateException("Cannot create in-memory .ttl on the fly for a resource with a null URI");
			}

			String rdfResPath = getFilePathForURI();
			if (rdfResPath == null) {
				throw new IllegalStateException("Cannot create in-memory .ttl on the fly for a resource in a non-file URI");
			}

			try {
				File fRdfRes = new File(rdfResPath).getCanonicalFile();
				File fTurtle = new File(fRdfRes.getParentFile(), fRdfRes.getName().replaceAll("[.]rdfres", ".ttl"));
				config.getDataModels().add(fTurtle.getName());
			} catch (IOException ex) {
				throw new RuntimeException("Could not compute path to .ttl on the fly", ex);
			}
		}

		// Create an in-memory graph based on the data models
		this.dataModelSet = DatasetFactory.createGeneral();
		for (String sURI : config.getDataModels()) {
			URI uri = URI.createURI(sURI);
			if (uri.isRelative()) {
				uri = uri.resolve(this.getURI());
			}
			dataModelSet = dataModelSet.addNamedModel(ResourceFactory.createResource(uri.toString()), ModelFactory.createDefaultModel());
		}
		initializeFromRDFModel(this.dataModelSet.getUnionModel());
	}

	protected String getFilePathForURI() {
		String rdfResPath = getURI().toFileString();
		if ("platform".equals(uri.scheme()) && GET_FILE_LOCATOR.isPresent()) {
			try {
				URL fileURL = convertToFileURL(new URL(uri.toString()));
				if (!"file".equals(fileURL.getProtocol())) {
					throw new IllegalStateException("Cannot convert the platform: URL to a file: URL - " + uri);
				}
				rdfResPath = URI.createURI(fileURL.toString()).toFileString();
			} catch (URISyntaxException | MalformedURLException ex) {
				throw new RuntimeException("Invalid URI syntax: " + uri);
			}
		}
		return rdfResPath;
	}
}
