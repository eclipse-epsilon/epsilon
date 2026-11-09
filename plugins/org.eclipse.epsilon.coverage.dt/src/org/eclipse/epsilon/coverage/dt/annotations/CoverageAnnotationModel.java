/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.annotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.epsilon.coverage.CoverageLines;
import org.eclipse.epsilon.coverage.CoverageLines.Status;
import org.eclipse.epsilon.coverage.SourceText;
import org.eclipse.epsilon.coverage.model.SourceFile;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelListener;
import org.eclipse.jface.text.source.IAnnotationModelListenerExtension;

/**
 * An annotation model that highlights fully/partially/not covered lines of a
 * document. It is attached to the annotation model of text editors (see
 * {@link CoverageAnnotationManager}). Annotations are removed as soon as the
 * contents of the document no longer match the contents the coverage was
 * recorded against (and restored if they match again, e.g. after an undo).
 */
public class CoverageAnnotationModel implements IAnnotationModel {
	
	/** Key under which this model is attached to editor annotation models */
	public static final Object KEY = new Object();
	
	public static final String FULL_COVERAGE = "org.eclipse.epsilon.coverage.dt.fullCoverageAnnotation";
	public static final String PARTIAL_COVERAGE = "org.eclipse.epsilon.coverage.dt.partialCoverageAnnotation";
	public static final String NO_COVERAGE = "org.eclipse.epsilon.coverage.dt.noCoverageAnnotation";
	
	protected static class CoverageAnnotation extends Annotation {
		protected final Position position;
		
		public CoverageAnnotation(String type, String text, Position position) {
			super(type, false, text);
			this.position = position;
		}
	}
	
	protected final SourceFile sourceFile;
	protected final List<IAnnotationModelListener> listeners = new CopyOnWriteArrayList<>();
	protected List<CoverageAnnotation> annotations = new ArrayList<>();
	protected IDocument document;
	protected int openConnections = 0;
	
	protected final IDocumentListener documentListener = new IDocumentListener() {
		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {}
		
		@Override
		public void documentChanged(DocumentEvent event) {
			update();
		}
	};
	
	public CoverageAnnotationModel(SourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	public SourceFile getSourceFile() {
		return sourceFile;
	}
	
	protected void update() {
		List<CoverageAnnotation> oldAnnotations = annotations;
		List<CoverageAnnotation> newAnnotations = new ArrayList<>();
		
		if (document != null && matches(document)) {
			for (Map.Entry<Integer, Status> entry : new CoverageLines(sourceFile).getStatuses().entrySet()) {
				try {
					IRegion line = document.getLineInformation(entry.getKey() - 1);
					newAnnotations.add(createAnnotation(entry.getValue(), new Position(line.getOffset(), line.getLength())));
				}
				catch (BadLocationException ex) {
					// Line not in the document
				}
			}
		}
		
		if (oldAnnotations.isEmpty() && newAnnotations.isEmpty()) return;
		if (!oldAnnotations.isEmpty() && !newAnnotations.isEmpty() && sameLines(oldAnnotations, newAnnotations)) return;
		
		annotations = newAnnotations;
		fireModelChanged(oldAnnotations, newAnnotations);
	}
	
	/**
	 * Removes all annotations and notifies listeners. Must be called before
	 * this model is detached from an editor's annotation model, as
	 * IAnnotationModelExtension.removeAnnotationModel() does not notify
	 * the editor, which would otherwise keep painting stale highlights.
	 */
	public void clear() {
		if (annotations.isEmpty()) return;
		List<CoverageAnnotation> oldAnnotations = annotations;
		annotations = new ArrayList<>();
		fireModelChanged(oldAnnotations, new ArrayList<>());
	}

	protected boolean sameLines(List<CoverageAnnotation> a, List<CoverageAnnotation> b) {
		if (a.size() != b.size()) return false;
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).position.equals(b.get(i).position) || !a.get(i).getType().equals(b.get(i).getType())) return false;
		}
		return true;
	}
	
	protected boolean matches(IDocument document) {
		return sourceFile.getContentHash() == null || sourceFile.getContentHash().equals(SourceText.hash(document.get()));
	}
	
	protected CoverageAnnotation createAnnotation(Status status, Position position) {
		switch (status) {
			case FULL: return new CoverageAnnotation(FULL_COVERAGE, "Fully covered", position);
			case PARTIAL: return new CoverageAnnotation(PARTIAL_COVERAGE, "Partially covered", position);
			default: return new CoverageAnnotation(NO_COVERAGE, "Not covered", position);
		}
	}
	
	protected void fireModelChanged(List<CoverageAnnotation> removed, List<CoverageAnnotation> added) {
		AnnotationModelEvent event = new AnnotationModelEvent(this);
		for (CoverageAnnotation annotation : removed) event.annotationRemoved(annotation, annotation.position);
		for (CoverageAnnotation annotation : added) event.annotationAdded(annotation);
		for (IAnnotationModelListener listener : listeners) {
			if (listener instanceof IAnnotationModelListenerExtension) {
				((IAnnotationModelListenerExtension) listener).modelChanged(event);
			}
			else {
				listener.modelChanged(this);
			}
		}
	}
	
	@Override
	public void addAnnotationModelListener(IAnnotationModelListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
			fireModelChanged(new ArrayList<>(), new ArrayList<>(annotations));
		}
	}
	
	@Override
	public void removeAnnotationModelListener(IAnnotationModelListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void connect(IDocument document) {
		if (this.document != null && this.document != document) {
			throw new IllegalStateException("Already connected to a different document");
		}
		if (openConnections++ == 0) {
			this.document = document;
			document.addDocumentListener(documentListener);
			update();
		}
	}
	
	@Override
	public void disconnect(IDocument document) {
		if (this.document != document) return;
		if (--openConnections == 0) {
			document.removeDocumentListener(documentListener);
			this.document = null;
		}
	}
	
	@Override
	public void addAnnotation(Annotation annotation, Position position) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeAnnotation(Annotation annotation) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Iterator<Annotation> getAnnotationIterator() {
		return new ArrayList<Annotation>(annotations).iterator();
	}
	
	@Override
	public Position getPosition(Annotation annotation) {
		return annotation instanceof CoverageAnnotation ? ((CoverageAnnotation) annotation).position : null;
	}
}
