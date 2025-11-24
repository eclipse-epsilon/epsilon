/********************************************************************************
 * Copyright (c) 2025 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.jena.atlas.lib.DateTimeUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Container;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Seq;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDF;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class RDFGraphResourceUpdate {
	
	private static final boolean CONSOLE_OUTPUT_ACTIVE = false;
	private static final boolean SINGLE_MULTIVALUES_AS_STATEMENT = true;
	
	private RDFDeserializer deserializer;
	private RDFGraphResourceImpl rdfGraphResource;
	
	public RDFGraphResourceUpdate(RDFDeserializer deserializer, RDFGraphResourceImpl rdfGraphResource) {
		this.deserializer = deserializer;
		this.rdfGraphResource = rdfGraphResource;
	}

	//
	// Generic Statement methods for EStructural Features
	
	private Literal createLiteral(Object value) {
		if (value.getClass().equals(Date.class)) {
			Calendar c = Calendar.getInstance();
			c.setTime((Date) value);
			String date = DateTimeUtils.calendarToXSDDateTimeString(c);
			return ResourceFactory.createTypedLiteral(date, XSDDatatype.XSDdateTime);
		} else {
			return ResourceFactory.createTypedLiteral(value);
		}
	}

	public Resource createNewEObjectResource(Model model, EObject eObject) {
		String iri = createEObjectIRI(model, eObject);
		return iri == null ? null : createNewEObjectResource(model, eObject, iri);
	}

	public Resource createNewEObjectResource(Model model, EObject eObject, String iri) {
		Resource eobResource = model.createResource(iri);
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("Created a new Resource node: " + eobResource);
		}

		// Update the deserializer maps
		deserializer.registerNewEObject(eObject, eobResource);
		return eobResource;
	}

	private Resource getEObjectResource(Model model, EObject eObject) {
		// get Resource for EObject from the model or make one
		Resource valueResource = null;
		
		valueResource = rdfGraphResource.getRDFResource((EObject)eObject);
		if (null != valueResource) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("getEObjectResource() - returned: " + getEObjectInstanceLabel(eObject));
			}
			return valueResource;
		}

		valueResource = deserializer.restoreEObjectResource(eObject);
		if (null != valueResource) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("getEObjectResource() - restored: " + getEObjectInstanceLabel(eObject));
			}
			addAllEObjectStatements(model, eObject);
			return valueResource;
		}

		valueResource = createNewEObjectResource(model, eObject);
		if (null != valueResource) {
			if (CONSOLE_OUTPUT_ACTIVE) {	
				System.out.println("getEObjectResource() - registered: " + getEObjectInstanceLabel(eObject));
			}
			addAllEObjectStatements(model, eObject);
			return valueResource;
		}

		return null;
	}
	
	private RDFNode createValueRDFNode(Object value, Model model) {
		if (value instanceof Resource) {
			return (Resource) value;
		}
		if (value instanceof Literal) {
			return (Literal) value;
		}

		if (value instanceof EObject eob) {
			return getEObjectResource(model, eob);
		} else {
			return createLiteral(value);
		}
	}
	
	private Property createProperty(EStructuralFeature eStructuralFeature) {
		// PREDICATE
		String ePackageNamespace = deserializer.normaliseEPackageNSURI(eStructuralFeature.getEContainingClass().getEPackage().getNsURI());
		return ResourceFactory.createProperty(ePackageNamespace, eStructuralFeature.getName());
	}

	private RDFNode getObjectRDFNode(EObject eObject, EStructuralFeature eStructuralFeature, Model model) {
		// SUBJECT
		Resource rdfNode = rdfGraphResource.getRDFResource(eObject);
		// PREDICATE
		Property property = createProperty(eStructuralFeature);
		// OBJECT
		if (model.contains(rdfNode, property)) {
			// TODO what happens when there are multiple statements? Are there any?
			List<RDFNode> propertyObjects = model.listObjectsOfProperty(rdfNode, property).toList();
			if (propertyObjects.size() > 1) {
				if (CONSOLE_OUTPUT_ACTIVE) {
					System.err.println(String.format(
						"getObjectRDFNode(): there is more than one object for property %s in node %s", property, rdfNode));
				}
			}
			return propertyObjects.get(0);
		} else {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println(String.format(" %s RDF Node missing property %s : ", rdfNode, property));
			}
			return null;
		}
	}
	
	private Statement findEquivalentStatement(Model model, EObject eob, EStructuralFeature eStructuralFeature, Object value) { 
		return findEquivalentStatement(model, rdfGraphResource.getRDFResource(eob), eStructuralFeature, value);
	}
	
	private Statement findEquivalentStatement(Model model, Resource eobRes, EStructuralFeature eStructuralFeature, Object value) {
		// Returns a statement for a value from similar model statements (Subject-Property) and the deserialized value matches
		List<Statement> matchedStatementList = new ArrayList<>();
		
		// SUBJECT
		Resource rdfNode = eobRes;
		// PREDICATE
		Property property = createProperty(eStructuralFeature);
		// OBJECT
		RDFNode object = (RDFNode) null;
		
		List<Statement> modelStatementList = model.listStatements(rdfNode, property, object).toList();
		for (Statement modelStatement : modelStatementList) {
			Object deserialisedValue = deserializer.deserializeProperty(modelStatement.getSubject(), eStructuralFeature);
			if (Objects.equals(value, deserialisedValue)) {
				matchedStatementList.add(modelStatement);
			}
		}
		
		if (matchedStatementList.isEmpty()) {
			return null;
		}
		
		// Warn if there is more than one statement matching
		if (matchedStatementList.size() > 1) {
			StringBuilder statementList = new StringBuilder();
			for (Statement statement : modelStatementList) {
				statementList.append(String.format("\n - %s", statement));
			}
			System.err.println(String.format(
				"Find equivalent statements method returned more than 1 statement. %s\n", statementList));
		}
		return matchedStatementList.get(0); // Only return the first statement found
	}

	private Statement createStatement(Model model, EObject eObject, EStructuralFeature eStructuralFeature, Object value) {
		Resource subject = rdfGraphResource.getRDFResource(eObject);
		if (null == subject) {
			System.err.println("createStatement() subject is null");
			return null;
		}
		return createStatement(model, subject, eStructuralFeature, value);
	}
	
	private Statement createStatement(Model model, Resource eObjectResource, EStructuralFeature eStructuralFeature, Object value) {
		// A statement is formed as "subject–predicate–object"
		// SUBJECT
		Resource rdfNode = eObjectResource;
		// PREDICATE
		Property property = createProperty(eStructuralFeature);
		// OBJECT
		RDFNode object = createValueRDFNode(value, model);

		if (object == null) {
			return null;
		} else {
			return ResourceFactory.createStatement(rdfNode, property, object);
		}
	}

	private String getEMFResourceURI(EObject eObject) {
		return eObject.eResource().getURI().toString();
	}

	private String createEObjectIRI(Model model, EObject eObject) {
		// If you configure a bad URI for the defaultModelNamespace then jena will revert to filename <File://path/file.ext>

		// TODO Add switch case here for different eObject names
		String eObjectName = EcoreUtil.generateUUID();  // This UUID is generated using Date and Time (now).

		String eObjectNamespace = rdfGraphResource.getConfig().getDefaultModelNamespace();
		if (null != eObjectNamespace && !"".equals(eObjectNamespace.strip())) {
			// Clean the user input from the rdfres config file; must end # or /
			eObjectNamespace = deserializer.normaliseEPackageNSURI(eObjectNamespace);
		} else {
			// Check for PREFIX : <default>
			String prefix = model.getNsPrefixMap().get("");
			if (null != prefix) {
				eObjectNamespace = prefix;
			} else if (eObject.eResource() != null) {
				// Fallback  to file names with a fragment for the eObject <file>#<eObjectname>
				eObjectNamespace = getEMFResourceURI(eObject) + "#";
			} else {
				// No default prefix and the EObject does not belong to a resource: give up
				System.err.println("Cannot generate IRI for object without resource and without default namespace: " + eObject);
				return null;
			}
		}

		String eObjectIRI = eObjectNamespace + eObjectName;
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("Created eObject node IRI: " + eObjectIRI);
		}
		return eObjectIRI;
	}

	private String createEClassIRI(EObject eObject) {
		//TODO We could check the name spaces on the named model, and only create the eClass IRI if the name space exists.
		String eClassNamespacePrefix = deserializer.normaliseEPackageNSURI(eObject.eClass().getEPackage().getNsURI()); // Name space based on EPackage prefix
		String eClassName = eObject.eClass().getName();
		
		String eClassIRI = eClassNamespacePrefix + eClassName;
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("eClass node IRI: " + eClassIRI);
		}
		return eClassIRI;
	}
	
	private Statement createEObjectEClassStatement(Model model, EObject eObject) {
		// TODO Add options here for creating EClassStatements with something other than RDF.type

		// Make a URI for the EObject's EClass
		String eClassIRI = createEClassIRI(eObject);

		// Create the root statement for the eObject
		Resource subject = getEObjectResource(model, eObject);
		Property predicate = RDF.type;
		Resource object = model.createResource(eClassIRI);

		// Add the statement to the model
		Statement statement = model.createStatement(subject, predicate, object);
		return statement;
	}

	//
	// Containers statement operations

	private void checkAndRemoveEmptyContainers(Container container, EObject onEObject, EStructuralFeature eStructuralFeature) {
		Model model = container.asResource().getModel();

		if (1 == container.size() && SINGLE_MULTIVALUES_AS_STATEMENT) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("\n Removing container with 1 item replacing with statement: " + container);
			}
			RDFNode firstValue = container.iterator().next();
			container.removeProperties();
			model.remove(createStatement(model, onEObject, eStructuralFeature, container));
			model.add(createStatement(model, onEObject, eStructuralFeature, firstValue));
			return;
		}
		
		if (0 == container.size()) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("\n Removing empty container: " + container);
			}
			container.removeProperties();
			model.remove(createStatement(model, onEObject, eStructuralFeature, container));
			return;
		}
	}
	
	private void searchContainerAndRemoveValue(Model model, Object value, Container container, EStructuralFeature sf) {
		if (CONSOLE_OUTPUT_ACTIVE) {System.out.println("Remove from container: " + value);}

		List<Statement> containerPropertyStatements = container.listProperties().toList();
		Iterator<Statement> cpsItr = containerPropertyStatements.iterator();
		boolean done = false;
		while (!done) {
			if (cpsItr.hasNext()) {
				Statement statement = cpsItr.next();
				if (CONSOLE_OUTPUT_ACTIVE) {
					System.out.println(" statement check: " + statement);
				}
				Object deserializedValue = deserializer.deserializeValue(statement.getObject(), sf);
				if (value.equals(deserializedValue)) {
					if (CONSOLE_OUTPUT_ACTIVE) {
						System.out.println(" removing statement " + statement);
					}
					container.remove(statement);
					if (!sf.isUnique()) {
						done = true;
					}
				}
			} else {
				done = true;
			}
		}
		
		if (checkContainment(sf, value)) {
			removeAllObjectStatements(model, value);
		}
	}

	private void removeFromContainer(Model model, Object values, Container container, EObject onEObject, EStructuralFeature eStructuralFeature) {
		if (CONSOLE_OUTPUT_ACTIVE) {reportContainer("Before remove", container);}
		
		EStructuralFeature sf = eStructuralFeature.eContainingFeature();
		if (values instanceof EList<?>) {
			EList<?> valuesList = (EList<?>) values;
			valuesList.iterator().forEachRemaining(value -> 
				searchContainerAndRemoveValue(model, value, container, sf));
		} else {
			searchContainerAndRemoveValue(model, values, container, sf);
		}
		
		if (CONSOLE_OUTPUT_ACTIVE) {reportContainer("After remove", container);}
		
		// Check if container is empty (size 0), remove the blank node if true
		checkAndRemoveEmptyContainers(container, onEObject, eStructuralFeature);
	}
	
	private void removeFromSeq(Model model, Object value, Container container, EObject onEObject, EStructuralFeature eStructuralFeature) {
		removeFromContainer(model, value, container, onEObject, eStructuralFeature);
	}
	
	private void removeFromBag(Model model, Object value, Container container, EObject onEObject, EStructuralFeature eStructuralFeature) {
		removeFromContainer(model, value, container, onEObject, eStructuralFeature);
	}
	
	private void addToSequence(Object values, Seq container, int position) {
		if (-1 == position) {
			position = container.size();
		}
		
		Model model = container.getModel();
		if (CONSOLE_OUTPUT_ACTIVE) {
			reportContainer("Before add to container ", container);
		}

		if (values instanceof List) {
			List<?> list = (List<?>) values;
			for (Object v : list) {
				++position;
				if (CONSOLE_OUTPUT_ACTIVE) {
					System.out.println(String.format("inserting: %s %s", position, v));
				}
				container.add(position, createValueRDFNode(v, model));
			}
		} else {
			// Assume values is a single value
			container.add(++position, createValueRDFNode(values, model));
		}

		if (CONSOLE_OUTPUT_ACTIVE) {
			reportContainer("After add to container ", container);
		}
	}

	private void addToBag(Object values, Bag container) {
		if (CONSOLE_OUTPUT_ACTIVE) {
			reportContainer("Before add to container ", container);
		}

		if (values instanceof Collection) {
			Collection<?> list = (Collection<?>) values;
			list.forEach(v -> container.add(v));
		} else {
			// Assume values is a single value
			container.add(values);
		}

		if (CONSOLE_OUTPUT_ACTIVE) {
			reportContainer("After add to container ", container);
		}
	}
	
	private Seq newSequence(Model model, EObject onEObject, EStructuralFeature eStructuralFeature) {
		Seq objectNode = model.createSeq();
		model.add(createStatement(model, onEObject, eStructuralFeature, objectNode));
		return objectNode;
	}
	
	private Bag newBag(Model model, EObject onEObject, EStructuralFeature eStructuralFeature) {
		Bag objectNode = model.createBag();
		model.add(createStatement(model, onEObject, eStructuralFeature, objectNode));
		return objectNode;
	}
	
	
	//
	// List statement operations	
	
	private void checkAndRemoveEmptyList(RDFList container, EObject onEObject, EStructuralFeature eStructuralFeature) {
		Model model = container.getModel();

		if (!container.isValid()) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("Removing invalid (empty) container:" + container.asResource());
			}
			Statement stmtToRemove = createStatement(model, onEObject, eStructuralFeature, container.asResource());
			model.remove(stmtToRemove);
			return;
		}
		
		if (1 == container.size() && SINGLE_MULTIVALUES_AS_STATEMENT) {
			// TODO convert list with 1 item to single statement
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("Removing container with 1 item and making a statement:" + container.asResource());
			}
			RDFNode firstValue = container.iterator().next();
			container.removeList();
			model.remove(createStatement(model, onEObject, eStructuralFeature, container.asResource()));
			model.add(createStatement(model, onEObject, eStructuralFeature, firstValue));
			return;
		}
		
		if (container.isEmpty()) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println("Removing empty container:" + container.asResource());
			}
			model.remove(createStatement(model, onEObject, eStructuralFeature, container.asResource()));
			return;
		}
	}
	
	private RDFList removeOneValueFromListHandleUnique(RDFList container, EStructuralFeature sf, RDFNode valueRDFNode) {
		RDFList newContainer = container;

		if (sf.isUnique() && null != valueRDFNode) {
			while (newContainer.isValid() && newContainer.contains(valueRDFNode)) {
				newContainer = newContainer.remove(valueRDFNode);
			}
		} else {
			newContainer = container.remove(valueRDFNode);
		}

		return newContainer;
	}
	
	private RDFList removeOneValueFromList(Model model, Object value, RDFList container, EStructuralFeature eStructuralFeature) {
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println(String.format(" - removeOneValueFromList: %s", value));
		}

		if (value instanceof EObject eob && eStructuralFeature instanceof EReference) {
			// References
			RDFNode valueRDFNode = rdfGraphResource.getRDFResource(eob);
			RDFList newContainer = removeOneValueFromListHandleUnique(container, eStructuralFeature, valueRDFNode);
			if (checkContainment(eStructuralFeature, value)) {
				removeAllObjectStatements(model, value);
			}
			return newContainer;
		} else {	
			// Attributes (Literal values)
			Iterator<RDFNode> containerItr = container.iterator();
			while (containerItr.hasNext()) {
				RDFNode rdfNode = containerItr.next();
				Object deserializedValue = deserializer.deserializeValue(rdfNode, eStructuralFeature);
				if (value.equals(deserializedValue)) {
					if (CONSOLE_OUTPUT_ACTIVE) {
						System.out.println(String.format("removing: %s == %s", value , deserializedValue));
					}
					return removeOneValueFromListHandleUnique(container, eStructuralFeature, rdfNode);
				}
			}
		}
		return container;
	}

	private void removeFromList(Object values, RDFList container, EObject onEObject, EStructuralFeature eStructuralFeature, Model model) {
		RDFList newContainer = container;
		if (values instanceof List<?> valueList) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println(String.format("list of values to remove: %s", valueList));
			}
			for (Object value : valueList) {
				newContainer = removeOneValueFromList(model, value, newContainer, eStructuralFeature);
			}
		} else {
			newContainer = removeOneValueFromList(model, values, newContainer, eStructuralFeature);
		}

		if (container != newContainer) {
			model.remove(createStatement(model, onEObject, eStructuralFeature, container.asResource()));
			model.add(createStatement(model, onEObject, eStructuralFeature, newContainer.asResource()));
		}
		checkAndRemoveEmptyList(newContainer, onEObject, eStructuralFeature);
	}
	
	private void addToList(Object values, RDFList container, int position, EStructuralFeature eStructuralFeature, EObject onEObject) {	
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println(String.format(
				"\n RDFList  ordered: %s position: %s size: %s",
				eStructuralFeature.isOrdered(), position, container.size()));
			reportRDFList("Before add to container ", container);
		}
		Model model = container.getModel();
		RDFList newList = createRDFListOnModel(values, model);

		// Un-ordered lists should be handed with these two methods
		if (container.isEmpty()) {
			// This should never happen: empty is handled elsewhere
			container.concatenate(newList);
		} else if (!eStructuralFeature.isOrdered()) {
			container.concatenate(newList);
		} else if (container.size() == position) {
			// Append new list to existing list on model
			container.concatenate(newList);
		} else if (0 == position) {
			// Add new list at the beginning of existing list
			model.remove(createStatement(model, onEObject, eStructuralFeature, container));
			model.add(createStatement(model, onEObject, eStructuralFeature, newList));
			newList.concatenate(container);
		} else {
			// Splice new list into the middle of the list:
			// we find the splice point by looping head/tail
			// over the list
			RDFList head = container;
			RDFList tail = head.getTail();
			for (int i = 1; i < position; i++) {
				head = tail;
				tail = head.getTail();
			}

			// Updated list is head -- newList -- tail
			newList.concatenate(tail);
			head.setTail(newList);
		}
	}

	private RDFList createRDFListOnModel(Object values, Model model) {
		List<RDFNode> rdfNodes = new ArrayList<>();
		if (values instanceof List<?> valuesList) {
			valuesList.forEach(v -> rdfNodes.add(createValueRDFNode(v, model)));
		} else {
			rdfNodes.add(createValueRDFNode(values, model));
		}
		return model.createList(rdfNodes.iterator());
	}
	
	private RDFList newList(Model model, EObject onEObject, EStructuralFeature eStructuralFeature, Object values) {		
		RDFList objectNode = createRDFListOnModel(values, model);
		model.add(createStatement(model, onEObject, eStructuralFeature, objectNode));
		return objectNode;
	}

	//
	// Feature operations

	private boolean checkContainment(EStructuralFeature eStructuralFeature, Object value) {
		return eStructuralFeature instanceof EReference eRef
			&& value instanceof EObject
			&& eRef.isContainment();
	}

	private void addAllEStructuralFeatureStatements(EObject eObject, Model model) {
		// Make statements for EStructuralFeatures (Single-value/Multi-values)
		EList<EStructuralFeature> eStructuralFeatureList = eObject.eClass().getEAllStructuralFeatures();

		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("Add all statements for: " + eObject.eClass().getName() + " #" + eObject.hashCode());
		}

		for (EStructuralFeature eStructuralFeature : eStructuralFeatureList) {
			Object value = eObject.eGet(eStructuralFeature, true);	
			if (null != value) {
				if (eStructuralFeature.isMany()) {
					if (value instanceof List) {
						if (CONSOLE_OUTPUT_ACTIVE) {
							System.out.println(String.format(" - %s : %s : [%s]", eStructuralFeature.eClass().getName(),
									eStructuralFeature.getName(), ((List<?>) value).size()));
						}
						for(Object v :  (List<?>) value) {
							addMultiValueEStructuralFeature(model, eObject, eStructuralFeature, v, Notification.NO_INDEX);
						} 
					} else { 
						if (CONSOLE_OUTPUT_ACTIVE) {
							System.out.println(String.format(" - %s : %s : %s", eStructuralFeature.eClass().getName(),
									eStructuralFeature.getName(),  value));
						}
						addMultiValueEStructuralFeature(model, eObject, eStructuralFeature, value, Notification.NO_INDEX);
					}
				} else {
					if (CONSOLE_OUTPUT_ACTIVE) {
						System.out.println(String.format(" - %s : %s : %s", eStructuralFeature.eClass().getName(),
								eStructuralFeature.getName(),  value));
					}
					addSingleValueEStructuralFeatureStatements(model, eObject, eStructuralFeature, value);
				}
			}
		}
	}
	
	private void removeAllEStructuralFeatureStatements(EObject eObject, Model model) {
		// Make statements for EStructuralFeatures (Single-value/Multi-values)
		EList<EStructuralFeature> eStructuralFeatureList = eObject.eClass().getEAllStructuralFeatures();
		
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("\nRemove all statements for: " + getEObjectInstanceLabel(eObject));
		}

		for (EStructuralFeature eStructuralFeature : eStructuralFeatureList) {
			Object value = eObject.eGet(eStructuralFeature);
			if (null != value) {
				if (eStructuralFeature.isMany()) {
					if (value instanceof List l) {
						if (CONSOLE_OUTPUT_ACTIVE) {
							System.out.println(String.format(" - %s : %s : [%s]", eStructuralFeature.eClass().getName(),
									eStructuralFeature.getName(), l.size()));
						}
						for (Object v : l) {
							removeMultiEStructuralFeature(model, eObject, eStructuralFeature, v);
						} 
					} else {
						if (CONSOLE_OUTPUT_ACTIVE) {
							System.out.println(String.format(" - %s : %s : %s", eStructuralFeature.eClass().getName(),
									eStructuralFeature.getName(),  value));
						}
							removeMultiEStructuralFeature(model, eObject, eStructuralFeature, value);
						}
				} else {
					if (CONSOLE_OUTPUT_ACTIVE) {
						System.out.println(String.format(" - %s : %s : %s", eStructuralFeature.eClass().getName(),
								eStructuralFeature.getName(),  value));
					}
					removeSingleValueEStructuralFeatureStatements(model, eObject, eStructuralFeature, value);
				}
			}
		}
		
	}

	//
	// EObject operations

	private void addEObjectEClassStatement(Model model, EObject eObject) {
		model.add(createEObjectEClassStatement(model, eObject));
	}

	private void removeEObjectEClassStatement(Model model, EObject eObject) {
		model.remove(createEObjectEClassStatement(model, eObject));
	}

	private void addAllEObjectStatements(Model model, EObject eObject) {
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("\n * Adding all statements for: " + getEObjectInstanceLabel(eObject));
		}
		addEObjectEClassStatement(model, eObject);
		addAllEStructuralFeatureStatements(eObject, model);
	}

	private void removeAllObjectStatements(Model model, Object oldValue) {
		if (oldValue instanceof EObject) {
			removeAllEObjectStatements(model, (EObject) oldValue);
		} else {
			System.err.println("removeAllObjectStatements() not an EObject: "
					+ oldValue.getClass().getName());
		}
	}

	private void removeAllEObjectStatements(Model model, EObject eObject) {
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("\n * Removing all statements for: " + getEObjectInstanceLabel(eObject));
		}
		
		removeAllEStructuralFeatureStatements(eObject, model);
		removeEObjectEClassStatement(model, eObject);

		deserializer.deregisterEObject(eObject);
	}

	//
	// Model resource operations (model root changes)

	protected void addToResource(EObject eObject, Model model) {
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("ADD to Resource EObject: " + eObject.eClass().getName() + " #"
				+ eObject.hashCode() + "\n Container: " + eObject.eContainer());
		}
		if (null == eObject.eContainer()) {
			addAllEObjectStatements(model, eObject);
		}
	}

	protected void removeFromResource(EObject eObject, Model model) {
		if (CONSOLE_OUTPUT_ACTIVE) {
			System.out.println("Remove from Resource EObject: " + eObject.eClass().getName() + " #"
				+ eObject.hashCode() + "\n Container: " + eObject.eContainer());
		}
		if (null == eObject.eContainer()) {
			removeAllEObjectStatements(model, eObject);
		}
	}

	//
	// Single-value Features operations
	
	public void addSingleValueEStructuralFeatureStatements(List<Resource> namedModelURIs, EObject onEObject, EStructuralFeature eStructuralFeature, Object newValue) {
		assert newValue != null : "new value must exist";

		// We default always to the first named model for a new statement.
		// In the future, we may use a rule-based system to decide which named model to use.
		List<Model> namedModelsToUpdate = rdfGraphResource.getNamedModels(namedModelURIs);
		for (Model model : namedModelsToUpdate) {
			addSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, newValue);
		}
	}

	public void addSingleValueEStructuralFeatureStatements(Model model, EObject onEObject, EStructuralFeature eStructuralFeature, Object newValue) {
		assert newValue != null : "new value must exist";
		Statement newStatement = createStatement(model, onEObject, eStructuralFeature, newValue);
		Statement existingStatements = findEquivalentStatement(model, onEObject, eStructuralFeature, newValue);

		if (null != newStatement && null == existingStatements && !model.contains(newStatement)) {
			model.add(newStatement);
		}
	}
	
	public void removeSingleValueEStructuralFeatureStatements(List<Resource> namedModelURIs, EObject onEObject, EStructuralFeature eStructuralFeature, Object oldValue) {
		// Object type values set a new value "null", remove the statement the deserializer uses the meta-model so we won't have missing attributes
		assert oldValue != null : "old value must exist";		
		List<Model> namedModelsToUpdate = rdfGraphResource.getNamedModels(namedModelURIs);
		for (Model model : namedModelsToUpdate) {
			removeSingleValueEStructuralFeatureStatements(model,onEObject,eStructuralFeature,oldValue);
		}
	}
	
	public void removeSingleValueEStructuralFeatureStatements(Model model, EObject onEObject, EStructuralFeature eStructuralFeature, Object oldValue) {
		// Object type values set a new value "null", remove the statement the
		// deserializer uses the meta-model so we won't have missing attributes
		assert oldValue != null : "old value must exist";

		// try object-to-literal
		Statement oldStatement = createStatement(model, onEObject, eStructuralFeature, oldValue);
		if (null == oldStatement) {
			System.err.println("Can't make a statement, probably removed from deserialiser already");
		}
		
		if (null != oldStatement  && model.contains(oldStatement)) {
			if (checkContainment(eStructuralFeature, oldValue)) {
				removeAllObjectStatements(model, oldValue);
			}
			model.remove(oldStatement);
			return;
		}

		// try literal-to-object
		Statement stmtToRemove = findEquivalentStatement(model, onEObject, eStructuralFeature, oldValue);
		if (stmtToRemove != null) {
			if (checkContainment(eStructuralFeature, oldValue)) {
				removeAllObjectStatements(model, oldValue);
			}
			model.remove(stmtToRemove);
			return;
		}
		
		// EAttribute has a default value no statements in the RDF to match
		if (oldValue.equals(eStructuralFeature.getDefaultValue())) {
			if (CONSOLE_OUTPUT_ACTIVE) {
				System.out.println(String.format(
						"Old statement not found, but the oldvalue matches the models default value, so there might not be a statement."
						+ "\n Remove - default value %s - old value %s ",
						eStructuralFeature.getDefaultValue(), oldValue));					
			}
			return;
		}

		// Couldn't find old statement through either object-to-literal or
		// literal-to-object conversion, and there is no default value
		 
		System.err.println(String.format("Old statement not found during single removal: %s\n - eObject (subject): %s\n - feature (predicate): %s\n - value (object): %s ", oldStatement, onEObject, eStructuralFeature,oldValue));
		return;
	}
	
	public void updateSingleValueEStructuralFeatureStatements(List<Resource> namedModelURIs, EObject onEObject, EStructuralFeature eStructuralFeature, Object newValue, Object oldValue) {
		assert oldValue != null : "old value must exist";
		assert newValue != null : "new value must exist";
		List<Model> namedModelsToUpdate = rdfGraphResource.getNamedModels(namedModelURIs);
		for (Model model : namedModelsToUpdate) { 
			updateSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, newValue, oldValue);
		};
	}
	
	public void updateSingleValueEStructuralFeatureStatements(Model model, EObject onEObject, EStructuralFeature eStructuralFeature, Object newValue, Object oldValue) {
		// Remove any existing statements and add a new one
		removeSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, oldValue);
		addSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, newValue);
	}
	
	//
	// Multi-value Feature operations
	
	public void removeMultiEStructuralFeature (List<Resource> namedModelURIs, EObject onEObject, EStructuralFeature eStructuralFeature, Object oldValue) { 
		List<Model> namedModelsToUpdate = rdfGraphResource.getNamedModels(namedModelURIs);
		for (Model model : namedModelsToUpdate) {
			removeMultiEStructuralFeature(model, onEObject, eStructuralFeature, oldValue);
		}
	}
	
	public void removeMultiEStructuralFeature(Model model, EObject onEObject, EStructuralFeature eStructuralFeature, Object oldValue) {
		Resource onEObjectNode = rdfGraphResource.getRDFResource(onEObject);
		Property property = createProperty(eStructuralFeature);
		if (!onEObjectNode.hasProperty(property)) {
			System.err.printf("Trying to remove the non-existing property %s in resource %s%s",
				property, onEObjectNode, System.lineSeparator());
		} else {
			// Exists in a model somewhere...
			RDFNode objectRDFNode = getObjectRDFNode(onEObject, eStructuralFeature, model);

			if (objectRDFNode == null) {
				// The node does not have a value in the current model - do nothing
			} else if (objectRDFNode.isLiteral()) {
				// A 1 multi-value exists as a statement with no container
				removeSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, oldValue);
			} else if (objectRDFNode.isResource()) {
				Resource objectResource = objectRDFNode.asResource();
				// Try and the container from each model to be updated
				if ( (objectResource.hasProperty(RDF.rest) && objectResource.hasProperty(RDF.first)) 
						|| objectResource.hasProperty(RDF.type, RDF.List) ) {
					RDFList list = model.getList(objectResource);
					list.setStrict(true);
					removeFromList(oldValue, list, onEObject, eStructuralFeature, model);
				} else if (objectResource.equals(RDF.nil)) {
					// Empty list
					System.err.println("Removing from Empty list");
				} else if (objectResource.hasProperty(RDF.type, RDF.Bag)) {
					Bag bag = model.getBag(objectResource);
					removeFromBag(model, oldValue, bag, onEObject, eStructuralFeature);
				} else if (objectResource.hasProperty(RDF.type, RDF.Seq)) {
					Seq seq = model.getSeq(objectResource);
					removeFromSeq(model, oldValue, seq, onEObject, eStructuralFeature);
				} else {
					// The first item might look like a single value EAttribute
					removeSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, oldValue);
				}
			}
		}
	}
	
	public void addMultiValueEStructuralFeature (List<Resource> namedModelURIs, EObject onEObject, EStructuralFeature eStructuralFeature, Object newValue, Object oldValue, int position) { 
		List<Model> namedModelsToUpdate = rdfGraphResource.getNamedModels(namedModelURIs);
		for (Model model : namedModelsToUpdate) {
			addMultiValueEStructuralFeature(model, onEObject, eStructuralFeature, newValue, position);
		}
	}
	
	public void addMultiValueEStructuralFeature (Model model, EObject onEObject, EStructuralFeature eStructuralFeature, Object newValue, int position) {
		// sequence (ordered), bag (unordered), list (ordered/unordered)
		
		Resource onEObjectNode = rdfGraphResource.getRDFResource(onEObject);
				
		// Work out if we are adding a NEW multi-value attribute with no existing RDF node.
		if (!onEObjectNode.hasProperty(createProperty(eStructuralFeature))) {
			// Does not exist anywhere so we need a NEW RDF representation			
			if (newValue instanceof List<?>) {
				if (CONSOLE_OUTPUT_ACTIVE) {System.out.println("\n No existing container, multiple values added making container");}
				createContainerAndAdd(model, onEObject, eStructuralFeature, newValue, position, null);
			} else {
				if (CONSOLE_OUTPUT_ACTIVE) {System.out.println("\n No existing container, first single value is a statement");}
				addSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, newValue);	
			}
		} else {
			// Exists on a model some where...
			RDFNode objectRDFNode = getObjectRDFNode(onEObject, eStructuralFeature, model);
			
			if (objectRDFNode.isLiteral()) {
				// There is a single value statement for the 1 multi-value
				if (CONSOLE_OUTPUT_ACTIVE) {System.out.println("\n No existing container, multiple values added making container");}
				createContainerAndAdd(model, onEObject, eStructuralFeature, newValue, position, objectRDFNode.asLiteral().getValue());		
			} else if (objectRDFNode.isResource()) {
				Resource objectResource = objectRDFNode.asResource();
				// If we have one of these types, then we are updating an existing statement on a model
				if ( objectResource.hasProperty(RDF.type, RDF.List)
						|| (objectResource.hasProperty(RDF.rest) && objectResource.hasProperty(RDF.first))) {
					// Lists can be ordered or unique, both or none.
					RDFList list = model.getList(objectResource);
					addToList(newValue, list, position, eStructuralFeature, onEObject);
				} else if (objectResource.equals(RDF.nil)) {
					// List - Empty lists may be represented with an RDF.nil value
					Statement stmt = createStatement(model, onEObject, eStructuralFeature, objectResource);
					model.remove(stmt);
					newList(model, onEObject, eStructuralFeature, newValue);
				} else if (objectResource.hasProperty(RDF.type, RDF.Bag)) {
					Bag bag = model.getBag(objectResource);
					addToBag(newValue, bag);
				} else if (objectResource.hasProperty(RDF.type, RDF.Seq)) {
					Seq seq = model.getSeq(objectResource);
					addToSequence(newValue, seq, position);
				} else {
					createContainerAndAdd(model, onEObject, eStructuralFeature, newValue, position, objectResource);
				}
			}
		}
	}

	private void createContainerAndAdd(Model model, EObject onEObject, EStructuralFeature eStructuralFeature,
			Object newValue, int position, Object firstValue) {

		if (null != firstValue) {
			// There is a statement for the first value, with no container structure
			removeSingleValueEStructuralFeatureStatements(model, onEObject, eStructuralFeature, firstValue);
		}
		
		if (rdfGraphResource.getConfig().getRawMultiValueAttributeMode().useLists()) {
			// List
			RDFList list = null;
			if (null != firstValue) {
				list = newList(model, onEObject, eStructuralFeature, firstValue);
				addToList(newValue, list, position, eStructuralFeature, onEObject);
			} else {
				list = newList(model, onEObject, eStructuralFeature, newValue);
			}
		} else {
			if (eStructuralFeature.isOrdered()) {
				// Sequence
				Seq sequence = newSequence(model, onEObject, eStructuralFeature);
				if (null != firstValue) {
					addToSequence(firstValue, sequence , 0);
				}
				addToSequence(newValue, sequence, position);
			} else {
				// Bag
				Bag bag = newBag(model, onEObject, eStructuralFeature);
				if (null != firstValue) {
					addToBag(firstValue, bag);
				}
				addToBag(newValue, bag);
			}
		}
	}
	
	
	//
	// Reporting (these could return formatted strings for logging or console use)

	private static void reportContainer(String label, Container container) {
		boolean hasType = container.hasProperty(RDF.type);
		if (hasType) {
			System.out.println(String.format("\n%s Container: Type %s, Size %s",
					label, container.getProperty(RDF.type), container.size()));
			container.iterator().forEach(i -> System.out.println("  * " + i));
		}
	}
	
	private static void reportRDFList (String label, RDFList container) {
		System.out.println(String.format("\n%s List: Strict %s, Size: %s", 
				label, container.getStrict(), container.size() ));
		
		Resource item = container;
		while (null != item) {
			Statement restStatement = item.getProperty(RDF.rest);
			Statement firstStatement = item.getProperty(RDF.first);
			
			if (null != restStatement) {
				System.out.println(String.format(" * RDFnode %s \n\t--> rest: %s \n\t--> first: %s ",
					item, restStatement.getObject(), firstStatement.getObject()));
				item = item.getProperty(RDF.rest).getResource();
			} else {
				item = null;
			}
		}
	}
	
	private String getEObjectInstanceLabel (EObject eObject) {
		return String.format( "%s#%s", eObject.eClass().getName(), eObject.hashCode());
	}
	
}
