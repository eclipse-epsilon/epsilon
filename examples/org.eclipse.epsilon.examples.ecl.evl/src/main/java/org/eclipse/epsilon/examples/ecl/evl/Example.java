package org.eclipse.epsilon.examples.ecl.evl;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Example {
    
    public static void main(String[] args) throws Exception {
        
        // Register the Flexmi and Emfatic parsers with EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());

        // Register the psl metamodel
        EmfUtil.register(URI.createFileURI(new File("psl.emf").getAbsolutePath()), EPackage.Registry.INSTANCE);

        // Parse the ECL matching rules
        EclModule eclModule = new EclModule();
        eclModule.parse(new File("psl.ecl"));

        // Load the left model from left.flexmi
        EmfModel left = new EmfModel();
        left.setName("Left");
        left.getAliases().add("Source");
        left.setModelFile("left.flexmi");
        left.setMetamodelUri("psl");
        left.setReadOnLoad(true);
        left.setStoredOnDisposal(false);
        left.load();

        // Load the right model from right.flexmi
        EmfModel right = new EmfModel();
        right.setName("Right");
        right.getAliases().add("Source");
        right.setModelFile("right.flexmi");
        right.setMetamodelUri("psl");
        right.setReadOnLoad(true);
        right.setStoredOnDisposal(false);
        right.load();

        // Make the left and right models available to the comparison rules
        eclModule.getContext().getModelRepository().addModels(left, right);

        // Execute the comparison
        eclModule.execute();

        // Parse the EVL constraints
        EvlModule evlModule = new EvlModule();
        evlModule.parse(new File("psl.evl"));

        // Make the left and right models available to the constraints
        evlModule.getContext().getModelRepository().addModels(left, right);
        // Also make the match trace computed with ECL available as a 
        // matchTrace variable
        evlModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("matchTrace", eclModule.getContext().getMatchTrace().getReduced()));

        // Execute the constraints
        evlModule.execute();

        // Print the messages of any unsatisfied constraints
        for (UnsatisfiedConstraint uc : evlModule.getContext().getUnsatisfiedConstraints()) {
            System.out.println(uc.getMessage());
        }
    }
}