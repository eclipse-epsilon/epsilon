/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.common.dt.editor;

import org.eclipse.epsilon.common.dt.editor.autoclose.AutoCloseEditStrategy;
import org.eclipse.epsilon.common.dt.editor.contentassist.AbstractModuleEditorCompletionProcessor;
import org.eclipse.epsilon.common.dt.editor.hyperlinks.AbstractModuleEditorHyperlinkDetector;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.MultipleHyperlinkPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;

public class AbstractModuleEditorSourceViewerConfiguration extends SourceViewerConfiguration {
	
	protected AbstractModuleEditor editor = null;
	protected AbstractModuleEditorScanner scanner;
	protected AbstractModuleEditorNormalFontScanner commentScanner;
	protected String[] defaultPrefixes = new String[] {"//"};
	
	public AbstractModuleEditorSourceViewerConfiguration(AbstractModuleEditor editor) {
		this.editor = editor;
		scanner = new AbstractModuleEditorScanner(editor);
		commentScanner = new AbstractModuleEditorNormalFontScanner(scanner.getCommentColor());
		//stringScanner = new AbstractModuleEditorNormalFontScanner(scanner.stringColor);
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();
		
		final MultilineDamagerRepairer dr = new MultilineDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		
		final MultilineDamagerRepairer commentDR = new MultilineDamagerRepairer(commentScanner);
		reconciler.setDamager(commentDR, AbstractModuleEditorPartitionScanner.COMMENT);
		reconciler.setRepairer(commentDR, AbstractModuleEditorPartitionScanner.COMMENT);
		
		//final MultilineDamagerRepairer stringDR = new MultilineDamagerRepairer(stringScanner);
		//reconciler.setDamager(stringDR, AbstractModuleEditorPartitionScanner.STRING);
		//reconciler.setRepairer(stringDR, AbstractModuleEditorPartitionScanner.STRING);
		
		return reconciler;
	}
	
	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		return new IAutoEditStrategy[]{new DefaultIndentLineAutoEditStrategy(), new AutoCloseEditStrategy()};
	}
	
	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		return viewer -> {
			try {
				IDocument doc = viewer.getDocument();
				int caretOffset = viewer.getSelectedRange().x;

				int start = caretOffset;
				int end = caretOffset;
				boolean isIdentifierPart = Character.isJavaIdentifierPart(doc.getChar(start));

				if (isIdentifierPart) {
					while (Character.isJavaIdentifierPart(doc.getChar(start))) {
						start--;
					}
					while (Character.isJavaIdentifierPart(doc.getChar(end))) {
						end++;
					}
				}

				viewer.setSelectedRange(start + 1, end - start - 1);

			} catch (BadLocationException e) {}
		};
	}
	
	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new DefaultAnnotationHover();
	}
	
	AbstractModuleEditorHyperlinkDetector hyperlinkDetector = null;
	
	@Override
	public IHyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
		return new MultipleHyperlinkPresenter(new RGB(0, 0, 255));
	}
	
	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		
		if (!editor.supportsHyperlinks()) return null;
		
		if (hyperlinkDetector == null) {
			hyperlinkDetector = new AbstractModuleEditorHyperlinkDetector();
			editor.addModuleParsedListener(hyperlinkDetector);
		}
		return new IHyperlinkDetector[] { hyperlinkDetector };
	}
	
	@Override
	public IContentAssistant getContentAssistant (ISourceViewer sourceViewer) {
		ContentAssistant assistance = new ContentAssistant();
		assistance.setContentAssistProcessor(
			new AbstractModuleEditorCompletionProcessor(editor), IDocument.DEFAULT_CONTENT_TYPE
		);
		assistance.enableAutoActivation (true);
		assistance.setAutoActivationDelay(500);
		assistance.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
		
		return assistance;
	}
	
	public AbstractModuleEditorScanner getScanner() {
		return scanner;
	}
	
	public AbstractModuleEditorNormalFontScanner getCommentScanner() {
		return commentScanner;
	}
	
	@Override
	public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
		return defaultPrefixes;
	}
}
