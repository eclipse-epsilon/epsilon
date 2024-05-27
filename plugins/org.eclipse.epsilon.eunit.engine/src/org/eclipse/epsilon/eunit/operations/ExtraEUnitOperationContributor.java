/*******************************************************************************
 * Copyright (c) 2011 Antonio García-Domínguez
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.exceptions.EolAssertionException;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.IEUnitModule;
import org.eclipse.epsilon.eunit.extensions.IModelComparator;

/**
 * Operation contributor for the EUnit operations which may require external
 * dependencies, or are particularly complex. The simplest operations are in
 * {@link org.eclipse.epsilon.eol.execute.operations.contributors.BasicEUnitOperationContributor}
 * .
 */
public class ExtraEUnitOperationContributor extends OperationContributor {

	// Filenames that should be ignored by assertEqualDirectories
	private static final Set<String> ASSERTEQUALDIRS_IGNORED_FILENAMES;

	static {
		ASSERTEQUALDIRS_IGNORED_FILENAMES = new HashSet<>();

		// By default, ignore hidden Subversion / Git directories 
		ASSERTEQUALDIRS_IGNORED_FILENAMES.add(".svn");
		ASSERTEQUALDIRS_IGNORED_FILENAMES.add(".git");
	}

	@Override
	public boolean contributesTo(Object target) {
		return true;
	}

