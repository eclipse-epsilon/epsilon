/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget.eol;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.sirius.widget.AbstractEmbeddedWidget;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;

public abstract class AbstractEolEditorWidget extends AbstractEmbeddedWidget {

	protected SourceViewer viewer;
	private static final String ERROR_ANNOTATION = "org.eclipse.xtext.ui.editor.error";
	private String[] annotations = new String[] { ERROR_ANNOTATION };
	private ITextListener textListener;

	@Override
	public void createControl(Composite parent) {
		CompositeRuler verticalRuler = new CompositeRuler();
		final Document doc = new Document();
		int rulerColumnCounter = 1;

		// configure source viewer
		viewer = new ProjectionViewer(parent, verticalRuler, null, false, SWT.V_SCROLL);
		viewer.configure(new EolEditor().createSourceViewerConfiguration());
		AnnotationModel annotationModel = new AnnotationModel();
		viewer.setDocument(doc, annotationModel);

		// configure annotations
		if (verticalRuler != null && annotations != null && annotations.length > 0) {
			AnnotationRulerColumn annotationRulerColumn = new AnnotationRulerColumn(viewer.getAnnotationModel(), 12,
					new DefaultMarkerAnnotationAccess() {
						@Override
						public int getLayer(Annotation annotation) {
							if (annotation.isMarkedDeleted()) {
								return IAnnotationAccessExtension.DEFAULT_LAYER;
							}
							return super.getLayer(annotation);
						}
					});
			for (String annotationType : annotations)
				annotationRulerColumn.addAnnotationType(annotationType);
			verticalRuler.addDecorator(rulerColumnCounter++, annotationRulerColumn);
		}

		// configure control layout
		Control control = viewer.getControl();
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.heightHint = 14 * 12;
		gridData.widthHint = 300;
		gridData.horizontalIndent = 5;
		control.setLayoutData(gridData);

		textListener = new ITextListener() {
			@Override
			public void textChanged(TextEvent event) {
				annotationModel.removeAllAnnotations();
				EolModule module = new EolModule();
				try {
					module.parse(viewer.getTextWidget().getText());
					if (!module.getParseProblems().isEmpty()) {
						for (ParseProblem p : module.getParseProblems()) {
							Annotation a = new Annotation(ERROR_ANNOTATION, false, p.getReason());
							if (p.getLine() > 0) {
								annotationModel.addAnnotation(a, new Position(doc.getLineOffset(p.getLine() - 1), 0));
							} else {
								annotationModel.addAnnotation(a, new Position(0, 0));
							}

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// add a listener to parse the EOL code
		viewer.addTextListener(textListener);

	}

	@Override
	public Control getControl() {
		return viewer.getControl();
	}

	@Override
	public String getText() {
		if (viewer.getTextWidget() != null) {
			return viewer.getTextWidget().getText();
		}
		return "";
	}

	@Override
	public void setEnabled(boolean enabled) {
		viewer.getControl().setEnabled(enabled);
	}

	@Override
	public String getLanguageName() {
		return "eol";
	}

	@Override
	public void dispose() {
		viewer.removeTextListener(textListener);
		viewer.getControl().dispose();
	}
}