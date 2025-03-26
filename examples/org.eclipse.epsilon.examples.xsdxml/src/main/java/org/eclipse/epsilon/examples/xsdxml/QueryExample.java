package org.eclipse.epsilon.examples.xsdxml;

import java.io.File;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.eclipse.epsilon.eol.EolModule;

public class QueryExample {
    
    public static void main(String[] args) throws Exception {
        
        // Parse the EOL program
        EolModule module = new EolModule();
        module.parse(new File("note.eol"));
        
        // Load the model
        XmlModel model = new XmlModel();
        StringProperties properties = new StringProperties();
        properties.put(XmlModel.PROPERTY_NAME, "M");
        properties.put(XmlModel.PROPERTY_XSD_URI, new File("note.xsd").toURI());
        properties.put(XmlModel.PROPERTY_MODEL_URI, new File("note.xml").toURI());
        properties.put(XmlModel.PROPERTY_READONLOAD, "true");
        properties.put(XmlModel.PROPERTY_STOREONDISPOSAL, "false");
        model.load(properties);
        
        // Make the model available to the program
        module.getContext().getModelRepository().addModel(model);
        
        // Execute the EOL program
        module.execute();

        // Dispose of the model
        module.getContext().getModelRepository().dispose();
    }
}