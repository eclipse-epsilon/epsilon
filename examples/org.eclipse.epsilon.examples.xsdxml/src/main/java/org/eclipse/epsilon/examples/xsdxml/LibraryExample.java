package org.eclipse.epsilon.examples.xsdxml;

import java.io.File;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.eclipse.epsilon.eol.EolModule;

public class LibraryExample {
    
    public static void main(String[] args) throws Exception {
        
        // Parse the EOL program
        EolModule module = new EolModule();
        module.parse(new File("library/query-by-type.eol"));
        
        // Load the model
        XmlModel model = new XmlModel();
        StringProperties properties = new StringProperties();
        properties.put(XmlModel.PROPERTY_NAME, "M");
        // No need to specify XSD URI due to xsi:noNamespaceSchemaLocation="library.xsd" in library.xml
        properties.put(XmlModel.PROPERTY_MODEL_URI, new File("library/library.xml").toURI());
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