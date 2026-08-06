/*******************************************************************************
 * Copyright (c) 2013 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.incremental;

public class IncrementalitySettings {

	private boolean overwriteUnchangedFiles = false;

	public IncrementalitySettings() {}
	
	public IncrementalitySettings(IncrementalitySettings original) {
		this.overwriteUnchangedFiles = original.overwriteUnchangedFiles;
	}

	public boolean isOverwriteUnchangedFiles() {
		return overwriteUnchangedFiles;
	}
	
	public void setOverwriteUnchangedFiles(boolean overwriteUnchangedFiles) {
		this.overwriteUnchangedFiles = overwriteUnchangedFiles;
	}
	
}
