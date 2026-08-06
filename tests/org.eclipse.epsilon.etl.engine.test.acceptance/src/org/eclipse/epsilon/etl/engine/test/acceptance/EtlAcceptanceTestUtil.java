/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance;

import java.util.Collection;
import java.util.function.Supplier;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.etl.*;
import org.eclipse.epsilon.etl.concurrent.EtlModuleParallel;
import org.eclipse.epsilon.etl.execute.context.concurrent.EtlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EtlAcceptanceTestUtil  extends EolAcceptanceTestUtil {
	protected EtlAcceptanceTestUtil() {}
	
	public static Collection<Supplier<? extends IEtlModule>> modules() {
		return EolAcceptanceTestUtil.parallelModules(THREADS, EtlModule::new,
			t -> new EtlModuleParallel(new EtlContextParallel(t))
		);
	}
}
