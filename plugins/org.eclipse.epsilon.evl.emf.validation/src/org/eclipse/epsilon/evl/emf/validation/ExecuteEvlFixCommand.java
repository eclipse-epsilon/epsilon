/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.execute.FixInstance;

public class ExecuteEvlFixCommand implements Command{
	
	protected FixInstance fix = null;
	protected InMemoryEmfModel model = null;
	protected ChangeDescription changeDescription;
	
	public ExecuteEvlFixCommand(FixInstance fix, InMemoryEmfModel model) {
		super();
		this.fix = fix;
		this.model = model;
	}

	public void execute() {
		ChangeRecorder recorder = new ChangeRecorder(model.getResource().getResourceSet());
		try {
			fix.perform();
		} catch (EolRuntimeException e) {
			e.printStackTrace(EpsilonConsole.getInstance().getErrorStream());
		}
		finally {
			changeDescription = recorder.endRecording();
		}
	}

	public void redo() {
		if (changeDescription != null) {
			changeDescription.applyAndReverse();
		}
	}

	public boolean canExecute() {
		return true;
	}

	public boolean canUndo() {
		return true;
	}

	public Command chain(Command command) {
		return null;
	}

	public void dispose() {
		model.dispose();
		changeDescription = null;
		fix = null;
	}

	public Collection<?> getAffectedObjects() {
		return Collections.EMPTY_LIST;
	}

	public String getDescription() {
		return getLabel();
	}

	public String getLabel() {
		try {
			return fix.getTitle();
		} catch (EolRuntimeException e) {
			return "";
		}
	}

	public Collection<?> getResult() {
		return null;
	}

	public void undo() {
		if (changeDescription != null) {
			changeDescription.applyAndReverse();
		}

	}
}

	
