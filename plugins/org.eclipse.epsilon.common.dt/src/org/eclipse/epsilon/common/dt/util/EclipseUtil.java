/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - add findIFile(AST) and openEditorAt(AST)
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.util;

import java.io.File;
import java.net.URI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class EclipseUtil {

	private EclipseUtil() {}
	
	public static boolean isDarkThemeEnabled() {
		try {
			final Display display = Display.getDefault();
			if (display == null) {
				// We're not in a UI thread: return false for now
				return false;
			}
			final IThemeEngine engine = (IThemeEngine)
			    display.getData("org.eclipse.e4.ui.css.swt.theme");
			final ITheme activeTheme = engine.getActiveTheme();
			return activeTheme != null && "org.eclipse.e4.ui.css.theme.e4_dark".equals(activeTheme.getId());
		}
		catch (Exception ex) {
			LogUtil.log(ex);
			return false;
		}
	}
	
	public static String getActiveTheme() {
		try {
			IThemeEngine engine = (IThemeEngine)
			    Display.getDefault().getData("org.eclipse.e4.ui.css.swt.theme");
			return engine.getActiveTheme().getId();
		}
		catch (Exception ex) {
			LogUtil.log(ex);
			return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme().getId();
		}
	}
	
	public static IFile findIFile(ModuleElement ast) {
		if (ast == null) {
			return null;
		}

		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (ast.getFile() != null) {
			final Path path = new Path(ast.getFile().getAbsolutePath());
			return workspaceRoot.getFileForLocation(path);
		}
		if (ast.getUri() != null) {
			try {
				final URI fileURI = FileLocator.toFileURL(ast.getUri().toURL()).toURI();
				final IFile[] files = workspaceRoot.findFilesForLocationURI(fileURI);
				if (files.length > 0) {
					return files[0];
				}
			} catch (Exception ex) {
				LogUtil.log(ex);
			}
		}

		return null;
	}

	public static String getWorkspacePath()  {
		return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString();
	}

	public static String getWorkspaceContainerAbsolutePath(String workspacePath) {
		try {
			return ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(workspacePath)).getLocation().toOSString();
		}
		catch (Exception ex) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(workspacePath.replace("/", "")).getLocation().toOSString();
		}
	}
	
	public static String getWorkspaceFileAbsolutePath(String workspacePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(workspacePath)).getLocation().toOSString();
	}
	
	public static void openEditorAt(ModuleElement ast) {
		
		if (ast == null) return;
		
		if (ast.getFile() != null) {
			if (ast.getRegion() != null) {
				if (ast.getRegion().getStart().getLine() != ast.getRegion().getEnd().getLine()) {
					openEditorAt(ast.getFile().getAbsolutePath(), new Region(
							ast.getRegion().getStart().getLine(), ast.getRegion().getStart().getColumn(), 
							ast.getRegion().getStart().getLine(), ast.getRegion().getStart().getColumn()));
					return;
				}
			}
			openEditorAt(ast.getFile().getAbsolutePath(), ast.getRegion());
		}
		else {
			openEditorAt(ast.getUri().toString(), ast.getRegion());
		}
	}
	
	public static void openEditorAt(String location, final Region region) {
		IFile iFile = null;
		
		// Get IFile from location path / URI
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		File file = new File(location);
		if (file.exists()) {
			final Path path = new Path(file.getAbsolutePath());
			iFile = workspaceRoot.getFileForLocation(path);
		}
		else {
			try {
				final URI fileURI = FileLocator.toFileURL(new URI(location).toURL()).toURI();
				final IFile[] files = workspaceRoot.findFilesForLocationURI(fileURI);
				if (files.length > 0) {
					iFile = files[0];
				}
			} catch (Exception ex) {
				LogUtil.log(ex);
			}
		}
		
		if (iFile == null) return;
		final IFile iFile2 = iFile;
		
		// Open the editor
		final FileEditorInput fileinput=new FileEditorInput(iFile);
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(iFile.getName());
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					
					AbstractTextEditor editor = (AbstractTextEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(fileinput,desc.getId(), false);		
					IDocument doc = editor.getDocumentProvider().getDocument(fileinput);
					
					int startOffset = doc.getLineOffset(region.getStart().getLine()-1) + region.getStart().getColumn();
					int endOffset = doc.getLineOffset(region.getEnd().getLine()-1) + region.getEnd().getColumn();
					
					EclipseUtil.openEditorAt(iFile2, region.getStart().getLine(), 
							region.getStart().getColumn(), endOffset - startOffset, false);

				}
				catch (Exception ex) {
					ex.printStackTrace();
				}				
			}
		});
	}
	
	/*
	public static void openEditorAt(File file, int line, int column, int length, boolean highlightLine) {
		if (file == null) return;
		IFile iFile = (IFile) ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI())[0];
		openEditorAt(iFile, line, column, length, highlightLine);
	}*/
	
	public static void openEditorAt(File file, int line, int column, boolean highlightLine) {
		if (file == null) return;
		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI())[0];
		openEditorAt(iFile, line, column, highlightLine);
	}
	
	public static void openEditorAt(final IFile file, final int line, final int column, final boolean highlightLine) {
		openEditorAt(file, line, column, 0, highlightLine);
	}
	
	public static void openEditorAt(final IFile file, final int line, final int column, final int length, final boolean highlightLine) {
		if (file == null) return;
		
		final FileEditorInput fileinput=new FileEditorInput(file);
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					int realLine = line;
					if (realLine == 0) realLine = 1;
					
					AbstractTextEditor editor = (AbstractTextEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(fileinput,desc.getId(), false);		
					IDocument doc = editor.getDocumentProvider().getDocument(fileinput);
					
					if (highlightLine) {
						editor.selectAndReveal(doc.getLineOffset(realLine - 1),doc.getLineLength(realLine - 1) - 1);
					}
					else {
						editor.selectAndReveal(doc.getLineOffset(realLine - 1) + column, length);
					}
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}				
			}
		});
	}

}
