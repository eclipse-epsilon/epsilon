/*******************************************************************************
 * Copyright (c) 2008-2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - rewrite with nested Ant task support
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolClasspathNativeTypeDelegate;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestListener;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.eclipse.epsilon.eunit.extensions.IModelComparator;
import org.eclipse.epsilon.eunit.operations.EUnitFailedModelComparisonException;
import org.eclipse.epsilon.workflow.tasks.hosts.HostManager;

/**
 * Ant task for running EUnit test suites.
 *
 * @author Antonio Garcia-Dominguez
 * @version 1.0
 */
public class EUnitTask extends ExecutableModuleTask implements EUnitTestListener {

	private static final String DEFAULT_EUNIT_MODEL_CLONE_DIR = "eunit-temp";

	/**
	 * Class for a nested element which simply contains tasks.
	 */
	public class TaskCollection implements TaskContainer {
		private List<Task> tasks = new ArrayList<>();

		@Override
		public void addTask(Task task) {
			tasks.add(task);
			if (task instanceof ExecutableModuleTask) {
				ExecutableModuleTask moduleTask = (ExecutableModuleTask)task;

				// The gui attribute of the EUnit Ant task is inherited by all nested tasks
				moduleTask.setGUI(isGUI());
			}
		}

		public void run() {
			// We trick tasks into using the EUnit model repository instead of the project's
			for (Task task : tasks) {
				task.perform();
			}
		}
	}

	public class ComparatorReference {
		private String className;

		public String getClassname() {
			return className;
		}

		public void setClassname(String klass) {
			this.className = klass;
		}
	}

	/**
	 * Class for a nested element which allows to specify a list of custom comparators,
	 * which take precedence over any registered OSGi extensions.
	 */
	public class ComparatorReferenceList {
		private final List<ComparatorReference> entries = new ArrayList<>();
		
		public ComparatorReference createComparator() {
			ComparatorReference ref = new ComparatorReference();
			entries.add(ref);
			return ref;
		}

		public List<ComparatorReference> getEntries() {
			return entries;
		}
	}

	public class RunTargetOperationContributor extends OperationContributor {
		@Override
		public boolean contributesTo(Object target) {
			return EolNoType.NoInstance.equals(target);
		}

		/**
		 * Operation which can call a series of Ant tasks described inside a
		 * "script" nested element of this Ant task.
		 */
		public void runTarget(String targetName) throws EolRuntimeException {
			// Check that the name of the target is not null
			if (targetName == null) {
				throw new EolRuntimeException("The name of the target to be run cannot be null");
			}

			// Run tasks, ensuring they manipulate our model repository instead of the project's
			getProject().executeTarget(targetName);
		}

		/**
		 * EUnit-specific operation which is equivalent to the "exports" nested element.
		 */
		public void exportVariable(String varName) {
			EUnitTask.this.exportVariable(varName, varName, false, false);
		}

		/**
		 * EUnit-specific operation which is equivalent to the "imports" nested element.
		 */
		public void useVariable(String varName) {
			EUnitTask.this.useVariable(varName, varName, false, false);
		}

