/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - findCause, message caching
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import java.util.Collection;
import java.util.HashSet;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolRuntimeException extends Exception {
	
	protected ModuleElement ast = null;
	protected String reason = "", message = null;
	protected IEolContext context = null;
	
	public EolRuntimeException() {
		super();
	}
	
	public EolRuntimeException(String reason) {
		super(reason);
		this.reason = reason;
	}

	public EolRuntimeException(String reason, ModuleElement ast) {
		this(reason);
		this.ast = ast;
	}
	
	public EolRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public EolRuntimeException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public ModuleElement getAst() {
		return ast;
	}

	public void setAst(ModuleElement ast) {
		this.ast = ast;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getLine() {
		if (getAst() != null) {
			return getAst().getRegion().getStart().getLine();
		}
		else return 0;
	}
	
	public int getColumn() {
		if (getAst() != null) {
			return getAst().getRegion().getStart().getColumn();
		}
		else return 0;
	}
	
	@Override
	public String getMessage() {
		if (message == null) {
			if ((message = getReason()) == null) {
				message = "Unknown reason";
			}
			
			if (ast != null && ast.getModule() instanceof IEolModule) {
				IEolContext context = ((IEolModule) ast.getModule()).getContext();
				if (context != null) {
					message += "\r\n" + context.getExecutorFactory().getStackTraceManager().getStackTraceAsString();
				}
			}
		}
		return message;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
	
	public static EolRuntimeException wrap(Throwable t) {
		if (t instanceof EolRuntimeException) return (EolRuntimeException) t;
		else return new EolInternalException(t);
	}
	
	public static void propagate(Throwable t) throws EolRuntimeException {
		throw wrap(t);
	}

	public static void propagateDetailed(Throwable t) throws EolRuntimeException {
		if (t instanceof EolRuntimeException) {
			throw (EolRuntimeException) t;
		}
		else {
			throw EolRuntimeException.findCause(t);
		}
	}
	
	/**
	 * Traverses the causes of the (usually unchecked) exception to find an EolRuntimeException.
	 * If not found, it will wrap the root cause into an instance of EolRuntimeException.
	 * This method accounts for any depth of cyclic causes, so it is guaranteed to terminate;
	 * unless there is a {@linkplain StackOverflowError}.
	 */
	public static EolRuntimeException findCause(Throwable runtimeEx) {
		if (runtimeEx == null) return null;
		if (runtimeEx instanceof EolRuntimeException) return (EolRuntimeException) runtimeEx;
		EolRuntimeException result = findCauseImpl(runtimeEx.getCause(), new HashSet<>());
		return result != null ? result : new EolRuntimeException(runtimeEx);
	}
	
	private static EolRuntimeException findCauseImpl(Throwable currentCause, Collection<Throwable> causes) {
		if (currentCause instanceof EolRuntimeException) {
			return (EolRuntimeException) currentCause;
		}
		else if (currentCause != null) {
			if (causes.contains(currentCause)) {
				return new EolRuntimeException(currentCause);
			}
			else {
				causes.add(currentCause);
				return findCauseImpl(currentCause.getCause(), causes);
			}
		}
		else return null;
	}
}
