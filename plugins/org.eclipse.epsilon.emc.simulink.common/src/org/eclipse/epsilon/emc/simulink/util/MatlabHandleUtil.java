/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;

public class MatlabHandleUtil {
	
	public static Object convert(Object handleObject, MatlabEngine engine, IGenericSimulinkModel owningModel) {
		if (handleObject instanceof HandleObject) {
			return new MatlabHandleElement(owningModel, engine, (HandleObject) handleObject);
		}
		else if (HandleObject.is(handleObject)) {
			return new MatlabHandleElement(owningModel, engine, new HandleObject(handleObject));
		}		
		else if (handleObject instanceof Collection) {
			ArrayList<Object> handleObjects = new ArrayList<>(((Collection<?>) handleObject).size());
			for (Object o : (Iterable<?>) handleObject) {
				handleObjects.add(MatlabHandleUtil.convert(o, engine, owningModel));
			}
			return handleObjects;
		}
		else return handleObject;
	}
}
