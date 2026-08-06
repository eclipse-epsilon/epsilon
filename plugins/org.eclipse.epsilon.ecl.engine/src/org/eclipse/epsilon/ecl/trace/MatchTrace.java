/*******************************************************************************
 * Copyright (c) 2008-2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - change to use bidi maps
 ******************************************************************************/
package org.eclipse.epsilon.ecl.trace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

/**
 * 
 *
 * @since 1.6 Can be accessed through Collection API rather than getMatches for convenience
 */
public class MatchTrace implements Collection<Match> {
	/*
	 * AVOID DIRECTLY ACCESSING THIS FIELD: use syncOnGraph() instead. This ensures
	 * we synchronize on the graph if we're in a concurrent scenario.
	 */
	protected MutableNetwork<Object, Match> _matchGraph;

	/* 
	 * Needed to compute "first match" - whole set of edges is insertion-ordered,
	 * but not the "small views" of in/out edges. Consistent insertion-based ordering
	 * is supported in Guava for Graph and ValueGraph, but not Network.
	 *
	 * @see https://github.com/google/guava/issues/2650
	 */
	protected final AtomicLong creationCounter = new AtomicLong();
	protected final Map<Match, Long> matchOrder = new IdentityHashMap<>();

	private class MatchInsertionComparator implements Comparator<Match> {
		@Override
		public int compare(Match o1, Match o2) {
			Long l1 = matchOrder.get(o1);
			Long l2 = matchOrder.get(o2);

			if (l1 == null || l2 == null) {
				throw new IllegalStateException("A match not part of the network was compared");
			}
			return l1.compareTo(l2);
		}
	}

	protected final boolean concurrent;
	protected String toStringCached;

	public MatchTrace() {
		this(false);
	}
	
	public MatchTrace(boolean concurrent) {
		this.concurrent = concurrent;
		_matchGraph = createNetwork();
	}

	private MutableNetwork<Object, Match> createNetwork() {
		return NetworkBuilder.directed()
			.allowsParallelEdges(true)
			.allowsSelfLoops(true)
			.nodeOrder(ElementOrder.unordered())
			.edgeOrder(ElementOrder.insertion())
			.expectedNodeCount(1_000)
			.expectedEdgeCount(1_000)
			.build();
	}

	public MatchTrace(MatchTrace copy) {
		this.concurrent = Objects.requireNonNull(copy).concurrent;

		this.creationCounter.set(copy.creationCounter.get());
		this.matchOrder.putAll(copy.matchOrder);
		copy.syncOnGraph((graph) -> {
			_matchGraph = Graphs.copyOf(copy._matchGraph);
			return null;
		});
	}

	private <T> T syncOnGraph(Function<MutableNetwork<Object, Match>, T> supplier) {
		if (concurrent) {
			synchronized (_matchGraph) {
				return supplier.apply(_matchGraph);
			}
		} else {
			return supplier.apply(_matchGraph);
		}
	}

	/**
	 * Returns a trace with only the successful matches.
	 */
	public MatchTrace getReduced() { 
		return syncOnGraph((graph) -> {
			MatchTrace reduced = new MatchTrace(concurrent);
			for (Match match: graph.edges()) {
				if (match.isMatching()) {
					reduced.add(match);
				}
			}
			return reduced;
		});
	}

	public Match add(Object left, Object right, boolean matching, MatchRule rule) {
		Match match = new Match(left, right, matching, rule);
		add(match);
		return match;
	}
	
	/**
	 * Returns the first match with a certain {@code left} and {@code right} object.
	 */
	public Match getMatch(Object left, Object right) {
		return syncOnGraph((graph) -> {
			Set<Object> nodes = graph.nodes();
			if (!nodes.contains(left) || !nodes.contains(right)) {
				return null;
			}
			
			Set<Match> edges = graph.edgesConnecting(left, right);
			return edges.isEmpty() ? null : Collections.min(edges, new MatchInsertionComparator());
		});
	}

	/**
	 * Returns all the matches for a given object
	 * @param object
	 * @return
	 */
	public Collection<Match> getMatches(Object object) {
		return syncOnGraph((graph) -> {
			if (!graph.nodes().contains(object)) {
				return Collections.emptyList();
			}
			return graph.incidentEdges(object);
		});
	}

	/**
	 * Returns the first match for the object
	 * @param object
	 * @return
	 */
	public Match getMatch(Object object) {
		return syncOnGraph((graph) -> {
			if (!graph.nodes().contains(object)) return null;

			// Try left-to-right edges first, then right-to-left edges
			List<Match> edges = new ArrayList<>(graph.incidentEdges(object));
			Collections.sort(edges, new MatchInsertionComparator());
			for (Match m : edges) {
				if (m.isMatching()) {
					return m;
				}
			}

			return null;
		});
	}

