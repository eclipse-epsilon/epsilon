/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.util.profiling;

import static java.lang.System.nanoTime;
import static java.lang.System.currentTimeMillis;
import static org.eclipse.epsilon.common.util.OperatingSystem.executeCommand;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.eclipse.epsilon.common.function.*;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.common.util.profiling.ProfileDiagnostic.MemoryUnit;

/**
 * Utility class for performance profiling; mainly for measuring execution times.
 * All long temporal values are assumed to be nanoseconds if not otherwise specified.
 * Similarly, all long memory values are assumed to be in bytes if not otherwise specified.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public final class BenchmarkUtils {
	
	private BenchmarkUtils() {}
	
	public static final String GC_PROFILE_STAGE = "GARBAGE_COLLECTION";
	public static final MemoryUnit DEFAULT_MEMORY_UNITS = MemoryUnit.MB;
	public static final TemporalUnit DEFAULT_TIME_UNITS = ChronoUnit.NANOS;
	static final Runtime RT = Runtime.getRuntime();
	
	/**
	 * Convenience method for formatting a collection of profile stages into a string.
	 * 
	 * @param profileInfo the stages profiled.
	 * @param includeTime whether to include execution times.
	 * @param conversionFactor - this parameter has two uses: if it is null, memory won't be included in the results.
	 * If it is present, then the displayed units will be standardized to the specified unit. Otherwise the original
	 * memory units will be used.
	 * 
	 * @return the formatted and raw execution time concerted to milliseconds and memory consumption.
	 * That is, a line-separated String in the form
	 * "[Stage name]: [Execution time in HH:mm:ss.000] ([milliseconds] ms), [Memory consumption] [MemoryUnits]".
	 */
	static String formatExecutionStages(Iterable<ProfileDiagnostic> profileInfo, boolean includeTime, Optional<MemoryUnit> conversionFactor) {
		StringBuilder formatted = new StringBuilder();
		for (ProfileDiagnostic pd : profileInfo) {
			formatted.append(pd.stageName+": ");
			if (includeTime) {
				formatted.append(formatDuration(pd.executionTime)+" ("+pd.executionTime.toMillis()+" ms)");
			}
			if (conversionFactor != null) {
				if (includeTime) {
					formatted.append(", ");
				}
				
				formatted.append(formatMemory(pd, conversionFactor));
			}
			
			formatted.append(System.lineSeparator());
		}
		return formatted.toString();
	}
	
	/**
	 * NOTE: Only durations less than 24 hours are supported.
	 */
	public static String formatDuration(Duration duration) {
    	String pattern = "";
    	if (duration.toHours() >= 1)
    		pattern += "H:";
    	if (duration.toMinutes() >= 1)
    		pattern += "mm:";
    	if (duration.getSeconds() >= 1)
    		pattern += "ss.";
    	pattern += "SSS";
        return LocalTime.MIDNIGHT.plus(duration).format(DateTimeFormatter.ofPattern(pattern));
    }
	
	/**
	 * Sums the execution times of the execution stages.
	 * @param profiledStages The profiled stages.
	 * @return The total duration of {@link ProfileDiagnostic#executionTime} for
	 * the given profiled stages.
	 */
	public static Duration getTotalExecutionTimeFrom(Collection<ProfileDiagnostic> profiledStages) {
		return Duration.ofNanos(profiledStages
			.stream()
			.mapToLong(pd -> pd.executionTime.toNanos())
			.sum()
		);
	}

	//Memory Utils
	
	/**
	 * @return The sum of peak memory usage for all memory pools.
	 */
	public static long getTotalMemoryUsage() {
		return ManagementFactory.getMemoryPoolMXBeans()
			.stream()
			.map(MemoryPoolMXBean::getPeakUsage)
			.filter(usage -> usage != null)
			.mapToLong(MemoryUsage::getUsed)
			.sum();
	}
	
	/**
	 * <a href="https://cruftex.net/2017/03/28/The-6-Memory-Metrics-You-Should-Track-in-Your-Java-Benchmarks.html#metric-used-memory-after-forced-gc">See this</a>
	 */
	public static long getCurrentMemoryUsage() {
		return RT.totalMemory() - RT.freeMemory();
	}
	
	/**
	 * @return The current time as a String.
	 */
	public static String getTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
	}
	
	public static String formatMemory(ProfileDiagnostic pd, Optional<MemoryUnit> conversionFactor) {
		String formatted;
		if (conversionFactor.isPresent()) {
			MemoryUnit cf = conversionFactor.get();
			formatted = (long) MemoryUnit.convertUnits(pd.memoryUnits, cf, pd.memoryUsage)+" "+cf;
		}
		else {
			formatted = (pd.memoryUsage+" "+pd.memoryUnits);
		}
		return formatted;
	}
	
	public static String getAvailableMemory(MemoryUnit units) {
		return formatMemory(RT.totalMemory(), units);
	}
	
	public static String getMaxMemory(MemoryUnit units) {
		return formatMemory(RT.maxMemory(), units);
	}
	
	public static String formatMemory(long amountInBytes, MemoryUnit units) {
		MemoryUnit mu = units != null ? units : DEFAULT_MEMORY_UNITS;
		return (long) MemoryUnit.convertFromBytes(mu, amountInBytes)+" "+mu.name();
	}
	
	//CPU utils
	
	/**
	 * Convenience method for getting the CPU model.
	 * @return The CPU model as reported by the operating system.
	 * @since 1.6
	 */
	public static String getCpuName() {
		try {
			switch (OperatingSystem.getOSFamily()) {
                case WINDOWS: return executeCommand(
                    "powershell.exe", "-Command", "\"(wmic CPU get NAME)[2]\""
                );
                case MAC: return executeCommand(
                    "/bin/sh", "-c", "sysctl -n machdep.cpu.brand_string"
                );
                default: return executeCommand(
                    "/bin/sh", "-c", "cat /proc/cpuinfo | grep -m 1 'model name' | cut -c 14-"
                );
            }
		}
		catch (Exception ex) {
			System.err.println("Could not get CPU name: "+ex.getMessage());
			return "";
		}
	}
	
	/**
	 * Delegates to {@link Runtime#availableProcessors()}.
	 * @return The number of logical cores available to the JVM.
	 */
	public static int getNumberOfHardwareThreads() {
		return RT.availableProcessors();
	}
	
	//Profile utils
	
	/**
	 * Finds the stage for the given name.
	 * @param profileInfo The profiled stages.
	 * @param stageName The stage name.
	 * @return The prifle stage, or <code>null</code> if there wasn't one.
	 */
	public static ProfileDiagnostic getProfileStageByName(Collection<ProfileDiagnostic> profileInfo, String stageName) {
		return profileInfo.stream().filter(pd -> pd.stageName.equals(stageName)).findAny().orElse(null);
	}
	
	/**
	 * Performs a {@link Runtime#gc()}, takes into account the time taken to do so
	 * and subtracts it from the given stage, identified by its name.
	 * @param profileInfo The profiled stages.
	 * @param stageName The stage identifier to subtract garbage collection time from.
	 * @return The updated stage, for convenience.
	 */
	public static ProfileDiagnostic removeGCTimeFromStage(Collection<ProfileDiagnostic> profileInfo, String stageName) {
		ProfileDiagnostic
			gc = getProfileStageByName(profileInfo, GC_PROFILE_STAGE),
			target = getProfileStageByName(profileInfo, stageName);
		
		if (gc == null || target == null) return target;
		
		ProfileDiagnostic updated = new ProfileDiagnostic(
			stageName,
			target.executionTime.minus(gc.executionTime),
			target.memoryUsage,
			target.memoryUnits
		);
		
		profileInfo.remove(target);
		profileInfo.add(updated);
		return updated;
	}
	
	/**
	 * Calls {@link Runtime#gc()}, measuring the time and accumulating it
	 * in the provided profiled stages using the {@link #GC_PROFILE_STAGE} key.
	 * 
	 * @param profileInfo The profiled stages to update.
	 * @return The time taken to perform the GC invocation.
	 */
	public static long measureAndAddGCTime(Collection<ProfileDiagnostic> profileInfo) {
		ProfileDiagnostic existingGCStage = getProfileStageByName(profileInfo, GC_PROFILE_STAGE);
		
		long gcTime = measureTimeNanos(RT::gc);
		
		if (existingGCStage != null) {
			gcTime += existingGCStage.executionTime.toNanos();
			profileInfo.remove(existingGCStage);
		}
		
		profileInfo.add(new ProfileDiagnostic(GC_PROFILE_STAGE, gcTime, 0));
		return gcTime;
	}
	
	/**
	 * Creates a new {@linkplain ProfileDiagnostic} and adds it to the collection of
	 * profiled stages, measuring execution time and memory consumption.
	 * 
	 * @param profileInfo The execution stages to write to.
	 * @param description The name of the execution stage.
	 * @param code The transformation to profile.
	 * @param argument The argument to the transformation code.
	 * @return The result of the transformation code.
	 * @throws E Any exception thrown from the transformation code.
	 */
	public static <T, R, E extends Exception> R profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, CheckedFunction<T, R, E> code, T argument) throws E {
		measureAndAddGCTime(profileInfo);
		final long
			endTime, endMemory,
			startMemory = getCurrentMemoryUsage(),
			startTime = nanoTime();
		
		R result = code.applyThrows(argument);
		
		endTime = nanoTime();
		endMemory = getCurrentMemoryUsage();
		measureAndAddGCTime(profileInfo);
		
		addProfileInfo(profileInfo, description, endTime-startTime, endMemory-startMemory);
		return result;
	}
	
	public static <T, E extends Exception> void profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, CheckedConsumer<T, E> code, T argument) throws E {
		CheckedFunction<T, Void, E> funcEquivalent = t -> {
			code.acceptThrows(t);
			return null;
		};
		profileExecutionStage(profileInfo, description, funcEquivalent, argument);
	}
	
	public static <R, E extends Exception> R profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, CheckedSupplier<R, E> code) throws E {
		CheckedFunction<Void, R, E> funcEquivalent = v -> code.getThrows();
		return profileExecutionStage(profileInfo, description, funcEquivalent, null);
	}
	
	public static <E extends Exception> void profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, CheckedRunnable<E> code) throws E {
		CheckedFunction<Void, Void, E> funcEquivalent = v -> {
			code.runThrows();
			return null;
		};
		profileExecutionStage(profileInfo, description, funcEquivalent, null);
	}
	
	
	public static <T, R> R profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, Function<T, R> code, T argument) {
		CheckedFunction<T, R, RuntimeException> checkedEquivalent = code::apply;
		return profileExecutionStage(profileInfo, description, checkedEquivalent, argument);
	}
	
	public static <T> void profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, Consumer<T> code, T argument) {
		CheckedConsumer<T, RuntimeException> checkedEquivalent = code::accept;
		profileExecutionStage(profileInfo, description, checkedEquivalent, argument);
	}
	
	public static <R> R profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, Supplier<R> code) {
		CheckedSupplier<R, RuntimeException> checkedEquivalent = code::get;
		return profileExecutionStage(profileInfo, description, checkedEquivalent);
	}
	
	public static void profileExecutionStage(Collection<ProfileDiagnostic> profileInfo, String description, Runnable code) {
		CheckedRunnable<RuntimeException> checkedEquivalent = code::run;
		profileExecutionStage(profileInfo, description, checkedEquivalent);
	}
	
	//Temporal utils
	
	// Nanoseconds
	
	public static <T, E extends Exception> long measureTimeNanos(CheckedConsumer<T, E> code, T argument) throws E {
		final long endTime, startTime = nanoTime();
		code.acceptThrows(argument);
		endTime = nanoTime();
		return endTime-startTime;
	}
	public static <E extends Exception> long measureTimeNanos(CheckedRunnable<E> code) throws E {
		CheckedConsumer<Void, E> consEquivalent = v -> code.run();
		return measureTimeNanos(consEquivalent, null);
	}
	public static long measureTimeNanos(Runnable code) {
		CheckedRunnable<RuntimeException> checkedEquivalent = () -> code.run();
		return measureTimeNanos(checkedEquivalent);
	}
	public static <T> long measureTimeNanos(Consumer<T> code, T argument) {
		CheckedConsumer<T, RuntimeException> checkedEquivalent = code::accept;
		return measureTimeNanos(checkedEquivalent, argument);
	}
	
	// Milliseconds
	
	public static <T, E extends Exception> long measureTimeMillis(CheckedConsumer<T, E> code, T argument) throws E {
		final long endTime, startTime = currentTimeMillis();
		code.acceptThrows(argument);
		endTime = currentTimeMillis();
		return endTime-startTime;
	}
	public static <E extends Exception> long measureTimeMillis(CheckedRunnable<E> code) throws E {
		CheckedConsumer<Void, E> consEquivalent = v -> code.run();
		return measureTimeMillis(consEquivalent, null);
	}
	public static long measureTimeMillis(Runnable code) {
		CheckedRunnable<RuntimeException> checkedEquivalent = () -> code.run();
		return measureTimeMillis(checkedEquivalent);
	}
	public static <T> long measureTimeMillis(Consumer<T> code, T argument) {
		CheckedConsumer<T, RuntimeException> checkedEquivalent = code::accept;
		return measureTimeMillis(checkedEquivalent, argument);
	}
	
	public static void printExecutionTime(String stageTag, Runnable code) {
		System.out.print(stageTag+" "+formatExecutionTime(measureTimeNanos(code)));
	}
	
	public static <E extends Exception> void printExecutionTime(String stageTag, CheckedRunnable<E> code) throws E {
		System.out.print(stageTag+" "+formatExecutionTime(measureTimeNanos(code)));
	}
	
	//Format utils
	
	public static void addProfileInfo(Collection<ProfileDiagnostic> profileStages, String stage, long nanos, long memory) {
		profileStages.add(new ProfileDiagnostic(stage, Duration.ofNanos(nanos), memory, MemoryUnit.BYTES));
	}
	
	public static String formatExecutionStages(Iterable<ProfileDiagnostic> profileInfo) {
		return formatExecutionStages(profileInfo, true, Optional.of(DEFAULT_MEMORY_UNITS));
	}
	
	public static String formatMemoryConsumption(Iterable<ProfileDiagnostic> profileInfo, MemoryUnit units) {
		return formatExecutionStages(profileInfo, false, Optional.ofNullable(units));
	}
	
	public static String formatExecutionTimes(Iterable<ProfileDiagnostic> profileInfo) {
		return formatExecutionStages(profileInfo, true, null);
	}
	
	public static String formatExecutionTime(Duration time) {
		return formatExecutionTime("Execution time", time);
	}
	
	public static String formatExecutionTime(String title, Duration time) {
		return formatExecutionTimes(Collections.singleton(new ProfileDiagnostic(title, time, 0, MemoryUnit.BYTES)));
	}
	
	public static String formatExecutionTime(long nanos) {
		return formatExecutionTime(Duration.ofNanos(nanos));
	}
	
	public static String formatExecutionTime(String title, long nanos) {
		return formatExecutionTime(title, Duration.ofNanos(nanos));
	}
	
	public static String formatMillis(long millis) {
		return formatDuration(Duration.ofMillis(millis));
	}
	
	public static String formatNanos(long nanos) {
		return formatDuration(Duration.ofNanos(nanos));
	}
	
	public static long nanosToMillis(long nanos) {
		return Duration.ofNanos(nanos).toMillis();
	}
	
}