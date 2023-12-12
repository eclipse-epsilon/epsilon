/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.m3.Metamodel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class EmfModel extends AbstractReflectiveEmfModel {

	/**
	 * @deprecated {@link #PROPERTY_METAMODEL_URI} and {@link #PROPERTY_FILE_BASED_METAMODEL_URI} are now
	 * interpreted as comma-separated lists of 0+ metamodel locations, and it is allowed to mix both types
	 * of metamodels now. This property is no longer used.
	 */
	@Deprecated
	public static final String PROPERTY_IS_METAMODEL_FILE_BASED = "isMetamodelFileBased";

	/**
	 * One of the keys used to construct the first argument to {@link EmfModel#load(StringProperties, String)}.
	 * 
	 * This key is a comma-separated list of zero or more namespaces URI of some of the metamodels to which
	 * this model conforms. Users may combine this key with {@link #PROPERTY_FILE_BASED_METAMODEL_URI} to load
	 * both file-based and URI-based metamodels at the same time.
	 */
	public static final String PROPERTY_METAMODEL_URI = "metamodelUri";

	/**
	 * One of the keys used to construct the first argument to {@link EmfModel#load(StringProperties, String)}.
	 * 
	 * This key is a comma-separated list of zero or more {@link URI}s that can be used to locate some of the
	 * metamodels to which this model conforms. Users may combine this key with {@link #PROPERTY_METAMODEL_URI}
	 * to load both file-based and URI-based metamodels at the same time.
	 */
	public static final String PROPERTY_FILE_BASED_METAMODEL_URI = "fileBasedMetamodelUri";

	/**
	 * @deprecated Replaced by
	 *             {@link #PROPERTY_FILE_BASED_METAMODEL_URI}.
	 *              This property will be removed in a future release of Epsilon.
	 */
	@Deprecated
	public static final String PROPERTY_METAMODEL_FILE = "metamodelFile";
	
	/**
	 * One of the keys used to construct the first argument to {@link EmfModel#load(StringProperties, String)}.
	 * 
	 * This key is paired with a {@link URI} that can be used to locate this model.
	 * This key must always be paired with a value.
	 */
	public static final String PROPERTY_MODEL_URI = "modelUri";

	/**
	 * @deprecated  Replaced by
	 *              {@link #PROPERTY_MODEL_URI}.
	 *              This property will be removed in a future release of Epsilon.
	 */
	@Deprecated
	public static final String PROPERTY_MODEL_FILE = "modelFile";

	/**
	 * One of the keys used to construct the first argument to
	 * {@link EmfModel#load(StringProperties, String)}.
	 *
	 * This key is a Boolean value that if set to <code>true</code> (the
	 * default), tries to reuse previously registered file-based EPackages that
	 * have not been modified since the last time they were registered.
	 */
	public static final String PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS = "reuseUnmodifiedFileBasedMetamodels";

	/**
	 * One of the keys used to construct the first argument to {@link EmfModel#load(StringProperties, String)}.
	 * 
	 * This key is a Boolean value that if set to <code>true</code>
	 * it triggers validation of all the resources in the model's
	 * resource set after loading (default is <code>false</code>)
	 */
	public static final String PROPERTY_VALIDATE = "validate";
	
	protected List<URI> metamodelUris = new ArrayList<>();
	protected List<EPackage> packages;
	
	/**
	 * @deprecated
	 */
	@Deprecated
	protected boolean isMetamodelFileBased = false;
	
	protected URI modelUri;
	protected List<URI> metamodelFileUris = new ArrayList<>();
	protected boolean useExtendedMetadata = false;
	protected boolean validate = false;
	
	protected boolean reuseUnmodifiedFileBasedMetamodels = true;
	protected static Map<String, List<EPackage>> fileBasedMetamodels = new HashMap<>();
	protected static Map<String, Long> fileBasedMetamodelTimestamps = new HashMap<>();
	

	public EmfModel() {
		super();
		propertySetter = new EmfPropertySetter();
	}
	
	/**
	 * Load the model using the set of properties specified by the first argument.
	 * 
	 * @see #PROPERTY_MODEL_URI
	 * @see #PROPERTY_IS_METAMODEL_FILE_BASED
	 * @see #PROPERTY_EXPAND
	 * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=341481">Rationale for deprecating the FILE properties</a>.
	 */
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		PropertyMigrator.migrateDeprecatedProperties(properties);	
		
		super.load(properties, resolver);
		this.modelUri = URI.createURI(properties.getProperty(PROPERTY_MODEL_URI));
		this.isMetamodelFileBased = properties.getBooleanProperty(PROPERTY_IS_METAMODEL_FILE_BASED, isMetamodelFileBased);
		
		this.metamodelUris = toURIList(properties.getProperty(PROPERTY_METAMODEL_URI));
		setMetamodelFileUris(toURIList(properties.getProperty(PROPERTY_FILE_BASED_METAMODEL_URI)));
		setReuseUnmodifiedFileBasedMetamodels(properties.getBooleanProperty(PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS, reuseUnmodifiedFileBasedMetamodels));
		setValidate(properties.getBooleanProperty(PROPERTY_VALIDATE, false));
		
		load();
	}

	@Override
	protected void loadModel() throws EolModelLoadingException {
		loadModelFromUri();
		if (validate) validate();
		setupContainmentChangeListeners();
	}

	/**
	 * This listener is the one that keeps the cached .allInstances
	 * and model .allContents() lists up to date, instead of the usual
	 * createInstance/deleteInstance methods.
	 *
	 * This prevents the cache from becoming inconsistent if EcoreUtil
	 * or any other code outside Epsilon is used to create instances
	 * within the model.
	 */
	protected class CachedContentsAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			Object notifier = notification.getNotifier();
			Object feature = notification.getFeature();

			if (notifier instanceof Resource && notification.getFeatureID(Resource.class) == Resource.RESOURCE__CONTENTS) {
				handle(notification);
			}
			else if (feature instanceof EReference && ((EReference)feature).isContainment()) {
				handle(notification);
			}
		}

		protected void handle(Notification notification) {
			try {
				switch (notification.getEventType()) {
				case Notification.UNSET: {
					Object oldValue = notification.getOldValue();
					if (oldValue != Boolean.TRUE && oldValue != Boolean.FALSE) {
						if (oldValue != null) {
							forceRemoveFromCache((EObject) oldValue);
						}
						EObject newValue = (EObject) notification.getNewValue();
						if (newValue != null) {
							forceAddToCache(newValue);
						}
					}
					break;
				}
				case Notification.SET: {
					EObject oldValue = (EObject) notification.getOldValue();
					if (oldValue != null) {
						forceRemoveFromCache(oldValue);
					}
					EObject newValue = (EObject) notification.getNewValue();
					if (newValue != null) {
						forceAddToCache(newValue);
					}
					break;
				}
				case Notification.ADD_MANY: {
					@SuppressWarnings("unchecked")
					Collection<EObject> newValues = (Collection<EObject>) notification.getNewValue();
					for (EObject newValue : newValues) {
						forceAddToCache(newValue);
					}
					break;
				}
				case Notification.REMOVE_MANY: {
					@SuppressWarnings("unchecked")
					Collection<EObject> oldValues = (Collection<EObject>) notification.getOldValue();
					for (EObject oldContentValue : oldValues) {
						forceRemoveFromCache(oldContentValue);
					}
					break;
				}
				case Notification.ADD: {
					EObject added = (EObject) notification.getNewValue();
					forceAddToCache(added);
					break;
				}
				case Notification.REMOVE: {
					EObject removed = (EObject) notification.getOldValue();
					forceRemoveFromCache(removed);
					break;
				}
				default:
					// do nothing
					break;
				}
			} catch (EolModelElementTypeNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Used for backwards-compatibility with existing Eclipse launch configurations.
	 * 
	 * See #341481
	 */
	static class PropertyMigrator {
		public static void migrateDeprecatedProperties(StringProperties properties) {
			migrateModelFileProperty(properties);
			migrateMetamodelFileProperty(properties);
		}
		
		private static void migrateModelFileProperty(StringProperties properties) {
			migrateUriValue(properties, PROPERTY_MODEL_FILE, PROPERTY_MODEL_URI);
		}
		
		private static void migrateMetamodelFileProperty(StringProperties properties) {
			migrateUriValue(properties, PROPERTY_METAMODEL_FILE, PROPERTY_FILE_BASED_METAMODEL_URI);
		}

		private static void migrateUriValue(StringProperties properties, String oldProperty, String newProperty) {
			if (properties.hasProperty(oldProperty) && !properties.hasProperty(newProperty)) {
				final String oldValue = properties.getProperty(oldProperty);

				final File oldFile = new File(oldValue);
				if (oldFile.canRead() || oldFile.getParentFile() != null && oldFile.getParentFile().canRead()) {
					// This is a regular path to a readable file (to be read) or in a readable directory (to be saved)
					properties.put(newProperty, EmfUtil.createFileBasedURI(oldValue));
				}
				else {
					// It's not a regular file path: treat it as a platform:/resource/ URI
					properties.put(newProperty, EmfUtil.createPlatformResourceURI(oldValue));
				}
			}
		}
	}

	@Override
	protected void addToCache(String type, EObject instance) throws EolModelElementTypeNotFoundException {
		// do nothing (we want to trigger changes through EMF adapters instead)
	}

	@Override
	protected void removeFromCache(EObject instance) throws EolModelElementTypeNotFoundException {
		// do nothing (we want to trigger changes through EMF adapters instead)
	}

	/**
	 * We want to use the overridden method, but not from
	 * {@link #createInstance(String)}, but rather from the adapter we set up to
	 * track additions and removals from the contents of a model. For this reason,
	 * we leave the overridden method empty and define this one that can be safely
	 * called from the adapter.
	 */
	protected void forceAddToCache(EObject instance) throws EolModelElementTypeNotFoundException {
		super.addToCache(getFullyQualifiedTypeNameOf(instance), instance);
		for (EObject child : instance.eContents()) {
			forceAddToCache(child);
		}
	}

	/** @see #forceAddToCache(String, EObject) */
	protected void forceRemoveFromCache(EObject instance) throws EolModelElementTypeNotFoundException {
		super.removeFromCache(instance);
		for (EObject child : instance.eContents()) {
			forceRemoveFromCache(child);
		}
	}
	
	public void setupContainmentChangeListeners() {
		// Add a notification adapter to all objects in the model
		// so that they get moved when their containment changes
		
		if (modelImpl != null) {
			for (EObject eObject : allContents()) {
				boolean isAdapted = false;
				for (Adapter adapter : eObject.eAdapters()) {
					if (adapter instanceof ContainmentChangeAdapter) {
						isAdapted = true;
					}
				}
				if (!isAdapted) {
					eObject.eAdapters().add(new ContainmentChangeAdapter(eObject, eObject.eResource()));
				}
			}
		}
	}
	
	protected ResourceSet createResourceSet() {
		return new CachedResourceSet();
	}
	
	public void loadModelFromUri() throws EolModelLoadingException {
		ResourceSet resourceSet = createResourceSet();
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new DefaultXMIResource.Factory());
		
        // Check if global package registry contains the EcorePackage
		if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		}
		
		determinePackagesFrom(resourceSet);
		
		// Note that AbstractEmfModel#getPackageRegistry() is not usable yet, as modelImpl is not set
		for (EPackage ep : packages) {
			String nsUri = ep.getNsURI();
			if (nsUri == null || nsUri.trim().length() == 0) {
				nsUri = ep.getName();
			}
			resourceSet.getPackageRegistry().put(nsUri, ep);
		}
		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		
		modelImpl = resourceSet.createResource(modelUri);
		if (this.readOnLoad) {
			try {
				modelImpl.load(getResourceLoadOptions());
				if (expand) {
					EcoreUtil.resolveAll(modelImpl);
				}
			} catch (IOException e) {
				throw new EolModelLoadingException(e, this);
			}
		}
		if (isCachingEnabled()) {
			addContentsAdapter();
		}
	}

	/**
	 * @since 1.6
	 */
	@Override
	public void setCachingEnabled(boolean cachingEnabled) {
		boolean wasEnabled = isCachingEnabled();
		super.setCachingEnabled(cachingEnabled);
		
		if (modelImpl != null) {
			if (!wasEnabled && cachingEnabled && !hasAdapter(CachedContentsAdapter.class)) {
				addContentsAdapter();
			}
			else if (wasEnabled && !cachingEnabled) {
				removeContentsAdapter();
			}
		}
	}

	@Override
	public void clearCache() {
		if (modelImpl != null && isCachingEnabled()) {
			removeContentsAdapter();
		}

		super.clearCache();
	}

	private void removeContentsAdapter() {
		modelImpl.eAdapters().removeIf(a -> a instanceof CachedContentsAdapter);
	}

	private void addContentsAdapter() {
		
		modelImpl.eAdapters().add(new CachedContentsAdapter());
	}
	
	public List<String> getMetamodelFiles() {
		final List<String> files = new ArrayList<>(metamodelFileUris.size());
		for (URI metamodelFileUri : this.metamodelFileUris) {
			files.add(EmfUtil.getFile(metamodelFileUri));
		}
		return files;
	}

	/**
	 * @deprecated This value is no longer used to load models: it is only
	 * kept for backwards compatibility, and it now simply indicates whether
	 * a file metamodel was loaded at all, or not.
	 */
	@Deprecated
	public boolean isMetamodelFileBased() {
		return isMetamodelFileBased;
	}

	/**
	 * @deprecated This value is no longer honored anymore. Please populate the
	 *             lists in {@link #getMetamodelUris()} (URI-based metamodels)
	 *             and {@link #getMetamodelFileUris()} (file-based metamodels)
	 *             appropriately instead.
	 */
	@Deprecated
	public void setMetamodelFileBased(boolean isMetamodelFileBased) {
		this.isMetamodelFileBased = isMetamodelFileBased;
	}

	public List<String> getMetamodelUris() {
		final List<String> uris = new ArrayList<>(metamodelUris.size());
		for (URI metamodelUri : this.metamodelUris) {
			uris.add(metamodelUri.toString());
		}
		return uris;
	}

	public String getModelFile() {
		return EmfUtil.getFile(modelUri);
	}

	public URI getModelFileUri() {
		return modelUri;
	}

	public void setModelFileUri(URI modelFileUri) {
		this.modelUri = modelFileUri;
	}

	public List<URI> getMetamodelFileUris() {
		return metamodelFileUris;
	}

	public void setMetamodelFileUris(List<URI> fileUris) {
		this.metamodelFileUris = new ArrayList<>(fileUris);
	}

	public void setMetamodelFileUri(URI uri) {
		metamodelFileUris = Arrays.asList(uri);
	}

	public void setMetamodelUris(List<String> uris) {
		this.metamodelUris.clear();
		CollectionUtil.addCapacityIfArrayList(this.metamodelUris, uris.size());
		
		for (String sURI : uris) {
			this.metamodelUris.add(URI.createURI(sURI));
		}
	}

	public void setMetamodelUri(String uri) {
		metamodelUris = Arrays.asList(URI.createURI(uri));
	}

	public void setMetamodelFiles(List<String> paths) {
		this.metamodelFileUris.clear();
		CollectionUtil.addCapacityIfArrayList(this.metamodelFileUris, paths.size());
		
		for (String sPath : paths) {
			this.metamodelFileUris.add(URI.createFileURI(sPath));
		}
	}

	public void setMetamodelFile(String path) {
		setMetamodelFileUri(URI.createFileURI(path));
	}

	public void setModelFile(String path) {
		this.modelUri = URI.createFileURI(path);
	}

	public boolean isReuseUnmodifiedFileBasedMetamodels() {
		return reuseUnmodifiedFileBasedMetamodels;
	}

	public void setReuseUnmodifiedFileBasedMetamodels(boolean reuseUnmodifiedFileBasedMetamodels) {
		this.reuseUnmodifiedFileBasedMetamodels = reuseUnmodifiedFileBasedMetamodels;
	}

	@Override
	public String toString() {
		return "EmfModel [name=" + getName() + "]";
	}

	protected void determinePackagesFrom(ResourceSet resourceSet) throws EolModelLoadingException {
		packages = new ArrayList<>();
		
		for (URI metamodelFileUri : this.metamodelFileUris) {
			List<EPackage> metamodelPackages = null;
			try {
				metamodelPackages = attemptFileBasedMetamodelReuse(metamodelFileUri);
				if (metamodelPackages == null) {
					metamodelPackages = EmfUtil.register(metamodelFileUri, resourceSet.getPackageRegistry(), false);
					saveFileBasedMetamodelForReuse(metamodelFileUri, metamodelPackages);
				}
			} catch (Exception e) {
				throw new EolModelLoadingException(e,this);
			}
			
			CollectionUtil.addCapacityIfArrayList(packages, metamodelPackages.size());
			for (EPackage metamodelPackage : metamodelPackages) {
				packages.add(metamodelPackage);
				EmfUtil.collectDependencies(metamodelPackage, packages);
			}
		}
	
		CollectionUtil.addCapacityIfArrayList(packages, this.metamodelUris.size());
		
		for (URI metamodelUri : this.metamodelUris) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(metamodelUri.toString());
			if (ePackage == null) {
				throw new EolModelLoadingException(new IllegalArgumentException("Could not locate a metamodel with the URI '" + metamodelUri + "'. Please ensure that this metamodel has been registered with Epsilon."), this);
			}
			packages.add(ePackage);
			EmfUtil.collectDependencies(ePackage, packages);
		}
	}

	private List<EPackage> attemptFileBasedMetamodelReuse(URI uri) {
		if (!reuseUnmodifiedFileBasedMetamodels || !uri.isFile()) {
			// Reuse has been disabled, or the URI is not for a file: do nothing
			return null;
		}

		final String path = uri.toFileString();
		final File metamodelFile = new File(path);
		final Long lastTimestamp = fileBasedMetamodelTimestamps.get(path);
		if (lastTimestamp == null || metamodelFile.lastModified() != lastTimestamp) {
			// We don't have this URI in our cache yet, or the file
			// has been modified since the last time we read it
			return null;
		}

		return fileBasedMetamodels.get(path);
	}

	private static void saveFileBasedMetamodelForReuse(URI uri, List<EPackage> packages) {
		// We always save the previously loaded metamodels, as we might want to force
		// a reload first in one EmfModel and then reuse the metamodel in the next
		// EmfModel.
		if (!uri.isFile()) {
			// The URI is not for a file: do nothing
			return;
		}

		final String path = uri.toFileString();
		final File metamodelFile = new File(path);
		final Long timestamp = metamodelFile.lastModified();
		fileBasedMetamodels.put(path, packages);
		fileBasedMetamodelTimestamps.put(path, timestamp);
	}

	private static List<URI> toURIList(final String commaSeparatedList) {
		return Stream.of(commaSeparatedList.trim().split("\\s*,\\s*"))
			.filter(s -> !s.isEmpty())
			.map(URI::createURI)
			.collect(Collectors.toList());
	}
	
	@Override
	public Metamodel getMetamodel(StringProperties properties, IRelativePathResolver resolver) {
		return new EmfModelMetamodel(properties, resolver);
	}
	
	@Override
	public boolean store() {
		if (modelImpl == null) return false;
		try {
			Map<Object, Object> options = getResourceStoreOptions();
			if (!metamodelFileUris.isEmpty()) {
				if (options == null) {
					options = new HashMap<>();
				}
				options.put(XMLResource.OPTION_SCHEMA_LOCATION, true);
			}
			modelImpl.save(options);
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isValidate() {
		return validate;
	}
	
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
}
