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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetColumnMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetReferenceMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetWorksheetMetadata;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.ISearchableModel;
import org.eclipse.epsilon.eol.models.Model;

/**
 * This class enables spreadsheets to be viewed as models in Epsilon.
 * 
 * @author Martins Francis
 */
public abstract class SpreadsheetModel extends Model implements ISearchableModel {
	
	protected List<SpreadsheetWorksheet> worksheets;
	protected List<SpreadsheetReference> references;
	protected boolean isLoaded;

	public SpreadsheetModel() {
		this.worksheets = new ArrayList<>();
		this.references = new ArrayList<>();
		propertyGetter = new SpreadsheetPropertyGetter(this);
	}

	@Override
	public IPropertySetter getPropertySetter() {
		return new SpreadsheetPropertySetter(this);
	}

	public List<SpreadsheetWorksheet> getWorksheets() {
		return this.worksheets;
	}

	/**
	 * The purpose of this method is to associate the given worksheet with this
	 * spreadsheet. Any worksheet that starts with
	 * SpreadsheetConstants.WSH_IGNORE_CHARS is ignored.
	 * 
	 * @param worksheet
	 */
	public void addWorksheet(final SpreadsheetWorksheet worksheet) {
		if (worksheet != null) {
			if (worksheet.getModel() == this) {
				final boolean ignoreWorksheet = worksheet.getName()
					.startsWith(SpreadsheetConstants.WORKSHEET_IGNORE_CHARS);
				if (!ignoreWorksheet) {
					this.worksheets.add(worksheet);
				}
			}
			else {
				throw new IllegalArgumentException("Worksheet does not belong to model " + this);
			}
		}
	}

	public List<SpreadsheetReference> getReferences() {
		return this.references;
	}

	/**
	 * Associate the given reference with this spreadsheet.
	 * 
	 * @param reference
	 */
	public void addReference(final SpreadsheetReference reference) {
		if (reference != null) {
			this.references.add(reference);
		}
	}

	@Override
	public void load() throws EolModelLoadingException {
		try {
			this.loadSpreadsheet();
			this.loadConfigurationFile();
		}
		catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
		isLoaded = true;
	}

	/**
	 * @since 1.6
	 */
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void dispose() {
		super.dispose();
		isLoaded = false;
	}

	/**
	 * The purpose of this method is to load the spreadsheet.
	 */
	protected abstract void loadSpreadsheet() throws Exception;

	/**
	 * The purpose of this method is to return an instance of the
	 * ISpreadsheetMetadata implementation for retrieving metadata for this
	 * spreadsheet model
	 */
	protected abstract ISpreadsheetMetadata getSpreadsheetMetadata();

	/**
	 * The purpose of this method is to load the configuration file
	 */
	protected void loadConfigurationFile() throws Exception {
		if (this.isMetadataConfigurationDefined()) {
			final ISpreadsheetMetadata metadata = this.getSpreadsheetMetadata();
			for (final SpreadsheetWorksheetMetadata worksheet : metadata.getWorksheetMetadata()) {
				this.loadWorksheetFromConfigurationFile(metadata, worksheet);
			}
			
			for (SpreadsheetReferenceMetadata reference : metadata.getReferenceMetadata()) {
				this.loadReferenceFromConfigurationFile(reference);
			}
		}
	}

	/**
	 * @return true if metadata has been provided, false otherwise
	 */
	protected abstract boolean isMetadataConfigurationDefined();

	protected void loadWorksheetFromConfigurationFile(final ISpreadsheetMetadata metadata,
		final SpreadsheetWorksheetMetadata worksheetMetadata) throws Exception {
		SpreadsheetWorksheet worksheet = this.getWorksheetByType(worksheetMetadata.getName());
		boolean createWorksheetInSpreadsheet = false;
		if (worksheet == null) {
			final String createWorksheetOnLoad = worksheetMetadata.getCreateOnLoad();
			if (!StringUtil.isEmpty(createWorksheetOnLoad)) {
				createWorksheetInSpreadsheet = Boolean.parseBoolean(createWorksheetOnLoad);
			}
			else {
				createWorksheetInSpreadsheet = SpreadsheetConstants.DEFAULT_WORKSHEET_CREATE_ON_LOAD;
			}
			
			worksheet = this.createWorksheet(worksheetMetadata);
			this.addWorksheet(worksheet);
		}
		worksheet.addWorksheetMetadata(worksheetMetadata);
		this.loadColumnsFromMetadata(metadata, worksheet);
		if (createWorksheetInSpreadsheet) {
			worksheet.createInSpreadsheet();
		}
	}

	/**
	 * The purpose of this method is to create a worksheet.
	 * 
	 * @param worksheetMetadata
	 * @return newly created worksheet
	 * @throws Exception
	 */
	protected abstract SpreadsheetWorksheet createWorksheet(final SpreadsheetWorksheetMetadata worksheetMetadata)
		throws Exception;

