/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.actions.AbstractObjectActionDelegate;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.hutn.dt.util.HutnBuilderHelper;
import org.eclipse.jface.action.IAction;

public class GenerateModelAction extends AbstractObjectActionDelegate {
	
	
	@Override
	public void run(IAction action) {
		if (getFirstElementInSelection() instanceof IFile) {
			new HutnBuilderHelper((IFile)getFirstElementInSelection(), new DialogueReporter()).buildHutn();
		}
	}
	
	private static class DialogueReporter implements HutnBuilderHelper.HutnBuildReporter {
		
		@Override
		public void reportFailure(IFile source, String message) {
			LogUtil.logInfo(message, true);
		}
	}
}
