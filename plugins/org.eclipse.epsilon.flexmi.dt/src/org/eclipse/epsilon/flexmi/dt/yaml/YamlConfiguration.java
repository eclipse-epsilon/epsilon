/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt.yaml;

import org.eclipse.epsilon.common.dt.editor.MultilineDamagerRepairer;
import org.eclipse.epsilon.flexmi.dt.FlexmiHighlightingManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class YamlConfiguration extends SourceViewerConfiguration {
	
	private FlexmiHighlightingManager colorManager;
	protected YamlScanner scanner;
	
	public YamlConfiguration(FlexmiHighlightingManager colorManager) {
		this.colorManager = colorManager;
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		
		return reconciler;
	}
	
	public YamlScanner getScanner() {
		if (scanner == null) {
			scanner = new YamlScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(new TextAttribute(colorManager.getDefaultColor())));
		}
		return scanner;
	}
}
