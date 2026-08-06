/*******************************************************************************
 * Copyright (c) 2016 Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.examples.testlang.engine;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.types.EolNoType;

public class TestLangModule extends EolModule {

	public class TestLangOperationContributor extends OperationContributor {
		@Override
		public boolean contributesTo(Object target) {
			// We only contribute "global" functions
			return target == EolNoType.NoInstance;
		}

		public void areEqual(Object a, Object b) throws FailedAssertionException {
			if (a != b) {
				if (a == null || b == null || !a.equals(b)) {
					throw new FailedAssertionException("Expected " + a + ", got " + b);
				}
			}
		}
	}

	@Override
	protected void prepareContext() {
		super.prepareContext();
		getContext().getOperationContributorRegistry().add(new TestLangOperationContributor());
	}

	@Override
	public Object execute() throws EolRuntimeException {
		Object ret = super.execute();

		final IEolContext ctx = getContext();
		for (Operation op : getOperations()) {
			if (op.hasAnnotation("test")) {
				runTest(ctx, op);
			}
		}

		return ret;
	}

	protected void runTest(final IEolContext ctx, final Operation op) throws EolRuntimeException {
		try {
			final List<Object> timesValues = op.getAnnotationsValues("times", ctx);
			int nTimes = 1;
			if (!timesValues.isEmpty()) {
				if (timesValues.get(0) instanceof Integer) {
					nTimes = (Integer) timesValues.get(0);
				} else {
					throw new EolRuntimeException("$times should receive an integer");
				}
			}

			for (int i = 0; i < nTimes; i++) {
				op.execute(null, Collections.emptyList(), ctx);
			}

			if (nTimes > 1) {
				getContext().getOutputStream().println(String.format("Test %s PASSED (ran %d times)", op.getName(), nTimes));
			} else {
				getContext().getOutputStream().println(String.format("Test %s PASSED", op.getName(), nTimes));
			}
		} catch (EolRuntimeException ex) {
			if (ex.getCause() instanceof FailedAssertionException) {
				getContext().getErrorStream().println("Test " + op.getName() + " FAILED: " + ex.getMessage());
			} else {
				throw ex;
			}
		}
	}

}
