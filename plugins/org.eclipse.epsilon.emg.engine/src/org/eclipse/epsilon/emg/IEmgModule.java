/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg;

import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.epl.IEplModule;

public interface IEmgModule extends IEplModule {

	void setSeed(long seed);

	void setUseSeed(boolean useSeed);

	Map<String, List<Object>> getNamedCreatedObjects();

}
