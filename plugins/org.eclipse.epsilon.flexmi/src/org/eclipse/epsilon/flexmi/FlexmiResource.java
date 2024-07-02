/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.flexmi.actions.Action;
import org.eclipse.epsilon.flexmi.actions.ActionMap;
import org.eclipse.epsilon.flexmi.actions.FeatureComputation;
import org.eclipse.epsilon.flexmi.actions.ObjectInitialization;
import org.eclipse.epsilon.flexmi.actions.VariableDeclaration;
import org.eclipse.epsilon.flexmi.actions.VariableDeclaration.VariableDeclarationType;
import org.eclipse.epsilon.flexmi.templates.Template;
import org.eclipse.epsilon.flexmi.xml.FlexmiXmlParser;
import org.eclipse.epsilon.flexmi.xml.FlexmiXmlParser.Handler;
import org.eclipse.epsilon.flexmi.xml.Location;
import org.eclipse.epsilon.flexmi.yaml.FlexmiYamlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class FlexmiResource extends ResourceImpl implements Handler {
	
	public static final String ROOT_NODE_NAME = "_";
	
	protected List<ProcessingInstruction> processingInstructions = new ArrayList<>();
	protected EObjectTraceManager eObjectTraceManager = new EObjectTraceManager();
	protected List<UnresolvedReference> unresolvedReferences = new ArrayList<>();
	protected Stack<Object> objectStack = new Stack<>();
	protected Node currentNode;
	protected Collection<String> importedEolModules = new ArrayList<>();
	protected Map<String, EClass> eClassCache = new HashMap<>();
	protected Map<EClass, List<EClass>> allSubtypesCache = new HashMap<>();
	protected StringSimilarityProvider stringSimilarityProvider = new CachedStringSimilarityProvider(new DefaultStringSimilarityProvider());
	protected Stack<URI> parsedFragmentURIStack = new Stack<>();
	protected Set<URI> parsedFragmentURIs = new HashSet<>();
	protected Map<String, Template> templates = new HashMap<>();
	protected BiMap<String, EObject> fullyQualifiedIDs = HashBiMap.create();
	protected Map<EObject, String> localIDs = new HashMap<>();
	protected FrameStack frameStack = new FrameStack();
	protected ActionMap actionMap = new ActionMap();
	protected Map<EObject, List<EObject>> orderedChildren = new HashMap<>();
	protected Collection<Operation> operations = new ArrayList<>();
	protected FlexmiResource importedFrom = null;
	protected FlexmiFlavour flavour = FlexmiFlavour.XML;
	protected boolean documentEnded = false;
	
	public void startProcessingFragment(URI uri) {
		parsedFragmentURIStack.push(uri);
		parsedFragmentURIs.add(uri);
	}
	
	public void endProcessingFragment() {
		parsedFragmentURIStack.pop();
	}
	
	public Set<URI> getParsedFragmentURIs() {
		return parsedFragmentURIs;
	}
	
	public void addTemplate(Template template) {
		templates.put(template.getName(), template);
	}
	
	public Template getTemplate(String templateName) {
		return templates.get(templateName);
	}
	
	public Collection<Template> getTemplates() {
		return templates.values();
	}

	public static void main(String[] args) throws Exception {
		
		// Load the metamodel
		ResourceSet metamodelResourceSet = new ResourceSetImpl();
		metamodelResourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		metamodelResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource metamodelResource = metamodelResourceSet.createResource(URI.createFileURI(new File("models/messaging.ecore").getAbsolutePath()));
		metamodelResource.load(null);
		
		EPackage metamodel = (EPackage) metamodelResource.getContents().get(0);
		
		// Load the model
		ResourceSet modelResourceSet = new ResourceSetImpl();
		modelResourceSet.getPackageRegistry().put(metamodel.getNsURI(), metamodel);
		modelResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
		Resource modelResource = modelResourceSet.createResource(URI.createFileURI(new File("models/messaging.flexmi").getAbsolutePath()));
		modelResource.load(null);
		
		System.out.println(modelResource.getEObject("tom"));
		
	}
	
	public FlexmiResource(URI uri) {
		super(uri);
	}
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		documentEnded = false;
		getContents().clear();
		unresolvedReferences.clear();
		objectStack.clear();
		importedEolModules.clear();
		operations.clear();
		eClassCache.clear();
		allSubtypesCache.clear();
		setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
		
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		FlexmiParser parser = createParser(bufferedInputStream);
		flavour = parser.getFlavour();
		parser.parse(this, bufferedInputStream, this);
	}
	
	public FlexmiFlavour getFlavour() {
		return flavour;
	}
	
	@Override
	public void save(Map<?, ?> options) throws IOException {
		// Do nothing. Flexmi resources are load-only.
	}
	
	protected void setEObjectId(EObject eObject, String id) {
		getIntrinsicIDToEObjectMap().put(id, eObject);
		localIDs.put(eObject, id);
		EObject containerWithId = eObject.eContainer();
		while (containerWithId != null && !fullyQualifiedIDs.containsValue(containerWithId)) {
			containerWithId = containerWithId.eContainer();
		}
		if (containerWithId != null) {
			fullyQualifiedIDs.put(fullyQualifiedIDs.inverse().get(containerWithId) + "." + id, eObject);
		}
		else {
			fullyQualifiedIDs.put(id, eObject);
		}
	}
	
	public FlexmiParser createParser(BufferedInputStream inputStream) {
		if (isXml(inputStream)) return new FlexmiXmlParser();
		else return new FlexmiYamlParser();
	}
	
	public static boolean isXml(BufferedInputStream inputStream) {
		try {
			int next;
			inputStream.mark(Integer.MAX_VALUE);
			while((next = inputStream.read()) != -1){
				char ch = (char) next;
				if (!Character.isWhitespace(ch)) {
					inputStream.reset();
					if (ch == '<') return true;
					else return false;
				}
			}
			return false;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	@Override
	public EObject getEObject(String uriFragment) {
		if (getIntrinsicIDToEObjectMap().containsKey(uriFragment)) {
			return getIntrinsicIDToEObjectMap().get(uriFragment);
		}
		else if (fullyQualifiedIDs.containsKey(uriFragment)) {
			// prevents a full model sweep
			return fullyQualifiedIDs.get(uriFragment);
		}
		return super.getEObject(uriFragment);
	}
	
	@Override
	public void startDocument(Document document) {}

	@SuppressWarnings("unchecked")
	@Override
	public void startElement(Element element) {
		currentNode = element;
		String name = element.getNodeName();
		
		//Remove prefixes
		//TODO: Add option to disable this
		//int colonIndex = name.indexOf(":");
		//if (colonIndex > -1) {
		//	name = name.substring(colonIndex+1);
		//}
		
		EObject eObject = null;
		EClass eClass = null;
		
		// We're at the root
		if (objectStack.isEmpty()) {
			eClass = eClassForName(name);
			if (eClass != null) {
				eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
				getContents().add(eObject);
				setAttributes(eObject, element);
			}
			else {
				addParseWarning("Could not map element " + name + " to an EObject");
			}
			objectStack.push(eObject);
		}
		else {
			Object peek = objectStack.peek();
			
			// We find an orphan element
			if (peek == null) {
				objectStack.push(null);
				addParseWarning("Could not map element " + name + " to an EObject");
				return;
			}
			// The parent is an already-established containment slot
			else if (peek instanceof EReferenceSlot) {
				EReferenceSlot containmentSlot = (EReferenceSlot) peek;
				eClass = (EClass) eNamedElementForName(name, getAllSubtypes(containmentSlot.getEReference().getEReferenceType()));
				
				if (eClass != null) {
					eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
					containmentSlot.newValue(eObject);
					addOrderedChild(containmentSlot.getEObject(), eObject);
					objectStack.push(eObject);
					setAttributes(eObject, element);
				}
				else {
					objectStack.push(null);
					addParseWarning("Could not map element " + name + " to an EObject");
				}
			}
			// The parent is an EObject
			else if (peek instanceof EObject) {
				EObject parent = (EObject) peek;
				
				if (element.getNodeName().equalsIgnoreCase(Template.PREFIX + "init")) {
					actionMap.addAction(parent, new ObjectInitialization(parent, element.getTextContent().trim(), getCurrentURI(), getLineNumber(element)));
					objectStack.push(null);
					return;
				}
				else if (element.getAttributes().getLength() == 0 && element.getChildNodes().getLength() == 1 && element.getFirstChild() instanceof Text) {
					EStructuralFeature eStructuralFeature = (EStructuralFeature) eNamedElementForName(name, parent.eClass().getEAllStructuralFeatures());
					
					if (eStructuralFeature instanceof EAttribute) {
						setEAttributeValue(parent, (EAttribute) eStructuralFeature, name, element.getTextContent().trim(), false);
						eObjectTraceManager.trace(parent, getCurrentURI(), getLineNumber(element));
						objectStack.push(null);
						return;
					}
					else if (eStructuralFeature instanceof EReference){
						unresolvedReferences.add(new UnresolvedReference(parent, getCurrentURI(), (EReference) eStructuralFeature, name, element.getTextContent().trim(), getLineNumber(element)));
						eObjectTraceManager.trace(parent, getCurrentURI(), getLineNumber(element));
						objectStack.push(null);
						return;
					}
				}
				
				EReference containment = null;
				
				// No attributes -> Check whether there is a containment reference with that name
				if (element.getAttributes().getLength() == 0) {
					containment = (EReference) eNamedElementForName(name, parent.eClass().getEAllContainments());
					
					if (containment != null) {
						EReferenceSlot containmentSlot = new EReferenceSlot(containment, parent);
						eObjectTraceManager.trace(parent, getCurrentURI(), getLineNumber(element));
						objectStack.push(containmentSlot);
						return;
					}
				}
				
				// Check for the best class or containment reference
				Set<ENamedElement> candidates = new HashSet<>();
				for (EReference eReference : parent.eClass().getEAllContainments()) {
					candidates.addAll(getAllSubtypes(eReference.getEReferenceType()));				
				}
				
				candidates.addAll(parent.eClass().getEAllContainments());
				
				// Search for the best match between containment refs and class names
				ENamedElement topCandidate = eNamedElementForName(name, candidates);
				
				if (topCandidate instanceof EClass) {
					// Get the best match and an appropriate containment reference
					eClass = (EClass) topCandidate;
					if (eClass != null) {
						Set<EReference> candidateEReferences = new HashSet<>();
						for (EReference eReference : parent.eClass().getEAllContainments()) {
							if (getAllSubtypes(eReference.getEReferenceType()).contains(eClass)) {
								candidateEReferences.add(eReference);
							}
						}
						containment = (EReference) eNamedElementForName(name, candidateEReferences);
					}
				}
				else {
					containment = (EReference) topCandidate;
					if (containment != null) {
						eClass = (EClass) eNamedElementForName(name, getAllSubtypes(containment.getEReferenceType()));
					}
				}
				
				// Found an appropriate containment reference
				if (containment != null && eClass != null) {
					eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
					if (containment.isMany()) {
						((List<EObject>) parent.eGet(containment)).add(eObject);
					}
					else {
						parent.eSet(containment, eObject);
					}
					addOrderedChild(parent, eObject);
					setAttributes(eObject, element);
					objectStack.push(eObject);
				}
				// No luck - add warning
				else {
					objectStack.push(null);
					addParseWarning("Could not map element " + name + " to an EObject");
				}
			}
		}
	}

	@Override
	public void endElement(Element element) {
		Object object = objectStack.pop();
		if (object != null && object instanceof EObject) {
			EObject eObject = (EObject) object;
			eObjectTraceManager.trace(eObject, getCurrentURI(), getLineNumber(element));
		}
	}

	@Override
	public void processingInstruction(ProcessingInstruction processingInstruction) {
		currentNode = processingInstruction;
		
		String key = processingInstruction.getTarget();
		String value = processingInstruction.getData();
		processingInstructions.add(processingInstruction);
		
		if ("nsuri".equalsIgnoreCase(key)) {
			
			EPackage ePackage = null;
			
			// If the package is already in the resource set's package registry
			// do nothing and do not raise a warning
			// We are copying the package registry's keyset to a new collection
			// as the existing keyset implementation delegates to the global EPackage registry
			if (getResourceSet() != null && new HashSet<String>(getResourceSet().getPackageRegistry().keySet()).contains(value)) {
				ePackage = getResourceSet().getPackageRegistry().getEPackage(value);
			}
			
			if (ePackage == null) {
				ePackage = EPackage.Registry.INSTANCE.getEPackage(value);
				if (ePackage != null) getResourceSet().getPackageRegistry().put(ePackage.getNsURI(), ePackage);
			}
			
			if (ePackage == null) addParseWarning("Failed to locate EPackage for nsURI " + value + " ");
		}
		else if ("eol".equalsIgnoreCase(key)) { 
			// Parse the module and cache its operations 
			// to be used later in feature computations
			URI relativeUri = URI.createURI(value);
			URI eolUri = relativeUri.resolve(getCurrentURI());
			if (!importedEolModules.contains(eolUri.toString())) {
				try {
					parseEol(eolUri.toString());
				} catch (Exception e) {
					addParseWarning(e.getMessage());
				}
				finally {
					importedEolModules.add(eolUri.toString());
				}
			}
		}
		
	}
	
	public void parseEol(String uri) throws Exception {
		EolModule module = new EolModule();
		module.parse(new java.net.URI(uri));
		if (!module.getParseProblems().isEmpty()) {
			throw new RuntimeException(module.getParseProblems().get(0).toString());
		}
		else {
			operations.addAll(module.getOperations());
		}
	}
	
	public List<ProcessingInstruction> getProcessingInstructions() {
		return processingInstructions;
	}
	
	public boolean isDocumentEnded() {
		return documentEnded;
	}
	
	@Override
	public void endDocument(Document document) {
		documentEnded = true;
		
		// Attempt to resolve references for all Flexmi resources in the resource set only when all of them have been loaded
		if (!getResourceSet().getResources().stream().anyMatch(r -> r instanceof FlexmiResource && !((FlexmiResource) r).isDocumentEnded())) {
			getResourceSet().getResources().stream().filter(r -> r instanceof FlexmiResource).forEach(r -> { ((FlexmiResource) r).resolveReferences(); });
		}

		for (EObject content : getContents()) {
			performActions(content);
		}
	}
	
	public List<UnresolvedReference> getUnresolvedReferences() {
		return unresolvedReferences;
	}
	
	protected void addParseWarning(String message) {
		addParseWarning(message, getLineNumber(currentNode));
	}
	
	protected void addParseWarning(String message, int line) {
		addParseWarning(message, getCurrentURI(), line);
	}
	
	protected void addParseWarning(String message, URI uri, int line) {
		getWarnings().add(new FlexmiDiagnostic(message, uri, line));
	}
	
	protected void performActions(EObject eObject) {
		
		ModuleElement entryPoint = null;
		
		for (Action action : actionMap.getActions(eObject)) {
			
			if (entryPoint == null && action instanceof VariableDeclaration && ((VariableDeclaration) action).getType() == VariableDeclarationType.LOCAL) {
				entryPoint = new AbstractModuleElement() {};
				((VariableDeclaration) action).setEntryPoint(entryPoint);
			}
			try {
				action.perform(this);
			}
			catch (Exception ex) {
				addParseWarning(ex.getMessage(), action.getUri(), action.getLineNumber());
			}
		}
		
		for (EObject content : getOrderedChildren(eObject)) {
			performActions(content);
		}
		
		if (entryPoint != null) frameStack.leaveLocal(entryPoint);
	}
	
	protected void resolveReferences() {
		List<UnresolvedReference> unresolvableReferences = new ArrayList<>();
		
		for (UnresolvedReference unresolvedReference : unresolvedReferences) {
			EReference eReference = unresolvedReference.getEReference();
			if (eReference.isMany()) {
				
				if ("*".equals(unresolvedReference.getValue())) {
					for (Iterator<EObject> it = this.getAllContents(); it.hasNext();) {
						EObject candidate = it.next();
						if (eReference.getEReferenceType().isInstance(candidate)) {
							new EReferenceSlot(eReference, unresolvedReference.getEObject()).newValue(candidate);
						}
					}
				}
				else {
					if (!resolveReference(unresolvedReference)) unresolvableReferences.add(unresolvedReference);
				}
			}
			else {
				if (!resolveReference(unresolvedReference)) unresolvableReferences.add(unresolvedReference);
			}
		}
		
		for (UnresolvedReference reference : unresolvableReferences) {
			addParseWarning("Could not resolve target " + reference.getValue() + " for reference " + reference.getAttributeName() + " (" + reference.getEReference().getName() + ")", reference.getUri(), reference.getLine());
		}
	}
	
	protected boolean resolveReference(UnresolvedReference unresolvedReference) {
		EObject candidate = getEObject(unresolvedReference.getValue());
		if (!unresolvedReference.resolve(candidate)) {
			for (Resource resource : getResourceSet().getResources()) {
				if (resource != this) {
					candidate = resource.getEObject(unresolvedReference.getValue());
					if (unresolvedReference.resolve(candidate)) return true;
				}
			}
			return false;
		}
		return true;
	}
	
	public int getLineNumber(Node node) {
		Location location = (Location) node.getUserData(Location.ID);
		if (location != null) {
			return location.getStartLine();
		}
		return 0;
	}
	
	public void handleVarAttribute(String attribute, VariableDeclarationType type, NamedNodeMap attributes, EObject eObject) {
		
		// Find the :var/:global/:local attribute, create a variable declaration and remove it from the node
		Node varAttribute = attributes.getNamedItem(Template.PREFIX + attribute);
		if (varAttribute != null) {
			
			for (String variable : varAttribute.getNodeValue().split(",")) {
				VariableDeclaration variableDeclaration = new VariableDeclaration(eObject, variable.trim(), type);
				if (type == VariableDeclarationType.GLOBAL) {
					try {
						variableDeclaration.perform(this);
					} catch (Exception e) {}
				}
				else {
					actionMap.addAction(eObject, variableDeclaration);
				}
			}
			
			attributes.removeNamedItem(varAttribute.getNodeName());
		}
	}
	
	protected void setAttributes(EObject eObject, Element element) {
		
		NamedNodeMap attributes = element.getAttributes();
		List<EStructuralFeature> eStructuralFeatures = getCandidateStructuralFeaturesForAttribute(eObject.eClass());
		
		if (attributes.getLength() == 0 || eStructuralFeatures.size() == 0) return;
		
		handleVarAttribute("var", VariableDeclarationType.REGULAR, attributes, eObject);
		handleVarAttribute("local", VariableDeclarationType.LOCAL, attributes, eObject);
		handleVarAttribute("global", VariableDeclarationType.GLOBAL, attributes, eObject);
		
		Map<Node, EStructuralFeature> allocation =
				new AttributeStructuralFeatureAllocator(eObject.eClass()).allocate(attributes, eStructuralFeatures);
		
		for (Node attribute : allocation.keySet()) {
			String name = attribute.getNodeName();
			String value = attribute.getNodeValue();
			
			EStructuralFeature sf = allocation.get(attribute);
			
			if (name.startsWith(Template.PREFIX)) {
				actionMap.addAction(eObject, new FeatureComputation(eObject, sf, name, value, getCurrentURI(), getLineNumber(element)));
			}
			else {
				if (sf instanceof EAttribute) {
					setEAttributeValue(eObject, (EAttribute) sf, name, value, true);
				}
				else if (sf instanceof EReference) {
					EReference eReference = (EReference) sf;
					if (eReference.isMany()) {
						for (String valuePart : value.split(",")) {
							unresolvedReferences.add(new UnresolvedReference(eObject, getCurrentURI(), eReference, name, valuePart.trim(), getLineNumber(element)));
						}
					}
					else {
						unresolvedReferences.add(new UnresolvedReference(eObject, getCurrentURI(), eReference, name, value, getLineNumber(element)));
					}
				}
			}
		}
		
		// If the EObject's EClass has a "text" String attribute and the attribute is unset
		EStructuralFeature textFeature = eObject.eClass().getEStructuralFeature("text");
		if (textFeature instanceof EAttribute && !eObject.eIsSet(textFeature)) {
			if (element.getChildNodes().getLength() == 1 && element.getFirstChild() instanceof Text) {
				eObject.eSet(textFeature, element.getTextContent().trim());
				eObjectTraceManager.trace(eObject, getCurrentURI(), getLineNumber(element));
			}
		}
		
	}
	
	public URI getCurrentURI() {
		return parsedFragmentURIStack.isEmpty() ? this.getURI() : parsedFragmentURIStack.peek();
	}
	
	@SuppressWarnings("unchecked")
	protected void setEAttributeValue(EObject eObject, EAttribute eAttribute, String attributeName, String value, boolean split) {
		if (eAttribute.isMany()) {
			
			String[] values = split ? value.split(",") : new String[] {value};
			
			for (String valuePart : values) {
				Object eValue = getEValue(eAttribute, attributeName, valuePart.trim());
				if (eValue == null) continue;
				((List<Object>) eObject.eGet(eAttribute)).add(eValue);
			}
		}
		else {
			Object eValue = getEValue(eAttribute, attributeName, value);
			if (eValue == null) return;
			eObject.eSet(eAttribute, eValue);
			EAttribute idAttribute = eObject.eClass().getEIDAttribute();
			if (eAttribute == idAttribute || (idAttribute == null && eAttribute.getName().equalsIgnoreCase("name"))) {
				setEObjectId(eObject, value);
			}
		}
	}
	
	protected Object getEValue(EAttribute eAttribute, String attributeName, String value) {
		try {
			if (eAttribute.getEAttributeType() instanceof EEnum) {
				EEnum eEnum = (EEnum) eAttribute.getEAttributeType();
				EEnumLiteral literal = (EEnumLiteral) eNamedElementForName(value, eEnum.getELiterals());
				if (literal != null) return literal.getInstance();
				else return null;
			}
			else {
				if (Object.class.getName().equals(eAttribute.getEAttributeType().getInstanceClassName())) return value;
				
				// If a boolean attribute is missing a value, assume it is true
				if (value != null && value.trim().isEmpty() && (eAttribute.getEAttributeType().getInstanceClass() == boolean.class 
						|| eAttribute.getEAttributeType().getInstanceClass() == Boolean.class)) {
					return true;
				}
				
				return eAttribute.getEAttributeType().getEPackage().getEFactoryInstance().createFromString(eAttribute.getEAttributeType(), value);
			}
		}
		catch (Exception ex) {
			addParseWarning(ex.getMessage() + " in the value of " + attributeName);
			return null;
		}
	}
	
	protected List<EStructuralFeature> getCandidateStructuralFeaturesForAttribute(EClass eClass) {
		List<EStructuralFeature> eStructuralFeatures = new ArrayList<>();
		for (EStructuralFeature sf : eClass.getEAllStructuralFeatures()) {
			if (sf.isChangeable() && (sf instanceof EAttribute || ((sf instanceof EReference) && !((EReference) sf).isContainment()))) {
				eStructuralFeatures.add(sf);
			}
		}
		return eStructuralFeatures;
	}
	
	protected List<EClass> getAllConcreteEClasses() {
		List<EClass> eClasses = new ArrayList<>();
		for (Object o : getResourceSet().getPackageRegistry().values()) {
			EPackage ePackage = (EPackage) o;
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass && !((EClass) eClassifier).isAbstract()) {
					eClasses.add((EClass) eClassifier);
				}
			}
		}
		return eClasses;
	}
	
	protected List<EClass> getAllSubtypes(EClass eClass) {

		List<EClass> allSubtypes = allSubtypesCache.get(eClass);
		if (allSubtypes == null) {
			// use all EcorePackage concrete classes (EObject does not appear as Ecore supertype)
			if (eClass.getName().equals("EObject")
					&& eClass.getEPackage().getNsURI().equals(EcorePackage.eINSTANCE.getNsURI())) {

				allSubtypes = eClass.getEPackage().getEClassifiers()
						.stream()
						.filter(ecl -> ecl instanceof EClass && !((EClass) ecl).isAbstract())
						.map(ecl -> (EClass) ecl)
						.collect(Collectors.toList());
			}
			// search for concrete subclasses the conventional way
			else {
				allSubtypes = new ArrayList<>();
				for (EClass candidate : getAllConcreteEClasses()) {
					if (candidate.getEAllSuperTypes().contains(eClass)) {
						allSubtypes.add(candidate);
					}
				}
				if (!eClass.isAbstract())
					allSubtypes.add(eClass);
			}
			allSubtypesCache.put(eClass, allSubtypes);
		}
		return allSubtypes;
	}
	
	protected EClass eClassForName(String name) {
		EClass eClass = eClassCache.get(name);
		if (eClass == null) {
			eClass = (EClass) eNamedElementForName(name, getAllConcreteEClasses());
			eClassCache.put(name, eClass);
		}
		return eClass;
		
	}
	
	protected ENamedElement eNamedElementForName(String name, Collection<? extends ENamedElement> candidates) {
		ENamedElement eNamedElement = eNamedElementForName(name, candidates, false);
		if (eNamedElement == null) eNamedElement = eNamedElementForName(name, candidates, true);
		return eNamedElement;
	}
	
	public EObjectTraceManager getEObjectTraceManager() {
		return eObjectTraceManager;
	}
	
	protected ENamedElement eNamedElementForName(String name, Collection<? extends ENamedElement> candidates, boolean fuzzy) {
		
		if (fuzzy) {
			int maxSimilarity = 0;
			ENamedElement bestMatch = null;
			for (ENamedElement candidate : candidates) {
				int similarity = stringSimilarityProvider.getSimilarity(candidate.getName().toLowerCase(), name.toLowerCase());
				if (similarity > maxSimilarity) {
					maxSimilarity = similarity;
					bestMatch = candidate;
				}
			}
			
			if (maxSimilarity == 0 && candidates.size() == 1) {
				return candidates.iterator().next();
			}
			
			return bestMatch;
		}
		else {
			for (ENamedElement candidate : candidates) {
				if (candidate.getName().equalsIgnoreCase(name)) return candidate;
			}
		}
		
		return null;
	}
	
	public void addOrderedChild(EObject parent, EObject child) {
		List<EObject> children = orderedChildren.get(parent);
		if (children == null) {
			children = new ArrayList<>();
			orderedChildren.put(parent, children);
		}
		children.add(child);
	}
	
	public List<EObject> getOrderedChildren(EObject parent) {
		if (orderedChildren.containsKey(parent)) return orderedChildren.get(parent);
		else return Collections.emptyList(); 
	}
	
	public FrameStack getFrameStack() {
		return frameStack;
	}
	
	public String getLocalId(EObject eObject) {
		return localIDs.get(eObject);
	}

	public Collection<Operation> getOperations() {
		return operations;
	}
	
	public FlexmiResource getImportedFrom() {
		return importedFrom;
	}
	
	public void setImportedFrom(FlexmiResource importedFrom) {
		this.importedFrom = importedFrom;
	}
	
	public FlexmiResource getRootResource() {
		if (importedFrom == null) return this;
		else return importedFrom.getRootResource();
	}

}
