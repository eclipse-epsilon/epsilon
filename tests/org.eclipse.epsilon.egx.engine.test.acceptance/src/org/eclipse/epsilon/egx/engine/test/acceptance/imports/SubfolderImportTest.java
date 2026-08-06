package org.eclipse.epsilon.egx.engine.test.acceptance.imports;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SubfolderImportTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void importRootIsPreserved() throws Exception {
		File mainEgx = FileUtil.getFileStandalone("main.egx", getClass());
		for (String path : new String[] {
			"java.ecore",
			"barchart/barchart.egx", "barchart/stats.egl",
			"dot/dot.egx", "dot/ecore2dot.egl"
		}) {
			FileUtil.getFileStandalone(path, getClass());
		}

		File outputFolder = folder.getRoot();
		EgxModule module = new EgxModule(outputFolder.getAbsolutePath());
		module.parse(mainEgx);

		EmfModel model = new EmfModel();
		model.setMetamodelUri(EcorePackage.eNS_URI);
		model.setModelFile(new File(mainEgx.getParentFile(), "java.ecore").getAbsolutePath());
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();

		module.getContext().getModelRepository().addModel(model);
		module.execute();
		
		assertFolderHasFile(outputFolder, "barchart.txt");
		assertFolderHasFile(outputFolder, "ecoredot.txt");
	}

	protected void assertFolderHasFile(File folder, String expectedFilename) {
		FilenameFilter filter = (dir, filename) -> expectedFilename.equals(filename);
		String message = String.format("File %s is in folder %s", expectedFilename, folder);
		assertTrue(message, folder.listFiles(filter).length > 0);
	}
}
