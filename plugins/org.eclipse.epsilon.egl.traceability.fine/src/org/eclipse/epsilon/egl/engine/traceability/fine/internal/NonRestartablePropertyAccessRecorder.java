/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.internal;

import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessRecorder;

/**
 * Nested [%=%] regions can lead to attempts to start an already started recorder
 * This subclass only starts the recorder if is not already recording
 */	
public class NonRestartablePropertyAccessRecorder extends PropertyAccessRecorder {
	
	@Override
	public void startRecording() {
		if (!recording) {
			super.startRecording();
			recording = true;
		}
	}
	
	@Override
	public void stopRecording() {
		super.stopRecording();
		recording = false;
	}

}
