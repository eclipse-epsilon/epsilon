/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.recording;


public class PropertyAccessRecorder implements IPropertyAccessRecorder {

	protected boolean recording = false;
	protected PropertyAccesses currentPropertyAccesses = new PropertyAccesses();
	
	@Override
	public void record(Object modelElement, String propertyName) {
		if (recording) {
			currentPropertyAccesses.add(createPropertyAccess(modelElement, propertyName));
		}
	}

	/**
	 * Subclasses should override this method to customise the type of PropertyAccess that is created.
	 */
	protected PropertyAccess createPropertyAccess(Object modelElement, String propertyName) {
		return new PropertyAccess(modelElement, propertyName);
	}

	@Override
	public void startRecording() {
		currentPropertyAccesses = new PropertyAccesses();
		recording = true;
	}

	@Override
	public void stopRecording() {
		recording = false;
	}
	
	@Override
	public PropertyAccesses getPropertyAccesses() {
		return currentPropertyAccesses;
	}
}
