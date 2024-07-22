package org.eclipse.epsilon.examples.evl.crossrefs;

import java.io.File;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Example {
    
    public static void main(String[] args) throws Exception {
        
        // Register the Flexmi and Emfatic parsers with EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
        
        EmfUtil.register(new File("ccl.emf"), EPackage.Registry.INSTANCE);

        // Parse the EVL constraints
        EvlModule module = new EvlModule();
        module.parse(new File("ccl.evl"));
        
        // Load the model a model.flexmi using ccl.emf as its metamodel
        EmfModel model = new EmfModel();
        model.setName("M");
        model.setModelFile(new File("a.flexmi").getAbsolutePath());
        model.setMetamodelUri("ccl");
        model.setReadOnLoad(true);
        model.setExpand(false);
        model.setStoredOnDisposal(false);
        model.load();
        
        // Make the model available to the constraints
        module.getContext().getModelRepository().addModel(model);
        
        // Execute the EVL constraints
        module.execute();

        // Print the messages of any unsatisfied constraints
        for (UnsatisfiedConstraint unsatisfiedConstraint : module.getContext().getUnsatisfiedConstraints()) {
            System.out.println(unsatisfiedConstraint.getMessage());
        }

        // Dispose of the model
        module.getContext().getModelRepository().dispose();
    }
}