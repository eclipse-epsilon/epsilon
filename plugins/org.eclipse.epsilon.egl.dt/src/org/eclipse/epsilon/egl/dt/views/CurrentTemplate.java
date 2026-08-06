/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.views;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.egl.traceability.Template;

public final class CurrentTemplate {

	private static final CurrentTemplate instance = new CurrentTemplate();
	
	private Template template;
	private List<CurrentTemplateObserver> observers = new LinkedList<>();
	
	private CurrentTemplate() {}
	
	public static CurrentTemplate getInstance() {
		return instance;
	}
	
	
	public Template getTemplate() {
		return template;
	}
	
	public void setTemplate(Template template) {
		final Template old = this.template;
		this.template = template;
		
		fireChangedEvent(old, template);
	}
	
	
	public void addObserver(CurrentTemplateObserver observer) {
		if (observer==null)
			throw new NullPointerException("observer cannot be null");
		
		observers.add(observer);
	}
	
	public void removeObserver(CurrentTemplateObserver observer) {
		if (observer==null)
			throw new NullPointerException("observer cannot be null");
		
		observers.remove(observer);
	}
	
	private void fireChangedEvent(Template old, Template nw) {
		final CurrentTemplateObserverEvent event = new CurrentTemplateObserverEvent(old, nw);
		
		for (CurrentTemplateObserver observer : observers) {
			observer.templateChanged(event);
		}
	}
}
