/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.emf;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.ewl.execute.WizardInstance;

public class ExecuteWizardInstanceCommand implements Command {
	
	protected WizardInstance wizardInstance;
	protected List<InMemoryEmfModel> models;
	protected ChangeDescription changeDescription;
	protected WorkbenchPartRefresher refresher;
	
	public ExecuteWizardInstanceCommand(WizardInstance wizardInstance, List<InMemoryEmfModel> models, WorkbenchPartRefresher refresher) {
		super();
		this.wizardInstance = wizardInstance;
		this.models = models;
		this.refresher = refresher;
	}

	@Override
	public void execute() {
		// Record changes only for the domain model (the first one): the others are assumed to be extra models (e.g. diagram models)
		ChangeRecorder recorder = new ChangeRecorder(models.get(0).getResource().getResourceSet());
		try {
			//recorder.beginRecording(model.allInstances());
			wizardInstance.process();
		}
		catch (EolRuntimeException e) {
			e.printStackTrace(EpsilonConsole.getInstance().getErrorStream());
		}
		finally {
			changeDescription = recorder.endRecording();
			refresher.refresh();
		}
	}

	@Override
	public void redo() {
		if (changeDescription != null) {
			changeDescription.applyAndReverse();
		}
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public Command chain(Command command) {
		return null;
	}

	@Override
	public void dispose() {
		for (InMemoryEmfModel model : models) {
			model.dispose();
		}
		changeDescription = null;
		wizardInstance = null;
	}

	@Override
	public Collection<?> getAffectedObjects() {
		return Collections.emptyList();
	}

	@Override
	public String getDescription() {
		return getLabel();
	}

	@Override
	public String getLabel() {
		try {
			return wizardInstance.getTitle();
		} catch (EolRuntimeException e) {
			return null;
		}
	}

	@Override
	public Collection<?> getResult() {
		return null;
	}

	@Override
	public void undo() {
		if (changeDescription != null) {
			changeDescription.applyAndReverse();
		}
	}
}
