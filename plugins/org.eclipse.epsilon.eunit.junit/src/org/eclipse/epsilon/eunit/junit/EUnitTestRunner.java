/*******************************************************************************
 * Copyright (c) 2012-2025 Antonio Garcia-Dominguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.junit;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestListener;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

/**
 * <p>
 * Bridge runner between EUnit and JUnit. Can be used to turn an EUnit script
 * into a JUnit Plug-in Test, for continuous integration.
 * </p>
 * 
 * <p>
 * This runner should only be used with test classes that implement the
 * {@link IEUnitSuite} interface for specifying the URI to the .eunit script and
 * reloading the models between tests.
 * </p>
 * 
 * <p>
 * Unlike previous EUnit test runners that only supported a subset of EUnit,
 * this runner supports every feature in EUnit except for those specific to Ant,
 * such as runTarget, useVariable, exportVariable and loadHutn.
 * </p>
 *
 * <p>
 * Users can provide additional built-in operations for their tests by implementing
 * the {@link IEUnitSuite#getOperationContributor()} method appropriately. This can
 * be useful when invoking EVL code, for instance, as we can't access the Ant tasks
 * from a JUnit plugin test.
 * </p>
 *
 * TODO: integrate the EOL debugger somehow.
 * 
 * TODO: add support for running Ant targets from a previously specified Ant
 * script, in order to reuse the workflow tasks. We could get back most of the
 * missing functionality this way. The main problem is that this would probably
 * require copying over the original project to the runtime workspace, and that
 * could complicate things quite a bit.
 */
public class EUnitTestRunner extends ParentRunner<EUnitTest> {

	protected class JUnitEUnitTestListener implements EUnitTestListener {
		private final RunNotifier notifier;

		private JUnitEUnitTestListener(RunNotifier notifier) {
			this.notifier = notifier;
		}

		@Override
		public void beforeCase(EUnitModule module, EUnitTest test) {
			final Description desc = describeChild(test);
			notifier.fireTestStarted(desc);
			if (test.getResult().equals(EUnitTestResultType.SKIPPED)) {
				notifier.fireTestIgnored(desc);
			}

			if (test.isLeafTest()) {
				final ModelRepository repository = module.getContext().getModelRepository();
				repository.dispose();
				try {
					final List<IModel> models = suiteInstance.prepareModels();
					if (models != null) {
						for (IModel model : models) {
							repository.addModel(model);
						}
					}
				} catch (Exception e) {
					test.setResult(EUnitTestResultType.ERROR);
					test.setException(e);
				}
			}
		}

		@Override
		public void afterCase(EUnitModule module, EUnitTest test) {
			final Description desc = describeChild(test);
			if (test.isLeafTest()) {
				EUnitTestResultType res = test.getResult();
				if (res.equals(EUnitTestResultType.FAILURE) || res.equals(EUnitTestResultType.ERROR))
					notifier.fireTestFailure(new Failure(desc, test.getException()));
			}
			notifier.fireTestFinished(desc);
		}
	}

	private Map<EUnitTest, Description> testDescriptions = new IdentityHashMap<>();
	protected EUnitModule module;
	protected IEUnitSuite suiteInstance;

	public EUnitTestRunner(Class<? extends IEUnitSuite> testClass) throws InitializationError {
		super(testClass);

		try {
			suiteInstance = testClass.newInstance();

			final URI moduleURI = suiteInstance.getModuleURI();
			module = new EUnitModule();
			module.parse(moduleURI);

			final OperationContributor contrib = suiteInstance.getOperationContributor();
			if (contrib != null) {
				module.getContext().getOperationContributorRegistry().add(contrib);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new InitializationError(e);
		}
	}

	@Override
	protected List<EUnitTest> getChildren() {
		try {
			return Arrays.asList(module.getSuiteRoot());
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	protected Description describeChild(EUnitTest child) {
		if (testDescriptions.containsKey(child)) {
			return testDescriptions.get(child);
		}
	
		Description desc;
		if (child.isRootTest()) {
			desc = Description.createSuiteDescription(this.getTestClass().getJavaClass());
		} else {
			desc = Description.createTestDescription(this.getTestClass().getJavaClass(), child.getCaseName());
		}
		for (EUnitTest c : child.getChildren()) {
			desc.addChild(describeChild(c));
		}
		testDescriptions.put(child, desc);
		return desc;
	}

	@Override
	protected void runChild(final EUnitTest child, final RunNotifier notifier) {
		final Description thisDesc = describeChild(child);
	
		/*
		 * We only need to launch the root EUnitTest from JUnit: EUnit will take
		 * care of the rest.
		 */
		if (child.isRootTest()) {
			try {
				final JUnitEUnitTestListener listener = new JUnitEUnitTestListener(notifier);
				module.addTestListener(listener);
				try {
					executeModule();
				} finally {
					module.removeTestListener(listener);
				}
			} catch (EolRuntimeException e) {
				notifier.fireTestFailure(new Failure(thisDesc, e));
			}
		}
	}

	/**
	 * Runs the module once test listeners have been set up. Subclasses may want to
	 * override this method for any other instrumentation (e.g. debugging).
	 */
	protected void executeModule() throws EolRuntimeException {
		module.execute();
	}

}
