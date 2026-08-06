/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset;

import java.util.HashMap;
import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * PostProcessing.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PostProcessing {

	/**
	 * FillType.
	 *
	 * @author Alfonso de la Vega
	 * @since 2.1
	 */
	public enum FillType {
		MEAN, MODE, VALUE
	};

	public static void normalize(List<ValueWrapper> values, Number value) throws EolRuntimeException {
		double normValue = value != null ? value.doubleValue() : max(values);
		for (ValueWrapper wrapper : values) {
			if (wrapper.get() != null) {
				wrapper.set(((Number) wrapper.get()).doubleValue() / normValue);
			}
		}
	}

	private static double max(List<ValueWrapper> values) throws EolRuntimeException {
		double max = Double.MIN_VALUE;
		for (ValueWrapper wrapper : values) {
			if (wrapper.get() != null) {
				if (!(wrapper.get() instanceof Number)) {
					throw new EolRuntimeException("Cannot calculate mean over non-numeric elements");
				}
				double value = ((Number) wrapper.get()).doubleValue();
				if (value > max) {
					max = value;
				}
			}
		}
		return max;
	}

	public static void fillNullValues(List<ValueWrapper> values,
			FillType fillType, String value) throws RuntimeException {
		switch (fillType) {
		case VALUE:
			fillNullsWithValue(values, value);
			break;
		case MEAN:
			fillNullsWithMean(values);
			break;
		case MODE:
			fillNullsWithMode(values);
			break;
		}
	}

	private static void fillNullsWithMode(List<ValueWrapper> values) {
		Object mode = mode(values);
		fillNulls(values, mode);
	}

	private static Object mode(List<ValueWrapper> wrappers) {
		HashMap<Object, Integer> counts = new HashMap<>();
		Object mode = null;
		int maxCount = 0;
		for (ValueWrapper wrapper : wrappers) {
			int count = 0;
			Object value = wrapper.get();
			if (value == null) {
				continue;
			}
			if (counts.get(value) == null) {
				count = 1;
			}
			else {
				count = counts.get(value) + 1;
			}
			counts.put(value, count);
			if (count > maxCount) {
				maxCount = count;
				mode = value;
			}
		}
		return mode;
	}

	private static void fillNullsWithMean(List<ValueWrapper> values) {
		Double mean = mean(values);
		// fix column types (an integer column is traduced to double)
		for (ValueWrapper wrapper : values) {
			if (wrapper.get() != null) {
				if (wrapper.get() instanceof Double) {
					break; // asumes every column is double
				}
				wrapper.set(((Number) wrapper.get()).doubleValue());
			}
		}
		fillNulls(values, mean);
	}

	private static double mean(List<ValueWrapper> values) {
		double mean = 0.0;
		int elems = 0;
		for (ValueWrapper wrapper : values) {
			Object value = wrapper.get();
			if (value != null) {
				if (!(value instanceof Number)) {
					throw new RuntimeException(
							"Cannot calculate mean over non-numeric elements");
				}
				mean += ((Number) value).doubleValue();
				elems += 1;
			}
		}
		return elems > 0 ? mean / elems : mean;
	}

	private static void fillNullsWithValue(List<ValueWrapper> values,
			String value) {
		if (value == null) {
			return;
		}
		// TODO: this does not check types (e.g. puts a string in an integer col)
		// it is not that much of a problem as filling nulls is the last
		// postprocessing step before generating datasets (no more operations ahead)
		// it could be a problem if escaping strings (e.g. adding \" to csv file)
		fillNulls(values, value);
	}

	private static void fillNulls(List<ValueWrapper> values, Object value) {
		for (ValueWrapper wrapper : values) {
			if (wrapper.get() == null) {
				wrapper.set(value);
			}
		}
	}
}
