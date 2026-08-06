/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolMap;
import org.eclipse.epsilon.eol.types.EolTuple;
import org.eclipse.epsilon.eol.types.concurrent.EolConcurrentMap;

public class MapLiteralExpression<K, V> extends LiteralExpression<Map<K, V>> {
	
	protected List<Entry<Expression, Expression>> keyValueExpressionPairs = new ArrayList<>();
	protected String mapName;
	
	/**
	 * 
	 * @param mapName
	 * @return
	 * @since 2.1
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> createMap(String mapName) {
		switch (mapName) {
			case "Map": return new EolMap<>();
			case "ConcurrentMap": return new EolConcurrentMap<>();
			case "Tuple": return (Map<K, V>) new EolTuple();
			default: return null;
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.mapName = cst.getText();
		
		final AST keyvalListAST = cst.getFirstChild();

		if (keyvalListAST != null) {
			for (AST keyValAst : keyvalListAST.getChildren()) {
				keyValueExpressionPairs.add(new SimpleEntry<>(
						(Expression) module.createAst(keyValAst.getFirstChild(), this),
						(Expression) module.createAst(keyValAst.getSecondChild(), this)
				));
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> execute(IEolContext context) throws EolRuntimeException {
		final Map<K, V> map = createMap(mapName);
		if (map == null) {
			throw new EolRuntimeException("Unknown map type: "+mapName);
		}
		
		ExecutorFactory executorFactory = context.getExecutorFactory();
		boolean tuple = isTuple();
		
		for (Entry<Expression, Expression> keyValueExpressionPair : keyValueExpressionPairs) {
			final Expression keyExpr = keyValueExpressionPair.getKey();
			final K key;
			if (tuple && keyExpr instanceof NameExpression) {
				key = (K) ((NameExpression) keyExpr).getName();
			}
			else {
				key = (K) executorFactory.execute(keyExpr, context);
			}
			final V val = (V) executorFactory.execute(keyValueExpressionPair.getValue(), context);
			map.put(key, val);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @return
	 * @since 2.2
	 */
	public boolean isTuple() {
		return "Tuple".equals(mapName);
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public List<Entry<Expression, Expression>> getKeyValueExpressionPairs() {
		return keyValueExpressionPairs;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
