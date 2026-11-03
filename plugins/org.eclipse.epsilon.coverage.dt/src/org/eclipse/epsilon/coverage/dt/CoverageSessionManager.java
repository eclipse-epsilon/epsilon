/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.swt.widgets.Display;

/**
 * Keeps track of the coverage sessions recorded/loaded in the workbench,
 * and of the active one (shown in the Coverage view and highlighted in editors).
 * Listeners are always notified in the UI thread.
 */
public class CoverageSessionManager {
	
	private static final CoverageSessionManager INSTANCE = new CoverageSessionManager();
	
	protected final List<CoverageSession> sessions = new ArrayList<>();
	protected final List<ICoverageSessionListener> listeners = new CopyOnWriteArrayList<>();
	protected CoverageSession activeSession;
	protected boolean highlightingEnabled = true;
	
	public static CoverageSessionManager getInstance() {
		return INSTANCE;
	}
	
	public synchronized List<CoverageSession> getSessions() {
		return new ArrayList<>(sessions);
	}
	
	public synchronized CoverageSession getActiveSession() {
		return activeSession;
	}
	
	/**
	 * Adds the session and makes it the active one
	 */
	public void addSession(CoverageSession session) {
		synchronized (this) {
			sessions.add(session);
			activeSession = session;
		}
		CoverageAnnotationManagerStarter.ensureStarted();
		fireSessionsChanged();
		fireActiveSessionChanged(session);
	}
	
	public void setActiveSession(CoverageSession session) {
		synchronized (this) {
			if (session == activeSession || (session != null && !sessions.contains(session))) return;
			activeSession = session;
		}
		fireActiveSessionChanged(session);
	}
	
	public void removeSession(CoverageSession session) {
		boolean activeChanged = false;
		CoverageSession newActive;
		synchronized (this) {
			if (!sessions.remove(session)) return;
			if (activeSession == session) {
				activeSession = sessions.isEmpty() ? null : sessions.get(sessions.size() - 1);
				activeChanged = true;
			}
			newActive = activeSession;
		}
		fireSessionsChanged();
		if (activeChanged) fireActiveSessionChanged(newActive);
	}
	
	public void removeAllSessions() {
		synchronized (this) {
			sessions.clear();
			activeSession = null;
		}
		fireSessionsChanged();
		fireActiveSessionChanged(null);
	}
	
	/**
	 * Merges the sessions into a new session, which replaces them and becomes the active one
	 */
	public CoverageSession mergeSessions(Collection<CoverageSession> toMerge) {
		CoverageSession merged = CoverageResources.merge(toMerge);
		synchronized (this) {
			sessions.removeAll(toMerge);
		}
		addSession(merged);
		return merged;
	}
	
	public CoverageSession load(File file) throws IOException {
		return load(URI.createFileURI(file.getAbsolutePath()));
	}
	
	public CoverageSession load(URI uri) throws IOException {
		CoverageSession session = CoverageResources.load(uri);
		if (session.getName() == null || session.getName().isEmpty()) {
			session.setName(uri.lastSegment());
		}
		addSession(session);
		return session;
	}
	
	public boolean isHighlightingEnabled() {
		return highlightingEnabled;
	}
	
	public void setHighlightingEnabled(boolean highlightingEnabled) {
		if (this.highlightingEnabled == highlightingEnabled) return;
		this.highlightingEnabled = highlightingEnabled;
		inUiThread(() -> {
			for (ICoverageSessionListener listener : listeners) listener.highlightingChanged(highlightingEnabled);
		});
	}
	
	public void addListener(ICoverageSessionListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ICoverageSessionListener listener) {
		listeners.remove(listener);
	}
	
	protected void fireSessionsChanged() {
		inUiThread(() -> {
			for (ICoverageSessionListener listener : listeners) listener.sessionsChanged();
		});
	}
	
	protected void fireActiveSessionChanged(CoverageSession session) {
		inUiThread(() -> {
			for (ICoverageSessionListener listener : listeners) listener.activeSessionChanged(session);
		});
	}
	
	protected static void inUiThread(Runnable runnable) {
		Display display = Display.getDefault();
		if (Display.getCurrent() == display) {
			runnable.run();
		}
		else {
			display.asyncExec(runnable);
		}
	}
	
	/**
	 * Indirection that avoids a static dependency cycle between the manager and the annotation manager
	 */
	static class CoverageAnnotationManagerStarter {
		static void ensureStarted() {
			inUiThread(() -> org.eclipse.epsilon.coverage.dt.annotations.CoverageAnnotationManager.getInstance().start());
		}
	}
}
