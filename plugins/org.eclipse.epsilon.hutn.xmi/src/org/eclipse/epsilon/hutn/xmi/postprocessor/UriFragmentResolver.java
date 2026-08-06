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
package org.eclipse.epsilon.hutn.xmi.postprocessor;

import static org.eclipse.epsilon.hutn.xmi.util.StringUtil.removeLeading;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

public class UriFragmentResolver {

	private Spec spec;
	
	public UriFragmentResolver(Spec spec)  {
		this.spec = spec;
	}

	public static boolean isUriFragment(String value) {
		return isSingleUriFragment(value) || isManyUriFragments(value);
	}
	
	public static boolean isSingleUriFragment(String value) {
		return value.startsWith("/") || value.startsWith("#/");
	}
	
	public static boolean isManyUriFragments(String value) {
		for (String potentialFragment : value.split(" ")) {
			if (!isSingleUriFragment(potentialFragment))
				return false;
		}
		
		return true;
	}
	
	public Collection<ClassObject> resolve(String value) {
		if (!isUriFragment(value)) {
			throw new IllegalArgumentException("Not a valid URI fragment: " + value);
		}
		
		final Collection<ClassObject> resolved = new LinkedList<>();
		
		for (String uriFragment : value.split(" ")) {
			resolved.add(resolveUriFragment(uriFragment));
		}
		
		return resolved;
	}

	private ClassObject resolveUriFragment(String uriFragment) {
		if (uriFragment.equals("//") || uriFragment.equals("#//")) {
			return getTopLevelObject(0);
		}
		
		
		final List<String> segments = Arrays.asList(removePrefix(uriFragment).split("/"));
		final int index;
		
		if (segments.get(0).length() == 0) {
			index = 0;
			
		} else {
			index = Integer.parseInt(segments.get(0));
		}
		
		return resolveRelativeTo(getTopLevelObject(index), tail(segments));
	}

	private String removePrefix(String uriFragment) {
		return removeLeading('/', removeLeading('#', uriFragment));
	}
	
	private ClassObject getTopLevelObject(int index) {
		return spec.getObjects().get(0).getClassObjects().get(index);
	}
	
	private ClassObject resolveRelativeTo(ClassObject base, List<String> segments) {
		// recurse until no more segments, or base was not found
		if (segments.isEmpty() || base == null)
			return base;
		
		// recurse down list of segments, resolving each segment relative to previous segment
		return resolveRelativeTo(resolveSegment(base, segments.get(0)), tail(segments));
	}
	
	private ClassObject resolveSegment(ClassObject base, String segment) {
		if (segment.startsWith("@")) {
			// Segments take the form: @feature.index where index is numeric
			// feature can contain full stops, so lastIndexOf is used
			final int positionOfDelimiter = segment.lastIndexOf('.');
			
			if (positionOfDelimiter <= 1) {
				System.err.println("!!!!!!!!!! " + segment);
				return null;
			}
			
			final String feature = segment.substring(1, positionOfDelimiter);
			final int    index   = Integer.parseInt(segment.substring(positionOfDelimiter + 1));
			
			final Slot<?> slot = base.findSlot(feature);
			
			if (!(slot instanceof ContainmentSlot))
				return null;
			
			return getClassObject((ContainmentSlot)slot, index);
			
		} else {
			// Segments may also use the value of an identifying feature
			// FIXME : support this
			
			return null;
		}
	}
	
	private static <T> List<T> tail(List<T> list) {
		return list.subList(1, list.size());
	}
	
	// Doesn't throw an IndexOutOfBoundsException
	private static ClassObject getClassObject(ContainmentSlot slot, int index) {
		if (index < 0 || index >= slot.getClassObjects().size()) {
				return null;
		}
			
		return slot.getClassObjects().get(index);
	}
}
