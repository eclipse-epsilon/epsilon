/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.swt.widgets.Display;

/**
 * Keeps track of the current coverage session (shown in the Coverage view and
 * highlighted in editors). Recording or loading a session replaces the current one.
 * Listeners are always notified in the UI thread.
 */
public class CoverageSessionManager {
	
	private static final CoverageSessionManager INSTANCE = new CoverageSessionManager();
	
	protected final List<ICoverageSessionListener> listeners = new CopyOnWriteArrayList<>();
	protected volatile CoverageSession session;
	
	public static CoverageSessionManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * @return The current session, or null if there is none
	 */
	public CoverageSession getSession() {
		return session;
	}
	
	/**
	 * Replaces the current session (null clears it)
	 */
	public void setSession(CoverageSession session) {
		this.session = session;
		if (session != null) CoverageAnnotationManagerStarter.ensureStarted();
		inUiThread(() -> {
			for (ICoverageSessionListener listener : listeners) listener.sessionChanged(session);
		});
	}
	
	public void clear() {
		setSession(null);
	}
	
	public CoverageSession load(File file) throws IOException {
		return load(URI.createFileURI(file.getAbsolutePath()));
	}
	
	public CoverageSession load(URI uri) throws IOException {
		CoverageSession session = CoverageResources.load(uri);
		if (session.getName() == null || session.getName().isEmpty()) {
			session.setName(uri.lastSegment());
		}
		setSession(session);
		return session;
	}
	
	public void addListener(ICoverageSessionListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ICoverageSessionListener listener) {
		listeners.remove(listener);
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
