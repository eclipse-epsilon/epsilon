/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.control;

import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.EglTemplate;

public class DefaultTemplateExecutionListener implements ITemplateExecutionListener {

	@Override
	public void aboutToProcess(EglTemplate template) {}

	@Override
	public void finishedProcessing(EglTemplate template) {}

	@Override
	public void finishedGenerating(EglPersistentTemplate template, String path) {}

}
