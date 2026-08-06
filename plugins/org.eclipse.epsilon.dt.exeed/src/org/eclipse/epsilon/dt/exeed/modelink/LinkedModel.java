/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;


public class LinkedModel {
	
	protected String path;
	protected ModelPosition position;

	public LinkedModel(String path, ModelPosition position) {
		this.path = path;
		this.position = position;
	}

	public LinkedModel(String path, String position) {
		this.path = path;
		if (position.equalsIgnoreCase("right")) {
			this.position = ModelPosition.RIGHT;
		} else if (position.equalsIgnoreCase("middle")) {
			this.position = ModelPosition.MIDDLE;
		} else {
			this.position = ModelPosition.LEFT;
		}
		
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String file) {
		this.path = file;
	}

	public ModelPosition getPosition() {
		return position;
	}

	public void setPosition(ModelPosition position) {
		this.position = position;
	}
	
	
	
}
