/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.eml;

import java.io.File;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.IEmlModule;

public class EmlStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		
		PlainXmlModel catalogue1 = new PlainXmlModel();
		catalogue1.setName("Catalogue1");
		catalogue1.getAliases().add("In");
		catalogue1.setFile(new File("models", "catalogue1.xml"));
		catalogue1.load();
	
		PlainXmlModel catalogue2 = new PlainXmlModel();
		catalogue2.setName("Catalogue2");
		catalogue2.getAliases().add("In");
		catalogue2.setFile(new File("models", "catalogue2.xml"));
		catalogue2.load();
		
		PlainXmlModel merged = new PlainXmlModel();
		merged.setName("Merged");
		merged.setReadOnLoad(false);
		merged.load();
		
		IEclModule eclModule = new EclModule();
		eclModule.parse(EmlStandaloneExample.class.getResource("/eml/catalogues.ecl").toURI());
		eclModule.getContext().getModelRepository().addModels(catalogue1, catalogue2);
		eclModule.execute();
		
		IEmlModule emlModule = new EmlModule();
		emlModule.parse(EmlStandaloneExample.class.getResource("/eml/catalogues.eml").toURI());
		emlModule.getContext().getModelRepository().addModels(catalogue1, catalogue2, merged);
		emlModule.getContext().setMatchTrace(eclModule.getContext().getMatchTrace().getReduced());
		emlModule.execute();
		
		System.out.println(merged.getXml());
	}

}
