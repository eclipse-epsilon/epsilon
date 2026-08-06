package org.eclipse.epsilon.examples.xsdxml;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;

public class XSDToEcoreExample {
    
    public static void main(String[] args) throws Exception {

        // Convert the XSD into a collection of Ecore EPackages
        XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
        Collection<EObject> ePackages = xsdEcoreBuilder.generate(URI.createURI("http://www.omg.org/spec/ReqIF/20110401/reqif.xsd"));
        
        // Create a new XMI resource to save the EPackages 
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
        Resource resource = resourceSet.createResource(URI.createURI(new File("reqif.ecore").toURI().toString()));

        // Add the XSD-generated EPackages to the resource and save
        resource.getContents().addAll(ePackages);
        resource.save(null);
    }

}
