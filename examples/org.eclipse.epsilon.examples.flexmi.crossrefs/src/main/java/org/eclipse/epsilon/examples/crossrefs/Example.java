package org.eclipse.epsilon.examples.crossrefs;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Example {
    
    public static void main(String[] args) throws Exception {
        
        // Register the Flexmi and Emfatic parsers with EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
        
        ResourceSet metamodelResourceSet = new ResourceSetImpl();
        Resource metamodelResource = metamodelResourceSet.getResource(URI.createFileURI(new File("metamodel.emf").getAbsolutePath()), true);
        metamodelResource.load(null);
        EPackage ePackage = (EPackage) metamodelResource.getContents().get(0);
        
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
        Resource personsResource = resourceSet.getResource(URI.createFileURI(new File("persons.flexmi").getAbsolutePath()), true);
        personsResource.load(null);

        System.out.println(personsResource.getContents().get(0));
        
        Resource tasksResource = resourceSet.getResource(URI.createFileURI(new File("model.flexmi").getAbsolutePath()), true);
        personsResource.load(null);

        InMemoryEmfModel model = new InMemoryEmfModel("M", tasksResource, ePackage);

        EolModule module = new EolModule();
        module.parse("Effort.all.first().person.println();");
        module.getContext().getModelRepository().addModel(model);
        module.execute();
    }
}