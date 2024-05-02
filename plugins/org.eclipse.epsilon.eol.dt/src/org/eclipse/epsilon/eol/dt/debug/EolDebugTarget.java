/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.debug.IEpsilonDebugTarget;
import org.eclipse.epsilon.eol.debug.SuspendReason;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EolDebugTarget extends EolDebugElement implements IDebugTarget, IEpsilonDebugTarget {

	protected boolean suspended;
	protected boolean terminated;
	protected EolDebugger debugger;
	protected ILaunch launch;
	protected IThread[] threads = new IThread[1];
	protected IProcess process;
	protected IEolModule module;
	protected String name;
	protected Map<String, IFile> iFiles = new HashMap<>();
	
	public EolDebugTarget(ILaunch launch, IEolModule module, EolDebugger debugger, String name) {
		super(null);
		this.launch = launch;
		threads[0] = new EolThread(this);
		this.module = module;
		this.debugger = debugger;
		module.getContext().getExecutorFactory().setExecutionController(debugger);
		this.name = name;
	}

	@Override
	public IEolModule getModule() {
		return module;
	}
	
	public void stepInto() throws DebugException {
		fireSuspendEvent(DebugEvent.SUSPEND);
		debugger.step();
		fireResumeEvent(DebugEvent.STEP_INTO);
	}

	public void stepOver() throws DebugException {
		fireSuspendEvent(DebugEvent.SUSPEND);
		debugger.stepOver();
		fireResumeEvent(DebugEvent.STEP_OVER);
	}

	public void stepReturn() {
		fireSuspendEvent(DebugEvent.SUSPEND);
		debugger.stepReturn();
		fireResumeEvent(DebugEvent.STEP_RETURN);
	}

	public Object debug() throws DebugException, EolRuntimeException {
		fireCreationEvent();

		final Object result = module.execute();
		try {
			terminate();
		} catch (DebugException e) {
			throw new EolRuntimeException(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public EolDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}

	@Override
	public void terminate() throws DebugException {
		this.terminated = true;
		this.suspended = false;
		fireTerminateEvent();
	}

	@Override
	public boolean canResume() {
		return isSuspended() && !isTerminated();
	}

	@Override
	public boolean canSuspend() {
		return !isSuspended() && !isTerminated();
	}

	@Override
	public boolean isSuspended() {
		return suspended;
	}

	@Override
	public void resume() throws DebugException {
		this.suspended = false;
		fireResumeEvent(DebugEvent.RESUME);
	}

	@Override
	public void suspend() throws DebugException {
		this.suspended = true;
		fireSuspendEvent(DebugEvent.SUSPEND);
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// nothing to do
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// nothing to do
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// nothing to do
	}

	@Override
	public IProcess getProcess() {
		return null;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		return threads;
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return true;
	}

	@Override
	public String getName() throws DebugException {
		return name;
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		return false;
	}

	/*
	 * Methods for the *Debugger classes
	 */

	@Override
	public boolean hasBreakpointItself(ModuleElement ast) {
		if (!DebugPlugin.getDefault().getBreakpointManager().isEnabled()) {
			// Debugging has been globally disabled
			return false;
		}
	
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(EolDebugConstants.MODEL_IDENTIFIER);
		for (IBreakpoint breakpoint : breakpoints) {
			IMarker marker = breakpoint.getMarker();
			if (marker.getResource().equals(getIFile(ast))) {
				final int markerLine = marker.getAttribute(IMarker.LINE_NUMBER, 0);
				final int astLine = ast.getRegion().getStart().getLine();
				if (markerLine == astLine) {
					try {
						return breakpoint.isEnabled();
					} catch (CoreException e) {
						LogUtil.log(e);
						return false;
					}
				}
			}
		}
		return false;
	}

	private IFile getIFile(ModuleElement ast) {
		if (ast.getFile() != null) {
			return getIFile(ast.getFile());
		} else {
			return getIFile(ast.getUri());
		}
	}

	private IFile getIFile(File file) {
		IFile iFile = iFiles.get(file.getAbsolutePath());
		if (iFile == null) {
			iFile = getIFile(file.toURI());
			iFiles.put(file.getAbsolutePath(), iFile);
		}
		return iFile;
	}

	private IFile getIFile(URI uri) {
		// If the URI starts by platform:/resource, we need to strip that off
		// before invoking ResourcesPlugin - see bug #286017 and its patch
		final String[] uriParts = uri.toString().split("platform:/resource");
		if (uriParts.length > 1) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uriParts[1]));
		}
		return ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri)[0];
	}

	@Override
	public void suspend(ModuleElement ast, SuspendReason reason) {
		try {
			this.suspend();

			IFile file = getIFile(ast);
			EclipseUtil.openEditorAt(file, ast.getRegion().getStart().getLine(), 1, false);
			while (this.isSuspended()
					&& !debugger.isStepping()
					&& debugger.getStopAfterModuleElement() == null
					&& debugger.getStopAfterFrameStackSizeDropsBelow() == null) {
				synchronized (this) {
					try {
						wait(500);
					} catch (InterruptedException ex) {
						// timeout
					}
				}
			}
		} catch (Exception ex) {
			// nothing to do
		}
	}

	/*
	 * Irrelevant methods
	 */

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public void disconnect() throws DebugException {
		// do nothing
	}

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

}
