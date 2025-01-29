package org.eclipse.epsilon.emc.simulink.dictionary.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;

import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.Test;

public class SimulinkDictionaryModelTests {
	
	@Test
	public void newDictionary() throws Exception {
		File file = Files.createTempFile("non_existent", ".sldd").toFile();
		file.delete();
		
		SimulinkDictionaryModel model = new SimulinkDictionaryModel();
		model.setName("M");
		model.setFile(file);
		model.setStoredOnDisposal(false);
		model.setCloseOnDispose(true);
		model.load();
		
		EolModule module = new EolModule();
		module.parse("""
				// Create a new entry in the Design Data section
				var de = new DesignDataEntry;
				de.Name = "e1";
				de.Value = "v1";
				
				assert(Entry.all.size = 1);
				assert(DesignDataEntry.all.size() = 1);
				assert(de.Name = "e1");
				assert(de.Value = "v1");
				
				// Create a new entry in the Other Data section
				var oe = new OtherDataEntry;
				oe.Name = "e2";
				oe.Value = "v2";
				
				assert(Entry.all.size = 2);
				assert(OtherDataEntry.all.size() = 1);
				assert(oe.Name = "e2");
				assert(oe.Value = "v2");
				
				assert(Section.all.exists(s|s.Name = "Design Data"));
				
				// Change the value of an existing Design Data entry
				de = DesignDataEntry.all.selectOne(e|e.Name = "e1");
				de.Value = "v3";
				assert(de.Value = "v3");
				
				// Delete all entries
				delete Entry.all;
				assert(Entry.all.size = 0);
				
				// TODO: Figure out how to create a new entry in the Configurations section
				""");
		
		module.getContext().getModelRepository().addModel(model);
		
		try {
			module.execute();
		}
		finally {
			model.dispose();
		}
		
		// Creating a data dictionary creates the underlying file
		// even if storeOnDisposal = false
		assertTrue(file.exists());
	}
	
}
