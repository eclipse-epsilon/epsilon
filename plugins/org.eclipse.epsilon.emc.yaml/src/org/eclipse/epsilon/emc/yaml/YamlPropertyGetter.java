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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;

public class YamlPropertyGetter extends JavaPropertyGetter {

	protected YamlModel model;
	
	public YamlPropertyGetter(YamlModel model) {
		this.model = model;
	}
	
	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		synchronized (this.model) {
			if ((object instanceof Entry) || (object instanceof Map) || (object instanceof List)) {
				Object yamlContent = object;
				if (object instanceof Entry) {
					yamlContent = ((Entry)object).getValue();		
					if(property.endsWith("value")) {
						return YamlTypeConverter.getValue(yamlContent, property);
					} 
					else if(property.equals("name")) {
						return ((Entry)object).getKey();
					}
					else if(property.equals("type")) {
						return YamlNodeUtility.getNodeType((Entry)object);
					}
				}
				YamlProperty yamlProperty = YamlProperty.parse(property, 2);	
				Collection<Entry> queryResult = YamlNodeUtility.getNodes(yamlContent, yamlProperty, false);			
				return YamlNodeUtility.getQueryResult(queryResult, yamlProperty);		
			}
			else {
				return super.invoke(object, property, context);
			}
		}
	}
}