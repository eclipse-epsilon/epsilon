/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.launch;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.function.*;
import org.eclipse.epsilon.common.util.profiling.*;

/**
 * Convenience interface for profiling methods in an {@linkplain IEolModule}.
 * @author Sina Madani
 * @since 1.6
 */
public interface ProfilableIEolModule extends IEolModule {
	
	Collection<ProfileDiagnostic> getProfiledStages();
	
	default void profileExecution(Consumer<EolRuntimeException> exceptionHandler) {
		try {
			profileExecution();
		}
		catch (EolRuntimeException | RuntimeException rx) {
			exceptionHandler.accept(EolRuntimeException.findCause(rx));
		}
	}
	
	default void addProfileInfo(String stage, long nanos, long memory) {
		BenchmarkUtils.addProfileInfo(getProfiledStages(), stage, nanos, memory);
	}
	
	default Object profileExecution() throws EolRuntimeException {
		final String stage = "executeImpl";
		Object result = profileExecutionStage(stage, this::execute);
		BenchmarkUtils.removeGCTimeFromStage(getProfiledStages(), stage);
		return result;
	}
	
	// UNCHECKED
	
	default <T, R> R profileExecutionStage(String description, Function<T, R> code, T argument) {
		return BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code, argument);
	}
	default <R> R profileExecutionStage(String description, Supplier<R> code) {
		return BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code);
	}
	default <T> void profileExecutionStage(String description, Consumer<T> code, T argument) {
		BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code, argument);
	}
	default void profileExecutionStage(String description, Runnable code) {
		BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code);
	}
	
	// CHECKED
	
	default <T, R> R profileExecutionStage(String description, CheckedEolFunction<T, R> code, T argument) throws EolRuntimeException {
		return BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code, argument);
	}
	default <R> R profileExecutionStage(String description, CheckedEolSupplier<R> code) throws EolRuntimeException {
		return BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code);
	}
	default <T> void profileExecutionStage(String description, CheckedEolConsumer<T> code, T argument) throws EolRuntimeException {
		BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code, argument);
	}
	default void profileExecutionStage(String description, CheckedEolRunnable code) throws EolRuntimeException {
		BenchmarkUtils.profileExecutionStage(getProfiledStages(), description, code);
	}
}
