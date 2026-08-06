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

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.erl.strategy.IEquivalentProvider;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public interface ITransformationStrategy extends IEquivalentProvider {
	
	public Collection<?> transform(Object source, IEtlContext context, List<String> rules) throws EolRuntimeException;
		
	public void transformModels(IEtlContext context) throws EolRuntimeException;

	public void setEquivalentProvider(IEquivalentProvider equivalentProvider);

	public boolean canTransform(Object source);
	
	public default IEquivalentProvider getEquivalentProvider() {
		return this;
	}
	
	@Override
	public default Object getEquivalent(Object source, IErlContext context_, List<String> rules) throws EolRuntimeException {
		IEtlContext context = (IEtlContext) context_;
		
		Collection<?> equivalents = getEquivalents(source, context, rules);
		
		if (equivalents != null && !equivalents.isEmpty()) {
			return CollectionUtil.getFirst(equivalents);
		}
		else {
			return null;
		}
	}
	
	@Override
	public default Collection<?> getEquivalent(Collection<?> collection, IErlContext context_, List<String> rules) throws EolRuntimeException {
		IEtlContext context = (IEtlContext) context_;
		return CollectionUtil.flatten(getEquivalents(collection, context, rules));
	}
	
	@Override
	public default Collection<?> getEquivalents(Collection<?> collection, IErlContext context_, List<String> rules) throws EolRuntimeException {
		IEtlContext context = (IEtlContext) context_;
		Collection<Object> equivalents = CollectionUtil.createDefaultList();
		for (Object item : collection) {
			Object equivalent = getEquivalents(item, context, rules);
			if (equivalent != null && !equivalents.contains(equivalent)) {
				equivalents.add(equivalent);
			}
		}
		return equivalents;
	}
	
}
