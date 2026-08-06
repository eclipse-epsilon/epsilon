package org.eclipse.epsilon.examples.egl.strings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Example {
    
    public static void main(String[] args) throws Exception {
        
        // Register the Flexmi and Emfatic parsers with EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());

        Map<String, String> templates = new HashMap<>();
        templates.put("template.egl", "Hello [%=p.name%]!");
        StringGeneratingTemplateFactory templateFactory = new StringGeneratingTemplateFactory(templates);

        // Parse the EGX transformation and configure it to produce
        // its output files in the root directory of the project
        EgxModule module = new EgxModule(templateFactory);
        
        module.parse("rule Person2Greeting transform p : Person { template: 'template.egl' target: 'gen/' + p.name + '.txt'}");
        
        // Load the model from people.flexmi using people.emf as its metamodel
        EmfModel model = new EmfModel();
        model.setName("M");
        model.setModelFile("people.flexmi");
        model.setMetamodelFile("people.emf");
        model.setReadOnLoad(true);
        model.setStoredOnDisposal(false);
        model.load();
        
        // Make the model available to the transformation
        module.getContext().getModelRepository().addModel(model);
        
        // Execute the EGX transformation
        module.execute();

        // Dispose of the model
        module.getContext().getModelRepository().dispose();

        // Print the generated "files"
        for (String key : templateFactory.getResults().keySet()) {
            System.out.println(key + ": " + templateFactory.getResults().get(key));
        }
    }
}