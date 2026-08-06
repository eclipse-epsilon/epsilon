/*******************************************************************************
 * Copyright (c) 2022 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Ionut Predoaia - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.yaml;

import java.util.List;
import java.util.Map.Entry;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;

public class YamlPropertySetter extends AbstractPropertySetter {

	protected YamlModel model;
	
	public YamlPropertySetter(YamlModel model) {
		this.model = model;
	}
	
	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {	
		synchronized (this.model) {
			if("value".equals(property)) {
				if (object instanceof Entry) {
					setValue((Entry)object, value);
				}
				else if(object instanceof List) {	
					setValue((List)object, value);
				}	
				else {
					throw new EolIllegalPropertyException(object, property, context);
				}
			}
			else {
				super.invoke(object, property, context);
			}
		}
	}
	
	private void setValue(Entry node, Object value) {
		Object newValue = (value instanceof Entry) ? ((Entry)value).getValue() : value;
		if(YamlProperty.PROPERTY_ROOT.equals(node.getKey())) {
			this.model.setYamlContent(newValue);
		}
		node.setValue(newValue);
	}
	
	private void setValue(List listNode, Object value) {
		for(Entry node: (List<Entry>) listNode) {
			setValue(node, value);
		}
	}
}