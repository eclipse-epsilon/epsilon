/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor.hyperlinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.IModuleParseListener;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

public class AbstractModuleEditorHyperlinkDetector implements IHyperlinkDetector, IModuleParseListener {

	protected AbstractModuleEditor editor;
	protected HashMap<OperationCallExpression, IRegion> astRegions = new HashMap<>();
	protected IEolModule module = null;
	
	public List<IHyperlink> createHyperlinks(OperationCallExpression ast) {
		
		ArrayList<IHyperlink> hyperlinks = new ArrayList<>();
		
		for (Object op : module.getOperations()) {
			Operation operation = (Operation) op;
			if (operation.getName().equals(ast.getName()) && operation.getFormalParameters().size() == ast.getParameterExpressions().size()) {
				hyperlinks.add(new ModuleElementHyperlink(astRegions.get(ast), operation, operation.toString()));
			}
		}
		
		if (hyperlinks.isEmpty()) {
			for (Object op : module.getOperations()) {
				Operation operation = (Operation) op;
			
				if (operation.getName().equals(ast.getName())) {
					hyperlinks.add(new ModuleElementHyperlink(astRegions.get(ast), operation, operation.toString()));
				}
			}	
		}
		
		return hyperlinks;
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {
		
		for (OperationCallExpression ast : astRegions.keySet()) {
			IRegion candidateRegion = astRegions.get(ast);
			
			if (region.getOffset() <= candidateRegion.getOffset() + candidateRegion.getLength() 
					&& region.getOffset() >= candidateRegion.getOffset()) {
				
				IHyperlink[] hyperlinks = CollectionUtil.toArray(createHyperlinks(ast), IHyperlink.class);
				if (hyperlinks.length > 0) return hyperlinks;
			}
			
		}
		
		return null;
		
	}

	@Override
	public void moduleParsed(AbstractModuleEditor editor, IModule module) {
		astRegions.clear();
		this.editor = editor;
		this.module = (IEolModule) module;
		findInterestingASTs(module);
	}
	
	protected void findInterestingASTs(ModuleElement ast) {
		
		if (ast == null) return;
		
		IDocument doc = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		
		if (ast instanceof OperationCallExpression) {
			try {
				OperationCallExpression operationCallExpression = (OperationCallExpression) ast;
				int linkOffset = doc.getLineOffset(operationCallExpression.getNameExpression().getRegion().getStart().getLine()-1) + operationCallExpression.getNameExpression().getRegion().getStart().getColumn();
				astRegions.put(operationCallExpression, new Region(linkOffset, operationCallExpression.getName().length()));
			} catch (BadLocationException e) { }
		}
		
		for (ModuleElement child : ast.getChildren()) {
			findInterestingASTs(child);
		}
	}
	
}
