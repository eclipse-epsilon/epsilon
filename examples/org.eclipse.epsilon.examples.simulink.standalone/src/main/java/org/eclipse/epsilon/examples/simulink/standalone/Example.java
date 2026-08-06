package org.eclipse.epsilon.examples.simulink.standalone;

import java.io.File;

import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.eol.EolModule;

public class Example {
    
    public static void main(String[] args) throws Exception {
        
        // Parse the EOL program
        EolModule module = new EolModule();
        module.parse(new File("program.eol"));
        
        // Set up the Simulink model
        SimulinkModel model = new SimulinkModel();
        model.setName("M");
        model.setFile(new File("model.slx"));
        model.setReadOnLoad(false);
        model.setStoredOnDisposal(true);
        model.load();

        // Make the model available to the program
        module.getContext().getModelRepository().addModel(model);
        
        // Execute the EOL program
        module.execute();

        // Dispose of the model
        module.getContext().getModelRepository().dispose();
    }
}