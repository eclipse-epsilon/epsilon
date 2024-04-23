package org.eclipse.epsilon.emc.spreadsheets.excel;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.Before;
import org.junit.Test;

public class ExcelEolTests {

	private ExcelModel model;

	@Before
	public void setup() throws Exception {
		model = new ExcelModel();
		model.setSpreadsheetFile(SharedTestMethods.getBasePath() + "resources/excel/Teams.xlsx");
		model.setConfigurationFile(SharedTestMethods.getBasePath() + "resources/excel/Teams.config.xml");
		model.load();
	}

	@Test
	public void testNumberOfRows() throws Exception {
		assertEval("Student.all.size()", 10);
		assertEval("Team.all.size()", 2);
	}
	
	@Test
	public void testColumnTypes() throws Exception {
		assertEval("Student.all.first().mark", 26);
		assertEval("Student.all.second().mark", 0);
		assertEval("Student.all.third().mark", 0);
		assertEval("Student.all.first().average", 51.2d);
		assertEval("Student.all.second().average", 0.0d);
		assertEval("Student.all.first().repeatsYear", true);
		assertEval("Student.all.second().repeatsYear", false);
		assertEval("Student.all.third().repeatsYear", false);
		assertEval("Student.all.fourth().repeatsYear", false);
	}
	
	@Test
	public void testTeam() throws Exception {
		assertEval("Student.all.first().team.isTypeOf(Team)", true);
		assertEval("Student.all.second().team", null);
	}
	
	@Test
	public void testConflicts() throws Exception {
		assertEval("Student.all.first().conflicts.firstName.second()", "Suzanna");
		assertEval("Student.all.second().conflicts.size()", 1);
		assertEval("Student.all.third().conflicts.isEmpty()", true);
		assertEval("Student.all.fourth().conflicts.size()", 0);		
	}
	
	protected void assertEval(String expression, Object result) throws Exception {
		EolModule module = new EolModule();
		module.parse("return " + expression + ";");
		module.getContext().getModelRepository().addModel(model);
		assertEquals(result, module.execute());
	}
	
}
