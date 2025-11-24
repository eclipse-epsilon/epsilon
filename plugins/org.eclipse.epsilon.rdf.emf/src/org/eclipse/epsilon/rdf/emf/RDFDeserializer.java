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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.AnonId;
import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFVisitor;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Seq;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Maps RDF nodes to EClasses and EObjects.
 */
public class RDFDeserializer {
	private final Supplier<Registry> packageRegistry;

	private final Map<EObject, Resource> eobToResource = new IdentityHashMap<>();
	private final Multimap<Resource, EObject> resourceToEob = HashMultimap.create();

	private final Map<EObject, Resource> deregisteredEObject = new IdentityHashMap<>();

	public RDFDeserializer(Supplier<EPackage.Registry> packageRegistry) {
		this.packageRegistry = packageRegistry;
	}
	
	/**
	 * <p>
	 * Normalise EMF package URI to end in '#' if they don't end in '#' or '/'.
	 * </p>
	 *
	 * <p>
	 * The resource assumes that the EPackage nsURI and the RDF nsURI used for
	 * `rdf:type` subjects and for property statements (e.g.
	 * `metamodel:featureName`) are a close match to each other.
	 * </p>
	 *
	 * <p>
	 * Specifically, we support two options:
	 * </p>
	 *
	 * <ul>
	 * <li>RDF namespace IRI = EPackage nsURI (including any trailing separator,
	 * such as `#` or `/`).</li>
	 * <li>RDF namespace IRI = EPackage nsURI + "#".</li>
	 * </ul>
	 *
	 * @param namespaceURI EPackage namespace URI to be normalised.
	 * @return EPackage namespace URI normalised to have a trailing separator (e.g.
	 *         # or /).
	 */
	public String normaliseEPackageNSURI(String namespaceURI) {
		if (!namespaceURI.endsWith("#") && !namespaceURI.endsWith("/")) {
			namespaceURI += "#";
		}
		return namespaceURI;
	}
	
	/**
	 * Populates the {@link #getEObjectToResourceMap()} from the contents of
	 * the {@code model}.
	 */
	public void deserialize(Model model) {
		// Phase 1: find all sources of an rdf:type edge
		for (ResIterator it = model.listResourcesWithProperty(RDF.type); it.hasNext(); ) {
			Resource res = it.next();
			if (!res.isAnon()) {
				deserializeObjectAttributes(res);
			}
		}

		// Phase 2: set up cross-references
		for (Entry<Resource, EObject> entry : resourceToEob.entries()) {
			deserializeObjectReferences(entry.getKey(), entry.getValue());
		}
	}

	public Resource getRDFResource(EObject eob) {
		return eobToResource.get(eob);
	}

	public Map<EObject, Resource> getEObjectToResourceMap() {
		return Collections.unmodifiableMap(eobToResource);
	}

	protected EObject deserializeObjectAttributes(Resource node, EClass eClass) {
		EObject eob = eClass.getEPackage().getEFactoryInstance().create(eClass);
		eobToResource.put(eob, node);
		resourceToEob.put(node, eob);

		for (EAttribute sf : eClass.getEAllAttributes()) {
			if (sf.isDerived() || sf.isTransient()) {
				continue;
			}

			deserializeProperty(node, eob, sf);
		}
		return eob;
	}

	protected void deserializeObjectReferences(Resource resource, EObject eob) {
		for (EReference eRef : eob.eClass().getEAllReferences()) {
			if (eRef.isDerived() || eRef.isTransient()) {
				continue;
			}
			deserializeProperty(resource, eob, eRef);
		}
	}

	@SuppressWarnings("unchecked")
	protected void deserializeProperty(Resource node, EObject eob, EStructuralFeature sf) {
		Object value = deserializeProperty(node, sf);	
		
		if (value instanceof Collection c) {
			((EList<Object>) eob.eGet(sf)).addAll(c);
		} else {
			eob.eSet(sf, value);
			return;
		}
	}

	/**
	 * The resource assumes that the EPackage nsURI and the RDF nsURI used for `rdf:type` subjects and for property statements (e.g. `metamodel:featureName`) are a close match to each other.
	 * Specifically, we support two options:
	 * <br>- RDF namespace IRI = EPackage nsURI (including any trailing separator, such as `#` or `/`).
	 * <br>- RDF namespace IRI = EPackage nsURI + "#".
	 */
	@SuppressWarnings("unchecked")
	protected Object deserializeProperty(Resource node, EStructuralFeature sf) {
		String sfPackageURI = normaliseEPackageNSURI(sf.getEContainingClass().getEPackage().getNsURI());

		List<Object> values = new ArrayList<>();
		for (StmtIterator itValue = node.listProperties(new PropertyImpl(sfPackageURI, sf.getName())); itValue.hasNext(); ) {
			Statement stmt = itValue.next();
			
			Object deserialized = deserializeValue(stmt.getObject(), sf);
			if (deserialized instanceof Collection c) {
				values.addAll(c);
			} else if (deserialized != null) {
				values.add(deserialized);
			}
		}

		if (sf.isMany()) {
			return values;
		} else if (values.isEmpty()) {
			return null;
		} else {
			return values.iterator().next();
		}
	}

