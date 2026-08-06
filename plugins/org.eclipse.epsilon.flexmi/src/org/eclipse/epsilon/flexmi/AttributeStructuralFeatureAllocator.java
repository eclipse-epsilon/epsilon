/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.flexmi.templates.Template;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeStructuralFeatureAllocator {
	
	public static Map<EClass, StringSimilarityProvider> eClass2StringSimilarityProvider = new HashMap<>();

	protected StringSimilarityProvider stringSimilarityProvider;

	public AttributeStructuralFeatureAllocator() {
		stringSimilarityProvider = new CachedStringSimilarityProvider(new DefaultStringSimilarityProvider());
	}

	public AttributeStructuralFeatureAllocator(EClass eclass) {
		stringSimilarityProvider = eClass2StringSimilarityProvider.get(eclass);
		if (stringSimilarityProvider == null) {
			stringSimilarityProvider = new CachedStringSimilarityProvider(new DefaultStringSimilarityProvider());
			eClass2StringSimilarityProvider.put(eclass, stringSimilarityProvider);
		}
	}

	public Map<Node, EStructuralFeature> allocate(NamedNodeMap attributes, List<EStructuralFeature> structuralFeatures) {

		final int attrLen = attributes.getLength();
		Map<String, String> attributeNamesMap = new HashMap<>();
		for (int i = 0; i < attrLen; i++) {
			// use only the latest apparition of an attribute (expression or not)
			String nodeName = attributes.item(i).getNodeName();
			attributeNamesMap.put(removePrefix(nodeName), nodeName);
		}
		List<String> attributeNames = new ArrayList<>(attributeNamesMap.values());
		
		List<String> structuralFeatureNames = new ArrayList<>(structuralFeatures.size());
		for (EStructuralFeature feature : structuralFeatures) {
			structuralFeatureNames.add(feature.getName());
		}
		
		Map<String, String> nameAllocation = allocate(attributeNames, structuralFeatureNames);
		Map<Node, EStructuralFeature> allocation = new HashMap<>();
		
		for (Map.Entry<String, String> entry : nameAllocation.entrySet()) {
			String structuralFeatureName = entry.getValue();
			EStructuralFeature feature = null;
			for (EStructuralFeature f : structuralFeatures) {
				if (structuralFeatureName.equals(f.getName())) {
					feature = f;
					break;
				}
			}
			allocation.put(attributes.getNamedItem(entry.getKey()), feature);
		}
		
		return allocation;
		
	}
	
	public Map<String, String> allocate(List<String> values, List<String> slots) {
		
		HashMap<String, String> result = new HashMap<>();
		for (String value : new ArrayList<>(values)) {
			String slot = slots.stream().filter(s -> s.equalsIgnoreCase(removePrefix(value))).findFirst().orElse(null);
			if (slot != null) {
				values.remove(value);
				slots.remove(slot);
				result.put(value, slot);
			}
		}

		if (!values.isEmpty()) {
			int[] bestSimilarity = new HungarianAllocator(values, slots).getResult();

			for (int value = 0; value < values.size(); value++) {
				if (bestSimilarity[value] < slots.size()) {
					result.put(values.get(value), slots.get(bestSimilarity[value]));
				}
			}
		}

		return result;
	}

	protected String removePrefix(String str) {
		if (str.startsWith(Template.PREFIX)) {
			str = str.substring(Template.PREFIX.length());
		}
		return str;
	}

	/**
	 * Class implementing the Hungarian algorithm for attribute to feature allocation
	 *
	 * Adapted from https://github.com/amirbawab/Hungarian-Algorithm
	 */
	public class HungarianAllocator {

		private int[][] originalValues; // Given values
		private int[][] values; // Cloned given values to be processed
		private int[][] lines; // Line drawn
		private int numLines; // Number of line drawn

		int rows[]; // Index of the column selected by every row (The final result)
		int occupiedCols[]; // Verify that all column are occupied, used in the optimization step

		public HungarianAllocator(List<String> attributeValues, List<String> slots) {
			int matrixSize = Math.max(attributeValues.size(), slots.size());
			originalValues = new int[matrixSize][matrixSize];

			for (int value = 0; value < matrixSize; value++) {
				for (int slot = 0; slot < matrixSize; slot++) {
					if (value >= attributeValues.size() || slot >= slots.size()) {
						originalValues[value][slot] = 0;
					}
					else {
						// the "-1" allows maximising the similarity
						originalValues[value][slot] = -1 * stringSimilarityProvider.getSimilarity(
								slots.get(slot), removePrefix(attributeValues.get(value)));
					}
				}
			}

			values = cloneMatrix(originalValues); // Cloned matrix to be processed
			rows = new int[values.length];
			occupiedCols = new int[values.length];

			//Algorithm
			subtractRowMinimal(); // Step 1
			subtractColMinimal(); // Step 2
			coverZeros(); // Step 3
			while (numLines < values.length) {
				createAdditionalZeros(); // Step 4 (Condition)
				coverZeros(); // Step 3 Again (Condition)
			}
			optimization(); // Optimization
		}

		/**
		 * Step 1
		 * Subtract from every element the minimum value from its row
		 */
		public void subtractRowMinimal() {
			int rowMinValue[] = new int[values.length];
			//get the minimum for each row and store in rowMinValue[]
			for (int row = 0; row < values.length; row++) {
				rowMinValue[row] = values[row][0];
				for (int col = 1; col < values.length; col++) {
					if (values[row][col] < rowMinValue[row])
						rowMinValue[row] = values[row][col];
				}
			}

			//subtract minimum from each row using rowMinValue[]
			for (int row = 0; row < values.length; row++) {
				for (int col = 0; col < values.length; col++) {
					values[row][col] -= rowMinValue[row];
				}
			}
		} //End Step 1

		/**
		 * Step 2
		 * Subtract from every element the minimum value from its column
		 */
		public void subtractColMinimal() {
			int colMinValue[] = new int[values.length];
			//get the minimum for each column and store them in colMinValue[]
			for (int col = 0; col < values.length; col++) {
				colMinValue[col] = values[0][col];
				for (int row = 1; row < values.length; row++) {
					if (values[row][col] < colMinValue[col])
						colMinValue[col] = values[row][col];
				}
			}

			//subtract minimum from each column using colMinValue[]
			for (int col = 0; col < values.length; col++) {
				for (int row = 0; row < values.length; row++) {
					values[row][col] -= colMinValue[col];
				}
			}
		} //End Step 2

		/**
		 * Step 3.1
		 * Loop through all elements, and run colorNeighbors when the element visited is equal to zero
		 */
		public void coverZeros() {
			numLines = 0;
			lines = new int[values.length][values.length];

			for (int row = 0; row < values.length; row++) {
				for (int col = 0; col < values.length; col++) {
					if (values[row][col] == 0)
						colorNeighbors(row, col, maxVH(row, col));
				}
			}
		}

		/**
		 * Step 3.2
		 * Checks which direction (vertical,horizontal) contains more zeros, every time a zero is found vertically, we increment the result
		 * and every time a zero is found horizontally, we decrement the result. At the end, result will be negative, zero or positive
		 * 
		 * @param row Row index for the target cell
		 * @param col Column index for the target cell
		 * @return Positive integer means that the line passing by indexes [row][col] should be vertical, Zero or Negative means that the line
		 *         passing by indexes [row][col] should be horizontal
		 */
		private int maxVH(int row, int col) {
			int result = 0;
			for (int i = 0; i < values.length; i++) {
				if (values[i][col] == 0)
					result++;
				if (values[row][i] == 0)
					result--;
			}
			return result;
		}

		/**
		 * Step 3.3
		 * Color the neighbors of the cell at index [row][col]. To know which direction to draw the lines, we pass maxVH value.
		 * 
		 * @param row   Row index for the target cell
		 * @param col   Column index for the target cell
		 * @param maxVH Value return by the maxVH method, positive means the line to draw passing by indexes [row][col] is vertical, negative or
		 *              zero means the line to draw passing by indexes [row][col] is horizontal
		 */
		private void colorNeighbors(int row, int col, int maxVH) {
			if (lines[row][col] == 2)
				// if cell is colored twice before (intersection cell), don't color it again
				return;

			if (maxVH > 0 && lines[row][col] == 1)
				// if cell colored vertically and needs to be recolored vertically, don't color it again
				// (Allowing this step, will color the same line (result won't change),
				// but the num of line will be incremented (wrong value for the num of line drawn))
				return;

			if (maxVH <= 0 && lines[row][col] == -1)
				// if cell colored horizontally and needs to be recolored horizontally, don't color it again
				// (Allowing this step, will color the same line (result won't change), 
				// but the num of line will be incremented (wrong value for the num of line drawn))
				return;

			for (int i = 0; i < values.length; i++) { // Loop on cell at indexes [row][col] and its neighbors
				if (maxVH > 0) // if value of maxVH is positive, color vertically
					// if cell was colored before as horizontal (-1), and now needs to be colored vertical (1), so this cell is an intersection (2).
					// Else if this value was not colored before, color it vertically
					lines[i][col] = lines[i][col] == -1 || lines[i][col] == 2 ? 2 : 1;

				else // if value of maxVH is zero or negative color horizontally
					// if cell was colored before as vertical (1), and now needs to be colored horizontal (-1), so this cell is an intersection (2).
					// Else if this value was not colored before, color it horizontally
					lines[row][i] = lines[row][i] == 1 || lines[row][i] == 2 ? 2 : -1; 
			}

			// increment line number
			numLines++;
			//			printMatrix(lines); // Monitor the line draw steps
		}//End step 3

		/**
		 * Step 4
		 * This step is not always executed. (Check the algorithm in the constructor)
		 * Create additional zeros, by coloring the minimum value of uncovered cells (cells not colored by any line)
		 */
		public void createAdditionalZeros() {
			// We don't know the value of the first uncovered cell, so we put a joker value 0
			// (0 is safe, because before this step, all zeros were covered)
			int minUncoveredValue = 0;

			// Find the min in the uncovered numbers
			for (int row = 0; row < values.length; row++) {
				for (int col = 0; col < values.length; col++) {
					if (lines[row][col] == 0 && (values[row][col] < minUncoveredValue || minUncoveredValue == 0))
						minUncoveredValue = values[row][col];
				}
			}

			// Subtract min form all uncovered elements, and add it to all elements covered twice
			for (int row = 0; row < values.length; row++) {
				for (int col = 0; col < values.length; col++) {
					if (lines[row][col] == 0) // If uncovered, subtract
						values[row][col] -= minUncoveredValue;

					else if (lines[row][col] == 2) // If covered twice, add
						values[row][col] += minUncoveredValue;
				}
			}
		} // End step 4

		/**
		 * Optimization, assign every row a cell in a unique column. Since a row can contain more than one zero,
		 * we need to make sure that all rows are assigned one cell from one unique column. To do this, use brute force
		 * 
		 * @param row
		 * @param boolean If all rows were assigned a cell from a unique column, return true (at the end, guarantee true)
		 * @return true
		 */
		private boolean optimization(int row) {
			if (row == rows.length) // If all rows were assigned a cell
				return true;

			for (int col = 0; col < values.length; col++) { // Try all columns
				// If the current cell at column `col` has a value of zero, and the column is not reserved by a previous row
				if (values[row][col] == 0 && occupiedCols[col] == 0) {
					rows[row] = col; // Assign the current row the current column cell
					occupiedCols[col] = 1; // Mark the column as reserved
					// If the next rows were assigned successfully a cell from a unique column, return true
					if (optimization(row + 1))
						return true;
					occupiedCols[col] = 0; // If the next rows were not able to get a cell, go back and try for the previous rows another cell from another column
				}
			}
			return false; // If no cell were assigned for the current row, return false to go back one row to try to assign to it another cell from another column
		}

		/**
		 * Overload optimization(int row) method
		 * 
		 * @return true
		 */
		public boolean optimization() {
			return optimization(0);
		} //End optimization

		/**
		 * Get the result by returning an array containing the cell assigned for each row
		 * 
		 * @return Array of rows where each array index represent the row number, and the value at each index is the column assigned to the
		 *         corresponding row
		 */
		public int[] getResult() {
			return rows;
		}

		/**
		 * Clone the 2D array
		 * 
		 * @return A copy of the 2D array
		 */
		public int[][] cloneMatrix(int[][] matrix) {
			int[][] tmp = new int[matrix.length][matrix.length];
			for (int row = 0; row < matrix.length; row++) {
				tmp[row] = matrix[row].clone();
			}
			return tmp;
		}
	}
}
