/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.widgets;

public interface ListListener {

	public void changed(ChangeType changeType, Object item, int index);
	
	public static enum ChangeType {
		ADDED, REMOVED, MOVED_UP, MOVED_DOWN;
	}
}
