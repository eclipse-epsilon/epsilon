/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance.importCaching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.SpecialAssignmentStatement;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.dom.EquivalentAssignmentStatement;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that ensure that import caching works as intended.
 */
public class ImportCachingTests {

	private static File mainETL;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class<?> relClass = ImportCachingTests.class;

		// Load dependencies
		mainETL = FileUtil.getFileStandalone("main.etl", relClass);
		FileUtil.getFileStandalone("b.eol", relClass);
		FileUtil.getFileStandalone("c.eol", relClass);
	}

	@Test
	public void reusedEOLFromETL() throws Exception {
		EtlModule etlModule = new EtlModule();
		etlModule.parse(mainETL);

		assertEquals("main.eol should import 2 modules", 2, etlModule.getImports().size());
		IEolModule bModule = (IEolModule) etlModule.getImports().get(0).getImportedModule();
		IEolModule cModuleFromETL = (IEolModule) etlModule.getImports().get(1).getImportedModule();
		assertEquals("First import in main.eol should be b.eol", "b.eol", bModule.getFile().getName());
		assertEquals("Second import in main.eol should be c.eol", "c.eol", cModuleFromETL.getFile().getName());

		assertEquals("b.eol should import 1 module", 1, bModule.getImports().size());
		IEolModule cModuleFromEOL = (IEolModule) bModule.getImports().get(0).getImportedModule();
		assertSame("main.etl and b.eol should reuse the same instance of c.eol", cModuleFromETL, cModuleFromEOL);

		Map<Class<?>, Set<Object>> nodesByTypeFromEOL = getOperationNodesByType(cModuleFromEOL);
		assertEquals("In an EOL file imported from an EOL file imported by an ETL file, ::= is not a SpecialAssignmentOperator",
			0, nodesByTypeFromEOL.getOrDefault(SpecialAssignmentStatement.class, Collections.emptySet()).size());
		assertEquals("In an EOL file imported from an EOL file imported by an ETL file, ::= is an EquivalentAssignmentOperator",
			1, nodesByTypeFromEOL.getOrDefault(EquivalentAssignmentStatement.class, Collections.emptySet()).size());
	}

	protected Map<Class<?>, Set<Object>> getOperationNodesByType(IEolModule module) {
		Map<Class<?>, Set<Object>> results = new HashMap<>();
		for (Operation op : module.getOperations()) {
			for (Statement st : op.getBody().getStatements()) {
				addNodesByType(results, st);
			}
		}
		return results;
	}

	private void addNodesByType(Map<Class<?>, Set<Object>> results, ModuleElement e) {
		results.computeIfAbsent(e.getClass(), k -> new HashSet<>()).add(e);
		for (ModuleElement child : e.getChildren()) {
			addNodesByType(results, child);
		}
	}

}
