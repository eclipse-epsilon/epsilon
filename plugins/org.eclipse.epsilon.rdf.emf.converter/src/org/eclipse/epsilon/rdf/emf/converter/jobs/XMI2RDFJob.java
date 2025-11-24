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
package org.eclipse.epsilon.rdf.emf.converter.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceImpl;
import org.eclipse.epsilon.rdf.emf.config.RDFResourceConfiguration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

/**
 * Job which converts an XMI-based model to an RDF format,
 * producing a minimal .rdfres file in the process.
 */
public class XMI2RDFJob extends Job {

	private final IFile modelFile;
	private final String targetFileExtension;

	public XMI2RDFJob(IFile modelFile, String targetFileExtension, String targetFileType) {
		super("Convert XMI to " + targetFileType);
		this.modelFile = modelFile;
		this.targetFileExtension = targetFileExtension;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		var rdfresFilename = replaceFileExtension(modelFile.getName(), "rdfres");
		var ttlFilename = replaceFileExtension(modelFile.getName(), targetFileExtension);
		var config = new RDFResourceConfiguration();
		config.getDataModels().add(ttlFilename);

		IContainer container = modelFile.getParent();
		if (container instanceof IFolder folder) {
			// Create .rdfres file
			IFile rdfresFile = folder.getFile(rdfresFilename);
			try {
				saveConfiguration(monitor, config, rdfresFile);
			} catch (Exception ex) {
				return Status.error("Failed to save .rdfres", ex);
			}

			// Create empty .ttl file
			IFile ttlFile = folder.getFile(ttlFilename);
			try {
				if (ttlFile.exists()) {
					ttlFile.delete(true, monitor);
				}
				ttlFile.create(new ByteArrayInputStream(new byte[0]), true, monitor);
			} catch (Exception ex) {
				return Status.error("Failed to generate empty .ttl", ex);
			}

			// Load source resource
			ResourceSet sourceRS = new ResourceSetImpl();
			Resource sourceR = sourceRS.getResource(
				URI.createURI(modelFile.getLocationURI().toString()), true);

			// Load target resource
			ResourceSet targetRS = new ResourceSetImpl();
			RDFGraphResourceImpl targetR = (RDFGraphResourceImpl) targetRS.getResource(
				URI.createURI(rdfresFile.getLocationURI().toString()), true);

			// Replace contents and set up prefixes
			targetR.getContents().clear();
			Model jenaModel = targetR.getFirstNamedModel();
			jenaModel.setNsPrefix("rdf", RDF.uri);
			jenaModel.setNsPrefix("owl", OWL.NS);
			jenaModel.setNsPrefix("f", targetR.getURI() + "#");
			if (!sourceR.getContents().isEmpty()) {
				EObject root = sourceR.getContents().get(0);
				EPackage ePkg = root.eClass().getEPackage();

				String nsURIPrefix = ePkg.getNsURI();
				if (!nsURIPrefix.endsWith("#") && !nsURIPrefix.endsWith("/")) {
					nsURIPrefix += "#";
				}
				jenaModel.setNsPrefix("mm", nsURIPrefix);
			}
			targetR.getContents().addAll(sourceR.getContents());

			try {
				targetR.save(null);
			} catch (IOException e) {
				return Status.error("Failed to save target .rdfres", e);
			}
		} else {
			return Status.error("Container is not a folder");
		}

		return Status.OK_STATUS;
	}

	protected void saveConfiguration(IProgressMonitor monitor, RDFResourceConfiguration config, IFile rdfresFile) throws IOException, CoreException {
			CustomClassLoaderConstructor constructor = new CustomClassLoaderConstructor(
				this.getClass().getClassLoader(), new LoaderOptions());
			String dumpedYaml = new Yaml(constructor).dumpAsMap(config);
			try (
				ByteArrayInputStream bis = new ByteArrayInputStream(dumpedYaml.getBytes(StandardCharsets.UTF_8));
			) {
				if (rdfresFile.exists()) {
					PlatformUI.getWorkbench().getDisplay().syncCall(() -> {
						if (MessageDialog.openConfirm(null, "Overwrite?", rdfresFile.getName() + " already exists: overwrite?")) {
							rdfresFile.delete(true, monitor);
						}
						return null;
					});
				}
				rdfresFile.create(bis, true, monitor);
			}
	}

	protected String replaceFileExtension(String originalFilename, String newExtension) {
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex != -1) {
			return originalFilename.substring(0, dotIndex) + '.' + newExtension;
		} else {
			return originalFilename + '.' + newExtension;
		}
	}
}
