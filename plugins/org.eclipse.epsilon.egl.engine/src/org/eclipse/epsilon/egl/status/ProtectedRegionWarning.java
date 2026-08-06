/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.status;

public class ProtectedRegionWarning extends Warning {

	private final String id;
	
	public ProtectedRegionWarning(String id) {
		this(id, null);
	}
	
	public ProtectedRegionWarning(String id, String path) {
		super("The protected region '" + id + "' " + 
		      "was not preserved in " + (path == null ? "the new output." : path));
		
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
