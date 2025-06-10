/*******************************************************************************
 * Copyright (c) 2010-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - concurrency support
 ******************************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;

/**
 * A model that performs memoization of allContents, getAllOfType
 * and getAllOfKind. Subclasses should implement the template
 * methods, which compute real values from the model
 * or provide keys that can be used with a {@link Map}.
 * 
 * Although the collections used here are optionally thread-safe,
 * subclasses should manage their own data structures according to the
 * isConcurrent flag. In particular, {@link #allContentsFromModel()} should
 * be managed appropriately by subclasses.
 */
public abstract class CachedModel<ModelElementType> extends Model {
	
	/**
	 * Whether to cache allOf* calls by default. False for compatibility.
	 */
	public static final String PROPERTY_CACHED = "cached";
	
	/**
	 * Whether to use thread-safe collections by default. False for compatibility.
	 * @since 1.6
	 */
	public static final String PROPERTY_CONCURRENT = "concurrent";
	
	protected abstract Collection<ModelElementType> allContentsFromModel();
	
	protected abstract Collection<ModelElementType> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException;
	protected abstract Collection<ModelElementType> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException;
	protected abstract ModelElementType createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException;
	protected abstract void loadModel() throws EolModelLoadingException;
	protected abstract void disposeModel();
	
	/**
	 * Returns true iff the given instance was deleted from the model.
	 */
	protected abstract boolean deleteElementInModel(Object instance) throws EolRuntimeException;
	
	/**
	 * Returns an identity for the given type, 
	 * which will be used by {@link CachedModel}
	 * as a key for the memoization of model elements
	 * by type.
	 * 
	 * @throws EolModelElementTypeNotFoundException
	 */
	protected abstract Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException;
	
	/**
	 * Returns the fully qualified names of every type to which the
	 * given object conforms. The values are used by
	 * by {@link CachedModel} for the memoization of
	 * model elements by their kind ({@link #getAllOfKind(String)}).
	 */
	protected abstract Collection<String> getAllTypeNamesOf(Object instance);
	
	
	protected Collection<ModelElementType> allContentsCache;
	protected Multimap<Object, ModelElementType> typeCache;
	protected Multimap<Object, ModelElementType> kindCache;
	boolean concurrent;
	boolean cachingEnabled;
	
	/**
	 * @since 1.6
	 */
	protected CachedModel() {
		initCaches();
	}
	
	/**
	 * Sets the caches based on this model's properties. This
	 * method should only be called if there has been a change to
	 * the properties ({@link #cachingEnabled} or {@link #concurrent})
	 * as the caches will be reset.
	 * 
	 * @since 1.6
	 */
	protected synchronized void initCaches() {
		typeCache = new Multimap<>(concurrent, false, typeCache);
		kindCache = new Multimap<>(concurrent, false, kindCache);
		
		if (allContentsCache != null) {
			allContentsCache = wrap(allContentsCache);
		}
	}
	
	public void setCachingEnabled(boolean cachingEnabled) {
		if (this.cachingEnabled != cachingEnabled) {
			this.cachingEnabled = cachingEnabled;
			if (cachingEnabled) {
				initCaches();
			}
			else {
				allContentsCache = null;
				kindCache = null;
				typeCache = null;
			}
		}
	}
	
