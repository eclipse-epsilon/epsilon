/*******************************************************************************
 * Copyright (c) 2016-2022 University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Antonio Garcia-Dominguez - initial API and implementation
 *******************************************************************************/
package org.eclipse.epsilon.emc.cdo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistry;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistryPopulator;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.InvalidURIException;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.AbstractReflectiveEmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.util.container.IPluginContainer;

/**
 * CDO driver for Epsilon.
 */
public class CDOModel extends AbstractReflectiveEmfModel {

	public static final String PROPERTY_CDO_URL = "cdo.url";
	public static final String PROPERTY_CDO_NAME = "cdo.repo";
	public static final String PROPERTY_CDO_PATH = "cdo.path";
	public static final String PROPERTY_CDO_BRANCH = "cdo.branch";

	public static final String PROPERTY_CDO_COLLECTION_INITIAL = "cdo.collection.initial";
	public static final String PROPERTY_CDO_COLLECTION_RCHUNK = "cdo.collection.rchunk";
	public static final String PROPERTY_CDO_REVPREFETCH = "cdo.revprefetch";
	public static final String PROPERTY_CDO_FEATANALYZER = "cdo.featureAnalyzer";

	public static final String PROPERTY_CDO_CREATE_MISSING = "cdo.createMissingResource";

	private String repositoryName, serverURL, modelPath, branchName;
	private int cdoCollectionInitial = 0, cdoCollectionRChunk = 300, cdoRevPrefetching = 100;
	private CDOTransaction cdoTransaction;
	private boolean useFeatureAnalyzer = false, createMissingResource = false;

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		this.serverURL = (String) properties.get(PROPERTY_CDO_URL);
		this.repositoryName = (String) properties.get(PROPERTY_CDO_NAME);
		this.modelPath = (String) properties.get(PROPERTY_CDO_PATH);
		this.branchName = (String) properties.getProperty(PROPERTY_CDO_BRANCH);

		if (properties.hasProperty(PROPERTY_CDO_COLLECTION_INITIAL)) {
			this.cdoCollectionInitial = Integer.valueOf(properties.get(PROPERTY_CDO_COLLECTION_INITIAL).toString());
		}
		if (properties.hasProperty(PROPERTY_CDO_COLLECTION_RCHUNK)) {
			this.cdoCollectionRChunk = Integer.valueOf(properties.get(PROPERTY_CDO_COLLECTION_RCHUNK).toString());
		}
		if (properties.hasProperty(PROPERTY_CDO_REVPREFETCH)) {
			this.cdoRevPrefetching = Integer.valueOf(properties.get(PROPERTY_CDO_REVPREFETCH).toString());
		}
		this.useFeatureAnalyzer = properties.hasProperty(PROPERTY_CDO_FEATANALYZER);
		this.createMissingResource = properties.hasProperty(PROPERTY_CDO_CREATE_MISSING);

