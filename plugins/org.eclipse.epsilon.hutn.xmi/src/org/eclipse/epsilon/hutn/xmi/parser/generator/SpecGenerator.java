/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.xmi.parser.generator;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.util.EmfUtil;
import org.eclipse.epsilon.hutn.xmi.util.HutnUtil;
import org.eclipse.epsilon.hutn.xmi.util.Stack;

/**
 * <p>Generates a HUTN spec in a LIFO manner. ClassObjects are generated
 * using {@link #generateTopLevelClassObject(String)} and 
 * {@link #generateContainedClassObject(String, String)}.</p>
 * 
 * <p>Before generating any ClassObjects, the generator must first be 
 * initialised using {@link #initialise()} or {@link #initialise(String)}.</p>
 * 
 * <p>To nest class objects, first use a call to 
 * {@link #generateTopLevelClassObject(String)} and then add levels of
 * nesting with {@link #generateContainedClassObject(String)}.</p>
 * 
 * <p>A call to {@link #stopGeneratingCurrentClassObject()} completes the
 * generation of the current class object. Furthermore, the next class
 * object to be generated will have the same parent as the last class
 * object to be generated.</p>
 * 
 * <p>Example: To generate the following structure:
 * <pre>  Family {
 *    members: Person { dog: Dog {} }, Person {}
 *  }</pre>
 * 
 *  the following calls would be made:
 *  <pre>  initialise("families");
 *  generateTopLevelClassObject("Family");
 *    generateContainedClassObject("Person");
 *      generateContainedClassObject("Dog");
 *      stopGeneratingCurrentClassObject(); // Dog
 *    stopGeneratingCurrentClassObject(); // Person
 *    generateContainedClassObject("Person");
 *    stopGeneratingCurrentClassObject();
 *  stopGeneratingCurrentClassObject(); // Family</pre>
 * 
 * The last two lines are optional, if no further ClassObjects are to
 * be generated.</p>
 * 
 * @author lrose
 */
public class SpecGenerator {

	private final Spec spec = HutnFactory.eINSTANCE.createSpec();
	private final Stack<ClassObject> stack = new Stack<>();

	private ContainmentSlot containingSlot;
	
	public SpecGenerator() {
		EmfUtil.createResource(spec);
	}
	
	public Spec getSpec() {
		return spec;
	}

	public void initialise() {
		initialise(null);
	}
	
	public void initialise(String nsUri) {
		addPackageObject(nsUri);
	}

	public void generateTopLevelClassObject(String identifier, String type, int lineNumber) {
		if (getPackageObject() == null)
			throw new IllegalStateException("Cannot create a top-level class object until initialise has been called");
		
		if (isGenerating())
			throw new IllegalStateException("Cannot create a top-level class object when generating another class object");
		
		getPackageObject().getClassObjects().add(createClassObject(identifier, type, lineNumber));
	}
	
	/**
	 * Generates a new ClassObject whose type is determined by
	 * inspecting the class of the parent ClassObject. If no 
	 * feature with the name specified can be found in the 
	 * class of the parent ClassObject, the newly generated
	 * ClassObject will have type "UnknownType".  
	 *   
	 * @param containingFeature - the name of the feature in the
	 * parent ClassObject that will contain the newly generated
	 * 
	 * @param identifier - the identifier for the ClassObject to
	 * be created. Identifiers are used when adding values to 
	 * reference slots. null is allowed, but this will create a
	 * ClassObject that cannot be referenced.
	 * 
	 * ClassObject.
	 */
	public void generateContainedClassObject(String containingFeature, String identifier, int lineNumber) {
		EStructuralFeature feature = HutnUtil.determineFeatureFromMetaClass(getCurrentClassObject(), containingFeature);
		
		if (EmfUtil.isContainmentReference(feature)) {
			generateContainedClassObject(containingFeature, identifier, feature.getEType().getName(), lineNumber);
			
		} else {
			generateContainedClassObject(containingFeature, identifier, "UnknownType", lineNumber);
		}
	}
	
