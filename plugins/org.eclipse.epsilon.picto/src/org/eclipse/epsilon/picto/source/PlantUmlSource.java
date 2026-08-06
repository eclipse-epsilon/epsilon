/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.source;

public class PlantUmlSource extends VerbatimSource {
	
	@Override
	public String getFormat() {
		return "plantuml";
	}

	@Override
	public String getFileExtension() {
		return "puml";
	}

}
