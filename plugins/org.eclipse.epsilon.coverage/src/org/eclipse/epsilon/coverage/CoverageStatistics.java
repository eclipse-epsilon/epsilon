/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;

/**
 * Computes (and caches) probe coverage counters for the elements of a coverage model.
 * 
 * @since 2.10
 */
public class CoverageStatistics {
	
	/**
	 * Covered/total probe counts
	 */
	public static class Counter {
		protected int covered, total;
		
		public int getCovered() {
			return covered;
		}
		
		public int getMissed() {
			return total - covered;
		}
		
		public int getTotal() {
			return total;
		}
		
		/**
		 * @return The covered ratio (0..1), or 1 if there are no probes
		 */
		public double getRatio() {
			return total == 0 ? 1 : ((double) covered) / total;
		}
		
		protected void add(Counter other) {
			covered += other.covered;
			total += other.total;
		}
		
		@Override
		public String toString() {
			return covered + "/" + total;
		}
	}
	
	protected final Map<EObject, Counter> counters = new IdentityHashMap<>();
	
	public synchronized Counter get(EObject object) {
		Counter counter = counters.get(object);
		if (counter == null) {
			counter = compute(object);
		}
		return counter;
	}
	
	protected Counter compute(EObject object) {
		Counter counter = new Counter();
		if (object instanceof CoverageSession) {
			for (SourceFile file : ((CoverageSession) object).getFiles()) counter.add(get(file));
		}
		else if (object instanceof SourceFile) {
			for (CoverageElement element : ((SourceFile) object).getElements()) counter.add(get(element));
		}
		else if (object instanceof CoverageElement) {
			CoverageElement element = (CoverageElement) object;
			if (element.isProbe()) {
				counter.total++;
				if (element.getHits() > 0) counter.covered++;
			}
			for (CoverageElement child : element.getChildren()) counter.add(get(child));
		}
		counters.put(object, counter);
		return counter;
	}
	
	/**
	 * Discards cached values (e.g. after the model has been modified)
	 */
	public synchronized void clear() {
		counters.clear();
	}
}
