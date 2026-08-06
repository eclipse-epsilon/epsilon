/*******************************************************************************
 * Copyright (c) 2008-2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - tree structure, time reporting
 ******************************************************************************/
package org.eclipse.epsilon.eunit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;

public class EUnitTest {
	
	private Operation operation;
	private EUnitTestResultType result = EUnitTestResultType.NOT_RUN_YET;
	private Exception exception;

	private EUnitTest parent;
	private List<EUnitTest> children = new ArrayList<>();

	// Data/model bindings
	private String dataVariable;
	private Object dataValue;
	private ModelBindings modelBindings;

	public final static long UNSET_TIME = -1;
	private long startCpuTime = UNSET_TIME;
	private long endCpuTime = UNSET_TIME;
	private long startWallclockTime = UNSET_TIME;
	private long endWallclockTime = UNSET_TIME;

	private FrameStack frameStack;

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public EUnitTestResultType getResult() {
		return result;
	}
	
	public void setResult(EUnitTestResultType result) {
		this.result = result;
	}

	public Exception getException() {
		if (exception != null) {
			return exception;
		}

		// Look in the children for an exception if this node does not have one
		for (EUnitTest child : children) {
			Exception ex = child.getException();
			if (ex != null) {
				return ex;
			}
		}

		// No child had an exception, and we don't have one either: return null
		return null;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * Returns the exception for this test case, removing all wrapping
	 * EolInternalExceptions that the Epsilon PointExecutor may have added.
	 */
	public Throwable getUnwrappedInternalException() {
		Throwable currentEx = getException();
		while (currentEx instanceof EolInternalException) {
			currentEx = ((EolInternalException) currentEx).getInternal();
		}
		return currentEx;
	}

	public EUnitTest getParent() {
		return parent;
	}

	public void setParent(EUnitTest parent) {
		this.parent = parent;
	}

	public String getDataVariableName() {
		return dataVariable;
	}

	public void setDataVariableName(String dataVariable) {
		this.dataVariable = dataVariable;
	}

	public Object getDataValue() {
		return dataValue;
	}

	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}

	public List<EUnitTest> getChildren() {
		return children;
	}

	public void addChildren(EUnitTest child) {
		children.add(child);
	}

	/**
	 * Sets the start time of the test, measured in nanoseconds of CPU time of the thread running the test.
	 */
	public void setStartCpuTime(long startCpuTime) {
		this.startCpuTime = startCpuTime;
	}

	/**
	 * Gets the start time of the test, measured in nanoseconds of CPU time of the thread running the test.
	 */
	public long getStartCpuTime() {
		return startCpuTime;
	}

	/**
	 * Gets the end time of the test, measured in nanoseconds of CPU time of the thread running the test.
	 */
	public void setEndCpuTime(long endCpuTime) {
		this.endCpuTime = endCpuTime;
	}

	/**
	 * Gets the end time of the test, measured in nanoseconds of CPU time of the thread running the test.
	 */
	public long getEndCpuTime() {
		return endCpuTime;
	}

	/**
	 * Gets the CPU time used by the test, in milliseconds. CPU time includes both user and system operations,
	 * and is much more stable than wallclock time in regards to system load.
	 */
	public long getCpuTimeMillis() {
		return (getEndCpuTime() - getStartCpuTime())/1000000;
	}

	/**
	 * Gets the start time of the test, as returned by {@link System#currentTimeMillis()}.
	 */
	public long getStartWallclockTime() {
		return startWallclockTime;
	}

	/**
	 * Sets the start time of the test, as returned by {@link System#currentTimeMillis()}.
	 */
	public void setStartWallclockTime(long startWallclockTime) {
		this.startWallclockTime = startWallclockTime;
	}

	/**
	 * Gets the end time of the test, as returned by {@link System#currentTimeMillis()}.
	 */
	public long getEndWallclockTime() {
		return endWallclockTime;
	}

	/**
	 * Sets the end time of the test, as returned by {@link System#currentTimeMillis()}.
	 */
	public void setEndWallclockTime(long endWallclockTime) {
		this.endWallclockTime = endWallclockTime;
	}

	/**
	 * Gets the wallclock time used by the test, in milliseconds.
	 */
	public long getWallclockTimeMillis() {
		return getEndWallclockTime() - getStartWallclockTime();
	}

	/**
	 * Stores the frame stack storing the state of the EOL program when the error or failure
	 * happened. This method should receive a copy of the original, so it won't be modified by
	 * the following tests.
	 */
	public void setFrameStack(FrameStack frameStack) {
		this.frameStack = frameStack;
	}

