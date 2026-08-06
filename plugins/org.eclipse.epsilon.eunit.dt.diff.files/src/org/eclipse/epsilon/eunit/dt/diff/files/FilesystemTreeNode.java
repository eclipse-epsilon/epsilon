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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;
import org.eclipse.swt.graphics.Image;

/**
 * Node for the difference engine which wraps a regular file.
 */
public class FilesystemTreeNode
	implements IStructureComparator, IModificationDate, ITypedElement, IStreamContentAccessor
{
	private final File file;

	public FilesystemTreeNode(File file) {
		this.file = file;
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public String getType() {
		if (file.isDirectory()) {
			return ITypedElement.FOLDER_TYPE;
		}
		else {
			final String extension = getExtension();
			if (extension != null) {
				return extension;
			}
			else {
				return ITypedElement.UNKNOWN_TYPE;
			}
		}
	}

	private String getExtension() {
		final String name = file.getName();
		final int lastDot = name.lastIndexOf('.');
		String extension = null;
		if (lastDot != -1) {
			extension = name.substring(lastDot + 1);
		}
		return extension;
	}

	@Override
	public long getModificationDate() {
		return file.lastModified();
	}

	@Override
	public Object[] getChildren() {
		File[] files = file.listFiles();
		if (files == null) return files;

		List<FilesystemTreeNode> children = new ArrayList<>();
		for (File childFile : file.listFiles()) {
			children.add(new FilesystemTreeNode(childFile));
		}
		return children.toArray();
	}

	@Override
	public Image getImage() {
		if (file.isDirectory()) {
			return CompareUI.getImage(FOLDER_TYPE);
		}
		else {
			return CompareUI.getImage(TEXT_TYPE);
		}
	}

	@Override
	public InputStream getContents() {
		try {
			if (file.isDirectory()) {
				return null;
			}
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			EUnitPlugin.getDefault().logException(e);
			return null;
		}
	}

	@Override
	public String toString() {
		return "FileSystemTreeNode[" + file.getPath() + "]";
	}

	@Override
	public boolean equals(Object other) {
		// Eclipse's differencer needs this to properly match files with
		// the same name in the left and right directories. We must not
		// take into account the content of the file/directory: Eclipse
		// will do that separately.
		if (other instanceof FilesystemTreeNode) {
			final FilesystemTreeNode otherNode = (FilesystemTreeNode)other;
			return getName().equals(otherNode.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}
