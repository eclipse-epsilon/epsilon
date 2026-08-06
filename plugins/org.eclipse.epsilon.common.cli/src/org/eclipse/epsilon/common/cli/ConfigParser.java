/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.cli;

import java.io.PrintWriter;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.cli.*;
import org.eclipse.epsilon.common.launch.ProfilableRunConfiguration;

/**
 * Convenience class providing an extensible command-line builder for {@link ProfilableRunConfiguration}.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ConfigParser<C extends ProfilableRunConfiguration, B extends ProfilableRunConfiguration.Builder<C, B>> implements Consumer<String[]>, Function<String[], C> {
	
	protected B builder;
	protected C runConfig;
	
	protected ConfigParser(B builder) {
		this.builder = builder;
	}
	
	protected String
		nL = System.lineSeparator(),
		profileExecutionOpt = "profile",
		repeatsOpt = "repeats",
		showResultsOpt = "results",
		helpOpt = "help",
		scriptOpt = "script",
		outFileOpt = "outfile",
		requiredUsage = " [absolute path to script] "+nL,
		optionalUsage = "  [show results] "+nL
		  + "  [profile execution] "+nL
		  + "  [output file] "+nL;
	
	protected String formatUsage() {
		return  nL+"Required: " + nL + requiredUsage + nL + "Optional: " + nL + optionalUsage + nL;
	}
	
	protected Options options = new Options()
		.addOption(Option.builder("r").longOpt(repeatsOpt).hasArg().desc("Number of times to repeat the experiment").build())
		.addOption(Option.builder("h").longOpt(helpOpt).desc(helpOpt).build())
		.addOption(Option.builder("p").longOpt(profileExecutionOpt).desc("Whether to profile execution time and memory usage.").build())
		.addOption(Option.builder("r").longOpt(showResultsOpt).desc("Whether to output the results.").build())
		.addOption(Option.builder("o").longOpt(outFileOpt).hasArg().desc("Specify a path to output file.").build())
		.addOption(Option.builder("i").longOpt(scriptOpt).hasArg().desc("Path to input file.").build());
	
	protected HelpFormatter help = new HelpFormatter();
	protected CommandLine cmdLine;
	
	protected void parseArgs(String[] args) throws Exception {
		cmdLine = new DefaultParser().parse(options, args);
		
		if (cmdLine.hasOption(helpOpt)) {
			help.printHelp(formatUsage(), options);
		}
		
		builder.profileExecution = cmdLine.hasOption(profileExecutionOpt);
		builder.showResults = cmdLine.hasOption(showResultsOpt);
		
		if (cmdLine.hasOption(outFileOpt)) {
			builder.outputFile = Paths.get(cmdLine.getOptionValue(outFileOpt));
		}
		
		builder.withRepeats(tryParse(repeatsOpt, builder.repeats));
		
		if (cmdLine.hasOption(scriptOpt)) {
			builder.script = Paths.get(cmdLine.getOptionValue(scriptOpt));
		}
		else try {
			builder.script = Paths.get(args[0]);
		}
		catch (ArrayIndexOutOfBoundsException | InvalidPathException ex) {
			throw new IllegalArgumentException(scriptOpt + " is mandatory!", ex);
		}
	}
	
	protected void handleException(Exception ex) {
		System.err.print("Invalid arguments: ");
		ex.printStackTrace();
		
		try (PrintWriter outWriter = new PrintWriter(System.out)) {
			help.printUsage(outWriter, 80, formatUsage(), options);
		}
	}
	
	protected double tryParse(String opt, float absentDefault) throws IllegalArgumentException {
		if (cmdLine.hasOption(opt)) {
			String value = cmdLine.getOptionValue(opt);
			if (value != null && !value.isEmpty()) try {
				return Float.parseFloat(value);
			}
			catch (NumberFormatException nan) {
				throw new IllegalArgumentException(
					"Invalid value for option '"+opt
					+ "': expected float but got "+value
				);
			}
		}
		return absentDefault;
	}
	
	protected double tryParse(String opt, double absentDefault) throws IllegalArgumentException {
		if (cmdLine.hasOption(opt)) {
			String value = cmdLine.getOptionValue(opt);
			if (value != null && !value.isEmpty()) try {
				return Double.parseDouble(value);
			}
			catch (NumberFormatException nan) {
				throw new IllegalArgumentException(
					"Invalid value for option '"+opt
					+ "': expected double but got "+value
				);
			}
		}
		return absentDefault;
	}
	
	protected int tryParse(String opt, int absentDefault) throws IllegalArgumentException {
		if (cmdLine.hasOption(opt)) {
			String value = cmdLine.getOptionValue(opt);
			if (value != null && !value.isEmpty()) try {
				return Integer.parseInt(value);
			}
			catch (NumberFormatException nan) {
				throw new IllegalArgumentException(
					"Invalid value for option '"+opt
					+ "': expected int but got "+value
				);
			}
		}
		return absentDefault;
	}
	
	protected long tryParse(String opt, long absentDefault) throws IllegalArgumentException {
		if (cmdLine.hasOption(opt)) {
			String value = cmdLine.getOptionValue(opt);
			if (value != null && !value.isEmpty()) try {
				return Long.parseLong(value);
			}
			catch (NumberFormatException nan) {
				throw new IllegalArgumentException(
					"Invalid value for option '"+opt
					+ "': expected long but got "+value
				);
			}
		}
		return absentDefault;
	}
	
	@Override
	public final void accept(String[] args) {
		try {
			parseArgs(args);
			runConfig = builder.build();
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}
	

	@Override
	public final C apply(String[] args) {
		accept(args);
		return runConfig;
	}
	
	public final void parseAndRun(String[] args) {
		apply(args).run();
	}
}