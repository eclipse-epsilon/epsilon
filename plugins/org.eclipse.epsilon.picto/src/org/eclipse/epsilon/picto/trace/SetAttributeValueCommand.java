package org.eclipse.epsilon.picto.trace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

public class SetAttributeValueCommand implements Command {
	
	protected String newValue;
	protected Object oldValue;
	protected EObject object;
	protected EAttribute attribute;
	
	public SetAttributeValueCommand(EObject object, String attribute, String newValue) {
		this.newValue = newValue;
		this.object = object;
		this.attribute = object.eClass().getEAllAttributes().stream().filter(a -> a.getName().equals(attribute)).findFirst().orElse(null);
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
		oldValue = object.eGet(attribute);
		object.eSet(attribute, newValue);
	}

	public Collection<?> getAffectedObjects() {
		List<EObject> col = new ArrayList<>();
		col.add(object);
		return col;
	}

	public String getDescription() {
		return "Set as " + attribute.getName();
	}

	public String getLabel() {
		return "Set as " + attribute.getName();
	}

	public Collection<?> getResult() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		object.eSet(attribute, oldValue);
	}
}
