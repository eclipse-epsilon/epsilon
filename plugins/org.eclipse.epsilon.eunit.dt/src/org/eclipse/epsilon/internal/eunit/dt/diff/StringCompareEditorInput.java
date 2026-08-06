/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.internal.eunit.dt.diff;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

/**
 * Extremely simple CompareEditorInput implementation which compares two Java strings.
 * Created using the tips in
 * <a href="http://www.eclipse.org/forums/index.php?&t=msg&th=103960">this forum post</a>.
 *
 * @author Antonio García-Domínguez
 */
public class StringCompareEditorInput extends CompareEditorInput {

	private final String left;
	private final String right;

	private final class StringTypedElement implements ITypedElement, IStreamContentAccessor {
		private String contents;

		public StringTypedElement(String contents) {
			this.contents = contents;
		}

		@Override
		public String getName() {
			return "no name";
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public String getType() {
			return "String";
		}

		@Override
		public InputStream getContents() throws CoreException {
			return new ByteArrayInputStream(contents.getBytes());
		}
	}

	public StringCompareEditorInput(CompareConfiguration configuration, String obtained, String expected) {
		super(configuration);
		this.left = obtained;
		this.right = expected;
	}

	@Override
	protected Object prepareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException
	{
		return new DiffNode(
			new StringTypedElement(left),
			new StringTypedElement(right));
	}
}