	/**
	 * Returns the first match which has a certain object on its left side and relates to a certain rule.
	 */
	public Match getMatch(Object left, MatchRule rule) {
		return syncOnGraph((graph) -> {
			if (!graph.nodes().contains(left)) return null;

			List<Match> outEdges = new ArrayList<>(graph.outEdges(left));
			Collections.sort(outEdges, new MatchInsertionComparator());
			for (Match match : outEdges) {
				if (match.isMatching() && match.getRule() == rule) {
					return match;
				}
			}
			return null;
		});
	}

	public boolean hasBeenMatched(Object object) {
		return syncOnGraph((graph) -> graph.nodes().contains(object));
	}

	public String toString(IEolContext context) {
		StringBuilder sb = new StringBuilder();

		syncOnGraph((graph) -> {
			for (Match match : graph.edges()) {
				sb.append("[");
				sb.append(match.isMatching());
				sb.append("]\n");

				sb.append(context.getPrettyPrinterManager().toString(match.getLeft()));
				sb.append("\n ->");
				sb.append(context.getPrettyPrinterManager().toString(match.getRight()));
			}
			return null;
		});

		sb.append("\n-------------------------------------------");
		return toStringCached = sb.toString();
	}

	/**
	 * Returns all matches, both successful and 
	 * pairs that have been compared but do not match
	 * @return
	 */
	public Collection<Match> getMatches() {
		return syncOnGraph((graph) -> graph.edges());
	}

	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	@Override
	public Stream<Match> stream() {
		return syncOnGraph((graph) -> graph.edges().stream());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public String toString() {
		return toStringCached != null ? toStringCached : super.toString();
	}
	
	@Override
	public int hashCode() {
		return syncOnGraph((graph) -> graph.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		// We cannot directly use equals() across maps as they are identity-based
		MatchTrace other = (MatchTrace) obj;
		return syncOnGraph((myGraph) -> other.syncOnGraph((otherGraph) -> myGraph.equals(otherGraph)));
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean add(Match match) {
		return syncOnGraph((graph) -> internalAdd(match, graph));
	}

	private boolean internalAdd(Match match, MutableNetwork<Object, Match> graph) {
		boolean changed = graph.addEdge(match.getLeft(), match.getRight(), match);
		if (changed) {
			toStringCached = null;
			matchOrder.put(match, creationCounter.incrementAndGet());
		}
		return changed;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean remove(Object o) {
		if (!(o instanceof Match)) {
			return false;
		}
		Match m = (Match) o;
		return syncOnGraph((graph) -> internalRemove(m, graph));
	}

	private boolean internalRemove(Match m, MutableNetwork<Object, Match> graph) {
		boolean changed = graph.removeEdge(m);
		if (changed) {
			toStringCached = null;
			matchOrder.remove(m);
		}
		return changed;
	}

	/**
	 * @since 1.6
	 */
	@Override
	public int size() {
		return syncOnGraph((graph) -> graph.edges().size());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean isEmpty() {
		return syncOnGraph((graph) -> graph.edges().isEmpty());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean contains(Object o) {
		if (!(o instanceof Match)) {
			return false;
		}
		return syncOnGraph((graph) -> graph.edges().contains(o));
	}

	/**
	 * @since 1.6
	 */
	@Override
	public Iterator<Match> iterator() {
		return syncOnGraph((graph) -> graph.edges().iterator());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public Object[] toArray() {
		return syncOnGraph((graph) -> graph.edges().toArray());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return syncOnGraph((graph) -> graph.edges().toArray(a));
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return syncOnGraph((graph) -> graph.edges().containsAll(c));
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean addAll(Collection<? extends Match> c) {
		return syncOnGraph((graph) -> {
			boolean changed = false;
			for (Match m : c) {
				changed = internalAdd(m, graph) || changed;
			}
			return changed;
		});
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return syncOnGraph((graph) -> {
			boolean ret = false;
			for (Object o : c) {
				if (o instanceof Match) {
					Match m = (Match) o;
					ret = internalRemove(m, graph) || ret;
				}
			}
			return ret;
		});
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return syncOnGraph((graph) -> {
			boolean changed = this.matchOrder.keySet().retainAll(c);

			// Rebuild the network after the retain on the Map
			_matchGraph = createNetwork();
			for (Match m : this.matchOrder.keySet()) {
				_matchGraph.addEdge(m.getLeft(), m.getRight(), m);
			}

			return changed;
		});
	}

	/**
	 * @since 1.6
	 */
	@Override
	public void clear() {
		syncOnGraph((graph) -> {
			this.toStringCached = null;
			this.matchOrder.clear();
			this._matchGraph = createNetwork();
			return null;
		});
	}

	/**
	 * @since 1.6
	 */
	@Override
	public Stream<Match> parallelStream() {
		return syncOnGraph((graph) -> graph.edges().parallelStream());
	}

}
