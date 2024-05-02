/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.epl.combinations.CombinationGenerator;
import org.eclipse.epsilon.epl.combinations.CombinationGeneratorListener;
import org.eclipse.epsilon.epl.combinations.CompositeCombinationGenerator;
import org.eclipse.epsilon.epl.combinations.DynamicList;
import org.eclipse.epsilon.epl.combinations.DynamicListCombinationGenerator;
import org.eclipse.epsilon.epl.debug.EplDebugger;
import org.eclipse.epsilon.epl.dom.NoMatch;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.epl.execute.RuntimeExceptionThrower;
import org.eclipse.epsilon.epl.execute.context.IEplContext;

/**
 * Generator-based sequential implementation of EPL.
 */
public class EplModule extends AbstractEplModule {
	
	public EplModule() {
		super();
	}
	
	public EplModule(IEplContext context) {
		super(context);
	}


	@SuppressWarnings({ "unchecked" })
	private static List<Object> wrapGetValues(Collection<?> superReturnInstances) {
		if (superReturnInstances instanceof ArrayList || superReturnInstances instanceof DynamicList)
			return (List<Object>) superReturnInstances;
		else
			return new ArrayList<>(superReturnInstances);
	}
	
	
	@Override
	protected DynamicList<Object> wrapBasicRoleInstances(Role role, String roleName, LazyBasicRoleInstancesInitializer initializer) throws EolRuntimeException {
		DynamicList<Object> dynamicInstances = new DynamicList<Object>() {
			@Override
			protected List<Object> getValues() throws EolRuntimeException {
				return wrapGetValues(initializer.get(role, roleName));
			}
		};
		dynamicInstances.setResetable(role.hasActiveAst() || (role.getDomain() != null && role.getDomain().isDynamic()));
		dynamicInstances.setExceptionHandler(new RuntimeExceptionThrower<EolRuntimeException>());
		return dynamicInstances;
	}
	
	@Override
	protected DynamicList<Object> wrapAdvancedRoleInstances(Role role, String roleName, Collection<?> currentInstances_, LazyAdvancedRoleInstancesInitializer initializer) throws EolRuntimeException {
		final DynamicList<?> currentInstances = (DynamicList<?>) currentInstances_;
		DynamicList<Object> dynamicInstances = new DynamicList<Object>() {
			
			@Override
			protected List<Object> getValues() throws EolRuntimeException {
				return wrapGetValues(initializer.get(role, roleName, currentInstances));
			}
			
			@Override
			public void reset() {
				super.reset();
				currentInstances.reset();
			}
		};
		dynamicInstances.setResetable(currentInstances.isResetable());
		dynamicInstances.setExceptionHandler(currentInstances.getExceptionHandler());
		return dynamicInstances;
	}
	
	
	@Override
	protected final Iterator<List<List<Object>>> getCandidates(Pattern pattern) throws EolRuntimeException {
		return initGenerator(pattern);
	}
	
	
	/**
	 * Create a new CompositeCombinationGenerator, add the generator for each role and attach the validator.
	 * 
	 * @param pattern the pattern being executed
	 * @return the created generator
	 * @throws EolRuntimeException
	 */
	protected CompositeCombinationGenerator<Object> initGenerator(Pattern pattern) throws EolRuntimeException {
		CompositeCombinationGenerator<Object> generator = new CompositeCombinationGenerator<>();
		
		for (Role role : pattern.getRoles()) {
			generator.addCombinationGenerator(createCombinationGenerator(role));
		}
		
		generator.setValidator(combination -> isValidCombination(pattern, combination));
		return generator;
	}
	
	
	@SuppressWarnings("unchecked")
	protected CombinationGenerator<Object> createCombinationGenerator(Role role) throws EolRuntimeException {
		
		DynamicListCombinationGenerator<Object> combinationGenerator = new DynamicListCombinationGenerator<Object>(
			(List<Object>) getRoleInstances(role, null), role.getNames().size()) {
			
			@Override
			public boolean checkOptional() {
				try {
					return role.isOptional(context);
				}
				catch (EolRuntimeException ex) {
					throw new RuntimeException(ex);
				}
			}
		};
		
		combinationGenerator.addListener(new CombinationGeneratorListener<Object>() {
			final FrameStack frameStack = context.getFrameStack();
			
			@Override
			public void generated(Collection<Object> next) {
				if (next == null) {
					for (String name : role.getNames()) {
						frameStack.put(Variable.createReadOnlyVariable(name, NoMatch.INSTANCE));
					}
				}
				else {
					frameStack.put(getVariables(next, role));
				}
			}
			
			@Override
			public void reset() {
				frameStack.remove(role.getNames());
			}
		});
		
		return combinationGenerator;
	}

	@Override
	public EolDebugger createDebugger() {
		return new EplDebugger();
	}

	
}
