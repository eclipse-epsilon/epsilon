/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - revised assertions
 *     Sina Madani - parallel operations, automatic substitution
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.eol.execute.operations.declarative.*;
import org.eclipse.epsilon.eol.execute.operations.declarative.concurrent.*;
import org.eclipse.epsilon.eol.execute.operations.simple.assertions.*;
import org.eclipse.epsilon.eol.models.IModel;

public class EolOperationFactory {
	
	protected final Map<String, AbstractOperation> operationCache = new HashMap<>(64);
	
	public EolOperationFactory() {
		operationCache.put("assert", new AssertOperation());
		operationCache.put("assertError", new AssertErrorOperation());
		operationCache.put("atLeastNMatch", new NMatchOperation(NMatchOperation.MatchMode.MINIMUM));
		operationCache.put("atMostNMatch", new NMatchOperation(NMatchOperation.MatchMode.MAXIMUM));
		operationCache.put("collect", new CollectOperation());
		operationCache.put("count", new CountOperation());
		operationCache.put("exists", new ExistsOperation());
		operationCache.put("none", new NMatchOperation(NMatchOperation.MatchMode.EXACT, 0));
		operationCache.put("one", new NMatchOperation(NMatchOperation.MatchMode.EXACT, 1));
		operationCache.put("nMatch", new NMatchOperation(NMatchOperation.MatchMode.EXACT));
		operationCache.put("forAll", new ForAllOperation());
		operationCache.put("reject", new RejectOperation());
		operationCache.put("rejectOne", new RejectOneOperation());
		operationCache.put("select", new SelectOperation());
		operationCache.put("selectOne", new SelectOneOperation());
		operationCache.put("selectFirst", new SelectFirstOperation());
		operationCache.put("aggregate", new AggregateOperation());
		operationCache.put("closure", new ClosureOperation());
		operationCache.put("sortBy", new SortByOperation());
		operationCache.put("mapBy", new MapByOperation());
		operationCache.put("as", new AsOperation());
		operationCache.put("find", new FindOperation());
		operationCache.put("findOne", new FindOneOperation());
		operationCache.put("parallelCount", new ParallelCountOperation());
		operationCache.put("parallelSelect", new ParallelSelectOperation());
		operationCache.put("parallelSelectOne", new ParallelSelectOneOperation());
		operationCache.put("parallelReject", new ParallelRejectOperation());
		operationCache.put("parallelRejectOne", new ParallelRejectOneOperation());
		operationCache.put("parallelOne", new ParallelNMatchOperation(NMatchOperation.MatchMode.EXACT, 1));
		operationCache.put("parallelNone", new ParallelNMatchOperation(NMatchOperation.MatchMode.EXACT, 0));
		operationCache.put("parallelNMatch", new ParallelNMatchOperation(NMatchOperation.MatchMode.EXACT));
		operationCache.put("parallelAtMostNMatch", new ParallelNMatchOperation(NMatchOperation.MatchMode.MAXIMUM));
		operationCache.put("parallelAtLeastNMatch", new ParallelNMatchOperation(NMatchOperation.MatchMode.MINIMUM));
		operationCache.put("parallelExists", new ParallelExistsOperation());
		operationCache.put("parallelForAll", new ParallelForAllOperation());
		operationCache.put("parallelCollect", new ParallelCollectOperation());
		operationCache.put("parallelSortBy", new ParallelSortByOperation());
		operationCache.put("parallelMapBy", new ParallelMapByOperation());
		createCache();
	}
	
	/**
	 * For backwards compatibility with 1.5
	 * @deprecated Add methods to the cache in the constructor instead
	 */
	@Deprecated
	protected void createCache() {}
	
	/**
	 * Retrieves the exact operation based on the given name.
	 * 
	 * @param name The operation name.
	 * @return The cached operation, or <code>null</code> if no operation could be found.
	 */
	public AbstractOperation getOperationFor(String name) {
		return operationCache.get(name);
	}
	
	/**
	 * 
	 * @param name The operation name
	 * @param owningModel 
	 * @param target 
	 * @param context The context
	 * @return An optimal implementation for the requested operation, based on the context's state.
	 * @since 1.6
	 */
	public AbstractOperation getOptimisedOperation(String name, Object target, IModel owningModel, IEolContext context) {
		AbstractOperation originalOp = operationCache.get(name);
		if (isOverridenDelegate(originalOp)) {
			return originalOp;
		}
		if (originalOp != null && context instanceof IEolContextParallel &&
			target instanceof Collection &&
			((IEolContextParallel) context).isParallelisationLegal() &&
			!name.startsWith("parallel") &&
			((Collection<?>) target).size() >= ((IEolContextParallel) context).getParallelism()
		) {
			AbstractOperation parallelOp = operationCache.get("parallel" + StringUtil.firstToUpper(name));
			if (parallelOp != null) return parallelOp;
		}
		if (originalOp == null && name.startsWith("sequential")) {
			AbstractOperation sequentialOp = operationCache.get(StringUtil.firstToLower(name.substring(10)));
			if (sequentialOp != null) return sequentialOp;
		}
		return originalOp;
	}
	
	/**
	 * Checks whether the given operation is a {@linkplain DelegateBasedOperation} and if so, whether
	 * its delegate operation is unknown (built-in) to this factory.
	 * 
	 * @param operation The operation to test.
	 * @return <code>true</code> if the operation has an unrecognised delegate, <code>false</code> otherwise.
	 * @since 1.6
	 */
	public boolean isOverridenDelegate(AbstractOperation operation) {
		if (operation instanceof DelegateBasedOperation<?>) {
			AbstractOperation delegate = ((DelegateBasedOperation<?>) operation).getDelegateOperation();
			if (delegate == null) return false;
			else {
				Class<? extends AbstractOperation> delegateClass = delegate.getClass();
				for (AbstractOperation op : operationCache.values()) {
					if (op.getClass() == delegateClass) return false;
				}
				return true;
			}
		}
		else return false;
	}
}
