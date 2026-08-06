package org.eclipse.epsilon.examples.odesign;

import java.util.Arrays;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.properties.PropertiesPackage;

public class App {
	
	public static void main(String[] args) throws Exception {
		
		// Load the .odesign model
		EmfModel odesign = new EmfModel();
		odesign.setModelFile("/absolute/path/of/your.odesign");
		
		odesign.setName("ODesign");
		odesign.setMetamodelUris(Arrays.asList(
				DescriptionPackage.eINSTANCE.getNsURI(), 
				PropertiesPackage.eINSTANCE.getNsURI()));
		odesign.load();
		
		// Parse an EOL program that prints 
		// the first viewpoint in the .odesign model
		EolModule module = new EolModule();
		module.parse("Viewpoint.all.first().println();");
		
		// Make the .odesign model available to the EOL program
		module.getContext().getModelRepository().addModel(odesign);
		
		// Execute the EOL program
		module.execute();
	}
	
}
