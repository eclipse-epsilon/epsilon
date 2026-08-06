/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.cli.Option;
import org.eclipse.epsilon.common.cli.ConfigParser;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.launch.EolRunConfiguration;

/**
 * A default config getter which effectively allows main method inheritance.
 * Uses reflection to find appropriate constructors and module interface to pass to parseModule method.
 * Please note: The constructors of this class must be inherited in R!
 * <br/>
 * Note that this needn't be subclassed to use it,
 * you can just add the required projects to the classpath
 * and call it with appropriate arguments, but you must provide
 * a module with the -module option.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolConfigParser<C extends EolRunConfiguration, B extends EolRunConfiguration.Builder<C, B>> extends ConfigParser<C, B> {

	/**
	 * Allows the caller to invoke any subclass of IEolModule.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String... args) throws ClassNotFoundException {
		if (args.length < 1) throw new IllegalArgumentException("Must provide arguments! At the minimum, an Epsilon script is required.");
		if (args[0].toUpperCase().startsWith("CONFIG")) {
			Class<? extends EolRunConfiguration> configClass = (Class<? extends EolRunConfiguration>)
				Class.forName(args[0].substring(7));
			
			String[] adjustedArgs = Arrays.copyOfRange(args, 1, args.length);
			new EolConfigParser(EolRunConfiguration.Builder(configClass)).apply(adjustedArgs).run();
		}
		else {
			new EolConfigParser(EolRunConfiguration.Builder(getRunConfigurationForScript(args[0])))
				.apply(args).run();
		}
	}
	
	protected final String
		moduleOpt = "module",
		modelsOpt = "models",
		scriptParamsOpt = "parameters",
		parallelismOpt = "parallelism",
		sequentialOpt = "sequential";
	
	
	public EolConfigParser(B builder) {
		super(builder);
		
		requiredUsage += "  -"+modelsOpt+" [model class]#[model properties];"+nL;
		optionalUsage += "  -"+moduleOpt+" [org.eclipse.epsilon.moduleClass]"+nL;
		
		options.addOption(Option.builder(moduleOpt)
			.hasArg()
			.desc("Specify the module and arguments to the module in key-value pairs. "
				+ "Please note: the arguments type must be a fully qualified class and the class must have a String constructor"
				+ "which is used to parse the provided argument."
			)
			.optionalArg(false)
			.hasArgs()
			.valueSeparator()
			.build()
		).addOption(Option.builder(modelsOpt)
			.hasArgs()
			.desc("Specify the models and properties. The format first specifies the concrete Java class to"
				+ "be instantiated (fully qualified name after org.eclipse.epsilon.emc.), followed by #,"
				+ "followed by comma-separated key=value properties. For example: "
				+ "emf.EmfModel#name=modelName,cached=true;plainxml.PlainXmlModel#name=model2. "
				+ "This example specifies an EMF and a PlainXML model with their names as properties."
			)
			.valueSeparator(';')
			.build()
		).addOption(Option.builder(scriptParamsOpt)
			.hasArgs()
			.desc("Specify parameters to the script in comma-separated key=value pairs. Note that "
				+ "the type of variable passed will always be a String."
			)
			.optionalArg(true)
			.valueSeparator(',')
			.build()
		).addOption(Option.builder("threads")
			.longOpt(parallelismOpt)
			.desc("Maximum number of simulatenously running jobs.")
			.hasArg()
			.build()
		)
		.addOption(Option.builder(sequentialOpt)
			.desc("Use sequential implementation.")
			.build()
		);
	}
	
	@Override
	protected void parseArgs(String[] args) throws Exception {
		super.parseArgs(args);
		builder.parallelism = tryParse(parallelismOpt, builder.parallelism);
		if (cmdLine.hasOption(sequentialOpt)) {
			builder.sequential();
		}
		if (cmdLine.hasOption(moduleOpt)) {
			builder.module = parseModule(cmdLine.getOptionValues(moduleOpt));
		}
		if (cmdLine.hasOption(modelsOpt)) {
			builder.modelsAndProperties.putAll(parseModelParameters(cmdLine.getOptionValues(modelsOpt)));
		}
		if (cmdLine.hasOption(scriptParamsOpt)) {
			builder.parameters.putAll(parseScriptParameters(cmdLine.getOptionValues(scriptParamsOpt)));
		}
	}
	
	public static Map<IModel, StringProperties> parseModelParameters(String[] arguments) throws Exception {
		Map<IModel, StringProperties> modelMap = new HashMap<>(arguments.length);
		
		for (String arg : arguments) {
			String[] modelPropertyEntry = arg.split("#");
			if (modelPropertyEntry.length != 2)
				continue;
			
			IModel model = (IModel) Class.forName("org.eclipse.epsilon.emc."+modelPropertyEntry[0])
				.getDeclaredConstructor()
				.newInstance();
			
			StringProperties properties = new StringProperties();
			for (String propertyToken : modelPropertyEntry[1].split(",")) {
				String[] propEntry = propertyToken.split("=");
				assert propEntry.length == 2;
				properties.put(propEntry[0], propEntry[1]);
			}
			
			modelMap.put(model, properties);
		}

		return modelMap;
	}
	
	public static Map<String, Object> parseScriptParameters(String[] arguments) {
		return Arrays.stream(arguments)
			.map(param -> {
				String[] entry = param.split("=");
				String key, value;
				
				if (entry.length != 2) {
					key = ""; value = "";
				}
				else {
					key = entry[0]; value = entry[1];
				}
				
				return new AbstractMap.SimpleEntry<>(key, value);
			})
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
	
	/**
	 * Attempts to parse a module from command-line arguments, based on assumptions on Epsilon's conventional naming schemes and package structure.
	 * The names are based on the class name of the return type; so for example for IEolModule, it will use "ERL" as the language and look
	 * for the appropriate modules and contexts based on this name.
	 * 
	 * @param args the name of the module (following org.eclipse.epsilon.) followed by an even-arity array with arguments to provide to the context constructor,
	 * where every even numbered argument (including 0) is the class (type) and every odd numbered argument is the value.
	 * Note: only types with a String constructor are valid.
	 */
	@SuppressWarnings("unchecked")
	protected static <R extends IEolModule> R parseModule(String[] args) throws IllegalArgumentException {
		String basePkg = "org.eclipse.epsilon.";
		try {
			if (args.length == 0)
				throw new IllegalArgumentException("Must provide a module name.");
			int additionals = args.length-1;
			if (additionals % 2 != 0)
				throw new IllegalArgumentException("Must provide the types and arguments for module.");
			int arrSize = additionals/2;
			Class<?>[] contextArgTypes = new Class[arrSize];
			Object[] parsedArgs = new Object[arrSize];
			
			for (int l = 0, a = 0; l < arrSize*2; l += 2, a++) {
				Class<?>[] type = getType(args[l+1]);
				Class<?> constructType = type.length == 2 ? type[1] : type[0];
				contextArgTypes[a] = type[0];
				parsedArgs[a] = constructType.getConstructor(String.class).newInstance(args[l+2]);
			}

			Class<?> moduleClass = Class.forName(basePkg+args[0]);
			Class<? extends IEolContext> moduleContextClass = (Class<? extends IEolContext>) Arrays.stream(
					moduleClass.getDeclaredConstructors()
				)
				.map(Constructor::getParameterTypes)
				.filter(c -> c.length == 1)
				.flatMap(Arrays::stream)
				.filter(IEolContext.class::isAssignableFrom)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Could not find appropriate constructor for "+moduleClass.getName()));
			
			Class<? extends IEolContext> concreteContextClass =  moduleContextClass;
			if (Modifier.isAbstract(concreteContextClass.getModifiers())) {
				String concreteClassName = concreteContextClass.getName().replace(".I", ".");
				concreteContextClass = (Class<? extends IEolContext>) Class.forName(concreteClassName);
				assert !Modifier.isAbstract(concreteContextClass.getModifiers());
			}
			
			IEolContext context = concreteContextClass.getDeclaredConstructor(contextArgTypes).newInstance(parsedArgs);
			
			try {
				return (R) moduleClass.getDeclaredConstructor(moduleContextClass).newInstance(context);
			}
			catch (IllegalAccessException ex) {
				System.err.println("WARNING: Could not find appropriate constructor for supplied parameters. Proceeding with defaults.");
				System.err.println(ex.getMessage());
				return (R) moduleClass.getConstructor().newInstance();
			}
		}
		catch (Exception ex) {
			throw new IllegalArgumentException("Could not find or instantiate the module", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	static Class<? extends EolRunConfiguration> getRunConfigurationForScript(String scriptPath) {
		String ext = FileUtil.getExtension(scriptPath).toLowerCase();
		String pkg = ext.equals("egx") ? "egl" : ext;
		String className = "org.eclipse.epsilon."+pkg+".launch."+StringUtil.firstToUpper(ext)+"RunConfiguration";
		
		try {
			return (Class<? extends EolRunConfiguration>) Class.forName(className);
		}
		catch (ClassNotFoundException cnfx) {
			return EolRunConfiguration.class;
		}
	}
	
	private static Class<?>[] getType(String specifiedType) throws ClassNotFoundException {
		final String pkg = "java.lang.";
		switch (specifiedType) {
			case "int": case "Integer": case pkg+"Integer":
				return new Class[]{Integer.TYPE, Integer.class};
			case "boolean": case "bool": case "Boolean": case pkg+"Boolean":
				return new Class[]{Boolean.TYPE, Boolean.class};
			case "double": case "Double": case pkg+"Double":
				return new Class[]{Double.TYPE, Double.class};
			case "float": case "Float": case pkg+"Float":
				return new Class[]{Float.TYPE, Float.class};
			case "long": case "Long": case pkg+"Long":
				return new Class[]{Long.TYPE, Long.class};
			case "byte": case "Byte": case pkg+"Byte":
				return new Class[]{Byte.TYPE, Byte.class};
			case "char": case "Character": case pkg+"Character":
				return new Class[]{Character.TYPE, Character.class};
			case "String": case "string": case pkg+"String":
				return new Class[]{String.class};
			default:
				return new Class[]{Class.forName(specifiedType)};
		}
	}
}