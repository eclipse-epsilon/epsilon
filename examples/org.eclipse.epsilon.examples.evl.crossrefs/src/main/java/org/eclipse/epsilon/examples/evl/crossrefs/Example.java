package org.eclipse.epsilon.examples.evl.crossrefs;

import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Example {
    
    public static void main(String[] args) throws Exception {
        
        // Register the Flexmi and Emfatic parsers with EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
        
        EmfUtil.register(new File("ccl.emf"), EPackage.Registry.INSTANCE);

        // Load the model a model.flexmi using ccl.emf as its metamodel
        final EmfModel model = new EmfModel();
        model.setName("M");
        model.setModelFile(new File("a.flexmi").getAbsolutePath());
        model.setMetamodelUri("ccl");
        model.setReadOnLoad(true);
        model.setExpand(true);
        model.setStoredOnDisposal(false);
        model.load();
        
        // Parse the EVL constraints
        EvlModule module = new EvlModule() {
            @Override
            public ModuleElement adapt(AST cst, ModuleElement parentAst) {
                ModuleElement moduleElement = super.adapt(cst, parentAst);
                if (moduleElement instanceof ConstraintContext) {
                    // Create a custom ConstraintContext implementation that only validates
                    // elements in the (first) resource of the model
                    return new ConstraintContext() {
                        public Collection<?> getAllOfSourceKind(IEolContext context) throws EolModelElementTypeNotFoundException ,EolModelNotFoundException {
                            // Return only the model elements that are contained in the (first) resource of the model
                            return super.getAllOfSourceKind(context).stream().filter(me -> ((EObject) me).eResource() == model.getResource()).collect(Collectors.toList());
                        };
                    };
                }
                else return moduleElement;
            }
        };
        module.parse(new File("ccl.evl"));
        
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