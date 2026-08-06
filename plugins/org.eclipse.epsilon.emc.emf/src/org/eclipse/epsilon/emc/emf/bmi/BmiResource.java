/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.bmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

public class BmiResource extends ResourceImpl {
	
	protected InputStream inputStream = null;
	
	public BmiResource() {
		super();
	}

	public BmiResource(URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		
		this.inputStream = inputStream;
		
		super.getContents();
		
		EPackage eCore = this.getResourceSet().getPackageRegistry().getEPackage(EcorePackage.eNS_URI);
		
		EClass eClass = (EClass) eCore.getEClassifier("EClass");
		
		contents.add(new BmiEObject(eClass));
		
		//eCore.getEFactoryInstance().create(eClass);
		
		//contents.add(new BmiEObject(EcoreFactory.eINSTANCE.createEClass()));
		//contents.add(new BmiEObject(EcoreFactory.eINSTANCE.createEClass()));
		
		// TODO Auto-generated method stub
		// super.doLoad(inputStream, options);
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		
		// TODO Auto-generated method stub
		// super.doSave(outputStream, options);
	}
	
	
	
}
