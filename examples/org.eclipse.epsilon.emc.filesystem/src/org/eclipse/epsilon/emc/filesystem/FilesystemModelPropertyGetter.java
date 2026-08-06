/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;

public class FilesystemModelPropertyGetter extends JavaPropertyGetter {
	
	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		
		try {
			if ("contents".equals(property)) {
				return new Scanner((File) object).useDelimiter("\\Z").next();
			}
			else if (property.startsWith("p_")) {
				Properties p = new Properties();
				p.load(new FileInputStream(((File) object)));
				return p.get(property.substring(2));
			}
			else return super.invoke(object, property, context);
		}
		catch (Exception e) {
			throw new EolInternalException(e);
		}
		
	}
	
}
