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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.epsilon.common.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class provides a concrete implementation for collecting spreadsheet ORM
 * Metadata from XML documents.
 * 
 * @author Martins Francis
 */
public class MetadataXMLParser implements ISpreadsheetMetadata {
	private final Document xml;

	public MetadataXMLParser(final Document xml) {
		this.xml = xml;
	}

	@Override
	public Set<SpreadsheetWorksheetMetadata> getWorksheetMetadata() {
		final Set<SpreadsheetWorksheetMetadata> worksheets = new HashSet<>();
		final Element document = this.xml.getDocumentElement();
		final NodeList nodeList = document.getElementsByTagName(ORMConstants.ORM_WORKSHEET);
		for (int i = 0; i < nodeList.getLength(); i++) {
			final Element element = (Element) nodeList.item(i);
			final SpreadsheetWorksheetMetadata worksheetMetadata = this.getWorksheetMetadata(element);
			worksheets.add(worksheetMetadata);
		}
		return worksheets;
	}

	private SpreadsheetWorksheetMetadata getWorksheetMetadata(final Element element) {
		final SpreadsheetWorksheetMetadata worksheet = new SpreadsheetWorksheetMetadata();

		worksheet.name = this.getValueFromElement(element, ORMConstants.ORM_WORKSHEET_NAME);
		worksheet.alias = this.getValueFromElement(element, ORMConstants.ORM_WORKSHEET_ALIAS);
		worksheet.dataTypeStrict = this.getValueFromElement(element, ORMConstants.ORM_WORKSHEET_DTSTRICT);
		worksheet.createOnLoad = this.getValueFromElement(element, ORMConstants.ORM_WORKSHEET_CREATE);

		this.validateWorksheetMetadata(worksheet);

		return worksheet;
	}

	private void validateWorksheetMetadata(final SpreadsheetWorksheetMetadata worksheet) {
		if (StringUtil.isEmpty(worksheet.name)) {
			String message = "A worksheet is missing its name in the configuration file";
			throw new IllegalArgumentException(message);
		}
	}

	@Override
	public Set<SpreadsheetColumnMetadata> getColumnMetadata(final String name) {
		final Set<SpreadsheetColumnMetadata> columns = new HashSet<>();
		final Element document = this.xml.getDocumentElement();
		final NodeList worksheetNodeList = document.getElementsByTagName(ORMConstants.ORM_WORKSHEET);
		for (int i = 0; i < worksheetNodeList.getLength(); i++) {
			final Element columnElement = (Element) worksheetNodeList.item(i);
			final String worksheetName = columnElement.getAttribute(ORMConstants.ORM_WORKSHEET_NAME);
			if (name.equals(worksheetName)) {
				final Set<SpreadsheetColumnMetadata> columnsForWorksheet = getColumnMetadataFromElement(columnElement);
				columns.addAll(columnsForWorksheet);
				break;
			}
		}

		return columns;
	}

	private Set<SpreadsheetColumnMetadata> getColumnMetadataFromElement(final Element columnElement) {
		final Set<SpreadsheetColumnMetadata> columns = new HashSet<>();

		final NodeList columnNodeList = columnElement.getChildNodes();
		for (int c = 0; c < columnNodeList.getLength(); c++) {
			final Node node = columnNodeList.item(c);
			if (node.getNodeName().equals(ORMConstants.ORM_COLUMN)) {
				final SpreadsheetColumnMetadata columnMetadata = this.getColumnMetadataFromNode(node);
				columns.add(columnMetadata);
			}
		}

		return columns;
	}

	private SpreadsheetColumnMetadata getColumnMetadataFromNode(final Node node) {
		final SpreadsheetColumnMetadata columnMetadata = new SpreadsheetColumnMetadata();
		final NamedNodeMap namedNodeMap = node.getAttributes();

		columnMetadata.index = this.getValueFromNode(namedNodeMap, ORMConstants.ORM_COLUMN_INDEX);
		columnMetadata.name = this.getValueFromNode(namedNodeMap, ORMConstants.ORM_COLUMN_NAME);
		columnMetadata.alias = this.getValueFromNode(namedNodeMap, ORMConstants.ORM_COLUMN_ALIAS);
		columnMetadata.dataType = this.getValueFromNode(namedNodeMap, ORMConstants.ORM_COLUMN_DATA_TYPE);
		columnMetadata.many = this.getValueFromNode(namedNodeMap, ORMConstants.ORM_COLUMN_MANY);
		columnMetadata.delimiter = this.getValueFromNode(namedNodeMap, ORMConstants.ORM_COLUMN_DELIMITER);

		this.validateColumnMetadata(columnMetadata);

		return columnMetadata;
	}

	private void validateColumnMetadata(final SpreadsheetColumnMetadata columnMetadata) {
		if (StringUtil.isEmpty(columnMetadata.index) && StringUtil.isEmpty(columnMetadata.name)) {
			String message = "Column is missing both index and name in the configuration file";
			throw new IllegalArgumentException(message);
		}
	}

	@Override
	public Set<SpreadsheetReferenceMetadata> getReferenceMetadata() {
		final Set<SpreadsheetReferenceMetadata> references = new HashSet<>();
		final Element document = this.xml.getDocumentElement();
		final NodeList nl = document.getElementsByTagName(ORMConstants.ORM_REFERENCE);
		for (int i = 0; i < nl.getLength(); i++) {
			final Element element = (Element) nl.item(i);
			final SpreadsheetReferenceMetadata reference = this.getReferenceMetadata(element);
			references.add(reference);
		}
		return references;
	}

	private SpreadsheetReferenceMetadata getReferenceMetadata(final Element element) {
		final SpreadsheetReferenceMetadata reference = new SpreadsheetReferenceMetadata();

		reference.source = this.getValueFromElement(element, ORMConstants.ORM_REFERENCE_SOURCE);
		reference.target = this.getValueFromElement(element, ORMConstants.ORM_REFERENCE_TARGET);
		reference.many = this.getValueFromElement(element, ORMConstants.ORM_REFERENCE_MANY);
		reference.cascadeUpdates = this.getValueFromElement(element, ORMConstants.ORM_REFERENCE_CASCADE);

		this.validateReferenceMetadata(reference);

		return reference;
	}

	private void validateReferenceMetadata(final SpreadsheetReferenceMetadata reference) {
		String message = null;
		if (StringUtil.isEmpty(reference.source)) {
			message = "Reference is missing its source in the configuration file";
		}
		else if (StringUtil.isEmpty(reference.target)) {
			message = "Reference is missing its target in the configuration file";
		}

		if (message != null) {
			throw new IllegalArgumentException(message);
		}
	}

	private String getValueFromElement(final Element element, final String attribute) {
		final String value = element.getAttribute(attribute);
		if (!StringUtil.isEmpty(value)) {
			return value;
		}
		else {
			return null;
		}
	}

	private String getValueFromNode(final NamedNodeMap nodeMap, final String elementName) {
		Node node = nodeMap.getNamedItem(elementName);
		
		if (node == null) {
			node = nodeMap.getNamedItem(elementName.toLowerCase());
		}
		
		String value = null;
		if (node != null) {
			value = node.getNodeValue();
		}
		return StringUtil.isEmpty(value) ? null : value;
	}

}