	/**
	 * <p>Generates a new ClassObject with the type specified. The
	 * newly generated ClassObject will be placed in a 
	 * ContainmentSlot, with the feature specified, of the 
	 * parent ClassObject.</p>
	 * 
	 * <p>If the parent ClassObject contains an existing ContainmentSlot 
	 * with the feature specified, the newly generated ClassObject 
	 * will be added to the values of that slot. Otherwise, a new 
	 * ContainmentSlot with the feature specified will be created.</p>
	 *  
	 * @param type - the type of ClassObject to be generated.
	 *   
	 * @param containingFeature - the name of the feature in the
	 * parent ClassObject that will contain the newly generated 
	 * ClassObject.
	 * 
	 * @param identifier - the identifier for the ClassObject to
	 * be created. Identifiers are used when adding values to 
	 * reference slots. null is allowed, but this will create a
	 * ClassObject that cannot be referenced.

	 * @param lineNumber - the line number of the generated ClassObject 
	 */
	public void generateContainedClassObject(String containingFeature, String identifier, String type, int lineNumber) {
		if (!isGenerating())
			throw new IllegalStateException("Cannot generate a contained class object when not generating any other class objects");
		
		containingSlot = stack.peek().findOrCreateContainmentSlot(containingFeature);
		addButDoNotOverwriteLineNumberOf(containingSlot, lineNumber);
		
		containingSlot.getClassObjects().add(createClassObject(identifier, type, lineNumber));
	}
	
	public ClassObject getCurrentClassObject() {
		return stack.peek();
	}
	
	public boolean hasCurrentClassObject() {
		return !stack.isEmpty();
	}

	public void stopGeneratingCurrentClassObject() {
		stack.pop();
	}
	
	public void stopGeneratingAndDeleteCurrentClassObject() {
		stopGeneratingCurrentClassObject();
		
		if (containingSlot != null) {
			stack.peek().getSlots().remove(containingSlot);
		}
	}
	
	
	public void addAttributeValue(String featureName, String value, int lineNumber) {
		final Slot<?> slot = determineSlot(featureName);
		
		addButDoNotOverwriteLineNumberOf(slot, lineNumber);
		
		getCurrentClassObject().getSlots().add(slot);
		
		HutnUtil.addValueToSlot(slot, value);
	}
	
	private Slot<?> determineSlot(String featureName) {
		Slot<?> slot = HutnUtil.determineSlotFromMetaFeature(getCurrentClassObject(), featureName);
		
		if (slot instanceof ContainmentSlot) {
			// Model must be inconsistent with metamodel
			// probably want a reference slot instead
			getCurrentClassObject().getSlots().remove(getCurrentClassObject().findSlot(featureName));
			
			slot = getCurrentClassObject().findOrCreateReferenceSlot(featureName);
		
		} else if (slot == null) {
			slot = getCurrentClassObject().findOrCreateAttributeSlot(featureName);
		}
		
		return slot;
	}
	
	private void addButDoNotOverwriteLineNumberOf(final Slot<?> slot, int lineNumber) {
		if (slot.getLine() == 0) {
			slot.setLine(lineNumber);
		}
	}
	
	
	private boolean isGenerating() {
		return getCurrentClassObject() != null;
	}
	
	private PackageObject getPackageObject() {
		if (spec.getObjects().isEmpty())
			return null;
		
		return spec.getObjects().get(0);
	}

	private void addPackageObject(String nsUri) {
		final PackageObject po = HutnFactory.eINSTANCE.createPackageObject();
		po.setType("package");
		
		spec.getObjects().add(po);
		
		if (nsUri != null) {
			addNsUri(nsUri);

			if (EPackage.Registry.INSTANCE.getEPackage(nsUri) != null)		
				po.getMetamodel().add(EPackage.Registry.INSTANCE.getEPackage(nsUri));
		}
	}
	
	private void addNsUri(String nsUri) {
		final NsUri nsUriObject = HutnFactory.eINSTANCE.createNsUri();
		nsUriObject.setValue(nsUri);
		spec.getNsUris().add(nsUriObject);
	}
	
	private ClassObject createClassObject(String identifier, String type, int line) {
		final ClassObject co = HutnFactory.eINSTANCE.createClassObject();
		co.setType(type);
		
		if (identifier != null) {
			co.setIdentifier(identifier);
		}
		
		co.setLine(line);

		stack.push(co);

		return co;
	}
}
