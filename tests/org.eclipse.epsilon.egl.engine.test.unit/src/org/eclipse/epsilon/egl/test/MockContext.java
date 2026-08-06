/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.execute.context.EglContext;
import org.eclipse.epsilon.egl.internal.EglModule;

public class MockContext extends EglContext {
	
	protected MockModule mockModule;
	
	public MockContext() {
		super(new EglTemplateFactory());
		mockModule = new MockModule(this);
	}

	@Override
	public EglModule getModule() {
		return mockModule;
	}
	
	private static class MockModule extends EglModule {
		public MockModule(IEglContext context) {
			super(context);
		}
	}
}
