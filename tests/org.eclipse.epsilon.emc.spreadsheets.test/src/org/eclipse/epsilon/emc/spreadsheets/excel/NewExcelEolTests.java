package org.eclipse.epsilon.emc.spreadsheets.excel;

import java.io.File;
import java.nio.file.Files;

import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.Test;

public class NewExcelEolTests {
	
	@Test
	public void testNewCalcSpreadsheet() throws Exception {
		
		File spreadsheetFile = Files.createTempFile("", ".xlsx").toFile();
		String configurationFile = "resources/excel/Calc.config.xml";
		
		ExcelModel model = getExcelModel(spreadsheetFile, configurationFile, false, true);
		
		EolModule module = new EolModule();
		module.parse("var c : new Calc(a=1, b=2, total='=A2+B2');");
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		model.dispose();
		
		model = getExcelModel(spreadsheetFile, configurationFile, true, false);
		
		module = new EolModule();
		module.getContext().getModelRepository().addModel(model);
		module.parse(
				"assertEquals(1, Calc.all.size());" + 
				"assertEquals(1, Calc.all.first().a);" +
				"assertEquals(2, Calc.all.first().b);" +
				"assertEquals(3, Calc.all.first().total);");
		;
		module.execute();
		
	}
	
	@Test
	public void testNewTeamsSpreadsheet() throws Exception {
		
		File spreadsheetFile = Files.createTempFile("", ".xlsx").toFile();
		String configurationFile = "resources/excel/Teams.config.xml";
		
		ExcelModel model = getExcelModel(spreadsheetFile, configurationFile, false, true);
		
		EolModule module = new EolModule();
		module.parse("var t : new Team(id='1', mark=10); \n" + 
					 "var s : new Student(firstName='Tom', average=82.1, team=t);");
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		model.dispose();
		
		model = getExcelModel(spreadsheetFile, configurationFile, true, false);
		
		module = new EolModule();
		module.getContext().getModelRepository().addModel(model);
		module.parse(
				"assertEquals(1, Student.all.size());" + 
				"assertEquals('Tom', Student.all.first().firstName);" +
				"assertEquals(10, Student.all.first().team.mark);" +
				"assertEquals(true, (Student.all.first().average - 82.1).abs() < 0.01);" +
				"assertEquals(10, Team.all.first().mark);");
		;
		module.execute();
		
	}
	
	protected ExcelModel getExcelModel(File spreadsheetFile, String configurationFile, boolean readOnLoad, boolean storeOnDisposal) throws Exception {
		ExcelModel model = new ExcelModel();
		model.setSpreadsheetFile(spreadsheetFile.getAbsolutePath());
		model.setConfigurationFile(SharedTestMethods.getBasePath() + configurationFile);
		model.setReadOnLoad(readOnLoad);
		model.setStoredOnDisposal(storeOnDisposal);
		model.load();
		return model;
	}
	
}
