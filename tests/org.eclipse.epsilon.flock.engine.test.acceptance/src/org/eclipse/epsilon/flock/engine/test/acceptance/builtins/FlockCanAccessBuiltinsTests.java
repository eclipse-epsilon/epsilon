/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.builtins;

import java.io.File;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfModelFactory;
import org.eclipse.epsilon.emc.emf.EmfModelFactory.AccessMode;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.IFlockModule;
import org.eclipse.epsilon.test.builtins.CanAccessBuiltinsTests;

public class FlockCanAccessBuiltinsTests extends CanAccessBuiltinsTests {

	@Override
	protected IEolModule createModule() throws Exception {
		final IFlockModule module = new FlockModule();
		module.getContext().getModelRepository().addModels(createOriginalModel(), createMigratedModel());
		return module;
	}

	private EmfModel createOriginalModel() throws Exception {
		return EmfModelFactory.getInstance().loadEmfModel("Original",
			FileUtil.getFileStandalone("Original.ecore", FlockCanAccessBuiltinsTests.class), EcorePackage.eINSTANCE, AccessMode.READ_ONLY
		);
	}

	private InMemoryEmfModel createMigratedModel() {
		return new InMemoryEmfModel("Migrated", EmfUtil.createResource(), EcorePackage.eINSTANCE);
	}
	
	@Override
	protected File getProgram() throws Exception {
		return FileUtil.getFileStandalone(("System.mig"), FlockCanAccessBuiltinsTests.class);
	}

	@Override
	protected String getExpectedPrintedValue() {
		return "Running Flock";
	}
}