		load();
	}

	@Override
	protected void loadModel() throws EolModelLoadingException {
		try {
			final IConnector connector = Net4jUtil.getConnector(IPluginContainer.INSTANCE, serverURL);
			final CDONet4jSessionConfiguration sessionConfig = CDONet4jUtil.createNet4jSessionConfiguration();
			sessionConfig.setConnector(connector);
			sessionConfig.setRepositoryName(repositoryName);

			// tests with singleton query don't reveal benefits with CDO feature analyzers
			if (useFeatureAnalyzer) {
				sessionConfig.setFetchRuleManager(CDOUtil.createThreadLocalFetchRuleManager());
			}

			final CDONet4jSession cdoSession = sessionConfig.openNet4jSession();
			
			// Some tweaks were taken from https://wiki.eclipse.org/CDO/Tweaking_Performance
			cdoSession.options().setCollectionLoadingPolicy(CDOUtil.createCollectionLoadingPolicy(cdoCollectionInitial, cdoCollectionRChunk));

			CDOBranch branch = cdoSession.getBranchManager().getMainBranch();
			if (branchName != null && !"".equals(branchName.trim())) {
				branch = cdoSession.getBranchManager().getBranch(branchName);
				if (branch == null) {
					StringBuilder sb = new StringBuilder(String.format("Branch '%s' does not exist. Available branches:", branchName));
					appendBranches(cdoSession.getBranchManager().getMainBranch(), sb);
					throw new NoSuchElementException(sb.toString());
				}
			}
			cdoTransaction = cdoSession.openTransaction(branch);

			cdoTransaction.options().setRevisionPrefetchingPolicy(CDOUtil.createRevisionPrefetchingPolicy(cdoRevPrefetching));
			if (useFeatureAnalyzer) {
				cdoTransaction.options().setFeatureAnalyzer(CDOUtil.createModelBasedFeatureAnalyzer());
			}

			try {
				modelImpl = cdoTransaction.getResource(modelPath);
			} catch (InvalidURIException ex) {
				if (createMissingResource) {
					modelImpl = cdoTransaction.createResource(modelPath);
				} else {
					throw new NoSuchElementException(String.format(
						"No resource exists with path %s, and automated resource creation was disabled", modelPath));
				}
			}

			compositeRegistry = cdoTransaction.getSession().getPackageRegistry();
		} catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		}
	}

	private void appendBranches(CDOBranch mainBranch, StringBuilder sb) {
		sb.append(String.format("\n- %s", mainBranch.getPathName()));
		for (CDOBranch branch : mainBranch.getBranches()) {
			appendBranches(branch, sb);
		}
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public boolean isCreateMissingResource() {
		return createMissingResource;
	}

	public void setCreateMissingResource(boolean createMissingResource) {
		this.createMissingResource = createMissingResource;
	}

	@Override
	public void disposeModel() {
		super.disposeModel();

		if (cdoTransaction != null) {
			cdoTransaction.close();
			cdoTransaction.getSession().close();
			cdoTransaction = null;
		}
	}

	@Override
	protected EClass classForName(String name, EPackage.Registry registry) {
		boolean absolute = name.indexOf("::") > -1;

		for (Object pkg : registry.values()) {
			if (pkg instanceof EPackage.Descriptor) {
				// CDO often uses package descriptors in place of real packages
				pkg = ((EPackage.Descriptor)pkg).getEPackage();
			}
			if (pkg instanceof EPackage) {
				EClass eClass = classForName(name, absolute, (EPackage) pkg);
				if (eClass != null) {
					return eClass;
				}
			}
		}

		// Not in this registry? Is this the CDO registry?
		if (registry instanceof CDOPackageRegistry && registry != EPackage.Registry.INSTANCE) {
			/* 
			 * Try to populate the CDO registry from the client's global registry.
			 *
			 * We do this eager type registration in order to avoid a nasty side-effect
			 * of postponing metamodel registration in CDO, where the very first newly
			 * created instance may just be a DynamicEObjectImpl (as it was created from
			 * the client's local EPackage, not CDO's version of it), and will not cast
			 * down into a CDOObject.
			 */
			EClass localEClass = super.classForName(name, EPackage.Registry.INSTANCE);
			if (localEClass != null) {
				final String nsURI = localEClass.getEPackage().getNsURI();

				EPackage.Registry temporaryRegistry = new EPackageRegistryImpl();
				temporaryRegistry.put(nsURI, localEClass.getEPackage());
				CDOPackageRegistryPopulator.populate(temporaryRegistry, (CDOPackageRegistry) registry);

				EPackage populatedPackage = registry.getEPackage(nsURI);
				return classForName(name, absolute, populatedPackage);
			}
		}

		return null;
	}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		// See https://www.eclipse.org/forums/index.php/t/156816/
		// ("Delete elements in the model very slowly").

		// Fetch the entire subtree to be removed
		final CDOObject eob = (CDOObject) instance;
		final Set<CDOObject> toRemove = new HashSet<>();
		toRemove.add(eob);
		for (TreeIterator<EObject> it = eob.eAllContents(); it.hasNext(); ) {
			toRemove.add((CDOObject) it.next());
		}

		// Disconnect the entire subtree
		List<CDOObjectReference> refs = cdoTransaction.queryXRefs(toRemove);
		for (CDOObjectReference ref : refs) {
			final CDOObject src = ref.getSourceObject();
			final CDOObject target = ref.getTargetObject();
			final EStructuralFeature feature = ref.getSourceReference();
			if (feature.isDerived() || !feature.isChangeable()) {
				continue;
			}

			EcoreUtil.remove(src, feature, target);
		}

		for (CDOObject cdoObject : toRemove) {
			EcoreUtil.remove(cdoObject);
		}

		return true;
	}

	@Override
	protected Collection<EObject> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		final EClass eClass = classForName(type);

		final CDOView cdoView = getCDOResource().cdoView();
		final CDOQuery query = cdoView.createQuery(CDOProtocolConstants.QUERY_LANGUAGE_INSTANCES, null);
		query.setParameter(CDOProtocolConstants.QUERY_LANGUAGE_INSTANCES_TYPE, eClass);
		query.setParameter(CDOProtocolConstants.QUERY_LANGUAGE_INSTANCES_EXACT, true);
		final List<EObject> allInstances = query.getResult();

		return filterByResource(allInstances);
	}

	@Override
	protected Collection<EObject> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
		final EClass eClass = classForName(kind);
		final List<EObject> allInstances = getCDOResource().cdoView().queryInstances(eClass);
		return filterByResource(allInstances);
	}

	protected Collection<EObject> filterByResource(final List<EObject> allInstances) {
		final List<EObject> filtered = new ArrayList<>();
		for (EObject eob : allInstances) {
			if (eob.eResource() == modelImpl) {
				filtered.add(eob);
			}
		}
		return filtered;
	}

	@Override
	protected Collection<EObject> allContentsFromModel() {
		if (getCDOResource().isExisting()) {
			getCDOResource().cdoPrefetch(CDORevision.DEPTH_INFINITE);
		}
		return super.allContentsFromModel();
	}

	protected CDOResource getCDOResource() {
		return (CDOResource)modelImpl;
	}

	@Override
	public boolean store() {
		if (modelImpl == null) return false;

		try {
			modelImpl.save(null);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