	/**
	 * Returns the frame stack with the state where the test failed.
	 */
	public FrameStack getFrameStack() {
		return frameStack;
	}

	/**
	 * Returns the position of this node among its siblings: 1 if it is the
	 * first one, 2 if it is the second one, and so on. Returns -1 if this
	 * is the root test.
	 */
	public int getPosition() {
		if (isRootTest()) return -1;

		int position = 1;
		for (EUnitTest sibling : getParent().getChildren()) {
			if (sibling == this) break;
			++position;
		}
		return position;
	}

	/**
	 * Returns the name of the operation, or <code>root</code> if this is the root of the test tree.
	 */
	public String getOperationName() {
		return getParent() == null ? "root" : getOperation().getName();
	}

	/**
	 * Returns the name of the test case. Includes the name of the operation and all
	 * bindings performed, in a format suited for humans "myOp (x = 1)".
	 */
	public String getCaseName() {
		final String sBindings = explainAllBindings();
		if (sBindings != null && sBindings.length() > 0) {
			return getOperationName() + " (" + sBindings + ")";
		}
		else {
			return getOperationName();
		}
	}

	/**
	 * Returns the name of the test case as it if were a JUnit method. Uses the
	 * [n] suffixes to indicate the position among the data and model bindings.
	 * For example, "myOp[1][2]" for the myOp operation, using the
	 * first value returned by the first <code>@data</code> operation and the
	 * second value returned by the second <code>@data</code> operation.
	 *
	 * Note: test cases with only one model binding do not get [1] at the end,
	 * to clean up the output a bit.
	 */
	public String getMethodName() {
		StringBuilder sBuilder = new StringBuilder(getOperationName());

		final List<EUnitTest> dataBindings = getAllBindings();
		for (EUnitTest binding : dataBindings) {
			if (binding.getModelBindings() != null && !binding.getOperationName().equals(binding.getParent().getOperationName())) {
				// Node representing a test case with only one model binding: do not produce [n]
				continue;
			}
			sBuilder.append('[');
			sBuilder.append(binding.getPosition());
			sBuilder.append(']');
		}
		return sBuilder.toString();
	}

	/**
	 * Returns a string explaining the bindings (for data and for models) used
	 * in this node. If no bindings have been used, returns the empty string.
	 */
	public String explainBinding() {
		if (getDataVariableName() == null && getModelBindings() == null) {
			return "";
		}

		StringBuffer sbuf = new StringBuffer();
		boolean bFirst = true;

		if (getModelBindings() != null) {
			for (Map.Entry<String, String> entry : getModelBindings().getMappings().entrySet()) {
				if (bFirst) {
					bFirst = false;
				}
				else {
					sbuf.append(", ");
				}
				sbuf.append("'");
				sbuf.append(entry.getKey());
				sbuf.append("' is '");
				sbuf.append(entry.getValue());
				sbuf.append("'");
			}
		}

		if (getDataVariableName() != null) {
			if (!bFirst) {
				sbuf.append(", ");
			}
			sbuf.append(getDataVariableName());
			sbuf.append(" = ");

			final String sValue = getDataValue().toString();
			sbuf.append(sValue);
		}

		return sbuf.toString();
	}

	/**
	 * Returns a string explaining all the data/model bindings used in this
	 * node, including those done in its ancestors. If no bindings have been
	 * used, returns the empty string.
	 */
	public String explainAllBindings() {
		List<EUnitTest> lBindings = getAllBindings();

		StringBuffer sbuf = new StringBuffer();
		boolean bFirst = true;
		for (EUnitTest t : lBindings) {
			final String s = t.explainBinding();
			if (bFirst) {
				bFirst = false;
			} else {
				sbuf.append(", ");
			}
			sbuf.append(s);
		}
		return sbuf.toString();
	}

	/**
	 * Returns a list of all ancestor test cases (excluding this one) with a binding.
	 */
	public List<EUnitTest> getAllBindings() {
		List<EUnitTest> lBindings = new LinkedList<>();
		for (EUnitTest t = this; t != null; t = t.getParent()) {
			if (t.getDataVariableName() == null && t.getModelBindings() == null) {
				continue;
			}
			lBindings.add(0, t);
		}
		return lBindings;
	}

