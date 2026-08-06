package org.eclipse.epsilon.ecl.engine.test.acceptance.domain;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.engine.test.acceptance.EclAcceptanceTestUtil;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 * @author Qurat ul ain Ali
 *
 */

@RunWith(Parameterized.class)
public class DomainTests {
	IEclModule module;
	static File script;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		script = FileUtil.getFileStandalone("compare.ecl", DomainTests.class);
	}
	
	@Parameter
	public Supplier<? extends IEclModule> moduleGetter;
	
	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEclModule>> modules() {
		return EclAcceptanceTestUtil.modules();
	}
	
	@Before
	public void setup() throws Exception {
		this.module = moduleGetter.get();
		loadEmfModel("Left", "Tree.xmi");
		loadEmfModel("Right", "Tree.xmi");
	}
	
	@Test
	public void testNoDomain() throws Exception {
		module.parse(FileUtil.getFileStandalone("noDomain.ecl", DomainTests.class));
		module.execute();
		MatchTrace allMatches = module.getContext().getMatchTrace();
		assertEquals(3, allMatches.getReduced().size());
	}
	
	@Test
	public void testLeftDomain() throws Exception {
		module.parse(FileUtil.getFileStandalone("left.ecl", DomainTests.class));
		module.execute();
		MatchTrace allMatches = module.getContext().getMatchTrace();
		assertEquals(1, allMatches.getReduced().size());
	}
	
	@Test
	public void testRightDomain() throws Exception {
		module.parse(FileUtil.getFileStandalone("right.ecl", DomainTests.class));
		module.execute();
		MatchTrace allMatches = module.getContext().getMatchTrace();
		assertEquals(3, allMatches.getReduced().size());
	}
	
	@Test
	public void testBothDomains() throws Exception {
		module.parse(FileUtil.getFileStandalone("both.ecl", DomainTests.class));
		module.execute();
		MatchTrace allMatches = module.getContext().getMatchTrace();
		assertEquals(1, allMatches.getReduced().size());
	}
	
	private EmfModel loadEmfModel(String modelName, String fileName) throws Exception {
		EmfModel model = new EmfModel();
		model.setName(modelName);
		model.setCachingEnabled(true);
		model.setConcurrent(true);
		model.setMetamodelFile(FileUtil.getFileStandalone("Tree.ecore", DomainTests.class).getAbsolutePath());
		model.setModelFile(FileUtil.getFileStandalone(fileName, DomainTests.class).getAbsolutePath());
		module.getContext().getModelRepository().addModel(model);
		model.load();
		return model;
	}
}
