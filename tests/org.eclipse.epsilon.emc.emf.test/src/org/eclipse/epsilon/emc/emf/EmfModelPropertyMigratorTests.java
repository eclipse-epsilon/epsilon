/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuppressWarnings("deprecation")
@RunWith(Suite.class)
@SuiteClasses({EmfModelPropertyMigratorTests.MetamodelFilePropertyMigratorTests.class,
               EmfModelPropertyMigratorTests.ModelFilePropertyMigratorTests.class})
public class EmfModelPropertyMigratorTests {
	
	public static abstract class UriPropertyMigratorTests {
		private final StringProperties properties = new StringProperties();
		
		@Before
		public void clearProperties() {
			properties.clear();
		}
		
		protected abstract String getUnqualifiedUri();		
		protected abstract URI getPlatformResourceUri();
		protected abstract String getFileSystemPath();
		
		protected URI getFileUri() {
			return URI.createFileURI(getFileSystemPath());
		}
		
		@Test
		public void unqualifiedUriBecomesAPlatformResourceUri() throws Exception {	
			final String unqualifiedUri = getUnqualifiedUri();
			
			setLegacyPropertyTo(unqualifiedUri);
			
			assertMigratedPropertyEquals(URI.createPlatformResourceURI(unqualifiedUri, true));
		}

		@Test
		public void platformResourceURIRemainsAPlatformResourceUri() throws Exception {
			final URI platformResourceUri = getPlatformResourceUri();
			
			setLegacyPropertyTo(platformResourceUri);
			
			assertMigratedPropertyEquals(platformResourceUri);
		}
		
		@Test
		public void fileURIRemainsAFileUri() throws Exception {
			final URI fileUri = getFileUri();
			
			setLegacyPropertyTo(fileUri);
			
			assertMigratedPropertyEquals(fileUri);
		}

		@Test
		public void nothingIsInferredWhenThereIsNoLegacyProperty() {
			assertMigratedPropertyEquals(null);
		}
		
		@Test
		public void nothingIsInferredWhenTheLegacyPropertyIsAnEmptyString() {
			setLegacyPropertyTo("");
			
			assertMigratedPropertyEquals(null);
		}
		
		@Test
		public void newPropertyIsNotOverwrittenWhenItAlreadyHasAValue() {
			setNewPropertyTo("foo");
			setLegacyPropertyTo("bar");
			
			assertMigratedPropertyEquals("foo");
		}
	
		protected abstract String keyOfOldProperty();
		protected abstract String keyOfNewProperty();
		
		private void setLegacyPropertyTo(final Object value) {
			properties.put(keyOfOldProperty(), value);
		}
		
		private void setNewPropertyTo(final String newPropertyValue) {
			properties.put(keyOfNewProperty(),  newPropertyValue);
		}
		
		private void assertMigratedPropertyEquals(Object expected) {
			EmfModel.PropertyMigrator.migrateDeprecatedProperties(properties);
			
			final String expectedString = expected == null ? null : expected.toString();
			assertEquals(expectedString, properties.get(keyOfNewProperty()));	
		}
	}
	
	public static class MetamodelFilePropertyMigratorTests extends UriPropertyMigratorTests {
		
		protected String keyOfOldProperty() {
			return EmfModel.PROPERTY_METAMODEL_FILE;
		}
		
		protected String keyOfNewProperty() {
			return EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI;
		}
		
		protected String getUnqualifiedUri() {
			return "/Sample/My.ecore";
		}
		
		protected URI getPlatformResourceUri() {
			return URI.createPlatformResourceURI(getUnqualifiedUri(), true);
		}
		
		protected String getFileSystemPath() {
			return OperatingSystem.isWindows() ? "C:\\tmp\\My.ecore" : "/tmp/My.ecore";
		}
	}

	public static class ModelFilePropertyMigratorTests extends UriPropertyMigratorTests {

		protected String keyOfOldProperty() {
			return EmfModel.PROPERTY_MODEL_FILE;
		}
		
		protected String keyOfNewProperty() {
			return EmfModel.PROPERTY_MODEL_URI;
		}
		
		protected String getUnqualifiedUri() {
			return "/Sample/My.model";
		}
		
		protected URI getPlatformResourceUri() {
			return URI.createPlatformResourceURI(getUnqualifiedUri(), true);
		}
		
		protected String getFileSystemPath() {
			return OperatingSystem.isWindows() ? "C:\\tmp\\My.model" : "/tmp/My.model";
		}
	}
}