		/**
		 * EUnit-specific operation for loading models inside the .eunit file
		 * from HUTN fragments.
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void loadHutn(String modelName, String hutnContent) throws EolModelLoadingException {
			IModel hutnModel = null;
			try {
				final Class hutnModelClass = Class.forName("org.eclipse.epsilon.emc.hutn.HutnModel");
				final Constructor constructor = hutnModelClass.getConstructor(String.class, String.class);
				hutnModel = (IModel)constructor.newInstance(modelName, hutnContent);
				hutnModel.load();
				module.getContext().getModelRepository().addModel(hutnModel);
			}
			catch (Exception ex) {
				throw new EolModelLoadingException(ex, hutnModel);
			}
		}
	}

	private File fReportDirectory;
	private String fPackage = EUnitModule.DEFAULT_PACKAGE;
	private boolean fGenerateReport = true;
	private boolean fSaveModelDeltas = false;
	private TaskCollection modelLoadingTasks;
	private ComparatorReferenceList comparatorRefs;
	private ModelRepository oldProjectRepository;

	public EUnitTask() {
		// By default, the EUnit Ant task disables JFace-based user input,
		// which hinders automated testing
		setGUI(false);
	}

	@Override
	protected void initialize() throws Exception {
		final EUnitModule eunitModule = (EUnitModule)module;
		eunitModule.addTestListener(this);
		eunitModule.setPackage(getPackage());

		if (isReport()) {
			// Generate an XML file, honoring the toDir setting and defaulting to the project basedir
			File reportDir = getToDir() == null ? getProject().getBaseDir() : getToDir();
			eunitModule.setReportDirectory(reportDir);
		} else {
			// Do not generate an XML file
			eunitModule.setReportDirectory(null);
		}

		if (isSaveModelDeltas()) {
			// Keep model clones in a subdirectory of the directory that will have the DELTA-* files
			// (Eclipse-based model browsers require DELTA and all model clones to belong to the workspace)
			final File baseDir = getToDir() == null ? getProject().getBaseDir() : getToDir();
			final File modelCloneDirectory = new File(baseDir, DEFAULT_EUNIT_MODEL_CLONE_DIR);
			eunitModule.setModelCloneDirectory(modelCloneDirectory);
		} else {
			// Use the OS temp folder (throwaway files) for model clones, as long as delta files
			// are not required (system will automatically clean them up in the next reboot).
			eunitModule.setModelCloneDirectory(null);
		}

		HostManager.getHost().addNativeTypeDelegates(eunitModule);
		final List<EUnitTestListener> testListeners = HostManager.getHost().getExtensionsOfType(EUnitTestListener.class);
		for (EUnitTestListener listener : testListeners) {
			eunitModule.addTestListener(listener);
		}
	}

	@Override
	protected void examine() throws Exception {
		final EUnitTest test = ((EUnitModule)createModule()).getSuiteRoot();
		final PrintStream out = module.getContext().getOutputStream();

		out.println("Global result: " + test.getResult());
		if (test.getResult() == EUnitTestResultType.FAILURE || test.getResult() == EUnitTestResultType.ERROR) {
			fail("At least one test case had a failure or an error: " + test.getException().getMessage(), test.getException());
		}
	}

	@Override
	public IEolModule createDefaultModule() {
		// We store the created module, so the EUnit view can call this,
		// register itself as a listener, and then let EUnitTask configure
		// it as usual
		if (module == null) {
			EUnitModule eunitModule = new EUnitModule();
			module = eunitModule;
			final IEolContext context = module.getContext();
			context.getOperationContributorRegistry().add(new RunTargetOperationContributor());
			context.getFrameStack().put(new Variable("antProject", getProject(), new EolAnyType(), true));

			// Replace the default native type delegate (which uses the Eclipse class loader) with
			// one which uses the Ant classpath, as customized by the user
			final ClassLoader classLoaderAnt = getProject().createClassLoader(org.apache.tools.ant.types.Path.systemClasspath);
			context.getNativeTypeDelegates().clear();
			context.getNativeTypeDelegates().add(new EolClasspathNativeTypeDelegate(classLoaderAnt));

			// Add any custom comparators
			if (comparatorRefs != null) {
				for (ComparatorReference cmpRef : comparatorRefs.getEntries()) {
					try {
						final Class<?> klass = Class.forName(cmpRef.getClassname());
						IModelComparator comparator = (IModelComparator) klass.newInstance();
						eunitModule.getCustomComparators().add(comparator);
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						throw new BuildException("Could not load custom comparator " + cmpRef.getClassname(), e);
					}
				}
			}
		}
		return module;
	}

	// TEST LISTENER METHODS

	@Override
	public void beforeCase(EUnitModule module, EUnitTest test) {
		if (test.isRootTest()) {
			// Disable notification through dialogs: it's bad for automated test cases.
			// Use the console instead.
			HostManager.getHost().configureUserInput(module, false);
		}

		if (test.isLeafTest()) {
			try {
				// Dispose all models in this module's model repository, and reload them from the <model> references
				populateModelRepository(true);
			}
			catch (Exception e) {
				fail("Exception while repopulating the model repository", e);
			}

			// We need to trick the other Ant tasks into loading models into this module's repository.
			// These may be run from <modelTasks> or from the @model operations
			oldProjectRepository = getProjectRepository();
			setProjectRepository(module.getContext().getModelRepository());

			// Run the <modelTasks>
			if (modelLoadingTasks != null) {
				modelLoadingTasks.run();
			}
		}
	}

	@Override
	public void afterCase(EUnitModule module, EUnitTest test) {
		// Restore the original model repository for the project after running the test
		if (test.isLeafTest()) {
			setProjectRepository(oldProjectRepository);
			module.getContext().getModelRepository().dispose();
		}

		final PrintStream out = module.getContext().getOutputStream();
		final PrintStream err = module.getContext().getErrorStream();
		final String sMillis = String.format(" [cpu: %d ms, wallclock: %d ms]", test.getCpuTimeMillis(), test.getWallclockTimeMillis());

		final String testDescription = "Test " + test.getMethodName() + " {" + test.explainAllBindings() + "}";
		if (test.getResult() == EUnitTestResultType.SUCCESS) {
			out.println(testDescription + " passed" + sMillis);
		}
		else if (test.getResult() == EUnitTestResultType.SKIPPED){
			out.println(testDescription + " skipped" + sMillis);
		}
		else {
			err.print(testDescription + " failed with status " + test.getResult());
			final Throwable testException = test.getUnwrappedInternalException();
			if (testException != null) {
				if (isSaveModelDeltas() && test.isLeafTest() && testException instanceof EUnitFailedModelComparisonException) {
					saveModelDeltas(module, test, err, testException);
				}
				err.println(": " + testException.getMessage());
			}
			else {
				err.println();
			}
		}
	}

	protected void saveModelDeltas(EUnitModule module, EUnitTest test, final PrintStream err,
			final Throwable testException) {
		EUnitFailedModelComparisonException ex = (EUnitFailedModelComparisonException) testException;
		if (ex.getComparator() != null && ex.getDelta() != null) {
			File fBasename = new File(getToDir() != null ? getToDir() : getProject().getBaseDir(),
				String.format("DELTA-%s.%s.%s", module.getPackage(), module.getClassName(), test.getCaseName())
			);
			try {
				ex.getComparator().saveDeltaToFile(ex.getDelta(), fBasename);
			} catch (IOException e) {
				e.printStackTrace(err);
			}
		}
	}

	// NESTED ELEMENTS

	public TaskCollection createModelTasks() {
		if (modelLoadingTasks == null) {
			modelLoadingTasks = new TaskCollection();
		}
		return modelLoadingTasks;
	}

	public ComparatorReferenceList createComparators() {
		if (comparatorRefs == null) {
			comparatorRefs = new ComparatorReferenceList();
		}
		return comparatorRefs;
	}

	// TEST REPORT METHODS

	/**
	 * Returns the destination directory for the JUnit-like report. By default,
	 * it is the base directory of the Ant project.
	 */
	public File getToDir() {
		return fReportDirectory;
	}

