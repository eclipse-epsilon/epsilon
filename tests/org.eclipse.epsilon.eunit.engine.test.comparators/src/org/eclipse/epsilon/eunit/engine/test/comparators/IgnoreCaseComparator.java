package org.eclipse.epsilon.eunit.engine.test.comparators;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eunit.extensions.IModelComparator;

/**
 * Dummy custom comparator which only does an in-order traversal of both
 * models, checking that all attributes are the same while (ignoring case and
 * ignoring references). Only usable for internal testing, to avoid a dependency
 * on the full-featured EMFModelComparator.
 *
 * This class is intentionally *NOT* registered as an OSGi extension, to test
 * how to register comparators manually in non-OSGi environments.
 */
public class IgnoreCaseComparator implements IModelComparator {

	@Override
	public boolean canCompare(IModel expectedModel, IModel obtainedModel) {
		return true;
	}

	@Override
	public Object compare(IModel expectedModel, IModel obtainedModel) throws Exception {
		Collection<?> contentsA = expectedModel.allContents();
		Collection<?> contentsB = obtainedModel.allContents();
		if (contentsA.size() != contentsB.size()) {
			return String.format("Sizes differ: %d vs %d",
				contentsA.size(), contentsB.size());
		}

		Iterator<?> itA = contentsA.iterator();
		Iterator<?> itB = contentsB.iterator();
		while (itA.hasNext() && itB.hasNext()) {
			EObject a = (EObject) itA.next();
			EObject b = (EObject) itB.next();

			if (a.eClass() != b.eClass()) {
				return String.format("EClasses differ: %s vs %s",
					a.eClass(), b.eClass());
			}

			for (EStructuralFeature sf : a.eClass().getEAllStructuralFeatures()) {
				final boolean aSet = a.eIsSet(sf);
				final boolean bSet = b.eIsSet(sf);
				if (aSet != bSet) {
					return String.format(
						"Feature %s not set on both sides of %s and %s",
						sf.getName(), a, b);
				} else if (aSet && sf instanceof EAttribute) {
					Object vA = a.eGet(sf); 
					Object vB = b.eGet(sf);
					if (!Objects.equals(vA, vB)) {
						// Values differ - try converting to lowercase before giving up
						String sALower = (vA + "").toLowerCase();
						String sBLower = (vB + "").toLowerCase();
						if (!sALower.equals(sBLower)) {
							return String.format(
								"Feature %s has different value (%s vs %s)",
								sf.getName(), vA, vB);
						}
					}
				}
			}
		}

		// No differences found
		return null;
	}

	@Override
	public void configure(Map<String, Object> options) {
		// nothing to do
	}

}