	public boolean isCachingEnabled() {
		return cachingEnabled;
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isConcurrent() {
		return concurrent;
	}
	
	/**
	 * 
	 * @param concurrent
	 * @since 1.6
	 */
	public void setConcurrent(boolean concurrent) {
		if (this.concurrent != concurrent) {
			this.concurrent = concurrent;
			initCaches();
		}
	}
	
	/**
	 * 
	 * @param contents The model elements
	 * @return
	 * @since 1.6
	 */
	protected Collection<ModelElementType> wrap(Collection<ModelElementType> contents) {
		Collection<ModelElementType> result = contents;
		if (isConcurrent()) {
			result = ConcurrencyUtils.concurrentOrderedCollection(contents);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> Collection<T> wrapUnmodifiable(Collection<T> original) {
		if (original instanceof List) {
			return Collections.unmodifiableList((List) original);
		} else if (original instanceof NavigableSet) {
			return Collections.unmodifiableNavigableSet((NavigableSet) original);
		} else if (original instanceof SortedSet) {
			return Collections.unmodifiableSortedSet((SortedSet) original);
		} else if (original instanceof Set) {
			return Collections.unmodifiableSet((Set) original);
		} else {
			return Collections.unmodifiableCollection(original);
		}
	}

	protected void addToCache(String type, ModelElementType instance) throws EolModelElementTypeNotFoundException {
		assert cachingEnabled;
		
		if (allContentsCache != null) {
			allContentsCache.add(instance);
		}
		
		if (typeCache != null) {
			Object typeCacheKey = getCacheKeyForType(type);
			typeCache.putIfPresent(typeCacheKey, instance);
		}

		if (kindCache != null) {
			for (String kind : getAllTypeNamesOf(instance)) {
				Object kindCacheKey = getCacheKeyForType(kind);
				kindCache.putIfPresent(kindCacheKey, instance);
			}
		}
	}

	protected void removeFromCache(ModelElementType instance) throws EolModelElementTypeNotFoundException {
		assert cachingEnabled;
		
		if (allContentsCache != null) {
			allContentsCache.remove(instance);
		}

		if (typeCache != null) {
			Object typeCacheKey = getCacheKeyForType(getTypeNameOf(instance));
			typeCache.remove(typeCacheKey, instance);
		}

		if (kindCache != null) {
			for (String kind : getAllTypeNamesOf(instance)) {
				Object kindCacheKey = getCacheKeyForType(kind);
				kindCache.remove(kindCacheKey, instance);
			}
		}
	}
	
	/**
	 * Convenience method for determining when a model has been loaded.
	 * @return Whether the cache has been populated.
	 * @since 1.6
	 */
	public boolean isLoaded() {
		return allContentsCache != null;
	}
	
	@Override
	public Collection<ModelElementType> allContents() {
		// Prevent race condition which could result in multiple threads calling allContentsFromModel
		if (isCachingEnabled()) {
			if (allContentsCache == null) synchronized (this) {
				// Could've changed while we were waiting on the lock
				if (allContentsCache == null) {
					allContentsCache = wrap(allContentsFromModel());
					if (allContentsCache == null) {
						return wrapUnmodifiable(wrap(new ArrayList<>(0)));
					}
				}
			}
			return wrapUnmodifiable(allContentsCache);
		}
		else return wrap(allContentsFromModel());
	}
	
	/**
	 * 
	 * @param isKind <code>true</code> if calling {@link #getAllOfKind(String)}.
	 * @param modelElementType The name of the model element type.
	 * @return All model elements conforming to the kind / type (depending on the flag).
	 * @throws EolModelElementTypeNotFoundException
	 * @since 1.6
	 */
	protected Collection<ModelElementType> getAllOfKindOrType(boolean isKind, String modelElementType) throws EolModelElementTypeNotFoundException {
		Collection<ModelElementType> values = null;
		
		// The code below is to prevent duplicate calls to getAllOf*FromModel.
		// With multiple threads there could be a race condition, so the
		// intent is to block the threads until the cache has been populated
		// by a single thread, and the others can just pick up from the cache
		// rather than recalculating.
		
		if (isCachingEnabled()) {
			final Multimap<Object, ModelElementType> cache = isKind ? kindCache : typeCache;
			final Object key = getCacheKeyForType(modelElementType);
			
			if ((values = cache.getMutable(key)) == null) synchronized (this) {
				// Could've changed while we were waiting on the lock
				if (!concurrent || (values = cache.getMutable(key)) == null) {
					values = wrap(isKind ?
						getAllOfKindFromModel(modelElementType) :
						getAllOfTypeFromModel(modelElementType)
					);
					cache.putAll(key, values, values == null);
				}
			}
		}
		else if (values == null) {
			values = wrap(isKind ?
				getAllOfKindFromModel(modelElementType) :
				getAllOfTypeFromModel(modelElementType)
			);
		}
		
		return wrapUnmodifiable(values);
	}
	
	@Override
	public Collection<ModelElementType> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		return getAllOfKindOrType(false, type);
	}
	
	@Override
	public Collection<ModelElementType> getAllOfKind(String kind) throws EolModelElementTypeNotFoundException {
		return getAllOfKindOrType(true, kind);
	}
	
	@Override
	public ModelElementType createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		ModelElementType instance = createInstanceInModel(type);
		if (isCachingEnabled()) {
			addToCache(type, instance);
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteElement(Object o) throws EolRuntimeException {
		if (deleteElementInModel(o) && isCachingEnabled()) {
			removeFromCache((ModelElementType) o);
		}
	}

	@Override
	public void load() throws EolModelLoadingException {
		clearCache();
		loadModel();
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		this.setCachingEnabled(properties.getBooleanProperty(PROPERTY_CACHED, cachingEnabled));
		this.setConcurrent(properties.getBooleanProperty(PROPERTY_CONCURRENT, concurrent));
	}

	@Override
	public void dispose() {
		try {
			super.dispose();
		} finally {
			clearCache();
			disposeModel();
		}
	}

	public void clearCache() {
		allContentsCache = null;
		if (typeCache != null) typeCache.clear();
		if (kindCache != null) kindCache.clear();
	}
}
