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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.dt.test.util.TestThatUsesAProject;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.osgi.framework.FrameworkUtil;

/**
 * These tests check the way in which an {@link EmfModel} can be loaded from
 * an instance of {@link StringProperties}, and should be run with the
 * EclipsePluginTest runner.
 * 
 * See #341481 for a description of the latest loading protocol.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({EmfModelLoadPropertiesTests.MetamodelsCanBeLoadedFromTheEPackageRegistry.class,
                     EmfModelLoadPropertiesTests.ModelsCanBeLoadedFromPlatformAndFileBasedURIs.class,
                     EmfModelLoadPropertiesTests.MetamodelsCanBeLoadedFromPlatformAndFileBasedURIs.class})
public class EmfModelLoadPropertiesTests {
	
	/*
	 * TODO: complete TODOs below
	 * TODO: put notes in 341481 to explain what I've changed and why
	 * TODO: link to 341481 from here and from EmfModel#loadProperties
	 * 
	 *  It might be best if the client creates the URI for the underlying model and passes it to loadProperties:
	 *   - Added a new property EmfModel.PROPERTY_MODEL_URI and deprecated EmfModel.PROPERTY_MODEL_FILE
	 *   - Updated clients (e.g. ANT tasks, EmfModelConfigurationDialogue)
	 *   - But there is a legacy problem (old ANT scripts, old launch configuration delegates)...
	 *   - TODO: Add notes on client changes to bug report
	 *   - TODO: Update LoadModelTask and EclipseContextManager, describe changes in bug report, test with new and legacy user data
	 *   
	 *  Added support for backwards-compatibility to loadProperties:
	 *    - When EmfModel.PROPERTY_MODEL_FILE_URI is not defined, defaults to old behaviour and uses EmfModel.PROPERTY_MODEL_FILE
	 *    - I did a try a more sophisticated approach where platform:/resource is tried, and then file:/, but sometimes users will
	 *      want to specify a platform:/resource URI that doen't yet exist (e.g. the target of a model transformation)
	 *
	 *   - TODO try changes on INESS
	 */
	
	// TODO fix other clients of load(Properties, String), such as EglServlet and the Ant tasks
	// TODO ensure only legacy code uses the EmfUtil.createPlatformResourceURI and createFileBasedURI methods, and maybe deprecate them
	
	private static final String BUNDLE_ID  = FrameworkUtil.getBundle(EmfModelLoadPropertiesTests.class).getSymbolicName();
	
	private static final URI METAMODEL_IN_BUNDLE = URI.createPlatformPluginURI("/" + BUNDLE_ID + "/model/Simple.ecore", true);
	private static final URI MODEL_IN_BUNDLE     = URI.createPlatformPluginURI("/" + BUNDLE_ID + "/model/Simple.model", true);
	
	
	private static StringProperties createPropertiesFor(URI modelFileUri) {
		final StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME,       "Simple");
		properties.put(EmfModel.PROPERTY_READONLOAD, "true");		
		properties.put(EmfModel.PROPERTY_MODEL_URI,  modelFileUri);
		return properties;
	}

	private static void assertModelCanBeLoadedFrom(StringProperties properties) throws EolModelLoadingException {
		final EmfModel model = new EmfModel();
		model.load(properties);
		final EObject firstElement = model.getResource().getContents().get(0);
		assertEquals("Simple", firstElement.eClass().getName());
	}
	
	
	public static class MetamodelsCanBeLoadedFromTheEPackageRegistry extends TestThatUsesAProject  {
		
		private static List<EPackage> registeredPackages;

		@BeforeClass
		public static void registerMetamodel() throws Exception {
			registeredPackages = EmfUtil.register(METAMODEL_IN_BUNDLE, EPackage.Registry.INSTANCE);
		}
		
		@AfterClass
		public static void unregisterMetamodel() throws Exception {
			for (EPackage registeredPackage : registeredPackages) {
				EPackage.Registry.INSTANCE.remove(registeredPackage.getNsURI());
			}
		}
		
		@Test
		public void registeredMetamodelTest() throws Exception {
			final StringProperties properties = createPropertiesFor(MODEL_IN_BUNDLE);
			properties.put(EmfModel.PROPERTY_METAMODEL_URI, registeredPackages.get(0).getNsURI());
			assertModelCanBeLoadedFrom(properties);
		}
	}
	
	
	public static class ModelsCanBeLoadedFromPlatformAndFileBasedURIs extends TestThatUsesAProject  {
		
		private static List<EPackage> registeredPackages;
		
		private static StringProperties createPropertiesWithRegisteredMetamodelFor(URI modelFileUri) {
			final StringProperties properties = createPropertiesFor(modelFileUri);
			properties.put(EmfModel.PROPERTY_METAMODEL_URI, registeredPackages.get(0).getNsURI());
			return properties;
		}
		
		@BeforeClass
		public static void registerMetamodel() throws Exception {
			registeredPackages = EmfUtil.register(METAMODEL_IN_BUNDLE, EPackage.Registry.INSTANCE);
		}
		
		@AfterClass
		public static void unregisterMetamodel() throws Exception {
			for (EPackage registeredPackage : registeredPackages) {
				EPackage.Registry.INSTANCE.remove(registeredPackage.getNsURI());
			}
		}
		
		@Test
		public void modelInAPlatformPlugin() throws Exception {
			final StringProperties properties = createPropertiesWithRegisteredMetamodelFor(MODEL_IN_BUNDLE);
			
			assertModelCanBeLoadedFrom(properties);
		}
	
		@Test
		public void modelInAPlatformResource() throws Exception {
			final IFile aModelInTheWorkspace        = createFile(project, "Simple.model", MODEL_IN_BUNDLE);
			final URI   uriOfTheModelInTheWorkspace = URI.createPlatformResourceURI(aModelInTheWorkspace.getFullPath().toPortableString(), true);
			
			final StringProperties properties = createPropertiesWithRegisteredMetamodelFor(uriOfTheModelInTheWorkspace);
			
			assertModelCanBeLoadedFrom(properties);
		}
		
		@Test
		public void modelOnFileSystem() throws Exception {
			final IFile aModelInTheWorkspace         = createFile(project, "Simple.model", MODEL_IN_BUNDLE);
			final URI   uriOfTheModelOnTheFileSystem = URI.createFileURI(aModelInTheWorkspace.getLocation().toOSString());
	
			final StringProperties properties = createPropertiesWithRegisteredMetamodelFor(uriOfTheModelOnTheFileSystem);
			
			assertModelCanBeLoadedFrom(properties);
		}
	
	}
	
	public static class MetamodelsCanBeLoadedFromPlatformAndFileBasedURIs extends TestThatUsesAProject  {
		
		private static StringProperties createPropertiesForMetamodelAt(URI metamodelUri) {
			final StringProperties properties = createPropertiesFor(MODEL_IN_BUNDLE);
			
			properties.put(EmfModel.PROPERTY_IS_METAMODEL_FILE_BASED, "true");
			properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, metamodelUri);
			
			return properties;
		}
	
		@Test
		public void metamodelInAPlatformPlugin() throws Exception {
			final StringProperties properties = createPropertiesForMetamodelAt(METAMODEL_IN_BUNDLE);
			
			assertModelCanBeLoadedFrom(properties);
		}
		
		@Test
		public void metamodelInAPlatformResource() throws Exception {
			final IFile aMetamodelInTheWorkspace        = createFile(project, "Simple.ecore", METAMODEL_IN_BUNDLE);
			final URI   uriOfTheMetamodelInTheWorkspace = URI.createPlatformResourceURI(aMetamodelInTheWorkspace.getFullPath().toPortableString(), true);

			final StringProperties properties = createPropertiesForMetamodelAt(uriOfTheMetamodelInTheWorkspace);
			
			assertModelCanBeLoadedFrom(properties);
		}
		
		@Test
		public void metamodelOnFileSystem() throws Exception {
			final IFile aMetamodelInTheWorkspace         = createFile(project, "Simple.ecore", METAMODEL_IN_BUNDLE);
			final URI   uriOfTheMetamodelOnTheFileSystem = URI.createFileURI(aMetamodelInTheWorkspace.getLocation().toOSString());

			final StringProperties properties = createPropertiesForMetamodelAt(uriOfTheMetamodelOnTheFileSystem);

			assertModelCanBeLoadedFrom(properties);
		}
	}
}
