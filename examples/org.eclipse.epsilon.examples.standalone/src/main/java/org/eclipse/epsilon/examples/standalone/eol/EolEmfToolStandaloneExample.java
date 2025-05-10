/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.eol;

import org.eclipse.epsilon.eol.EolModule;

/**
 * This example demonstrates instantiating
 * EmfTool using EOL in a standalone manner.
 * @author Dimitrios Kolovos
 */
public class EolEmfToolStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		EolModule module = new EolModule();
		module.parse(EolEmfToolStandaloneExample.class.getResource("/eol/EmfTool.eol"));
		module.execute();
	}
	
}
