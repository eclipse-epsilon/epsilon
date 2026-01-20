/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.prettyprinting;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.execute.introspection.IUndefined;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolMap;
import org.eclipse.epsilon.eol.types.EolNoType.EolNoTypeInstance;

public class DefaultPrettyPrinter implements PrettyPrinter {
	
	protected PrettyPrinterManager manager = null;
	protected int maximumCollectionSize = 100;
	
	public DefaultPrettyPrinter(PrettyPrinterManager manager) {
		this.manager = manager;
	}
	
	public boolean appliesTo(Object o) {
		return true;
	}

	public String print(Object o) {
		
		if (o instanceof Collection) {
			
			Collection<?> c = (Collection<?>) o;
			String result = EolCollectionType.getTypeName(c) + " {";
			
			if (c.size() > maximumCollectionSize) {
				result += " ... large collection with more than " + maximumCollectionSize + " elements ... ";
			}
			else {
				Iterator<?> li = c.iterator();
				
				while (li.hasNext()) {
					Object next = li.next();
					result += quoteOrPrint(next);
					if (li.hasNext()) {
						result = result + ", ";
					}
				}
			}
			result = result + "}";
			
			return result;
		}
		else if (o instanceof EolMap) {
			String simpleClassName = o.getClass().getSimpleName();
			String result = simpleClassName.substring(3, simpleClassName.length()) + " {";
			Iterator<?> li = ((EolMap<?, ?>) o).keySet().iterator();
			
			while (li.hasNext()) {
				Object key = li.next();
				Object value = ((EolMap<?, ?>) o).get(key);
				result = result + quoteOrPrint(key) + "->" + quoteOrPrint(value);
				if (li.hasNext()) {
					result = result + ", ";
				}
			}
			result = result + "}";
			
			return result;
		}
		else if (o instanceof IUndefined || o instanceof EolNoTypeInstance) {
			return "";
		}
		
		return StringUtil.toString(o);
	}

	public int getMaximumCollectionSize() {
		return maximumCollectionSize;
	}
	
	public void setMaximumCollectionSize(int maximumCollectionSize) {
		this.maximumCollectionSize = maximumCollectionSize;
	}

	protected String quoteOrPrint(Object o) {
		/*
		 * Strings in collections should be quoted (to distinguish empty collection from
		 * collection with empty string).
		 */
		if (o instanceof String) {
			return '"' + (String)o + '"';
		} else {
			return manager.print(o);
		}
	}
}
