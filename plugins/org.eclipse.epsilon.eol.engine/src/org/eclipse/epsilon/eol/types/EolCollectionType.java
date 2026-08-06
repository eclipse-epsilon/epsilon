/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García Domínguez - add generics, clean up dead code,
 *                                remove type cache (bug #410403).
 *    Sina Madani - concurrent types
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.types.concurrent.EolConcurrentBag;
import org.eclipse.epsilon.eol.types.concurrent.EolConcurrentSet;

public class EolCollectionType extends EolType {
	
	protected static Set<IEolCollectionTypeResolver> collectionTypeResolvers;
	protected EolType contentType = EolAnyType.Instance;
	
	public static Set<IEolCollectionTypeResolver> getCollectionTypeResolvers() {
		if (collectionTypeResolvers == null) {
			collectionTypeResolvers = new HashSet<>();
		}
		return collectionTypeResolvers;
	}
	
	private String name;
	
	public static final EolCollectionType
		Collection = new EolCollectionType("Collection"),
		Bag = new EolCollectionType("Bag"),
		Sequence = new EolCollectionType("Sequence"),
		Set = new EolCollectionType("Set"),
		OrderedSet = new EolCollectionType("OrderedSet"),
		ConcurrentBag = new EolCollectionType("ConcurrentBag"),
		ConcurrentSet = new EolCollectionType("ConcurrentSet");
	
	public EolCollectionType(String name) {
		this.name = name;
	}
	
	public EolCollectionType(String name, EolType contentType) {
		this(name);
		this.contentType = contentType;
	}
	
	public EolCollectionType getTypeOf(Collection<?> c) {
		if (c instanceof EolConcurrentSet) return ConcurrentSet;
		if (c instanceof EolConcurrentBag) return ConcurrentBag;
		if (c instanceof EolSequence) return Sequence;
		if (c instanceof EolOrderedSet) return OrderedSet;
		if (c instanceof EolSet) return Set;
		
		for (IEolCollectionTypeResolver collectionTypeResolver : getCollectionTypeResolvers()) {
			if (collectionTypeResolver.canResolveType(c)) {
				return collectionTypeResolver.resolveType(c);
			}
		}
		
		if (c instanceof List) return (c instanceof Set) ? OrderedSet : Sequence;
		if (c instanceof Set) return Set;
		return Bag;
	}
	
	@Override
	public boolean isType(Object o) {
		if (!(o instanceof Collection)) return false;
		Collection<?> c = (Collection<?>) o;
		if (this.isCollection()) return false; // Collection is abstract
		return Objects.equals(getTypeOf(c).getName(), this.getName());
	}

	@Override
	public Collection<Object> createInstance() throws EolRuntimeException {
		if (this.isCollection()) {
			return null;
		}
		else if (this.isConcurrentBag()) {
			return new EolConcurrentBag<>();
		}
		else if (this.isConcurrentSet()) {
			return new EolConcurrentSet<>();
		}
		else if (this.isSet()) {
			return new EolSet<>();
		}
		else if (this.isSequence()) {
			return new EolSequence<>();
		}
		else if (this.isOrderedSet()) {
			return new EolOrderedSet<>();
		}
		else if (this.isBag()) {
			return new EolBag<>();
		}
		else {
			throw new EolRuntimeException("Unknown collection type");
		}
	}
	
	@Override
	public Object createInstance(List<Object> parameters) throws EolRuntimeException {
		throw new EolIllegalOperationParametersException("createInstance");
	}
	
	@Override
	public boolean isKind(Object o) {
		if (!(o instanceof Collection)) return false;
		EolCollectionType collectionType = getTypeOf((Collection<?>) o);
		if (this.isCollection()) return true;
		else if (this.isConcurrentBag()) return collectionType.isConcurrentBag();
		else if (this.isConcurrentSet()) return collectionType.isConcurrentSet();
		else if (this.isSequence()) return collectionType.isSequence();
		else if (this.isOrderedSet()) return collectionType.isOrderedSet();
		else if (this.isBag()) return collectionType.isBag() || collectionType.isSequence() || collectionType.isConcurrentBag();
		else if (this.isSet()) return collectionType.isSet() || collectionType.isOrderedSet() || collectionType.isConcurrentSet(); 
		else return false;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public static String getTypeName(Collection<?> c) {
		if (Bag.isType(c)) return Bag.getName();
		else if (Sequence.isType(c)) return Sequence.getName();
		else if (OrderedSet.isType(c)) return OrderedSet.getName();
		else if (Set.isType(c)) return Set.getName();
		else if (ConcurrentBag.isType(c)) return ConcurrentBag.getName();
		else if (ConcurrentSet.isType(c)) return ConcurrentSet.getName();
		else return c.getClass().getSimpleName();
	}
	
	public static <T> Collection<T> createSameType(Collection<T> c) {
		if (Bag.isType(c)) return new EolBag<>();
		else if (Sequence.isType(c)) return new EolSequence<>();
		else if (OrderedSet.isType(c)) return new EolOrderedSet<>();
		else if (Set.isType(c)) return new EolSet<>();
		else if (ConcurrentBag.isType(c)) return new EolConcurrentBag<>();
		else if (ConcurrentSet.isType(c)) return new EolConcurrentSet<>();
		else return null;
	}
	
	public static <T> Collection<T> clone(Collection<T> c) {
		Collection<T> clone = createSameType(c);
		clone.addAll(c);
		return clone;
	}
	
	public static <T> Collection<T> join(Collection<T> c1, Collection<T> c2) {
		Collection<T> joined = createSameType(c1);
		joined.addAll(c1);
		joined.addAll(c2);
		return joined;
	}
	
	public static boolean isUnique(Collection<?> c) {
		return Set.isType(c) || OrderedSet.isType(c) || ConcurrentSet.isType(c);
	}
	
	public static boolean isOrdered(Collection<?> c) {
		return Sequence.isType(c) || OrderedSet.isType(c);
	}
	
	public boolean isBag() {
		return "Bag".equals(getName());
	}
	
	public boolean isSequence() {
		return "Sequence".equals(getName());
	}
	
	public boolean isSet() {
		return "Set".equals(getName());
	}
	
	public boolean isOrderedSet() {
		return "OrderedSet".equals(getName());
	}
	
	public boolean isCollection() {
		return "Collection".equals(getName());
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isConcurrentBag() {
		return "ConcurrentBag".equals(getName());
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isConcurrentSet() {
		return "ConcurrentSet".equals(getName());
	}
	
	public EolType getContentType() {
		return contentType;
	}
	
	public void setContentType(EolType contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return this.getName() + "<" + this.getContentType() + ">";
	}
	
	/*
	If A is a sub-type of B, then the parent types of Sequence(A) are:
		- Collection(A)
		- Sequence(B) [This is not supported yet by the implementation below]
	 */
	@Override
	public EolType getParentType() {
		if (this.isBag() || this.isSet() || this.isOrderedSet() || this.isSequence())
			return new EolCollectionType("Collection", this.getContentType());
		else {
			if (!(this.getContentType() instanceof EolAnyType))
				return new EolCollectionType("Collection", EolAnyType.Instance);
			else
				return EolAnyType.Instance;
		}
	}
}
