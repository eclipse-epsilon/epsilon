/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.diff.files;

import java.io.File;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.epsilon.eunit.dt.diff.IDifferenceViewer;

/**
 * Difference viewer for file directories.
 */
public class FilesystemTreeDifferenceViewer implements IDifferenceViewer {

	@Override
	public boolean canCompare(Object expected, Object actual, Object delta) {
		return expected instanceof File && actual instanceof File;
	}

	@Override
	public void compare(Object oExpected, Object oActual, Object delta) {
		final File fileExpected = (File)oExpected;
		final File fileActual = (File)oActual;

		// Compare files using the Eclipse differencer
		final FilesystemTreeNode expected = new FilesystemTreeNode(fileExpected);
		final FilesystemTreeNode actual = new FilesystemTreeNode(fileActual);
		final Differencer diffEngine = new Differencer();
		DiffNode diffNode =
			(DiffNode)diffEngine.findDifferences(false, null, null, null, actual, expected);

		// Show the differences in the UI
		CompareConfiguration config = new CompareConfiguration();
		config.setLeftLabel("Obtained");
		config.setRightLabel("Expected");
		config.setLeftEditable(false);
		config.setRightEditable(false);

		CompareUI.openCompareEditor(new FilesystemCompareEditorInput(config, diffNode));
	}

}
