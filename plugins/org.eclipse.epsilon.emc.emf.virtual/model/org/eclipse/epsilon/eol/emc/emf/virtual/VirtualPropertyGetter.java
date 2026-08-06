/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.emc.emf.virtual;

import org.eclipse.epsilon.emc.emf.virtual.Slot;
import org.eclipse.epsilon.emc.emf.virtual.VirtualObject;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;

class VirtualPropertyGetter extends AbstractPropertyGetter {

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		final VirtualObject vObject = (VirtualObject)object;
		for (Slot slot : vObject.getSlots()) {
			if (property.equals(slot.getType())) {
				final Object value = SlotFactory.getInstance().getValueFrom(slot);
				
				return value;
			}
		}
		throw new EolIllegalPropertyException(object, property, context);
	}
}