	protected Object deserializeValue(RDFNode node, EStructuralFeature sf) {
		 Object value = node.visitWith(new RDFVisitor() {
			 
			private Collection<?> createCollectionOfMultiValues (ExtendedIterator<RDFNode> i) {
				List<Object> values = new ArrayList<>();
				i.forEach(n -> {
					Object newValue = deserializeValue(n,sf);
					if (newValue != null) {
						values.add(newValue);
					}
				});
				return values;
			}
			 
			@Override
			public Object visitBlank(Resource r, AnonId id) {
				if (r.hasProperty(RDF.type, RDF.List)) {
					RDFList list = r.as(RDFList.class);
					return createCollectionOfMultiValues(list.iterator());
				}

				if (r.hasProperty(RDF.type, RDF.Bag)) {
					Bag bag = r.as(Bag.class);
					return createCollectionOfMultiValues(bag.iterator());
				}

				if (r.hasProperty(RDF.type, RDF.Seq)) {
					Seq seq = r.as(Seq.class);
					return createCollectionOfMultiValues(seq.iterator());
				}

				return null;
			}

			@Override
			public Object visitURI(Resource r, String uri) {
				Collection<EObject> potentialTargets = resourceToEob.get(r);
				for (EObject target : potentialTargets) {
					if (sf.getEType().isInstance(target)) {
						return target;
					}
				}

				return null;
			}

			@Override
			public Object visitLiteral(Literal l) {
				// TODO add resource option for language preference

				Class<?> type = l.getDatatype().getJavaClass();
				if (type == Byte.class) { return l.getByte() ; }
				if (type == Long.class) { return l.getLong() ; }
				if (type == Short.class) { return l.getShort() ; }
				if (type == XSDDateTime.class) {
					return ((XSDDateTime) l.getValue()).asCalendar().getTime();
				}

				// RDF does not have a "char" type: check the EMF eType instead
				if (sf.getEType().equals(EcorePackage.eINSTANCE.getEChar())
						|| sf.getEType().equals(EcorePackage.eINSTANCE.getECharacterObject())) {
					 return l.getString().charAt(0);
				}

				// Just return the value (jena.datatypes.BaseDatatype)
				return l.getValue();
			}
			
		});
		 
		 return value;
	}

	protected Set<EClass> findMostSpecificEClasses(Resource node) {
		Set<EClass> eClasses = new HashSet<>();

		for (StmtIterator it = node.listProperties(RDF.type); it.hasNext(); ) {
			RDFNode typeObject = it.next().getObject();
			if (typeObject.isAnon()) {
				continue;
			}

			String nsURI = typeObject.asResource().getNameSpace();
			String typeName = typeObject.asResource().getLocalName();
			EPackage ePackage = this.packageRegistry.get().getEPackage(nsURI);
			if (ePackage == null && (nsURI.endsWith("#") || nsURI.endsWith("/"))) {
				// Try stripping out the final # or /, to be more flexible:
				// the user may not have included them in the EPackage nsURI.
				ePackage = this.packageRegistry.get().getEPackage(nsURI.substring(0, nsURI.length() - 1));
			}

			/*
			 * NOTE: there may be URIs that don't correspond to any namespaces, such as the
			 * OWL or XML Schema ones. We skip them without raising errors.
			 */
			if (ePackage != null) {
				EClassifier eClassifier = ePackage.getEClassifier(typeName);
				if (eClassifier == null) {
					System.err.println(String.format(
						"Cannot find type '%s' in EPackage with nsURI '%s'",
						typeName, nsURI));
				}

				if (eClassifier instanceof EClass newEClass) {
					final boolean isSuperTypeOfExisting = eClasses.stream()
						.filter(existing -> newEClass.isSuperTypeOf(existing))
						.findFirst().isPresent();

					if (!isSuperTypeOfExisting) {
						/*
						 * New EClass is not a supertype of anything existing,
						 * so it should be added after removing any eClasses
						 * that are less concrete than this one.
						 */
						for (var itEClass = eClasses.iterator(); itEClass.hasNext(); ) {
							EClass existingEClass = itEClass.next();
							if (existingEClass.isSuperTypeOf(newEClass)) {
								itEClass.remove();
							}
						}
						eClasses.add(newEClass);
					}
				}
			}
		}

		return eClasses;
	}

	protected void deserializeObjectAttributes(Resource node) {
		Set<EClass> eClasses = findMostSpecificEClasses(node);
		for (EClass eClass: eClasses) {
			EObject eob = deserializeObjectAttributes(node, eClass);

			eobToResource.put(eob, node);
			resourceToEob.put(node, eob);
		}
	}

	public void deregisterEObject(EObject eob) {
		Resource node = getRDFResource(eob);
		eobToResource.remove(eob);
		resourceToEob.remove(eob, node);
		deregisteredEObject.put(eob, node);
	}

	public void registerNewEObject(EObject eob, Resource node) {
		eobToResource.put(eob, node);
		resourceToEob.put(node, eob);
	}

	public Resource restoreEObjectResource(EObject eObject) {
		Resource node = deregisteredEObject.remove(eObject);
		if (node != null) {
			registerNewEObject(eObject, node);
		}
		return node;
	}

}