	/**
	 * Returns the name of this test as if it was a JUnit test method, adding
	 * the specified package at the beginning.
	 * @param packageName Package name (as set in the EUnitModule).
	 */
	public String getQualifiedName(String packageName) {
		if (getOperation() == null) {
			// The root node in a test tree has no operation
			return packageName + ".(root)";
		}

		// Remove the file extension
		String filename = EUnitModule.getBasename(getOperation());
		final int lastDot = filename.lastIndexOf('.');
		if (lastDot != -1) {
			filename = filename.substring(0, lastDot);
		}

		return packageName + "." + filename;
	}

	public String toString() {
		StringBuilder sbuf = new StringBuilder(getOperationName());

		// data binding
		if (getDataVariableName() != null) {
			sbuf.append(" (");
			sbuf.append(explainBinding());
			sbuf.append(")");
		}

		if (getEndWallclockTime() != UNSET_TIME) {
			sbuf.append(" [");
			sbuf.append(getWallclockTimeMillis());
			sbuf.append("ms]");
		}

		return sbuf.toString();
	}

	/**
	 * Returns the number of leaf tests contained in this node. Returns 1 for leaf nodes,
	 * and 1 or more for inner nodes. Takes test case filtering into account.
	 *
	 * @see #collectLeafTests(List, EUnitTestResultType)
	 */
	@SuppressWarnings("rawtypes")
	public int countLeafTests(List selectedOperations) {
		return countLeafTests(selectedOperations, null);
	}

	/**
	 * Returns the number of leaf tests contained in this node with results of a
	 * particular type. Returns 1 for leaf nodes, and 1 or more for inner nodes.
	 * Takes test case filtering into account.
	 *
	 * @see #collectLeafTests(List, EUnitTestResultType)
	 */
	@SuppressWarnings("rawtypes")
	public int countLeafTests(List selectedOperations, EUnitTestResultType resultFilter) {
		return collectLeafTests(selectedOperations, resultFilter).size();
	}

	/**
	 * Collects the leaf tests contained in this node with results of a particular type
	 * into a new list. Takes test filtering into account.
	 *
	 * @see #collectLeafTests(List, EUnitTestResultType, Collection)
	 */
	@SuppressWarnings("rawtypes")
	public List<EUnitTest> collectLeafTests(List selectedOperations, EUnitTestResultType resultFilter) {
		final List<EUnitTest> tests = new ArrayList<>();
		collectLeafTests(selectedOperations, resultFilter, tests);
		return tests;
	}

	/**
	 * Collects the leaf tests contained in this node with results of a particular type
	 * into the provided collection. Takes test filtering into account.
	 */
	@SuppressWarnings("rawtypes")
	public void collectLeafTests(List selectedOperations, EUnitTestResultType resultFilter, Collection<EUnitTest> collection) {
		if (!isSelected(selectedOperations)) {
			return;
		}

		if (isLeafTest()) {
			if (resultFilter == null || resultFilter.equals(getResult())) {
				collection.add(this);
			}
		}
		else {
			for (EUnitTest child : getChildren()) {
				child.collectLeafTests(selectedOperations, resultFilter, collection);
			}
		}
	}

	/**
	 * Returns <code>true</code> if this test case should be run with a certain list
	 * of selected operations to be tested.
	 */
	@SuppressWarnings("rawtypes")
	public boolean isSelected(List selectedOperations) {
		return selectedOperations == null
			|| selectedOperations.isEmpty()
			|| getOperationName() == null
			|| selectedOperations.contains(getOperationName());
	}

	/**
	 * Returns <code>true</code> iff this test has no children.
	 */
	public boolean isLeafTest() {
		return getChildren().isEmpty();
	}

	/**
	 * Returns <code>true</code> iff this test is the root of the test tree.
	 */
	public boolean isRootTest() {
		return getParent() == null;
	}

	/* MODEL->TEST BINDINGS */

	/**
	 * Changes the set of model bindings for this test. It is a sequence of strings
	 * with an even number of elements: each consecutive pair of elements A and B
	 * indicates that model A will be an alias for model B. Therefore, if we wanted
	 * to set the default model as an alias for model X, we would use
	 * 'Sequence {"", "X"}'.
	 * @throws EolRuntimeException The map includes a key or a value that is not a String.
	 */
	public synchronized void setModelBindings(ModelBindings mb) throws EolRuntimeException {
		this.modelBindings = mb;
	}

	/**
	 * Returns the model bindings for this test. By default, no model bindings
	 * are used, and this method returns <code>null</code>. This map cannot be
	 * changed: if you want to make changes to it, you'll need to make a copy.
	 * Trying to change it will throw an {@link UnsupportedOperationException}.
	 */
	public synchronized ModelBindings getModelBindings() {
		return modelBindings;
	}
}
