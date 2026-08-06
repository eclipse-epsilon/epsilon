/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Martins Francis - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.spreadsheets;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetReferenceMetadata;

/**
 * This class represents a reference between two worksheets and two columns.
 * Reference has multiplicity one-to-one or one-to-many meaning that either the
 * first referenced row is relevant or all of them. This is most important when
 * reading spreadsheet data. Reference may also cascade updates - this means
 * that any changes to the referenced cell would be cascaded to all referencing
 * rows.
 * 
 * @author Martins Francis
 */
public class SpreadsheetReference {
	protected SpreadsheetModel model;
	protected SpreadsheetWorksheet referencingWorksheet;
	protected SpreadsheetWorksheet referencedWorksheet;
	protected SpreadsheetColumn referencingColumn;
	protected SpreadsheetColumn referencedColumn;
	protected boolean many;
	protected boolean cascadingUpdates;

	/**
	 * Constructs an object representing a reference between two worksheets and two
	 * columns as specified in the provided reference metadata.
	 * 
	 * @param model
	 * @param reference
	 */
	public SpreadsheetReference(final SpreadsheetModel model, final SpreadsheetReferenceMetadata reference) {
		if (reference.source == null || reference.target == null) {
			throw new IllegalArgumentException("The source or target of the reference metadata has not been defined");
		}

		this.model = model;

		this.constructReferenceFromMetadata(reference);

		this.many = this.getBooleanOrDefault(reference.getMany(), SpreadsheetConstants.DEFAULT_REFERENCE_MANY);
		this.cascadingUpdates = this.getBooleanOrDefault(reference.getCascadeUpdates(),
			SpreadsheetConstants.DEFAULT_REFERENCE_CASCADE);
	}

	private void constructReferenceFromMetadata(final SpreadsheetReferenceMetadata reference) {
		final ExtractedReference sourceReference = this.extractReferenceFromMetadata(reference.getSource());
		this.referencingWorksheet = sourceReference.worksheet;
		this.referencingColumn = sourceReference.column;

		final ExtractedReference targetReference = this.extractReferenceFromMetadata(reference.getTarget());
		this.referencedWorksheet = targetReference.worksheet;
		this.referencedColumn = targetReference.column;

		this.validateExtractedReference();
	}

	private static class ExtractedReference {
		SpreadsheetWorksheet worksheet = null;
		SpreadsheetColumn column = null;
	}

	private ExtractedReference extractReferenceFromMetadata(final String plainReference) {
		final ExtractedReference extractedReference = new ExtractedReference();
		final String[] plainReferenceComponents = plainReference.split(ORMConstants.ORM_REFERENCE_SEPARATOR);
		if (plainReferenceComponents.length == 2) {
			final SpreadsheetWorksheet worksheet = this.model.getWorksheetByType(plainReferenceComponents[0]);
			if (worksheet != null) {
				extractedReference.worksheet = worksheet;
				extractedReference.column = worksheet.getColumn(plainReferenceComponents[1]);
			}
		}
		return extractedReference;
	}

	private void validateExtractedReference() {
		final boolean worksheetsAreNotSet = this.referencingWorksheet == null || this.referencedWorksheet == null;
		if (worksheetsAreNotSet) {
			throw new IllegalArgumentException("Reference source or target worksheet could not be found");
		}

		final boolean columnsAreNotSet = this.referencingColumn == null || this.referencedColumn == null;
		if (columnsAreNotSet) {
			throw new IllegalArgumentException("Reference source or target column could not be found");
		}

		final boolean columnIsReferencingItself = this.referencingWorksheet == this.referencedWorksheet
			&& this.referencingColumn == this.referencedColumn;
		if (columnIsReferencingItself) {
			throw new IllegalArgumentException("Column may not reference itself: '" + this.referencingWorksheet + "'->'"
				+ this.referencingColumn + "'");
		}
	}

	private boolean getBooleanOrDefault(final String value, final boolean defaultValue) {
		return StringUtil.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value);
	}

	public SpreadsheetModel getModel() {
		return model;
	}

	public SpreadsheetWorksheet getReferencingWorksheet() {
		return referencingWorksheet;
	}

	public SpreadsheetWorksheet getReferencedWorksheet() {
		return referencedWorksheet;
	}

	public SpreadsheetColumn getReferencingColumn() {
		return referencingColumn;
	}

	public SpreadsheetColumn getReferencedColumn() {
		return referencedColumn;
	}

	public boolean isMany() {
		return many;
	}

	public boolean isCascadingUpdates() {
		return cascadingUpdates;
	}

	@Override
	public String toString() {
		return "SpreadsheetReference [model=" + model + ", referencingWorksheet=" + referencingWorksheet
			+ ", referencedWorksheet=" + referencedWorksheet + ", referencingColumn=" + referencingColumn
			+ ", referencedColumn=" + referencedColumn + ", many=" + many + ", cascadingUpdates=" + cascadingUpdates
			+ "]";
	}

}