	public void assertEqualFiles(String pathExpected, String pathActual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, true);
	}
	
	public void assertEqualFilesIgnoringLineEndings(String pathExpected, String pathActual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, true, true);
	}

	public void assertEqualDirectories(String pathExpected, String pathActual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, true);
	}

	public void assertEqualModels(String expectedModelName, String obtainedModelName) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(null, expectedModelName, obtainedModelName, true, new HashMap<String, Object>());
	}

	public void assertEqualModels(String expectedModelName, String obtainedModelName, Map<String, Object> options) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(null, expectedModelName, obtainedModelName, true, options);
	}

	public void assertEqualModels(String message, String expectedModelName, String obtainedModelName) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(message, expectedModelName, obtainedModelName, true, new HashMap<String, Object>());
	}

	public void assertEqualModels(String message, String expectedModelName, String obtainedModelName, Map<String, Object> options) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(message, expectedModelName, obtainedModelName, true, options);
	}

	public void assertNotEqualFiles(String pathExpected, String pathActual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, false);
	}
	
	public void assertNotEqualFilesIgnoringLineEndings(String pathExpected, String pathActual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, false, true);
	}

	public void assertNotEqualDirectories(String pathExpected, String pathActual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, false);
	}

	public void assertNotEqualModels(String expectedModelName, String obtainedModelName) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(null, expectedModelName, obtainedModelName, false, new HashMap<String, Object>());
	}

	public void assertNotEqualModels(String expectedModelName, String obtainedModelName, Map<String, Object> options) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(null, expectedModelName, obtainedModelName, false, options);
	}

	public void assertNotEqualModels(String message, String expectedModelName, String obtainedModelName) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(message, expectedModelName, obtainedModelName, false, new HashMap<String, Object>());
	}

	public void assertNotEqualModels(String message, String expectedModelName, String obtainedModelName, Map<String, Object> options) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		compareModels(message, expectedModelName, obtainedModelName, false, options);
	}

	public void assertMatchingLine(String pathExpected, String regexp) throws EolInternalException {
		assertMatchingLine(null, pathExpected, regexp);
	}

	public void assertMatchingLine(String message, String pathExpected, String regexp) throws EolInternalException {
		if (message == null) {
			message = String.format("No lines matched '%s' from start to finish", regexp);
		}
		assertLineMatchingPredicate(message, pathExpected, regexp, (rgx, ln) -> rgx.matcher(ln).matches());
	}

	public void assertLineWithMatch(String pathExpected, String regexp) throws EolInternalException {
		assertLineWithMatch(null, pathExpected, regexp);
	}

	public void assertLineWithMatch(String message, String pathExpected, String regexp) throws EolInternalException {
		if (message == null) {
			message = String.format("No lines contained a match for '%s'", regexp);
		}
		assertLineMatchingPredicate(message, pathExpected, regexp, (rgx, ln) -> rgx.matcher(ln).find());
	}

	private void assertLineMatchingPredicate(String message, String pathExpected, String regexp, BiPredicate<Pattern, String> predicate) throws EolInternalException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(pathExpected)));) {
			final Pattern regex = Pattern.compile(regexp);

			String line;
			while ((line = reader.readLine()) != null) {
				if (predicate.test(regex, line)) {
					return;
				}
			}

			final ModuleElement ast = getContext().getFrameStack().getCurrentStatement();
			throw new EolAssertionException(
					message != null ? message : String.format("No lines matched '%s' from start to finish", regexp),
					ast, null, null, null);
		}
		catch (Exception ex) {
			throw new EolInternalException(ex);
		}
	}

	private void compareTrees(final String pathExpected, final String pathActual, boolean mustBeEqual) throws EolAssertionException, EolInternalException {
		compareTrees(pathExpected, pathActual, mustBeEqual, false);
	}
	
	private void compareTrees(final String pathExpected, final String pathActual, boolean mustBeEqual, boolean ignoreLineEndings) throws EolAssertionException, EolInternalException {
		File fileExpected = new File(pathExpected);
		File fileActual = new File(pathActual);
		try {
			// Compare and check against results
			FileUtil.checkFileExists(fileExpected);
			FileUtil.checkFileExists(fileActual);
			if (FileUtil.sameContents(fileExpected, fileActual, ASSERTEQUALDIRS_IGNORED_FILENAMES, ignoreLineEndings) == mustBeEqual) {
				return;
			}

			if (mustBeEqual) {
				// If they were expected to be equal and they were different,
				// the user may want to see the differences later. We need
				// to copy the files to a temporary location so they can be
				// safely compared later on.
				fileExpected = FileUtil.copyToTemp(fileExpected);
				fileActual   = FileUtil.copyToTemp(fileActual);
			}
			else {
				// There are no differences: disable the 'Compare' button
				fileExpected = fileActual = null;
			}
		} catch (Exception ex) {
			throw new EolInternalException(ex);
		}

		String message = String.format(
			"Expected %s and %s to be %s, but they weren't",
			pathExpected, pathActual, mustBeEqual ? "equal" : "different");

		// Since the assertion has failed, !mustBeEqual shows whether the
		// trees were equal or not. If they are equal, there is no point
		// in showing differences in the UI.
		final ModuleElement ast = getContext().getFrameStack().getCurrentStatement();
		throw new EolAssertionException(
			message, ast, fileExpected, fileActual, null);
	}

	private IModel getModel(String name) throws EolModelNotFoundException {
		return getContext().getModelRepository().getModelByName(name);
	}

	private File getModelCloneDirectory() {
		if (getContext().getModule() instanceof EUnitModule) {
			EUnitModule module = (EUnitModule) getContext().getModule();
			return module.getModelCloneDirectory();
		}
		return null;
	}

	private void compareModels(String message, String expectedModelName, String actualModelName, boolean mustBeEqual, Map<String, Object> options) throws EolModelNotFoundException, EolAssertionException, EolInternalException {
		final IModel expectedCModel = getModel(expectedModelName);
		final IModel actualCModel = getModel(actualModelName);
		final IModelComparator comparator = getComparator(expectedCModel, actualCModel);
		if (comparator == null) {
			throw new IllegalArgumentException("No matching comparator has been found for " + expectedCModel + " and " + actualCModel);
		}
		if (options == null) {
			options = Collections.singletonMap(IModelComparator.OPTION_MODEL_CLONE_DIRECTORY, getModelCloneDirectory());
		} else {
			options.put(IModelComparator.OPTION_MODEL_CLONE_DIRECTORY, getModelCloneDirectory());
		}
		comparator.configure(options);

		// Compare the models
		Object delta = null;
		final boolean bExpectedEmpty = expectedCModel.allContents().isEmpty();
		final boolean bActualEmpty = actualCModel.allContents().isEmpty();
		try {
			if (!bExpectedEmpty && !bActualEmpty) {
				// We only use the driver-specific comparison if both models are not empty
				delta = comparator.compare(expectedCModel, actualCModel);
			}
			else if (bExpectedEmpty != bActualEmpty) {
				delta = "expected "
					+ (bExpectedEmpty ? "is" : "is not")
					+ " empty, actual "
					+ (bActualEmpty ? "is" : "is not")
					+ " empty";
			}
		} catch (Exception e) {
			throw new EolInternalException(e);
		}

		// Does the comparison result match our expectations?
		if ((delta == null) == mustBeEqual) {
			return;
		}

		if (message == null) {
			if (bExpectedEmpty) {
				message = "Expected " + actualModelName
					+ (mustBeEqual ? " to be also" : " not to be") + " empty, but it is "
					+ (bActualEmpty ? "empty" : "not");
			}
			else {
				message = "Expected " + actualModelName
					+ " to be " + (mustBeEqual ? "equal" : "different") + " to "
					+ expectedModelName + ", but it is " + (bActualEmpty ? "empty" : "not");
			}
		}

		if (mustBeEqual) {
			throw new EUnitFailedModelComparisonException(message.toString(), getContext().getFrameStack().getCurrentStatement(), expectedCModel, actualCModel, delta, comparator);
		}
		else {
			throw new EUnitFailedModelComparisonException(message.toString(), getContext().getFrameStack().getCurrentStatement(), null, null, null, comparator);
		}
	}

	private IModelComparator getComparator(IModel expectedCModel, IModel actualCModel) throws EolInternalException {
		try {
			// Try to use first the custom comparators
			final IModule module = this.getContext().getModule();
			if (module instanceof IEUnitModule) {
				IEUnitModule eunitModule = (IEUnitModule) module;
				for (IModelComparator comparator : eunitModule.getCustomComparators()) {
					if (comparator.canCompare(expectedCModel, actualCModel)) {
						return comparator;
					}
				}
			}

			// Use reflection to avoid static dependency on ClassBasedExtension (we can't use HostManager in epsilon.workflow from here)
			Class<?> klazz = Class.forName("org.eclipse.epsilon.common.dt.extensions.ClassBasedExtension");
			Method getImpl = klazz.getMethod("getImplementations", String.class, Class.class);

			@SuppressWarnings("unchecked")
			List<IModelComparator> comparators = (List<IModelComparator>) getImpl.invoke(null, IModelComparator.EXTENSION_POINT_ID, IModelComparator.class);
			for (IModelComparator comparator : comparators) {
				if (comparator.canCompare(expectedCModel, actualCModel)) {
					return comparator;
				}
			}
		} catch (ClassNotFoundException ex) {
			// do nothing
		} catch (Exception ex) {
			throw new EolInternalException(ex);
		}
		return null;
	}

}
