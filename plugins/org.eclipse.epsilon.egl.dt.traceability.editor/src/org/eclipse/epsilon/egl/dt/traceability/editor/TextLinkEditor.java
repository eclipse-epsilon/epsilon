/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.egl.dt.traceability.editor.SelectTextDestinationDialogue.ISelectedTextDestinationHandler;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.ModelLocation;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiEditor;
import org.eclipse.ui.texteditor.ITextEditor;

public class TextLinkEditor extends MultiEditor {
		
	public static final String ID = "org.eclipse.epsilon.egl.dt.traceability.editor.EglTraceAwareEditor";
	private TabbedEditor<EcoreEditor> sources;
	private TabbedEditor<ITextEditor> destinations;
	private TextLinkInnerEditors innerEditors;
	
	@Override
	protected void drawGradient(IEditorPart innerEditor, Gradient g) {}
	
	public TextLinkModel getTextlinkModel() {
		return ((TextLinkEditorInput)getEditorInput()).textlinkModel;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		final SashForm form = new SashForm(parent, SWT.HORIZONTAL);
		form.setLayout(new FillLayout());
		
		innerEditors = new TextLinkInnerEditors(getInnerEditors());
		sources = new TabbedEditor<>(form, innerEditors.getAllModelEditors());
		destinations = new TabbedEditor<>(form, innerEditors.getAllTextEditors());
		
		sources.addMouseListenerToEditors(new MouseAdapter() {
			@Override public void mouseDoubleClick(MouseEvent e) {
				selectAndRevealDestination();
			}
		});
		
		addIndicatorstoModelEditors();
		
		setPartName(getTextlinkModel().getFileName());
	}

	private void addIndicatorstoModelEditors() {
		for (EcoreEditor modelEditor : innerEditors.getAllModelEditors()) {
			addIndicatorsToModelElementsWithTraceLinks(modelEditor);
		}
	}

	/**
	 * Emboldens the labels of model elements that have trace links
	 */
	private void addIndicatorsToModelElementsWithTraceLinks(EcoreEditor editor) {
		new AddFontProviderToTreeViewer((TreeViewer)editor.getViewer()).add(new EmboldeningLabelProvider());
	}
	
	private class EmboldeningLabelProvider implements ITableFontProvider {

		private final FontRegistry registry = new FontRegistry();
		
		@Override
		public Font getFont(Object element, int columnIndex) {
			if (element instanceof EObject && getTextlinkModel().hasDestinationFor((EObject)element))
				// Replaced registry.getBold with registry.get because bold didn't look nice on Mac OS X
				// A better way to get a bold variant of the font is 
				/*
				Font font = ((TreeViewer) sources.getActiveEditor().getViewer()).getControl().getFont();
				FontDescriptor boldDescriptor = FontDescriptor.createFrom(font).setStyle(SWT.BOLD);
				Font bold = boldDescriptor.createFont(Display.getCurrent());
				 */
				// TODO: Instead of relying on a different font, use a label next to the tree item with
				// the number of times it appears in generated files e.g. (5).
				return registry.get(Display.getCurrent().getSystemFont().getFontData()[0].getName());
			else
				return null;
		}	
	}
	
	@Override
	public void dispose() {
		getTextlinkModel().dispose();
		super.dispose();
	}

	public boolean isActiveDestination(TextLocation destination) {
		final TextLinkInnerEditorInput<?> activeDestinationEditorInput = (TextLinkInnerEditorInput<?>) destinations.getActiveEditor().getEditorInput();
		return activeDestinationEditorInput.contains(destination);
	}

	public void selectAndReveal(ModelLocation source) {
		if (source instanceof EmfModelLocation) {
			final EcoreEditor sourceEditor = (EcoreEditor)innerEditors.getEditorThatContains(source);
			
			final EObject selection = sourceEditor.getEditingDomain().getResourceSet().getEObject(modelElementToUri(((EmfModelLocation) source).getModelElement()), true);
			
//			((ISelectionProvider)innerEditor).setSelection(new StructuredSelection(selection));
			sourceEditor.setSelectionToViewer(Arrays.asList(selection));
			
			sources.switchTo(sourceEditor);
		}
	}
	
	private URI modelElementToUri(EObject me) {
		URI uri = EcoreUtil.getURI(me);

		try {
			java.net.URI location = new java.net.URI(uri.trimFragment().toString());
			final IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(location);
			
			if (files.length > 0) {
				uri = URI.createPlatformResourceURI(files[0].getFullPath().toString(), true).appendFragment(uri.fragment());
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return uri;
	}
	
	private void selectAndRevealDestination() {
		final List<TextLocation> destinations = getTextlinkModel().getDestinationsFor(getFirstSelectedModelElement());
			
		if (destinations.size() == 1) {
			selectAndReveal(destinations.iterator().next());
		
		} else if (destinations.size() > 1) {
			SelectTextDestinationDialogue.promptForSelectionFrom(getEditorSite().getShell(), destinations, new ISelectedTextDestinationHandler() {
				@Override
				public void reactToSelectionOf(TextLocation destination) {
					selectAndReveal(destination);
				}
			});
		}
	}

	private EObject getFirstSelectedModelElement() {
		final EcoreEditor activeModelEditor = sources.getActiveEditor();
		final ITreeSelection selection = (ITreeSelection)activeModelEditor.getSelection();
		
		return (EObject)selection.getFirstElement();
	}
	
	private void selectAndReveal(TextLocation destination) {
		final ITextEditor destinationEditor = (ITextEditor)innerEditors.getEditorThatContains(destination);
		destinationEditor.selectAndReveal(destination.getRegion().getOffset(), destination.getRegion().getLength());
		destinations.switchTo(destinationEditor);
	}
	
	private static class TextLinkInnerEditors {
		
		private final IEditorPart[] editors;
		
		public TextLinkInnerEditors(IEditorPart[] editorParts) {
			this.editors = editorParts;
		}
		
		public Collection<EcoreEditor> getAllModelEditors() {
			return getEditors(true);
		}
		
		public Collection<ITextEditor> getAllTextEditors() {
			return getEditors(false);
		}

		@SuppressWarnings("unchecked")
		private <T> Collection<T> getEditors(boolean isSource) {
			final List<T> innerTextEditors = new LinkedList<>();
			
			for (IEditorPart editor : editors) {
				if (((TextLinkInnerEditorInput<?>)editor.getEditorInput()).isSource() == isSource) {
					innerTextEditors.add((T)editor);
				}
			}
			
			return innerTextEditors;
		}
		
		public IEditorPart getEditorThatContains(Object o) {
			for (IEditorPart editor : editors) {
				
				if (((TextLinkInnerEditorInput<?>)editor.getEditorInput()).contains(o)) {
					return editor;
				}
			}
			
			return null;
		}
	}
}
