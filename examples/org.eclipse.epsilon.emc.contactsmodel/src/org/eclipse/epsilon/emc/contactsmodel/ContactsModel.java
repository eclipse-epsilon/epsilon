/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.contactsmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.ISearchableModel;
import org.eclipse.epsilon.eol.models.Model;
import org.eclipse.epsilon.eol.parse.EolParser;

public class ContactsModel extends Model implements ISearchableModel {
	
	public static void main(String[] args) throws Exception {
		ContactsModel model = new ContactsModel();
		model.setName("M");
		model.addContact(new Contact("John", "York"));
		model.addContact(new Contact("Nick", "York"));
		model.addContact(new Contact("Tim", "Leeds"));
		
		EolModule module = new EolModule();
		module.parse("M.find(c:Contact|c.area = 'York').size().println();");
		module.getContext().getModelRepository().addModel(model);
		module.execute();
	}
	
	protected List<Contact> contacts = new ArrayList<>();
	protected HashMap<String, List<Contact>> contactsByArea = new HashMap<>();
	
	public void addContact(Contact contact) {
		contacts.add(contact);
		List<Contact> contactsInArea = contactsByArea.get(contact.getArea());
		if (contactsInArea == null) {
			contactsInArea = new ArrayList<>();
			contactsByArea.put(contact.getArea(), contactsInArea);
		}
		contactsInArea.add(contact);
	}
	
	@Override
	public boolean owns(Object instance) {
		return instance instanceof Contact;
	}

	@Override
	public boolean hasType(String type) {
		return "Contact".equals(type);
	}
	
	@Override
	public Collection<?> find(Variable iterator, AST ast, IEolContext context) throws EolRuntimeException {
		
		String iteratorName = iterator.getName();
		
		if (ast.getText().equals("=") && ast.getType() == EolParser.OPERATOR && ast.getChildCount() == 2) {
			
			AST pointAst = ast.getFirstChild();
			
			if (pointAst.getType() == EolParser.POINT) {
				
				if (pointAst.getFirstChild().getText().equals(iteratorName) && 
					pointAst.getFirstChild().getNextSibling().getText().equals("area")) {
					
					String area = context.getExecutorFactory().executeAST(pointAst.getNextSibling(), context) + "";
					return contactsByArea.get(area);
				}
				
			}
		}
		
		throw new EolRuntimeException("Only queries of the form find(c:Contact | c.area = <value>) are supported in this driver.");
	}
	
	@Override
	public Object findOne(Variable iterator, ModuleElement ast, IEolContext context)
			throws EolRuntimeException {
		
		List<?> results = (List<?>) find(iterator, ast, context).iterator().next();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public void load() throws EolModelLoadingException {
		
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return null;
	}

	@Override
	public Collection<?> allContents() {
		return null;
	}

	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		return null;
	}

	@Override
	public Collection<?> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
		return null;
	}

	@Override
	public Object getTypeOf(Object instance) {
		return null;
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return null;
	}

	@Override
	public Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		return null;
	}

	@Override
	public Object getElementById(String id) {
		return null;
	}

	@Override
	public String getElementId(Object instance) {
		return null;
	}

	@Override
	public void setElementId(Object instance, String newId) {
		
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		
	}

	@Override
	public boolean isInstantiable(String type) {
		return false;
	}

	@Override
	public boolean isModelElement(Object instance) {
		return false;
	}

	@Override
	public boolean store(String location) {
		return false;
	}

	@Override
	public boolean store() {
		return false;
	}
}
