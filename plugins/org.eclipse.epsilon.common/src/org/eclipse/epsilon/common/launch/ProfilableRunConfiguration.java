/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.launch;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.eclipse.epsilon.common.util.OperatingSystem.*;
import org.eclipse.epsilon.common.function.CheckedSupplier;
import org.eclipse.epsilon.common.util.profiling.ProfileDiagnostic;
import static org.eclipse.epsilon.common.util.profiling.BenchmarkUtils.*;
import org.eclipse.epsilon.common.util.profiling.ProfileDiagnostic.MemoryUnit;

/**
 * Generic utility class for building standalone applications with support for: <br/>
 * - Multi-stage, detailed AST profiling <br/>
 * - Writing to output files <br/>
 * - Identifying and comparing configurations <br/>
 * - Displaying results <br/>
 * - Building configurations using an elegant builder syntax <br/>
 * ... and more <br/>
 * <br/>
 * The intention is that this class is used as a base for building more complex
 * standalone run configurations which can be built and invoked easily (at least,
 * from the client's perspective!).
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class ProfilableRunConfiguration implements Runnable, Callable<Object>, CheckedSupplier<Object, Exception> {
	
	protected String printMarker = "-----------------------------------------------------";
	protected int id;
	public final boolean showResults, profileExecution;
	public final Path script, outputFile;
	protected final Collection<ProfileDiagnostic> profiledStages;
	protected boolean hasRun = false;
	protected Object result;
	protected final int targetRepeats;
	private int currentRepeat;
	
	@SuppressWarnings("unchecked")
	public abstract static class Builder<C extends ProfilableRunConfiguration, B extends Builder<C, B>> {
		protected Class<C> configClass;
		
		public int repeats = 1;
		public Integer id;
		public boolean showResults, profileExecution;
		public Path script, outputFile;
		
		protected Builder() {
			this(null);
		}
		protected Builder(Class<C> runConfigClass) {
			this.configClass = runConfigClass != null ? runConfigClass : (Class<C>) getClass().getDeclaringClass();
		}

		public abstract C build() throws IllegalArgumentException, IllegalStateException;
		
		protected C buildReflective(Supplier<? extends C> alternative) throws IllegalStateException {
			if (Modifier.isAbstract(this.configClass.getModifiers())) {
				if (alternative == null) {
					throw new IllegalStateException(
						"Impossible build for class '"+configClass.getName()+"' and no concrete implementation!"
					);
				}
				return alternative.get();
			}
			
			try {
				return (C) Stream.of(configClass.getDeclaredConstructors())
					.filter(constructor ->
						constructor.getParameterCount() == 1 &&
						constructor.getParameterTypes()[0].isAssignableFrom(this.getClass())
					)
					.findFirst()
					.orElseThrow(() -> new NoSuchMethodException("Couldn't find builder constructor for class '"+configClass.getName()+"'."))
				.newInstance(this);
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				ex.printStackTrace();
				if (alternative != null) return alternative.get();
				else throw new IllegalStateException(ex);
			}
		}
		
		public B with(Consumer<B> builderFunction) {
			builderFunction.accept((B) this);
			return (B) this;
		}
		
		public B withScript(Path scriptPath) {
			this.script = scriptPath;
			return (B) this;
		}
		public B withScript(String path) {
			return withScript(Paths.get(path));
		}
		
		public B withOutputFile(Path output) {
			this.outputFile = output;
			return (B) this;
		}
		public B withOutputFile(String path) {
			return withOutputFile(Paths.get(path));
		}
		
		public B withId(int id) {
			this.id = id;
			return (B) this;
		}
		
		public B withResults() {
			return showResults(true);
		}
		public B showResults() {
			return showResults(true);
		}
		public B showResults(boolean show) {
			this.showResults = show;
			return (B) this;
		}
		public B withRepeats(int repetition) {
			if (repetition < 1)
				throw new IllegalArgumentException("Repeats must be positive!");
			this.repeats = repetition;
			return (B) this;
		}
		public B withProfiling() {
			return profileExecution(true);
		}
		public B profileExecution() {
			return profileExecution(true);
		}
		public B profileExecution(boolean profile) {
			this.profileExecution = profile;
			return (B) this;
		}
	}
	
	public static <C extends ProfilableRunConfiguration, B extends Builder<C, B>> B Builder(Class<C> configClass) {
		Constructor<B> constructor = findBuilder(configClass);
		if (constructor == null) {
			throw new IllegalArgumentException("Could not find suitable Builder constructor for "+configClass.getName());
		}
		try {
			constructor.setAccessible(true);
			return constructor.getParameterCount() == 0 ? constructor.newInstance() : constructor.newInstance(configClass);
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static <C extends ProfilableRunConfiguration, B extends Builder<C, B>> Constructor<B> findBuilder(Class<C> configClass) {
		Objects.requireNonNull(configClass);
		Constructor<?> constructor = null;
		for (Class<?> clazz = configClass; constructor == null && clazz != Object.class; clazz = configClass.getSuperclass()) {
			Collection<Constructor<?>> constructors = Arrays.stream(clazz.getDeclaredClasses())
				.filter(c -> Modifier.isStatic(c.getModifiers()))
				.filter(ProfilableRunConfiguration.Builder.class::isAssignableFrom)
				.flatMap(c -> Arrays.stream(c.getDeclaredConstructors()))
				.collect(Collectors.toList());
			
			constructor = constructors.stream()
				.filter(c -> Arrays.stream(c.getParameterTypes()).anyMatch(p -> p.getClass() == Class.class))
				.findAny().orElseGet(() -> constructors.stream()
					.filter(c -> c.getParameterCount() == 0)
					.findAny().orElse(null)
				);
		}
		return (Constructor<B>) constructor;
	}
	
	protected ProfilableRunConfiguration(Builder<?, ?> builder) {
		if (builder == null) throw new IllegalArgumentException("Builder shouldn't be null!");
		this.script = builder.script;
		this.profileExecution = builder.profileExecution;
		this.showResults = builder.showResults;
		this.outputFile = builder.outputFile;
		this.profiledStages = new ConcurrentLinkedDeque<>();
		this.id = Optional.ofNullable(builder.id).orElseGet(() -> Objects.hash(Objects.toString(script)));
		this.targetRepeats = builder.repeats;
	}
	
	protected ProfilableRunConfiguration(ProfilableRunConfiguration other) {
		this.script = other.script;
		this.showResults = other.showResults;
		this.profileExecution = other.profileExecution;
		this.id = other.id;
		this.outputFile = other.outputFile;
		this.result = other.result;
		this.profiledStages = other.profiledStages;
		this.targetRepeats = other.targetRepeats;
	}
	
	@Override
	public final Object getThrows() throws Exception {
		return call();
	}
	
	@Override
	public final Object call() throws Exception {
		beforeRepeatLoop();
		while (currentRepeat++ < targetRepeats) {
			if (currentRepeat > 1) {
				reset();
			}
			if (profileExecution) {
				System.gc();
			}
			preExecute();
			result = execute();
			hasRun = true;
			postExecute();
		}
		afterRepeatLoop();
		return result;
	}
	
	protected void beforeRepeatLoop() throws Exception {
		currentRepeat = 0;
	}
	
	protected void afterRepeatLoop() throws Exception {
		currentRepeat = 0;
	}
	
	protected final int getCurrentRepeat() {
		return currentRepeat;
	}
	
	protected final boolean isFirstRepeat() {
		return getCurrentRepeat() <= 1;
	}
	
	protected final boolean isLastRepeat() {
		return getCurrentRepeat() == targetRepeats;
	}
	
	@Override
	public final void run() {
		try {
			call();
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}
	
	protected void handleException(Exception ex) {
		ex.printStackTrace();
	}
	
	protected void preExecute() throws Exception {
		if (outputFile != null) {
			Path parent = outputFile.getParent();
			if (parent != null) {
				Files.createDirectories(parent);
			}
		}
		if (profileExecution) {
			writeOut(
				getOsNameAndVersion(),
				getJavaVersion(),
				getCpuName(),
				"Logical processors: "+getNumberOfHardwareThreads(),
				"Xms: "+getAvailableMemory(MemoryUnit.MB),
				"Xmx: "+getMaxMemory(MemoryUnit.MB),
				"Starting execution at "+getTime(),
				printMarker
			);
		}
	}
	
	protected abstract Object execute() throws Exception;
	
	protected List<Object> getProfilingOutput() {
		return Arrays.asList(
			"Profiled processes:",
			formatExecutionStages(profiledStages),
			"Finished at "+getTime()
		);
	}
	
	protected List<Object> getResultOutput() {
		return Arrays.asList("Result: ", result);
	}
	
	protected void postExecute() throws Exception {
		if (profileExecution) {
			writeOut(printMarker);
			writeOut(getProfilingOutput());
			writeOut(printMarker);
		}
		if (showResults) {
			writeOut(printMarker);
			writeOut(getResultOutput());
			writeOut(printMarker);
		}
	}
	
	protected void reset() throws Exception {
		profiledStages.clear();
		hasRun = false;
	}
	
	public Duration getExecutionTime() {
		if (!hasRun) {
			throw new IllegalStateException("Not yet run!");
		}
		return getTotalExecutionTimeFrom(profiledStages);
	}
	
	public Object getResult() {
		if (!hasRun)
			throw new IllegalStateException("Attempted to get result without calling run()!");
		
		return result;
	}
	
	public int getId() {
		return id;
	}
	
	public final void writeOut(Object... lines) {
		if (lines == null || lines.length == 0) return;
		writeOut(Arrays.asList(lines));
	}
	
	protected void writeOut(Collection<?> lines) {
		if (lines == null || lines.isEmpty()) return;
		if (outputFile != null) {
			try {
				Files.write(
					outputFile,
					lines.stream().map(Objects::toString).collect(Collectors.toList()),
					StandardOpenOption.APPEND, StandardOpenOption.CREATE, StandardOpenOption.WRITE
				);
				return;
			}
			catch (IOException iox) {
				System.err.println("Couldn't write to file '"+outputFile+"': "+iox.getMessage());
			}
		}
		// Fall back to stdout if couldn't write to file
		lines.forEach(System.out::println);
	}
	
	@Override
	public String toString() {
		return
			getClass().getSimpleName()+
			": id="+id+
			", script='"+Objects.toString(script.getFileName())+"'";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, script, showResults, profileExecution, outputFile);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return false;
		if (!(obj instanceof ProfilableRunConfiguration))
			return false;
		
		ProfilableRunConfiguration other = (ProfilableRunConfiguration) obj;
		return
			Objects.equals(this.id, other.id) &&
			Objects.equals(this.script, other.script) &&
			Objects.equals(this.showResults, other.showResults) &&
			Objects.equals(this.profileExecution, other.profileExecution) &&
			Objects.equals(this.outputFile, other.outputFile) &&
			Objects.equals(this.result, other.result);
	}
}
