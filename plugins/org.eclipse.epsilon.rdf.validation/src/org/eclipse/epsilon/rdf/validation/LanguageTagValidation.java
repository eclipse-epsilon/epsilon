/********************************************************************************
 * Copyright (c) 2024 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.validation;

import java.util.Locale;

public class LanguageTagValidation {
	
	public static boolean isValidLanguageTag (String bcp47tag) {
		boolean isValidBCP47 = !("und".equals(Locale.forLanguageTag(bcp47tag).toLanguageTag()));
		return isValidBCP47;
	}

}
