/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.egl.propertyaccessrecording;

import java.nio.file.Paths;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

/**
 * This example demonstrates monitoring the execution of an EGX model-to-text transformation
 * to record model element property accesses. This can be useful e.g. for coverage analysis
 * or incremental execution of EGX transformations. The same technique can be used to record
 * element property accesses in other Epsilon languages too.
 * @author Dimitris Kolovos
 *
 */
public class EglPropertyAccessRecordingExample {
	
	public static void main(String[] args) throws Exception {
		
		// Make Flexmi and Emfatic known to EMF as we will use them 
		// to express the input model and metamodel of the transformation
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
		
		// Create the property access recorder that will record 
		// all the property access events in the EGX transformation
		final GenerationRulePropertyAccessRecorder propertyAccessRecorder = new GenerationRulePropertyAccessRecorder();
		propertyAccessRecorder.startRecording();
		
		// Subclass the default EgxModule so that we can return custom generation rules
		EgxModule module = new EgxModule() {
			@Override
			protected GenerationRule createGenerationRule(AST generationRuleAst) {
				return new GenerationRule() {
					@Override
					public Object execute(IEolContext context_, Object element) throws EolRuntimeException {
						// Before executing a generation rule against an element
						// update the rule and element fields of the property change recorder
						propertyAccessRecorder.setElement(element);
						propertyAccessRecorder.setRule(this);
						return super.execute(context_, element);
					}
				};
			}
		};
		
		// Create a custom template factory so that we can monitor property
		// access events during template execution too
		module.setTemplateFactory(new EglFileGeneratingTemplateFactory() {
			@Override
			protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
				EglTemplate template = super.createTemplate(spec);
				// Every time the factory creates a template, attach a listener to it
				// to record property accesses to our custom recorder
				template.getModule().getContext().getExecutorFactory().addExecutionListener(new PropertyAccessExecutionListener(propertyAccessRecorder));
				return template;
			}
		});
		
		// Now we're in business as normal
		// Parse the EGX program
		module.parse(EglPropertyAccessRecordingExample.class.getResource("/egl/propertyaccessrecording/psl.egx").toURI());
		
		// Configure its source model
		EmfModel model = new EmfModel();
		model.setModelFile("models/psl.flexmi");
		model.setMetamodelFile(Paths.get(
			EglPropertyAccessRecordingExample.class.getResource("/metamodels/psl.emf")
			.toURI()).toFile().getAbsolutePath()
		);
		model.setName("M");
		model.load();
		module.getContext().getModelRepository().addModel(model);
		
		// Add a listener to record property access events during
		// the execution of the EGX program
		module.getContext().getExecutorFactory().addExecutionListener(new PropertyAccessExecutionListener(propertyAccessRecorder));
		
		// Execute the EGX program
		try {
			module.execute();
			
			// Print the collected property access traces
			for (IPropertyAccess propertyAccess : propertyAccessRecorder.getPropertyAccesses().all()) {
				GenerationRulePropertyAccess generationRulePropertyAccess = (GenerationRulePropertyAccess) propertyAccess;
				System.out.println(
				"Rule: " + generationRulePropertyAccess.getRule().getName() + "\n" + 
				"Context: " + generationRulePropertyAccess.getElement() + "\n" + 
				"Element: " + generationRulePropertyAccess.getModelElement() + "\n" +
				"Property: " + generationRulePropertyAccess.getPropertyName() + "\n" +
				"----------------------------");
			}
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}
}
