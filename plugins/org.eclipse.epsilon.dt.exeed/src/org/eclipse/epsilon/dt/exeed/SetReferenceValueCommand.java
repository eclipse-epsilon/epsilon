/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class SetReferenceValueCommand implements Command {
	
	protected EObject newValue;
	protected EObject oldValue;
	protected EObject object;
	protected EReference reference;
	
	public SetReferenceValueCommand(EObject object, EObject newValue, EReference reference) {
		this.newValue = newValue;
		this.object = object;
		this.reference = reference;
	}
	
	public boolean canExecute() {
		return true;
	}

	public boolean canUndo() {
		return true;
	}

	public Command chain(Command arg0) {
		return null;
	}

	public void dispose() {
		
	}

	public void execute() {
		oldValue = (EObject) object.eGet(reference);
		object.eSet(reference, newValue);
	}

	public Collection<?> getAffectedObjects() {
		List<EObject> col = new ArrayList<>();
		col.add(object);
		return col;
	}

	public String getDescription() {
		return "Set as " + reference.getName();
	}

	public String getLabel() {
		return "Set as " + reference.getName();
	}

	public Collection<?> getResult() {
		return null;
	}

	public void redo() {
		
	}

	public void undo() {
		object.eSet(reference, oldValue);
	}
}
