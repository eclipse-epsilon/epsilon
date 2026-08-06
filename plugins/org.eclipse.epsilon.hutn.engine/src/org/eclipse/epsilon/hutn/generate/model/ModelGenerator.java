/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.generate.model;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.emc.emf.EmfMetaModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;
import org.eclipse.epsilon.hutn.generate.AbstractGenerator;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.util.EmcUtil;
import org.eclipse.epsilon.hutn.util.EpsilonUtil;

public class ModelGenerator extends AbstractGenerator {

	private final IModel source;
	private final Collection<IModel> metaModels = new LinkedList<>();	
	private final List<String> metaModelUris = new LinkedList<>();
	
	private boolean generateTransformationForWholeMetamodel = false;
	
	public ModelGenerator(Spec spec) throws HutnGenerationException {
		if (spec.getNsUris().isEmpty())
			throw new IllegalArgumentException("Spec does not contain any nsUris: " + spec);
		
		try {
			source = new InMemoryEmfModel("Intermediate", spec.eResource(), HutnPackage.eINSTANCE);
			
			for (NsUri nsUri : spec.getNsUris()) {
				this.metaModelUris.add(nsUri.getValue());
				
				final IModel metaModel = new EmfMetaModel("MetaModel", nsUri.getValue());
				metaModel.load();
				metaModels.add(metaModel);
			}
		
		} catch (EolModelLoadingException e) {
			throw new HutnGenerationException(e);
		}
	}
	
	public ModelGenerator(Spec spec, File... metaModelFiles) throws HutnGenerationException {
		try {			
			
			source = new InMemoryEmfModel("Intermediate", spec.eResource(), HutnPackage.eINSTANCE);
			
			for (File metaModelFile : metaModelFiles) {
				this.metaModelUris.add(URI.createFileURI(metaModelFile.getAbsolutePath()).toString());
				
				EmfUtil.register(URI.createFileURI(metaModelFile.getAbsolutePath()), EPackage.Registry.INSTANCE);				
				metaModels.add(EmcUtil.loadMetaModel("MetaModel", metaModelFile));
			}
		
		} catch (Exception e) {
			throw new HutnGenerationException(e);
		}
	}
	
	protected String[] getNsUrisForTargetModel() throws EolModelElementTypeNotFoundException {
		final List<String> nsUris = new LinkedList<>();
		
		for (IModel metaModel : metaModels) {
			for (Object o : metaModel.getAllOfType("EPackage")) {
				if (o instanceof EPackage) {
					nsUris.add(((EPackage)o).getNsURI());
				}
			}
		}
		
		return nsUris.toArray(new String[]{});
	}
	
	@Override
	protected EmfModel generate(Resource resource) throws HutnGenerationException {
		try {
			final EmfModel target = new InMemoryEmfModel("Model", resource, getNsUrisForTargetModel());
			final IEtlModule transformer = EpsilonUtil.initialiseEtlModule(source, target);
			
			target.setMetamodelUris(metaModelUris);
			
			/*  modelFile needs to be set for load() to work
			 *  but as we set readOnLoad to false, the value of
			 *  modelFile isn't important
			 */
			target.setModelFile("foo.model");
			target.load();
			
			transformer.parse(generateTransformation());
			
			if (transformer.getParseProblems().isEmpty()) {
				transformer.execute();
			} else {
				throw new HutnGenerationException("Could not parse transformation: " + transformer.getParseProblems());
			}
			
			return target;
			
		} catch (Exception e) {
			if (e instanceof HutnGenerationException)
				throw (HutnGenerationException)e;
			else
				throw new HutnGenerationException(e);
		}
	}
	
	public void forceGenerationOfTransformationForWholeMetamodel() {
		generateTransformationForWholeMetamodel = true;
	}
	
	public String generateTransformation() throws HutnGenerationException {
		try {
			final EglTemplateFactory generator = new EglTemplateFactory();
			generator.getContext().getModelRepository().addModel(source);
			
			final EglTemplate template = generator.load(FileUtil.getFileStandalone("GenerateIntermediate2ModelTransformation.egl", ModelGenerator.class));
			
			final Collection<EClass> classes;
			
			if (generateTransformationForWholeMetamodel) {
				classes = getAllClassesFromMetamodel();
			} else {
				classes = getOnlyClassesThatAreUsedInSource();
			}
			
			generator.getContext().getFrameStack().putGlobal(Variable.createReadOnlyVariable("classes", classes));
			
			final String transformation = template.process();
//			System.err.println(transformation);
			return transformation;
	
		} catch (Exception e) {
			throw new HutnGenerationException(e);
		}
	}

	private Collection<EClass> getOnlyClassesThatAreUsedInSource() throws EolRuntimeException {
		final Set<EClass> classes = new HashSet<>();
	
		for (Object o : source.getAllOfKind("ClassObject")) {
			classes.add(((ClassObject)o).getEClass());
		}
		
		return classes;
	}

	private Collection<EClass> getAllClassesFromMetamodel() throws EolRuntimeException {
		final Set<EClass> classes = new HashSet<>();
		
		for (IModel metaModel : metaModels) {
			for (Object clazz : metaModel.getAllOfKind("EClass")) {
				classes.add((EClass) clazz);
			}
		}
		
		return classes;
	}
}
