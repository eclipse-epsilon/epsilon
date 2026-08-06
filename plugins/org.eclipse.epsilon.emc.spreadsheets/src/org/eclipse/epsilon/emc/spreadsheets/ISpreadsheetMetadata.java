/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets;

import java.util.Set;

/**
 * Any concrete implementation of this interface can be used for collecting
 * spreadsheet ORM metadata.
 * 
 * @author Martins Francis
 */
public interface ISpreadsheetMetadata {
	/**
	 * The purpose of this method is to get metadata for every worksheet specified
	 * in the ORM metadata source.
	 * 
	 * @return Set<SpreadsheetWorksheetMetadata>
	 */
	public Set<SpreadsheetWorksheetMetadata> getWorksheetMetadata();

	public class SpreadsheetWorksheetMetadata {
		protected String name;
		protected String alias;
		protected String dataTypeStrict;
		protected String createOnLoad;

		public String getName() {
			return name;
		}

		public String getAlias() {
			return alias;
		}

		public String getDataTypeStrict() {
			return dataTypeStrict;
		}

		public String getCreateOnLoad() {
			return createOnLoad;
		}

		@Override
		public String toString() {
			return "SpreadsheetWorksheetMetadata [name=" + name + ", alias=" + alias + ", dataTypeStrict="
				+ dataTypeStrict + ", createOnLoad=" + createOnLoad + "]";
		}

	}

	/**
	 * The purpose of this method is to get every column metadata specified in the
	 * ORM metadata source for the worksheet identifiable by the given name.
	 * 
	 * @param name
	 * @return Set<SpreadsheetColumnMetadata>
	 */
	public Set<SpreadsheetColumnMetadata> getColumnMetadata(final String name);

	public class SpreadsheetColumnMetadata {
		protected String index;
		protected String name;
		protected String alias;
		protected String dataType;
		protected String many;
		protected String delimiter;

		public SpreadsheetColumnMetadata() {
		}

		public SpreadsheetColumnMetadata(final String index, final String name) {
			this.index = index;
			this.name = name;
		}

		public String getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}

		public String getAlias() {
			return alias;
		}

		public String getDataType() {
			return dataType;
		}

		public String getMany() {
			return many;
		}

		public String getDelimiter() {
			return delimiter;
		}

		@Override
		public String toString() {
			return "SpreadsheetColumnMetadata [index=" + index + ", name=" + name + ", alias=" + alias + ", dataType="
				+ dataType + ", many=" + many + ", delimiter=" + delimiter + "]";
		}

	}

	/**
	 * The purpose of this method is to get every reference specified in the ORM
	 * metadata source.
	 * 
	 * @return Set<SpreadsheetReferenceMetadata>
	 */
	public Set<SpreadsheetReferenceMetadata> getReferenceMetadata();

	public class SpreadsheetReferenceMetadata {
		protected String source;
		protected String target;
		protected String many;
		protected String cascadeUpdates;

		public String getSource() {
			return source;
		}

		public String getTarget() {
			return target;
		}

		public String getMany() {
			return many;
		}

		public String getCascadeUpdates() {
			return cascadeUpdates;
		}

		@Override
		public String toString() {
			return "SpreadsheetReferenceMetadata [source=" + source + ", target=" + target + ", many=" + many
				+ ", cascadeUpdates=" + cascadeUpdates + "]";
		}

	}
}
