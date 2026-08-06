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

public class AddReferenceValuesCommand implements Command {
	
	protected EReference reference;
	protected EObject object;
	protected Collection<?> newValues;
	
	public AddReferenceValuesCommand(EObject object, Collection<?> newValues, EReference reference) {
		this.object = object;
		this.newValues = newValues;
		this.reference = reference;
	}
	
	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Command chain(Command arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute() {
		Collection oldValues = (Collection) object.eGet(reference);
		oldValues.addAll(newValues);
	}

	@Override
	public Collection<EObject> getAffectedObjects() {
		List<EObject> col = new ArrayList<>();
		col.add(object);
		return col;
	}

	@Override
	public String getDescription() {
		return "Add to " + reference.getName();
	}

	@Override
	public String getLabel() {
		return "Add to " + reference.getName();
	}

	@Override
	public Collection<?> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void redo() {
		execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void undo() {
		Collection newValues = (Collection) object.eGet(reference);
		newValues.removeAll(this.newValues);
	}

}
