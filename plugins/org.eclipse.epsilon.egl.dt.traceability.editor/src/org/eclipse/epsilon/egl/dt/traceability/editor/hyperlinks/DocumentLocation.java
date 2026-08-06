/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor.hyperlinks;

import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region;

public class DocumentLocation {

	private final int offset;
	
	public DocumentLocation(int offset) {
		this.offset = offset;
	}
	
	public boolean isIn(Region region) {
		return region.getOffset() <= offset &&
			   offset <= (region.getOffset() + region.getLength());
	}

}
