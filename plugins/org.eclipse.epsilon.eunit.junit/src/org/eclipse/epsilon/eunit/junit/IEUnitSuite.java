/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/*******************************************************************************
 * Copyright (c) 2012 Antonio Garcia-Dominguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.junit;

import java.net.URI;
import java.util.List;

import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * Base interface that must be implemented by all test suites using the
 * {@link EclipseEUnitTestRunner}.
 */
public interface IEUnitSuite {

	/**
	 * Returns an operation contributor which can provide additional
	 * global functions through its methods. This operation is optional:
	 * if desired, users may simply return <code>null</code> if they do
	 * not need this functionality.
	 *
	 * A custom operation contributor can be useful if we need to run
	 * tasks in another model-handling language, as the Ant tasks are
	 * not easy to use from a JUnit Plug-in Test.
	 */
	OperationContributor getOperationContributor();

	/**
	 * Returns the URI of the main .eunit script to be run.
	 */
	URI getModuleURI() throws Exception;

	/**
	 * Returns the list of the models that should be used for the next
	 * test. These models should be reloaded in every call to this method,
	 * in order to ensure that each EUnit test is isolated from the rest.
	 */
	List<IModel> prepareModels() throws Exception;

}
