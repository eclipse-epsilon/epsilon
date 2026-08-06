/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.editor;

import org.eclipse.epsilon.hutn.dt.editor.contentAssist.HutnContentAssistProcessor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class HutnSourceViewerConfiguration extends SourceViewerConfiguration {
	
	protected final HutnScanner               scanner = new HutnScanner();
	protected final HutnCommentScanner commentScanner = new HutnCommentScanner();
	
	protected final IReconciler reconciler;
	protected final HutnReconcileStrategy strategy;
	
	public HutnSourceViewerConfiguration(HutnEditor editor) {
		strategy   = new HutnReconcileStrategy(editor, scanner);
		reconciler = new MonoReconciler(strategy, false);
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();
		
		final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		
		final DefaultDamagerRepairer commentDR = new DefaultDamagerRepairer(commentScanner);
		reconciler.setDamager(commentDR, HutnPartitionScanner.HUTN_COMMENT);
		reconciler.setRepairer(commentDR, HutnPartitionScanner.HUTN_COMMENT);

		return reconciler;
	}
	
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		return reconciler;
	}
	
	public void reconcile(IDocument document) {
		strategy.setDocument(document);
		strategy.reconcile(new Region(0, document.getLength()));
	}
	
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
	    ContentAssistant ca = new ContentAssistant();
	    IContentAssistProcessor pr = new HutnContentAssistProcessor();
	    ca.setContentAssistProcessor(pr, IDocument.DEFAULT_CONTENT_TYPE);
	    ca.setInformationControlCreator(getInformationControlCreator(sourceViewer));
	    return ca;
	}
}
