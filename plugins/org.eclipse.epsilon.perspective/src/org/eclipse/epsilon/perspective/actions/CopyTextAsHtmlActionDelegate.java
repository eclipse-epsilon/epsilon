/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.perspective.actions;

import org.eclipse.epsilon.common.dt.util.ClipboardUtil;
import org.eclipse.epsilon.common.dt.util.ColorUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

public class CopyTextAsHtmlActionDelegate implements IEditorActionDelegate {

	@Override
	public void run(IAction action) {
		getActiveEditor();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}
	
	public void getActiveEditor() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		IEditorPart part = page.getActiveEditor();
		
		if (part instanceof AbstractDecoratedTextEditor) {
			AbstractDecoratedTextEditor editor = (AbstractDecoratedTextEditor) part;
			ISelection selection =  editor.getSelectionProvider().getSelection();
			Document document = (Document) editor.getDocumentProvider().getDocument(editor.getEditorInput());
			TextSelection textSelection = (TextSelection) selection;	
			
			StyledText widget = (StyledText) editor.getAdapter(Control.class);
			
			StyleRange[] styleRanges = widget.getStyleRanges(textSelection.getOffset(), textSelection.getLength());
			StringBuffer sb = new StringBuffer();
			
			for (StyleRange styleRange : styleRanges) {
				try {
					String range = document.get(styleRange.start, styleRange.length);
					String lineSeparator = System.getProperty("line.separator");
					range = range.replaceAll(lineSeparator, "<br>" + lineSeparator);
					range = range.replaceAll("\t", "  ");
					range = range.replace(" ", "&nbsp;");
					
					if (styleRange.foreground != null) {
						range = "<font color='#" + ColorUtil.toHex(styleRange.foreground) + "'>" +
								range + "</font>";
					}
					
					switch (styleRange.fontStyle) {
						case SWT.BOLD:
							sb.append("<b>" + range + "</b>");
							break;
						case SWT.ITALIC:
							sb.append("<i>" + range + "</i>");
							break;
						case SWT.BOLD | SWT.ITALIC:
							sb.append("<b><i>" + range + "</i></b>");
							break;
						default:
							sb.append(range);
					}
				}
					
				catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			ClipboardUtil.copyToClipboard(sb.toString(),true);
		}
		
		
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		// TODO Auto-generated method stub
		
	}

}
