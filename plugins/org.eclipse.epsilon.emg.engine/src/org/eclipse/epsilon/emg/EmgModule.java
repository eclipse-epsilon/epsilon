/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.emg.execute.operations.contributors.EmgOperationContributor;
import org.eclipse.epsilon.eol.dom.Annotation;
import org.eclipse.epsilon.eol.dom.AnnotationBlock;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolIllegalReturnException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.combinations.CompositeCombinationGenerator;
import org.eclipse.epsilon.epl.dom.NoMatch;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.epl.execute.PatternMatch;

/**
 * The Emg Module is responsible for execution emg scripts. Emg scripts are used
 * to generate models.
 */
public class EmgModule extends EplModule implements IEmgModule {

	/**
	 * Assign the created elements to a list
	 */
	static final String LIST_ID_ANNOTATION = "list";

	/**
	 * How many instances must be created
	 */
	static final String NUMBER_OF_INSTANCES_ANNOTATION = "instances";

	/**
	 * Parameters to pass for instance craetion
	 */
	static final String PARAMETERS_ANNOTATION = "parameters";

	/**
	 * The name of the create operation
	 */
	static final String CREATE_OPERATION = "create";
	
	/**
	 * How many matches should the pattern find
	 */
	static final String NUMBER_ANNOTATION = "number";

	/**
	 * What is the probability of executing the pattern
	 */
	static final String PROBABILITY_ANNOTATION = "probability";

	/**
	 * Don't re-execute the pattern for the same set of input elements
	 */
	static final String NO_REPEAT_ANNOTATION = "noRepeat";

	/** The random generator */
	private EmgOperationContributor randomGenerator;

	/** The seed used for random generation. */
	private long seed;

	private boolean useSeed;
	
	/**
	 * A maps to keep track of objects created by create operations that us
	 * the @name annotation. The key of the map is the value of the annotation.
	 */
	private Map<String, List<Object>> namedCreatedObjects = new HashMap<>(); //

	/**
	 * @param seed the seed to set
	 */
	@Override
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * @param useSeed the useSeed to set
	 */
	@Override
	public void setUseSeed(boolean useSeed) {
		this.useSeed = useSeed;
	}

	/**
	 * @return the namedCreatedObjects
	 */
	@Override
	public Map<String, List<Object>> getNamedCreatedObjects() {
		return namedCreatedObjects;
	}

