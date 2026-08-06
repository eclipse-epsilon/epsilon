/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.recording;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * A PropertyAccessRecorder can be attached to an {@link IEolContext} 
 * in order to be notified when a program execution reads a property
 * value from a model element. For example, execution of the EOL code:
 * `Person.all.first.name` would result in a PropertyAccessRecorder
 * being notified of a property access for the `name` property of 
 * the `Person.all.first` model element.
 * 
 * Recording is separated into sessions. A session is started using
 * {@link #startRecording()} and ended with {@link #stopRecording()}.
 * Property accesses for the most recently started session are available
 * from {@link #getPropertyAccesses()}. Clients that wish to access 
 * property accesses from earlier recording sessions are expected to 
 * call {@link #getPropertyAccesses()} and store the resulting value 
 * before calling {@link #startRecording()}. 
 */
public interface IPropertyAccessRecorder {

	/**
	 * Tells the PropertyAccessRecorder to start a new recording
	 * session. Any subsequent notifications of property accesses
	 * are recorded and are accessible via {@link #getPropertyAccesses()}.
	 * Property accesses from any previous recording session are 
	 * disregarded (i.e., are no longer accessible from 
	 * {@link #getPropertyAccesses()})  
	 */
	public void startRecording();

	/**
	 * Tells the PropertyAccessRecorder to finalise the current recording
	 * session. Any subsequent notifications of property accesses are not
	 * recorded, until {@link #startRecording()} is called. Property accesses 
	 * from the ended recording session are available via 
	 * {@link #getPropertyAccesses()}.  
	 */
	public void stopRecording();

	/**
	 * Returns the property accesses that have occurred in the most 
	 * recently started recording session.
	 */
	public PropertyAccesses getPropertyAccesses();

	/**
	 * Tells the {@link PropertyAccessRecorder} that a property access
	 * for the specified model element and property name has occurred. 
	 */
	public void record(Object modelElement, String propertyName);
}
