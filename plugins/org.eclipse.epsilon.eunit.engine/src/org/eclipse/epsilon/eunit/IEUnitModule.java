/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eunit;

import java.io.File;
import java.util.List;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.extensions.IModelComparator;
import org.eclipse.epsilon.internal.eunit.util.Pair;

public interface IEUnitModule extends IEolModule {

	void runSuite(EUnitTest node) throws EolRuntimeException;

	EUnitTest getSuiteRoot() throws EolRuntimeException;

	boolean isAnnotatedAs(Operation operation, String annotation);

	List<Pair<Operation, String>> getDataVariableNames();

	List<Operation> getSuiteTeardowns();

	List<Operation> getSuiteSetups();

	List<Operation> getTeardowns();

	List<Operation> getSetups();

	List<Operation> getInlineModelOperations();

	List<Operation> getTests();

	List<IModelComparator> getCustomComparators();

	String getQualifiedName();

	void setPackage(String packageName);

	String getPackage();

	File getReportDirectory();

	void setReportDirectory(File reportFile);

	void setSelectedOperations(List<?> attribute) throws EolRuntimeException;

	List<?> getSelectedOperations();

	boolean removeTestListener(EUnitTestListener listener);

	boolean addTestListener(EUnitTestListener listener);

	String getClassName();

	/**
	 * Returns the path to the directory which will store the clones used to freeze models
	 * during comparisons. By default, this will be {@link #DEFAULT_EUNIT_MODEL_CLONE_DIR}
	 * in the current directory. If set to {@code null}, the operating system's system-wide
	 * temporary folder will be used.
	 */
	File getModelCloneDirectory();

	/**
	 * Changes the model clone directory.
	 * @see #getModelCloneDirectory()
	 */
	void setModelCloneDirectory(File modelCloneDirectory);

}
