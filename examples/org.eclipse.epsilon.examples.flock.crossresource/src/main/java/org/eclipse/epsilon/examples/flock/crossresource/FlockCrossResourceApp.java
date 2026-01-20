package org.eclipse.epsilon.examples.flock.crossresource;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.eol.models.ModelGroup;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.execute.context.FlockContext;

public class FlockCrossResourceApp {
	
	public static void main(String[] args) throws Exception {
		new FlockCrossResourceApp().run();
	}
	
	public void run() throws Exception {
		
		CrossResourceFlockModule module = new CrossResourceFlockModule();
		module.parse("migrate EClass { migrated.name = 'C' + original.name; } ");
		
		EmfModel m1Original = getModel("original/m1.ecore", true, false);
		EmfModel m2Original = getModel("original/m2.ecore", true, false);
		
		EmfModel m1Migrated = getModel("migrated/m1.ecore", false, true);
		EmfModel m2Migrated = getModel("migrated/m2.ecore", false, true);
		
		ReflectiveModelGroup original = new ReflectiveModelGroup(m1Original, m2Original);
		ReflectiveModelGroup migrated = new ReflectiveModelGroup(m1Migrated, m2Migrated);
		
		ModelRepository modelRepository = ((FlockContext) module.getContext()).getModelRepository();
		modelRepository.addModel(original);
		modelRepository.addModel(migrated);
		
		Map<IReflectiveModel, IReflectiveModel> modelMap = module.getModelMap();
		modelMap.put(m1Original, m1Migrated);
		modelMap.put(m2Original, m2Migrated);
		migrated.setModelMap(modelMap);
		
		module.getContext().setOriginalModel(original);
		module.getContext().setMigratedModel(migrated);
		
		module.execute();
		
		modelRepository.dispose();
		
	}
	
	class ReflectiveModelGroup extends ModelGroup implements IReflectiveModel {
		
		protected Map<IReflectiveModel, IReflectiveModel> modelMap;
		
		public ReflectiveModelGroup(IReflectiveModel... models) {
			for (IReflectiveModel model : models) {
				this.models.add(model);
			}
		}
		
		@Override
		public Object createInstance(String metaClass)
				throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
			return getFirstModel().createInstance(metaClass);
		}
		
		
		public void setModelMap(Map<IReflectiveModel, IReflectiveModel> modelMap) {
			this.modelMap = modelMap;
		}
		
		@Override
		public boolean preventLoadingOfExternalModelElements() {
			return true;
		}
		
		protected IReflectiveModel getOwningModel(Object instance) {
			for (IModel model : models) {
				if (((IReflectiveModel) model).owns(instance)) {
					return (IReflectiveModel) model;
				}
			}
			return null;
		}
		
		protected IReflectiveModel getFirstModel() {
			return (IReflectiveModel) models.get(0);
		}
		
		@Override
		public Object getContainerOf(Object object) {
			return getOwningModel(object).getContainerOf(object);
		}

		@Override
		public Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
			return getFirstModel().getPropertiesOf(type);
		}

		@Override
		public IReflectivePropertySetter getPropertySetter() {
			return getFirstModel().getPropertySetter();
		}

		@Override
		public IPropertyGetter getPropertyGetter() {
			return getFirstModel().getPropertyGetter();
		}
		
		@Override
		public boolean hasProperty(String type, String property) throws EolModelElementTypeNotFoundException {
			return getFirstModel().hasProperty(type, property);
		}

		@Override
		public boolean isEnumerationValue(Object object) {
			return getFirstModel().isEnumerationValue(object);
		}

		@Override
		public String getEnumerationTypeOf(Object literal) throws EolNotAnEnumerationValueException {
			return getFirstModel().getEnumerationTypeOf(literal);
		}

		@Override
		public String getEnumerationLabelOf(Object literal) throws EolNotAnEnumerationValueException {
			return getFirstModel().getEnumerationLabelOf(literal);
		}

		@Override
		public boolean hasPackage(String packageName) {
			return getFirstModel().hasPackage(packageName);
		}
		
	}
	
	public void run2() throws Exception {
		
		FlockModule module = new FlockModule();
		module.parse("");
		
		EmfModel original = new EmfModel() {
			@Override
			public boolean preventLoadingOfExternalModelElements() {
				return true;
			}
			
			@Override
			protected Collection<EObject> allContentsFromModel() {
				Collection<EObject> allContentsFromModel = super.allContentsFromModel();
				return allContentsFromModel;
			}
			
			@Override
			public boolean owns(Object instance) {
				if (instance instanceof EObject && ((EObject) instance).eResource() != null) {
					return ((EObject) instance).eResource().getResourceSet() == modelImpl.getResourceSet();
				}
				return false;
			}
		};
		
		original.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
		original.setModelFileUri(URI.createFileURI(new File("original/m2.ecore").getAbsolutePath()));
		original.setExpand(true);
		original.setReadOnLoad(true);
		original.setStoredOnDisposal(false);
		original.load();
		EcoreUtil.resolveAll(original.getResource().getResourceSet());
		
		EmfModel migrated = new EmfModel();
		migrated.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
		migrated.setModelFile("migrated/m2.ecore");
		migrated.setExpand(true);
		migrated.setReadOnLoad(false);
		migrated.setStoredOnDisposal(true);
		migrated.load();
		
		ModelRepository modelRepository = ((FlockContext) module.getContext()).getModelRepository();
		modelRepository.addModel(original);
		modelRepository.addModel(migrated);
		
		module.getContext().setOriginalModel(original);
		module.getContext().setMigratedModel(migrated);
		
		module.execute();
		
		modelRepository.dispose();
		
	}

	protected EmfModel getModel(String path, boolean readOnLoad, boolean storedOnDisposal) throws Exception {
		EmfModel model = new EmfModel();
		model.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
		model.setModelFileUri(URI.createFileURI(new File(path).getAbsolutePath()));
		model.setReadOnLoad(readOnLoad);
		model.setStoredOnDisposal(storedOnDisposal);
		model.load();
		return model;
	}
	
}
