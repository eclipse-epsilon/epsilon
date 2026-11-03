/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.dt.CoverageSessionManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ResourceTransfer;

/**
 * Loads coverage (*.ecv) files dropped on the Coverage view, either from
 * workspace views (e.g. the Project Explorer) or from the file system.
 */
public class CoverageDropListener extends DropTargetAdapter {

	public static final Transfer[] TRANSFERS = {
		LocalSelectionTransfer.getTransfer(), ResourceTransfer.getInstance(), FileTransfer.getInstance()
	};

	protected final Shell shell;

	public CoverageDropListener(Shell shell) {
		this.shell = shell;
	}

	@Override
	public void dragEnter(DropTargetEvent event) {
		updateOperation(event);
	}

	@Override
	public void dragOperationChanged(DropTargetEvent event) {
		updateOperation(event);
	}

	@Override
	public void dragOver(DropTargetEvent event) {
		updateOperation(event);
	}

	/**
	 * Only accept the drop if it (possibly) contains coverage files. The contents of
	 * local selection transfers can be inspected while dragging; those of other
	 * transfers are only available on drop, so they are accepted optimistically.
	 */
	protected void updateOperation(DropTargetEvent event) {
		TransferData transfer = getSupportedTransfer(event);
		boolean accept = transfer != null && (!LocalSelectionTransfer.getTransfer().isSupportedType(transfer)
				|| !getUris(LocalSelectionTransfer.getTransfer().getSelection()).isEmpty());

		if (!accept) {
			event.detail = DND.DROP_NONE;
		}
		else {
			event.currentDataType = transfer;
			if ((event.operations & DND.DROP_COPY) != 0) event.detail = DND.DROP_COPY;
			else if ((event.operations & DND.DROP_LINK) != 0) event.detail = DND.DROP_LINK;
			else event.detail = DND.DROP_NONE;
		}
	}

	protected TransferData getSupportedTransfer(DropTargetEvent event) {
		for (Transfer transfer : TRANSFERS) {
			for (TransferData data : event.dataTypes) {
				if (transfer.isSupportedType(data)) return data;
			}
		}
		return null;
	}

	@Override
	public void drop(DropTargetEvent event) {
		List<URI> uris = getUris(event);
		if (uris.isEmpty()) {
			MessageDialog.openInformation(shell, "Coverage", "Only coverage (*." + CoverageResources.FILE_EXTENSION + ") files can be dropped on the Coverage view.");
			return;
		}
		List<String> errors = new ArrayList<>();
		for (URI uri : uris) {
			try {
				CoverageSessionManager.getInstance().load(uri);
			}
			catch (Exception ex) {
				errors.add((uri.isPlatformResource() ? uri.toPlatformString(true) : uri.toFileString()) + ": " + ex.getMessage());
			}
		}
		if (!errors.isEmpty()) {
			MessageDialog.openError(shell, "Coverage", "Could not load:\n" + String.join("\n", errors));
		}
	}

	protected List<URI> getUris(DropTargetEvent event) {
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.currentDataType)) {
			return getUris(LocalSelectionTransfer.getTransfer().getSelection());
		}
		List<URI> uris = new ArrayList<>();
		if (ResourceTransfer.getInstance().isSupportedType(event.currentDataType) && event.data instanceof IResource[]) {
			for (IResource resource : (IResource[]) event.data) {
				addUri(resource, uris);
			}
		}
		else if (FileTransfer.getInstance().isSupportedType(event.currentDataType) && event.data instanceof String[]) {
			for (String path : (String[]) event.data) {
				if (isCoverageFile(path)) uris.add(URI.createFileURI(path));
			}
		}
		return uris;
	}

	protected List<URI> getUris(ISelection selection) {
		List<URI> uris = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			for (Object element : ((IStructuredSelection) selection).toList()) {
				addUri(Adapters.adapt(element, IFile.class), uris);
			}
		}
		return uris;
	}

	protected void addUri(IResource resource, List<URI> uris) {
		if (resource instanceof IFile && isCoverageFile(resource.getName())) {
			uris.add(URI.createPlatformResourceURI(resource.getFullPath().toString(), true));
		}
	}

	protected boolean isCoverageFile(String name) {
		return name != null && name.toLowerCase().endsWith("." + CoverageResources.FILE_EXTENSION);
	}
}