	/**
	 * Changes the destination directory for the JUnit-like report. See {@link #getToDir()} for the default value.
	 */
	public void setToDir(File f) {
		fReportDirectory = f;
	}

	/**
	 * Returns the package in which all tests will be contained. By default, it
	 * is set to {@link EUnitModule#DEFAULT_PACKAGE}.
	 */
	public String getPackage() {
		return fPackage ;
	}

	/**
	 * Changes the package in which all tests will be contained. Empty or null arguments are <b>ignored</b>.
	 */
	public void setPackage(String packageName) {
		fPackage = packageName;
	}

	/**
	 * Returns <code>true</code> if a XML report compatible with the &lt;junit&gt; Ant task should be generated.
	 */
	public boolean isReport() {
		return fGenerateReport;
	}

	/**
	 * Changes whether an XML report compatible with the &lt;junit&gt; Ant task should be generated. By default,
	 * it will be generated.
	 * @param generate If <code>true</code>, the XML report will be generated. Otherwise, it will not be generated.
	 */
	public void setReport(boolean generate) {
		this.fGenerateReport = generate;
	}

	/**
	 * Returns <code>true</code> if any detected model differences will be saved to a file, named after the
	 * test suite package, test suite class, and test name. The extension will depend on the comparator being
	 * used.
	 */
	public boolean isSaveModelDeltas() {
		return fSaveModelDeltas;
	}

	/**
	 * Changes whether model differences will be saved to a file (when set to <code>true</code>) or not (when
	 * set to <code>false</code>).
	 */
	public void setSaveModelDeltas(boolean save) {
		this.fSaveModelDeltas = save;
	}

}
