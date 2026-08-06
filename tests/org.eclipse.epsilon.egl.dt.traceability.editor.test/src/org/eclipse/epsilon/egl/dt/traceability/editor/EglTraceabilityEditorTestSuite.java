/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor;

import org.eclipse.epsilon.egl.dt.traceability.editor.hyperlinks.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({TextLinkHyperlinkDetectorTests.class})
public class EglTraceabilityEditorTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EglTraceabilityEditorTestSuite.class);
	}
}