	protected void loadColumnsFromMetadata(final ISpreadsheetMetadata metadata, final SpreadsheetWorksheet worksheet) {
		for (final SpreadsheetColumnMetadata column : metadata.getColumnMetadata(worksheet.getName())) {
			worksheet.addColumn(column);
		}
	}

	protected void loadReferenceFromConfigurationFile(final SpreadsheetReferenceMetadata referenceMetadata) {
		final SpreadsheetReference reference = new SpreadsheetReference(this, referenceMetadata);
		this.addReference(reference);
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method returns all rows of all worksheets.
	 */
	@Override
	public List<SpreadsheetRow> allContents() {
		final List<SpreadsheetRow> rows = new ArrayList<>();
		for (final SpreadsheetWorksheet worksheet : this.getWorksheets()) {
			try {
				rows.addAll(this.getAllOfType(worksheet.getName()));
			}
			catch (EolModelElementTypeNotFoundException e) {}
		}
		return rows;
	}

	/**
	 * Returns every row contained by the worksheet identifiable by the given type.
	 */
	@Override
	public List<SpreadsheetRow> getAllOfType(final String type) throws EolModelElementTypeNotFoundException {
		final SpreadsheetWorksheet worksheet = this.getWorksheetByType(type);
		if (worksheet == null) {
			throw new EolModelElementTypeNotFoundException(this.name, type);
		}
		else {
			final List<SpreadsheetRow> rows = new ArrayList<>();
			rows.addAll(worksheet.getRows());
			return rows;
		}
	}

	@Override
	public Collection<SpreadsheetRow> getAllOfKind(final String type) throws EolModelElementTypeNotFoundException {
		return this.getAllOfType(type);
	}

	/**
	 * This method returns the worksheet that the given instance (row) belongs to.
	 * If the instance is not a SpreadsheetRow object then null is returned.
	 */
	@Override
	public SpreadsheetWorksheet getTypeOf(final Object instance) {
		if (instance instanceof SpreadsheetRow) {
			return ((SpreadsheetRow) instance).getWorksheet();
		}
		return null;
	}

	/**
	 * This method returns the name of the worksheet that the instance (row) belongs
	 * to.
	 */
	@Override
	public String getTypeNameOf(final Object instance) {
		final SpreadsheetWorksheet worksheet = this.getTypeOf(instance);
		if (worksheet != null) {
			return worksheet.getName();
		}
		return null;
	}

	/**
	 * This method creates a blank row in the worksheet identifiable by type. The
	 * newly created SpreadsheetRow is returned.
	 */
	@Override
	public Object createInstance(final String type) throws EolModelElementTypeNotFoundException {
		return createInstance(type, Collections.emptyList());
	}

	/**
	 * This method creates a new row in the worksheet identifiable by type. The
	 * given collection is expected to contain one instance of map. Every cell is
	 * assigned a value from the map in the order in which the values are returned
	 * by the collections framework. If the worksheet does not exist in the
	 * spreadsheet then an attempt is made to create it.
	 */
	@Override
	public Object createInstance(final String type, final Collection<Object> parameters)
		throws EolModelElementTypeNotFoundException {
		return this.createInstance(type, SpreadsheetUtils.extractMapFromCollection(parameters));
	}

	/**
	 * This method creates a new row in the worksheet identifiable by type. Every
	 * cell is assigned a value from the map in the order in which the values are
	 * returned by the collections framework. If the worksheet does not exist in the
	 * spreadsheet then an attempt is made to create it.
	 * 
	 * @param type
	 * @param parameters
	 * @return newly created SpreadsheetRow
	 * @throws EolModelElementTypeNotFoundException if worksheet cannot be found
	 */
	public Object createInstance(final String type, final Map<String, Object> parameters)
		throws EolModelElementTypeNotFoundException {
		final SpreadsheetWorksheet worksheet = this.getWorksheetByType(type);
		if (worksheet == null) {
			throw new EolModelElementTypeNotFoundException(this.getName(), type);
		}

		final boolean worksheetDoesNotExist = !worksheet.getExistsInSpreadsheet();
		if (worksheetDoesNotExist) {
			worksheet.createInSpreadsheet();
		}

		return worksheet.addRow(parameters);
	}

	@Override
	public Object getElementById(final String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementId(final Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementId(final Object instance, final String newId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method deletes the given instance (row) from the worksheet it belongs
	 * to.
	 */
	@Override
	public void deleteElement(final Object instance) throws EolRuntimeException {
		if (instance instanceof SpreadsheetRow) {
			final SpreadsheetRow row = (SpreadsheetRow) instance;
			row.getWorksheet().deleteRow(row);
		}
		else {
			throw new EolRuntimeException("Expecting a row, got " + instance);
		}
	}

	@Override
	public boolean owns(final Object instance) {
		if (instance instanceof SpreadsheetModel && ((SpreadsheetModel) instance) == this) {
			return true;
		}
		else if (instance instanceof SpreadsheetWorksheet && owns(((SpreadsheetWorksheet) instance).getModel())) {
			return true;
		}
		else if (instance instanceof SpreadsheetRow && owns(((SpreadsheetRow) instance).getWorksheet())) {
			return true;
		}
		else if (instance instanceof SpreadsheetColumn && owns(((SpreadsheetColumn) instance).getWorksheet())) {
			return true;
		}
		else if (instance instanceof Collection<?>) {
			final Collection<?> collection = (Collection<?>) instance;
			final Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				final boolean owns = this.owns(it.next());
				if (!owns) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean hasType(final String type) {
		return this.getWorksheetByType(type) != null;
	}

	@Override
	public boolean isInstantiable(final String type) {
		return hasType(type);
	}

	@Override
	public boolean store(final String location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean store() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method returns the first worksheet of the given type as determined by
	 * {@link SpreadsheetWorksheet#isIdentifiablyBy(String)} method.
	 * 
	 * @param type
	 * @return worksheet identifiable by type or null if none found
	 */
	public SpreadsheetWorksheet getWorksheetByType(final String type) {
		if (!StringUtil.isEmpty(type)) {
			for (final SpreadsheetWorksheet worksheet : this.getWorksheets()) {
				if (worksheet.isIdentifiablyBy(type)) {
					return worksheet;
				}
			}
		}
		return null;
	}

	/**
	 * The purpose of this method is to find all references where the given
	 * worksheet is a source i.e. is referencing.
	 * 
	 * @param worksheet
	 * @return Set<SpreadsheetReference>
	 */
	public Set<SpreadsheetReference> getReferencesBySource(final SpreadsheetWorksheet worksheet) {
		final Set<SpreadsheetReference> references = new HashSet<>();
		for (final SpreadsheetReference reference : this.getReferences()) {
			if (reference.getReferencingWorksheet() == worksheet) {
				references.add(reference);
			}
		}
		return references;
	}

	/**
	 * The purpose of this method is to find all references where the given
	 * worksheet and column is a source i.e. are referencing.
	 * 
	 * @param worksheet
	 * @param column
	 * @return Set<SpreadsheetReference>
	 */
	public Set<SpreadsheetReference> getReferencesBySource(final SpreadsheetWorksheet worksheet,
		final SpreadsheetColumn column) {
		final Set<SpreadsheetReference> references = new HashSet<>();
		for (final SpreadsheetReference reference : this.getReferencesBySource(worksheet)) {
			if (reference.getReferencingColumn() == column) {
				references.add(reference);
			}
		}
		return references;
	}

	/**
	 * The purpose of this method is to find all references where the given
	 * worksheet is a target i.e. being referenced.
	 * 
	 * @param worksheet
	 * @return Set<SpreadsheetReference>
	 */
	public Set<SpreadsheetReference> getReferencesByTarget(final SpreadsheetWorksheet worksheet) {
		final Set<SpreadsheetReference> references = new HashSet<>();
		for (final SpreadsheetReference reference : this.getReferences()) {
			if (reference.getReferencedWorksheet() == worksheet) {
				references.add(reference);
			}
		}
		return references;
	}

	/**
	 * The purpose of this method is to find all references where the given
	 * worksheet and column is a target i.e. being referenced.
	 * 
	 * @param worksheet
	 * @param column
	 * @return Set<SpreadsheetReference>
	 */
	public Set<SpreadsheetReference> getReferencesByTarget(final SpreadsheetWorksheet worksheet,
		final SpreadsheetColumn column) {
		final Set<SpreadsheetReference> references = new HashSet<>();
		for (final SpreadsheetReference reference : this.getReferencesByTarget(worksheet)) {
			if (reference.getReferencedColumn() == column) {
				references.add(reference);
			}
		}
		return references;
	}

	@Override
	public Object findOne(final Variable iterator, final ModuleElement ast, final IEolContext context)
		throws EolRuntimeException {
		final Collection<SpreadsheetRow> results = this.find(iterator, ast, context);
		if (results != null) {
			Iterator<?> iter = results.iterator();
			if (iter.hasNext()) {
				return iter.next();
			}
		}
		return null;
	}

	@Override
	public abstract Collection<SpreadsheetRow> find(Variable iterator, ModuleElement ast, IEolContext context) throws EolRuntimeException;

	/**
	 * The purpose of this method is to delete the given worksheet from this
	 * spreadsheet
	 * 
	 * @param worksheet
	 */
	protected abstract void deleteWorksheet(SpreadsheetWorksheet worksheet);

}
