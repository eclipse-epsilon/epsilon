/*******************************************************************************
 * Copyright (c) 2011 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.extensions;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.epsilon.eol.models.IModel;

/**
 * Interface for a model comparator. They compare entire models and return a set
 * of relevant differences, if any exist. Comparators may only be able to compare
 * certain models, based on various criteria.
 */
public interface IModelComparator {

	/**
	 * If {@link #configure(Map)} is called with this key set to a File or a String,
	 * the model clones needed by the comparator should be created in that directory.
	 */
	public static final String OPTION_MODEL_CLONE_DIRECTORY = "modelCloneDirectory";

	String EXTENSION_POINT_ID = "org.eclipse.epsilon.eunit.engine.comparator";

	/**
	 * Checks if the comparator can compare these two models.
	 * 
	 * @return <code>true</code> if {@link #compare(IModel, IModel)} should
	 *         finish successfully, and <tt>false</tt> otherwise.
	 */
	boolean canCompare(IModel expectedModel, IModel obtainedModel);

	/**
	 * Returns an object with the differences between this model and
	 * <code>otherModel</code>. If there are no differences, returns
	 * <code>null</code>.
	 *
	 * The exact configuration of the comparison depends on the model. For
	 * instance, unique identifiers (such as XMI IDs) may be ignored if the main
	 * object of these comparisons is to test model transformations. These tend
	 * to produce different unique identifiers each time they are run.
	 * 
	 * @throws IllegalArgumentException
	 *             The models cannot be compared: for instance, they use
	 *             incompatible drivers.
	 * @throws Exception
	 *             There was some other kind of problem when performing the
	 *             comparison.
	 */
	Object compare(IModel expectedModel, IModel obtainedModel) throws Exception;

	/**
	 * Configures the model comparator with the specified options. At least
	 * the {@link #OPTION_MODEL_CLONE_DIRECTORY} option should be honored, if
	 * models are being frozen into clones before performing the comparison.
	 * 
	 * @throws IllegalArgumentException
	 *             One of the options has an invalid value is or not available.
	 */
	void configure(Map<String, Object> options);

	/**
	 * Saves the provided delta (as produced by {@link #compare(IModel, IModel)}
	 * to a file with the provided basename. The actual extension will be picked
	 * by this component.
	 *
	 * @param delta Detected model difference (e.g. a Comparison when using EMF Compare).
	 * @param basename File with the desired basename (extension to be picked by comparator).
	 * @throws IOException There was a problem writing to the file.
	 * @return File where the delta has been saved, after adding the extension.
	 */
	default File saveDeltaToFile(Object delta, File basename) throws IOException {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support saving deltas yet");
	}

}
