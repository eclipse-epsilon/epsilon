/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.test;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumnTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetDataTypeTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModelTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertyGetterTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetReferenceTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetUtilsTest;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheetTest;
import org.eclipse.epsilon.emc.spreadsheets.common.CellMultiplicityTest;
import org.eclipse.epsilon.emc.spreadsheets.common.DataTypeStrictnessTest;
import org.eclipse.epsilon.emc.spreadsheets.common.IgnoreWorksheetTest;
import org.eclipse.epsilon.emc.spreadsheets.delete.DeleteReferencedAndReferencingRowTest;
import org.eclipse.epsilon.emc.spreadsheets.delete.DeleteReferencedRowTest;
import org.eclipse.epsilon.emc.spreadsheets.delete.DeleteRowTest;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelEolTests;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelModelTest;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelRowTest;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelWorksheetTest;
import org.eclipse.epsilon.emc.spreadsheets.excel.NewExcelEolTests;
import org.eclipse.epsilon.emc.spreadsheets.instantiate.CreateInstanceReferencingSheetDataTypeStrictTest;
import org.eclipse.epsilon.emc.spreadsheets.instantiate.CreateInstanceReferencingSheetNotDataTypeStrictTest;
import org.eclipse.epsilon.emc.spreadsheets.instantiate.CreateInstanceSheetDataTypeStrictTest;
import org.eclipse.epsilon.emc.spreadsheets.instantiate.CreateInstanceSheetNotDataTypeStrictTest;
import org.eclipse.epsilon.emc.spreadsheets.instantiate.CreateInstanceTest;
import org.eclipse.epsilon.emc.spreadsheets.metadata.MetadataTest;
import org.eclipse.epsilon.emc.spreadsheets.propertygetter.PrefixedVisibleValuePopertyGetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertygetter.PropertyGetterReferencingCellManyTest;
import org.eclipse.epsilon.emc.spreadsheets.propertygetter.PropertyGetterReferencingCellNotManyTest;
import org.eclipse.epsilon.emc.spreadsheets.propertygetter.PropertyGetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.CascadeUpdatesCellsManyToManyPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.CascadeUpdatesCellsManyToNotManyPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.CascadeUpdatesCellsNotManyPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.CascadeUpdatesCellsNotManyToManyPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.PlainTextCellPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.ReferencedCellPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.propertysetter.ReferencingCellPropertySetterTest;
import org.eclipse.epsilon.emc.spreadsheets.read.GetAllContentsTest;
import org.eclipse.epsilon.emc.spreadsheets.read.GetAllOfTypeTest;
import org.eclipse.epsilon.emc.spreadsheets.read.GetTypeOfTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({ SpreadsheetColumnTest.class, SpreadsheetDataTypeTest.class, SpreadsheetModelTest.class,
	SpreadsheetPropertyGetterTest.class, SpreadsheetPropertySetterTest.class, SpreadsheetReferenceTest.class,
	SpreadsheetUtilsTest.class, SpreadsheetWorksheetTest.class, CellMultiplicityTest.class,
	DataTypeStrictnessTest.class, IgnoreWorksheetTest.class, DeleteReferencedAndReferencingRowTest.class,
	DeleteReferencedRowTest.class, DeleteRowTest.class, ExcelModelTest.class, ExcelRowTest.class,
	ExcelWorksheetTest.class, CreateInstanceReferencingSheetDataTypeStrictTest.class,
	CreateInstanceReferencingSheetNotDataTypeStrictTest.class, CreateInstanceSheetDataTypeStrictTest.class,
	CreateInstanceSheetNotDataTypeStrictTest.class, CreateInstanceTest.class, MetadataTest.class,
	PrefixedVisibleValuePopertyGetterTest.class, PropertyGetterReferencingCellManyTest.class,
	PropertyGetterReferencingCellNotManyTest.class, PropertyGetterTest.class,
	CascadeUpdatesCellsManyToManyPropertySetterTest.class, CascadeUpdatesCellsManyToNotManyPropertySetterTest.class,
	CascadeUpdatesCellsNotManyPropertySetterTest.class, CascadeUpdatesCellsNotManyToManyPropertySetterTest.class,
	PlainTextCellPropertySetterTest.class, ReferencedCellPropertySetterTest.class,
	ReferencingCellPropertySetterTest.class, GetAllContentsTest.class, GetAllOfTypeTest.class, GetTypeOfTest.class,
	ExcelEolTests.class, NewExcelEolTests.class})
public class SpreadsheetDriverTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(SpreadsheetDriverTestSuite.class);
	}
}