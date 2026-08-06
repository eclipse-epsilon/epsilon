/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eol.execute.Return;

/**
 * ReturnValueParser.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class ReturnValueParser {

	public static Object obtainValue(Object obj) {
		if (obj instanceof Return) {
			return ((Return) obj).getValue();
		}
		return obj;
	}

	public static List<Object> getValues(List<Object> elements) {
		List<Object> res = new ArrayList<>();
		for (Object elem : elements) {
			res.add(obtainValue(elem));
		}
		return res;
	}
}
