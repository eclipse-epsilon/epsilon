/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.internal;

import java.util.Collection;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;

public class TracedPropertyAccessLedger {
	
	protected Multimap<EglTemplate, TracedPropertyAccess> accessesByTemplate = new Multimap<>();
	
	void associate(IPropertyAccess access, Region region, EglTemplate template) {
		accessesByTemplate.put(template, new TracedPropertyAccess(access, region));
	}

	Collection<TracedPropertyAccess> retrieve(EglTemplate template) {
		return accessesByTemplate.get(template);
	}
	
	void remove(EglTemplate template) {
		accessesByTemplate.remove(template);
	}
}
