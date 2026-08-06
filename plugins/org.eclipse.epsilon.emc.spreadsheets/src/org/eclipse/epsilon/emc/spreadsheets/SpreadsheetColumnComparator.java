/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class implements a comparator for SpreadsheetColumn objects, which are
 * ordered by their indices in ascending order.
 * 
 * @author Martins Francis
 */
public class SpreadsheetColumnComparator implements Comparator<SpreadsheetColumn>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(final SpreadsheetColumn c1, final SpreadsheetColumn c2) {
		if (c1.getWorksheet() != c2.getWorksheet()) {
			return c1.getWorksheet().getName().compareTo(c2.getWorksheet().getName());
		}

		if (c1.getIndex() > c2.getIndex()) {
			return 1;
		}
		else if (c1.getIndex() < c2.getIndex()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
