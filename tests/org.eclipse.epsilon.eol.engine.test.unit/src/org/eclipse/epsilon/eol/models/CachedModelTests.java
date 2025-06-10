/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CachedModelTests.AllContentsCachingTests.class,
                     CachedModelTests.AllOfTypeCachingTests.class,
                     CachedModelTests.AllOfKindCachingTests.class,
                     CachedModelTests.CreationTests.class,
                     CachedModelTests.DeletionTests.class,
                     CachedModelTests.ClearCacheTests.class,
                     CachedModelTests.CachingOffTests.class})
public class CachedModelTests {
	
	public static class AllContentsCachingTests {
		@Test
		public void shouldComputeAllContentsContentsOnFirstCall() throws Exception {			
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.allContents();
			
			verify(model).allContentsFromModel();
		}
		
		@Test
		public void shouldNotComputeAllContentsOnSecondCall() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.allContents();
			model.allContents();
			
			verify(model, times(1)).allContentsFromModel();
		}

		@SuppressWarnings("resource")
		@Test
		public void shouldNotAllowChanges() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			assertThrows(UnsupportedOperationException.class,
				() -> model.allContents().add("x"));
		}
	}

	public static class AllOfTypeCachingTests {
		@Test
		public void shouldComputeAllOfTypeOnFirstCall() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfType("Widget");
			
			verify(model).getAllOfTypeFromModel("Widget");
		}
		
		@Test
		public void shouldNotComputeAllOfTypeOnSecondCall() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfType("Widget");
			model.getAllOfType("Widget");
			
			verify(model, times(1)).getAllOfTypeFromModel("Widget");
		}
		
		@Test
		public void shouldNotRecomputeAllOfTypeIfFirstCallReturnsEmptyCollection() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getAllOfTypeFromModel("Widget")).thenReturn(new ArrayList<String>());
			
			model.getAllOfType("Widget");
			model.getAllOfType("Widget");
			
			verify(model, times(1)).getAllOfTypeFromModel("Widget");
		}
		
		@Test
		public void shouldComputeAllOfTypeIfTypeHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfType("Widget");
			model.getAllOfType("Sprocket");
			
			verify(model, times(1)).getAllOfTypeFromModel("Sprocket");
		}
		
		@Test
		public void shouldNotComputeAllOfTypeIfTypeIsEquivalentToACachedType() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getCacheKeyForType("org.example.Widget")).thenReturn("Widget");
			
			model.getAllOfType("Widget");
			model.getAllOfType("org.example.Widget");
			
			verify(model, never()).getAllOfTypeFromModel("org.example.Widget");
		}

		@SuppressWarnings("resource")
		@Test
		public void shouldNotAllowChanges() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			assertThrows(UnsupportedOperationException.class,
				() -> model.getAllOfType("Widget").add("x"));
		}
	}

	public static class AllOfKindCachingTests {
		@Test
		public void shouldComputeAllOfKindOnFirstCall() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfKind("Widget");
			
			verify(model).getAllOfKindFromModel("Widget");
		}
		
		@Test
		public void shouldNotComputeAllOfKindOnSecondCall() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfKind("Widget");
			model.getAllOfKind("Widget");
			
			verify(model, times(1)).getAllOfKindFromModel("Widget");
		}
		
		@Test
		public void shouldNotRecomputeAllOfKindIfFirstCallReturnsEmptyCollection() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getAllOfKindFromModel("Widget")).thenReturn(new ArrayList<String>());
			
			model.getAllOfKind("Widget");
			model.getAllOfKind("Widget");
			
			verify(model, times(1)).getAllOfKindFromModel("Widget");
		}
		
		@Test
		public void shouldComputeAllOfKindIfKindHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfKind("Widget");
			model.getAllOfKind("Sprocket");
			
			verify(model, times(1)).getAllOfKindFromModel("Sprocket");
		}
		
		@Test
		public void shouldNotComputeAllOfKindIfKindIsEquivalentToACachedKind() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getCacheKeyForType("org.example.Widget")).thenReturn("Widget");
			
			model.getAllOfKind("Widget");
			model.getAllOfKind("org.example.Widget");
			
			verify(model, never()).getAllOfKindFromModel("org.example.Widget");
		}

		@SuppressWarnings("resource")
		@Test
		public void shouldNotAllowChanges() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			assertThrows(UnsupportedOperationException.class,
				() -> model.getAllOfKind("Widget").add("x"));
		}
	}
	
	// TODO create / delete before caching has occured
	
	public static class CreationTests {
		@Test
		public void shouldAddToAllContentsCache() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			
			model.allContents();
			model.createInstance("Widget");
			
			assertTrue(model.allContents().contains("NewWidget"));
		}
		
		@Test
		public void shouldAddToTypeCache() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			
			model.getAllOfType("Widget");
			model.createInstance("Widget");
			
			assertTrue(model.getAllOfType("Widget").contains("NewWidget"));
		}
		
		@Test
		public void shouldAddToKindCache() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			
			model.getAllOfKind("Widget");
			model.createInstance("Widget");
			
			assertTrue(model.getAllOfKind("Widget").contains("NewWidget"));
		}
		
		@Test
		public void shouldAddToKindCacheForEveryTypeOfInstance() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getAllTypeNamesOf("NewWidget")).thenReturn(Arrays.asList("Widget", "Product", "NamedElement"));
			
			model.getAllOfKind("Widget");
			model.getAllOfKind("Product");
			model.getAllOfKind("NamedElement");
			model.createInstance("Widget");
			
			assertTrue(model.getAllOfKind("Widget").contains("NewWidget"));
			assertTrue(model.getAllOfKind("Product").contains("NewWidget"));
			assertTrue(model.getAllOfKind("NamedElement").contains("NewWidget"));
		}
		
		@Test
		public void shouldNotAddToAllContentsCacheIfAllContentsHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.createInstance("Widget");
			model.allContents();
			
			verify(model).allContentsFromModel();
		}
		
		@Test
		public void shouldNotAddToTypeCacheIfTypeHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.createInstance("Widget");
			model.getAllOfType("Widget");
			
			verify(model).getAllOfTypeFromModel("Widget");
		}
		
		@Test
		public void shouldNotAddToKindCacheIfKindHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.createInstance("Widget");
			model.getAllOfKind("Widget");
			
			verify(model).getAllOfKindFromModel("Widget");
		}
	}
	
	public static class DeletionTests {
		@Test
		public void shouldRemoveFromAllContentsCache() throws Exception {
			final SimpleCachedModel model = new SimpleCachedModel();
			
			model.allContents();
			model.deleteElement("FakeWidget");
			
			assertFalse(model.allContents().contains("FakeWidget"));
		}
		
		@Test
		public void shouldRemoveFromTypeCache() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
						
			model.getAllOfType("Widget");
			model.deleteElement("FakeWidget");
			
			assertFalse(model.getAllOfType("Widget").contains("FakeWidget"));
		}
		
		@Test
		public void shouldRemoveFromKindCache() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfKind("Widget");
			model.deleteElement("FakeWidget");
			
			assertFalse(model.getAllOfKind("Widget").contains("FakeWidget"));
		}
		
		@Test
		public void shouldRemoveFromEveryKindCacheForWhichTheDeletedElementIsAMember() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getAllOfKindFromModel("Product")).thenReturn(new ArrayList<>(Arrays.asList("FakeWidget")));
			when(model.getAllOfKindFromModel("NamedElement")).thenReturn(new ArrayList<>(Arrays.asList("FakeWidget")));

			when(model.getAllTypeNamesOf("FakeWidget")).thenReturn(new ArrayList<>(Arrays.asList("Widget", "Product", "NamedElement")));
			
			model.getAllOfKind("Widget");
			model.getAllOfKind("Product");
			model.getAllOfKind("NamedElement");
			model.deleteElement("FakeWidget");
			
			assertFalse(model.getAllOfKind("Widget").contains("FakeWidget"));
			assertFalse(model.getAllOfKind("Product").contains("FakeWidget"));
			assertFalse(model.getAllOfKind("NamedElement").contains("FakeWidget"));
		}
		
		@Test
		public void shouldNotRemoveFromAllContentsCacheIfAllContentsHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.deleteElement("FakeWidget");
			model.allContents();
			
			verify(model).allContentsFromModel();
		}
		
		@Test
		public void shouldNotRemoveFromTypeCacheIfTypeHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.deleteElement("FakeWidget");
			model.getAllOfType("Widget");
			
			verify(model).getAllOfTypeFromModel("Widget");
		}
		
		@Test
		public void shouldNotRemoveFromKindCacheIfTypeHasNotBeenCached() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.deleteElement("FakeWidget");
			model.getAllOfKind("Widget");
			
			verify(model).getAllOfKindFromModel("Widget");
		}
	}
	
	public static class ClearCacheTests {
		@Test
		public void shouldRecomputeAllContentsAfterCacheIsCleared() {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.allContents(); // cache all contents
			model.clearCache();  // should clear the cache
			model.allContents(); // should recompute from model
			
			verify(model, times(2)).allContentsFromModel();
		}
		
		@Test
		public void shouldRecomputeAllOfTypeAfterCacheIsCleared() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfType("Widget"); // cache all of type
			model.clearCache();           // should clear the cache
			model.getAllOfType("Widget"); // should recompute from model
			
			verify(model, times(2)).getAllOfTypeFromModel("Widget");
		}
		
		@Test
		public void shouldRecomputeAllOfKindAfterCacheIsCleared() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			model.getAllOfKind("Widget"); // cache all of kind
			model.clearCache();           // should clear the cache
			model.getAllOfKind("Widget"); // should recompute from model
			
			verify(model, times(2)).getAllOfKindFromModel("Widget");
		}
	}
	
	public static class CachingOffTests {
		@Test
		public void shouldRecomputeAllContentsFromModel() {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.allContentsFromModel()).thenReturn(Collections.singleton("FakeWidget"));
			
			model.setCachingEnabled(false);
			
			model.allContents(); 
			final Collection<String> result = model.allContents();
			
			verify(model, times(2)).allContentsFromModel();
			assertTrue(result.contains("FakeWidget"));
		}
		
		@Test
		public void shouldRecomputeAllOfTypeFromModel() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getAllOfTypeFromModel("Widget")).thenReturn(Collections.singleton("FakeWidget"));
			
			model.setCachingEnabled(false);
			
			model.getAllOfType("Widget");
			final Collection<String> result = model.getAllOfType("Widget"); 
			
			verify(model, times(2)).getAllOfTypeFromModel("Widget");
			assertTrue(result.contains("FakeWidget"));
		}
		
		@Test
		public void shouldRecomputeAllOfKindFromModel() throws Exception {
			final SimpleCachedModel model = spy(new SimpleCachedModel());
			
			when(model.getAllOfKindFromModel("Widget")).thenReturn(Collections.singleton("FakeWidget"));
			
			model.setCachingEnabled(false);
			
			model.getAllOfKind("Widget");
			final Collection<String> result = model.getAllOfKind("Widget");
			
			verify(model, times(2)).getAllOfKindFromModel("Widget");
			assertTrue(result.contains("FakeWidget"));
		}
	}

	// TODO: Could see if JUnit rules help to tidy up the tests
	
	public static class SimpleCachedModel extends CachedModel<String> {
		
		SimpleCachedModel() {
			setCachingEnabled(true);
		}
		
		@Override
		protected Collection<String> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
			return new ArrayList<>(Collections.singleton("Fake" + type));
		}

		@Override
		public Collection<String> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
			return new ArrayList<>(Collections.singleton("Fake" + kind));
		}	

		@Override
		protected Collection<String> allContentsFromModel() {
			return new ArrayList<>(Arrays.asList("FakeWidget", "FakeSprocket"));
		}

		@Override
		protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
			return type;
		}
		
		@Override
		protected String createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
			return "New" + type;
		}
		
		@Override
		protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
			return true;
		}
		
		@Override
		protected Collection<String> getAllTypeNamesOf(Object instance) {
			return Collections.singleton("Widget");
		}
		
		@Override
		public String getTypeNameOf(Object instance) {
			return "Widget";
		}
		
		@Override
		protected void loadModel() throws EolModelLoadingException {}
		
		@Override
		protected void disposeModel() {}
		
		/* The following methods aren't used by CachedModel */
		
		
		@Override
		public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getTypeOf(Object instance) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getElementById(String id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getElementId(Object instance) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setElementId(Object instance, String newId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean owns(Object instance) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isInstantiable(String type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isModelElement(Object instance) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasType(String type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean store(String location) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean store() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
