/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.control;

/**
 * Marker interface for execution listeners that should also be notified
 * about module elements executed by executor factories that use the
 * factory they are registered with as their base (e.g. thread-local
 * executor factories in parallel modules, or the executor factories of
 * EGL templates invoked from EGX). Implementations must be thread-safe.
 * 
 * @since 2.10
 */
public interface IInheritableExecutionListener extends IExecutionListener {

}