	/**
	 * Initialise the contributors
	 */
	private void preload() {
		context.setModule(this);
		if (useSeed) {
			randomGenerator = new EmgOperationContributor(this, seed);
		}
		else {
			randomGenerator = new EmgOperationContributor(this);
		}
		context.getOperationContributorRegistry().add(randomGenerator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.epl.EplModule#getImportConfiguration()
	 */
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("emg", EmgModule.class);
		return importConfiguration;
	}

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		preload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.epl.EplModule#execute()
	 */
	@Override
	public Object processRules() throws EolRuntimeException {
		executeCreateOperations();
		super.processRules();
		IModel model = context.getModelRepository().getModels().get(0);
		model.store();
		// TODO Is the total size more important than the matches?
		return model.allContents().size();
	}

	/**
	 * Execute the create operations in the EMG script.
	 *
	 * @throws EolModelElementTypeNotFoundException the eol model element type not
	 *                                              found exception
	 * @throws EolRuntimeException                  If the type to be instantiated
	 *                                              can't be found or any of the
	 *                                              random functions fails.
	 */
	protected void executeCreateOperations() throws EolRuntimeException {

		AnnotationBlock annotationBlock;
		String annotationName, instancesListName;
		String parameters = null;
		for (Operation operation : getOperations()) {
			if (operation.getName().equals(CREATE_OPERATION)) {
				// get the class context
				EolModelElementType instancesType = (EolModelElementType) operation.getContextType(context);
				if (!instancesType.isInstantiable()) {
					continue;
				}
				int instances = 1;
				instancesListName = "";
				// guard="";
				// get the annotations
				annotationBlock = operation.getAnnotationBlock();
				if (!(annotationBlock == null)) {
					List<Object> annotationValues;
					for (Annotation annotation : annotationBlock.getAnnotations()) {
						if (!(annotation.hasValue()))
							continue;
						annotationName = annotation.getName();
						annotationValues = operation.getAnnotationsValues(annotationName, context);
						// search for instances to be created
						if (annotationName.equals(NUMBER_OF_INSTANCES_ANNOTATION)) {
							if (!annotationValues.isEmpty()) {
								Object val = annotationValues.get(0);
								if (val instanceof List) {
									List<?> valC = (List<?>) val;
									if (valC.size() > 1)
										instances = randomGenerator.nextInteger(getInt(valC.get(0)),
											getInt(valC.get(1)));
									else
										instances = getInt(valC.get(0));
								}
								else
									instances = getInt(annotationValues.get(0));
							}
							if (instances < 1)
								instances = 1;
						}
						else if (annotationName.equals(LIST_ID_ANNOTATION)) {
							if (!annotationValues.isEmpty()) {
								instancesListName = (String) annotationValues.get(0);
							}
						}
						// Parameters for element initialization
						else if (annotationName.equals(PARAMETERS_ANNOTATION)) {
							if (!annotationValues.isEmpty()) {
								// parameters = (List<Object>) annotationValues.get(0);
								parameters = annotationName;
							}
						}
					} // end for loop annotations
				}
				// Create the instances
				createInstances(instancesListName, operation, instancesType, instances, parameters);
				parameters = null;
			}

		} // end for loop (operations)
	}

	/**
	 * @param instancesListName
	 * @param operation
	 * @param instancesType
	 * @param instances
	 * @param paramAnotation
	 * @return
	 * @throws EolRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private void createInstances(String instancesListName, Operation operation, EolModelElementType instancesType,
		int instances, String paramAnotation) throws EolRuntimeException {
		ArrayList<Object> classes = new ArrayList<>();
		// Add the list to the context first so previous instances can be used for
		// attribute assignment
		if (!instancesListName.isEmpty()) {
			if (namedCreatedObjects.containsKey(instancesListName)) {
				namedCreatedObjects.get(instancesListName).addAll(classes);
			}
			else
				namedCreatedObjects.put(instancesListName, classes);
		}
		for (int i = 0; i < instances; i++) {
			List<Object> paramObj = Collections.emptyList();
			if (paramAnotation != null) {
				List<Object> annotationValues = operation.getAnnotationsValues(paramAnotation, context);
				paramObj = (List<Object>) annotationValues.get(0);
			}
			Object modelObject = instancesType.createInstance(paramObj);
			// Execute statements in the operation to initialise object attributes
			operation.execute(modelObject, Collections.emptyList(), context);
			classes.add(modelObject);
		}
	}


	@Override
	public List<PatternMatch> match(final Pattern pattern) throws EolRuntimeException {

		List<PatternMatch> patternMatches = new ArrayList<>();
		context.getFrameStack().enterLocal(FrameType.PROTECTED, pattern);
		boolean noRepeat = pattern.hasAnnotation(NO_REPEAT_ANNOTATION);
		boolean withProbability = pattern.hasAnnotation(PROBABILITY_ANNOTATION);
		boolean number = pattern.hasAnnotation(NUMBER_ANNOTATION);
		boolean annotationChange;

		int num = 0, value = 1;
		List<Object> matchList = new ArrayList<>();
		CompositeCombinationGenerator<Object> generator = new CompositeCombinationGenerator<>();
		for (Role role : pattern.getRoles()) {
			generator.addCombinationGenerator(createCombinationGenerator(role));
		}
		generator.setValidator(combination -> {
			for (Object o : combination.get(combination.size() - 1)) {
				if (o instanceof NoMatch)
					return true;
			}

			Frame frame = context.getFrameStack().enterLocal(FrameType.PROTECTED, pattern);
			boolean result = true;
			int i = 0;
			Role role = null;
			for (List<Object> values : combination) {
				role = pattern.getRoles().get(i);
				for (Variable variable : getVariables(values, role)) {
					frame.put(variable);
				}
				i++;
			}
			if (!role.isNegative() && role.getGuard() != null && role.isActive(context)
				&& role.getCardinality().isOne()) {
				result = role.getGuard().execute(context);
			}
			context.getFrameStack().leaveLocal(pattern);
			return result;
		});

		// annotation number
		if (number) {
			List<Object> vals = pattern.getAnnotationsValues(NUMBER_ANNOTATION, context);
			int size = vals.size();
			if (size > 1) {
				Object val0 = vals.get(0);
				Object val1 = vals.get(1);
				if (val0 != null && val1 != null) {
					value = randomGenerator.nextInteger(getInt(val0), getInt(val1));
				}
			}
			else if (size > 0) {
				Object val = vals.get(0);
				if (val instanceof List) {
					List<?> valC = (List<?>) val;
					if (size > 1)
						value = randomGenerator.nextInteger(getInt(valC.get(0)), getInt(valC.get(1)));
					else
						value = getInt(valC.get(0));
				}
				else {
					value = getInt(val);
				}
			}
		} // end annotation number
		while (generator.hasNext()) {
			if (value == 0)
				break;
			List<List<Object>> candidate = generator.next();
			boolean test = false;
			// Don't repeat
			if (noRepeat) {
				for (Object temp : candidate) {
					// System.out.println(temp);
					if (matchList.contains(temp)) {
						test = true;
						break;
					}
				}
				if (test) {
					continue;
				}
			} // end annotation noRepeat
			boolean matches = true;
			annotationChange = true;

			Frame frame = context.getFrameStack().enterLocal(FrameType.PROTECTED, pattern);

			if (pattern.getMatch() != null || pattern.getNoMatch() != null || pattern.getOnMatch() != null) {
				int i = 0;
				for (Role role : pattern.getRoles()) {
					for (Variable variable : getVariables(candidate.get(i), role)) {
						frame.put(variable);
					}
					i++;
				}
			}

			if (pattern.getMatch() != null) {
				Object result = context.getExecutorFactory().execute(pattern.getMatch(), context);
				if (result instanceof Return)
					result = ((Return) result).getValue();
				if (result instanceof Boolean) {
					matches = (Boolean) result;
				}
				else
					throw new EolIllegalReturnException("Boolean", result, pattern.getMatch(), context);
			}

			if (matches) {
				if (noRepeat) {
					matchList.addAll(candidate);

				} // end annotation noRepeat

				// annotation probability
				if (withProbability) {
					Object val = 1.0;
					if (pattern.getAnnotationsValues(PROBABILITY_ANNOTATION, context).size() > 0) {
						val = pattern.getAnnotationsValues(PROBABILITY_ANNOTATION, context).get(0);
					}
					double value2 = 1;
					if (val != null) {
						value2 = getFloat(val);
					}

					if (!(randomGenerator.nextValue() < value2)) {
						annotationChange = false;
					}

				} // end annotation probability
				if (annotationChange) {
					context.getExecutorFactory().execute(pattern.getOnMatch(), context);
					patternMatches.add(createPatternMatch(pattern, candidate));
				}
				else {
					context.getFrameStack().leaveLocal(pattern);
				}
				// annotation number
				// If there was a match and the pattern has a number annotation keep track
				if (number && ++num == value) {
					break;
				}
				// end annotation number
			}
			else
				context.getExecutorFactory().execute(pattern.getNoMatch(), context);
		}

		context.getFrameStack().leaveLocal(pattern);
		return patternMatches;
	}

	/**
	 * Gets the int.
	 *
	 * @param object the object
	 * @return the int
	 */
	protected int getInt(Object object) {
		if (object instanceof Integer)
			return (int) object;
		else
			return Integer.parseInt((String) object);
	}

	/**
	 * Gets the float.
	 * 
	 * @param object the object
	 * @return the float
	 */
	protected double getFloat(Object object) {
		if (object instanceof Number) // || object instanceof Double)
			return ((Number) object).doubleValue();
		else
			return Float.parseFloat((String) object);
	}

	/**
	 * Contain any.
	 *
	 * @param first the first
	 * @param last  the last
	 * @return true, if successful
	 */
	protected boolean containAny(Collection<Object> first, Collection<Object> last) {
		for (Object o : first) {
			if (last.contains(o))
				return true;
		}
		return false;
	}
}