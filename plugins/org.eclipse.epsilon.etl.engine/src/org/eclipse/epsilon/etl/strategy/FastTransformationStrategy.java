/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;
import org.eclipse.epsilon.etl.trace.Transformation;
import org.eclipse.epsilon.etl.trace.TransformationTrace;

public class FastTransformationStrategy extends AbstractTransformationStrategy {
	
	protected Map<Object, Collection<Object>> flatTrace = new HashMap<>();
	protected Map<Object, Collection<Transformation>> pendingTransformations = new HashMap<>();
	
	@Override
	public Collection<?> transform(Object source, IEtlContext context, List<String> rules) throws EolRuntimeException {	
		throw new UnsupportedOperationException(
				"FastTransformationStrategy cannot transform single objects. " +
				"Please use DefaultTransformationStrategy instead.");
	}
	
	@Override
	public Collection<?> getEquivalents(Object source, IErlContext context_, List<String> rules) throws EolRuntimeException {
		IEtlContext context = (IEtlContext) context_;
		
		if (pendingTransformations.containsKey(source)) {
			Collection<Transformation> transformations = pendingTransformations.remove(source);
			executeTransformations(transformations, context);
		}
		
		if (rules == null || rules.isEmpty()) return flatTrace.get(source);
		
		Collection<Object> equivalents = new ArrayList<>();
		for (Transformation transformation : context.getTransformationTrace().getTransformations(source)) {
			if (rules.contains(transformation.getRule().getName())) {
				equivalents.addAll(transformation.getTargets());
			}
		}
		return equivalents;
	}
	
	@Override
	public void transformModels(IEtlContext context) throws EolRuntimeException {
		
		TransformationTrace transTrace = context.getTransformationTrace();
		
		for (TransformationRule transformRule : context.getModule().getTransformationRules()) {			
			if (!transformRule.isLazy(context) && !transformRule.isAbstract(context)) {
				Collection<?> sources = transformRule.getAllInstances(transformRule.getSourceParameter(), context, !transformRule.isGreedy(context));
				
				for (Object instance : sources) {
					if (!getExcluded().contains(instance) && transformRule.appliesTo(instance, context, false, false)) {
						
						Collection<Object> targets = CollectionUtil.createDefaultList();

						for (int i = 0; i < transformRule.getTargetParameters().size(); i++) {
							Parameter p = transformRule.getTargetParameters().get(i);

							Optional<Expression> init = transformRule.getTargetParameterInitializers().get(i);
							if (init.isPresent()) {
								context.getFrameStack().enterLocal(
									FrameType.UNPROTECTED, init.get(),
									Collections.singletonMap(
										transformRule.getSourceParameter().getName(),
										instance));
								try {
									targets.add(init.get().execute(context));
								} finally {
									context.getFrameStack().leaveLocal(init.get());
								}
							} else {
								targets.add(p.getType(context).createInstance());
							}
						}

						transTrace.add(instance, targets, transformRule);
					}
				}
			}
		}
		
		for (Transformation transformation : transTrace.getTransformations()) {
			Object source = transformation.getSource();
			
			if (flatTrace.containsKey(transformation.getSource())) {
				if (transformation.getRule().isPrimary(context)) {
					flatTrace.put(source, 
							EolCollectionType.join(transformation.getTargets(), flatTrace.get(source)));
				}
				else {
					flatTrace.get(source).addAll(transformation.getTargets());
				}
			}
			else {
				flatTrace.put(source, EolCollectionType.clone(transformation.getTargets()));
			}
			
			Collection<Transformation> pending = pendingTransformations.get(source);
			if (pending != null) {
				pending.add(transformation);
			}
			else {
				Collection<Transformation> transformations = new ArrayList<>(1);
				transformations.add(transformation);
				pendingTransformations.put(source, transformations);
			}
			
		}
		
		executeTransformations(transTrace.getTransformations(), context);
	}
	
	protected void executeTransformations(Collection<Transformation> transformations, IEtlContext context) throws EolRuntimeException {
		for (Transformation transformation : transformations) {
			TransformationRule rule = transformation.getRule();
			Object source = transformation.getSource();
			if (!rule.hasTransformed(source)) {
				rule.transform(source, transformation.getTargets(), context);
			}
		} 		
	}
}
