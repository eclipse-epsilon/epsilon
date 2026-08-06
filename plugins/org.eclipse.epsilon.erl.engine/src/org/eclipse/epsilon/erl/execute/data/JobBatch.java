/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.execute.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Simple convenience input for splitting deterministically-ordered
 * jobs and processing them in chunks based on their position in the list.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class JobBatch implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 5469863968982457471L;
	
	/**
	 * The start index.
	 */
	public int from;
	
	/**
	 * The end index.
	 */
	public int to;
	
	public JobBatch() {}
	
	public JobBatch(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	protected JobBatch clone() {
		JobBatch clone;
		try {
			clone = (JobBatch) super.clone();
		}
		catch (CloneNotSupportedException cnsx) {
			throw new UnsupportedOperationException(cnsx);
		}
		clone.from = this.from;
		clone.to = this.to;
		return clone;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof JobBatch)) return false;
		JobBatch other = (JobBatch) obj;
		return this.from == other.from && this.to == other.to;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [from="+from+", to="+to+"]";
	}
	
	/**
	 * Provides a List of indices based on the desired split size.
	 * 
	 * @param totalJobs The size of the source List being split
	 * @param chunks The range (i.e. <code>to - from</code>) of each batch.
	 * The last batch may be smaller than this but the other batches are guaranteed
	 * to be of this size.
	 * @return A Serializable List of indexes with {@code totalJobs/batches} increments.
	 */
	public static List<JobBatch> getBatches(int totalJobs, int chunks) {
		assert totalJobs >= 1 : "Must have at least one job";
		assert chunks >= 1 : "Batch size (chunks) must be at least 1";
		
		final int
			modulo = totalJobs % chunks,
			division = totalJobs / chunks,
			batches = modulo > 0 ? 1 + division : division;
		
		ArrayList<JobBatch> resultList = new ArrayList<>(batches);

		for (int prev = 0, curr = chunks; curr <= totalJobs; curr += chunks) {
			resultList.add(new JobBatch(prev, prev = curr));
		}
		
		if (batches > 0 && modulo > 0) {
			resultList.add(new JobBatch(totalJobs - modulo, totalJobs));
		}
		
		assert resultList.size() == batches : "Expected number of batches met";
		assert resultList.get(batches-1).to == totalJobs &&
			   resultList.get(0).from == 0
			: "All jobs retained";
		
		return resultList;
	}

	public <T> List<T> splitToList(T[] arr) {
		return Arrays.asList(split(arr));
	}
	public <T> T[] split(T[] arr) {
		return Arrays.copyOfRange(arr, from, to);
	}
	
	/**
	 * Splits the given list based on this class's indices.
	 * @param <T> The type of the List
	 * @param list The list to call {@link List#subList(int, int)} on
	 * @return The split list.
	 */
	public <T> List<T> split(List<T> list) {
		return list.subList(from, to);
	}
}
