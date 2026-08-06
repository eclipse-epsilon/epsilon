package org.eclipse.epsilon.emc.cdo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.cdo.internal.server.mem.MEMStore;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.cdo.CDOModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.IJVMConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class CDOPluggedInTestSuite {

	private IRepository repo;
	private IJVMAcceptor acceptor;

	@Before
	public void setup() {
		repo = CDOServerUtil.createRepository("repo", new MEMStore(), Collections.emptyMap());
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, repo);
		acceptor = JVMUtil.getAcceptor(IPluginContainer.INSTANCE, "local");
	}

	@After
	public void teardown() {
		if (acceptor != null) {
			LifecycleUtil.deactivate(acceptor);
		}
		LifecycleUtil.deactivate(repo);
	}

	@Test
	public void missingModelNoCreate() {
		try (CDOModel model = createModel("/missing-model")) {
			model.setCreateMissingResource(false);
			assertThrows("Trying to load a missing model without creating it should fail",
					EolModelLoadingException.class, () -> model.load());
		}
	}

	@Test
	public void missingModelCreateEmpty() throws Exception {
		try (CDOModel model = createModel("/missing-model")) {
			model.setCreateMissingResource(true);
			model.load();
			assertEquals("Loading a newly created resource should result in an empty model", 0,
					model.allContents().size());
		}
	}

	@Test
	public void createTree() throws Exception {
		registerTreeMetamodel();

		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(true);
			model.setCreateMissingResource(true);
			model.load();

			EObject tree = model.createInstance("Tree");
			assertNotNull("Tree has been created", tree);
			assertCountOfKind("There should be one Tree in the newly created resource", 1, model, "Tree");
		}

		try (CDOModel model = createModel("/tree")) {
			model.setCreateMissingResource(false);
			model.setStoredOnDisposal(false);
			model.load();
			assertCountOfKind("There should be one Tree in the reloaded resource", 1, model, "Tree");
		}
	}

	@Test
	public void createMissingType() throws Exception {
		try (CDOModel model = createModel("/tree")) {
			model.setCreateMissingResource(true);
			model.load();

			assertThrows(EolModelElementTypeNotFoundException.class,
				() -> model.createInstance("DoNotExist"));
		}
	}

	@Test
	public void missingBranch() throws Exception {
		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(false);
			model.setCreateMissingResource(true);
			model.setBranchName("MAIN/do-not-exist");
			assertThrows("Loading a missing branch should fail", EolModelLoadingException.class, () -> model.load());
		}
	}

	@Test
	public void explicitMainBranch() throws Exception {
		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(false);
			model.setCreateMissingResource(true);
			model.setBranchName("MAIN");
			model.load();
		} catch (Exception ex) {
			fail("Loading the model by explicitly mentioning the MAIN branch should work correctly, but threw " + ex.getMessage());
		}
	}

	@Test
	public void existingBranch() throws Exception {
		registerTreeMetamodel();

		// Create the first Tree in the main branch
		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(true);
			model.setCreateMissingResource(true);
			model.load();
			model.createInstance("Tree");
		}

		// Create a second Tree in another branch
		createBranch("mybranch");
		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(true);
			model.setBranchName("MAIN/mybranch");
			model.load();
			model.createInstance("Tree");
		}

		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(false);
			model.load();
			assertCountOfKind("There should still be 1 Tree in the main branch", 1, model, "Tree");
		}

		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(false);
			model.setBranchName("MAIN/mybranch");
			model.load();
			assertCountOfKind("The branch 'mybranch' should have 2 Tree objects", 2, model, "Tree");
		}
	}

	@Test
	public void deleteLeaf() throws Exception {
		registerTreeMetamodel();
		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(true);
			model.setCreateMissingResource(true);
			model.load();

			EObject leaf = (EObject) eol(model, "var root = new Tree; var child = new Tree; root.children.add(child); return child;");
			assertNotNull("The EOL script should have returned the leaf node", leaf);

			assertCountOfKind("The model should have the root and leaf Tree objects before deleting the leaf", 2, model, "Tree");
			model.deleteElement(leaf);
			assertCountOfKind("The model should only have the root Tree object after deleting the leaf", 1, model, "Tree");
		}
	}

	@Test
	public void deleteParent() throws Exception {
		registerTreeMetamodel();
		try (CDOModel model = createModel("/tree")) {
			model.setStoredOnDisposal(true);
			model.setCreateMissingResource(true);
			model.load();

			EObject root = (EObject) eol(model, "var root = new Tree; var child = new Tree; root.children.add(child); return root;");
			assertNotNull("The EOL script should have returned the root node", root);

			assertCountOfKind("The model should have the root and leaf Tree objects before deleting the root", 2, model, "Tree");
			model.deleteElement(root);
			assertCountOfKind("The model should have no Tree objects after deleting the leaf", 0, model, "Tree");
		}
	}

	@Test
	public void deleteWithXRef() throws Exception {
		registerMetamodel("metamodels/XRef.ecore");
		try (CDOModel model = createModel("/xref")) {
			model.setStoredOnDisposal(true);
			model.setCreateMissingResource(true);
			model.load();

			EObject second = (EObject) eol(model,
				"var first = new xref::Element; var second = new xref::Element; "
				+ "first.ref = second; return second;");
			model.deleteElement(second);
			assertTrue("The reference from the first Element should have been unset",
				(boolean) eol(model, "return Element.all.first.ref == null;"));
		}
	}

	@Test
	public void isType() throws Exception {
		registerMetamodel("metamodels/Zoo.ecore");

		try (CDOModel model = createModel("/animals")) {
			model.setStoredOnDisposal(true);
			model.setCreateMissingResource(true);
			model.load();
			model.createInstance("Panda");

			assertCountOfKind("The model should have 1 Panda", 1, model, "Panda");
			assertCountOfType("The model should have 1 exact Panda", 0, model, "Panda");

			assertCountOfKind("The model should have 1 Animal", 1, model, "Animal");
			assertCountOfType("The model should have 0 exact Animals", 0, model, "Animal");
		}
	}

	private void assertCountOfKind(String message, int count, CDOModel model, String kind) throws EolModelElementTypeNotFoundException {
		assertEquals(message, count, model.getAllOfKind(kind).size());
	}

	private void assertCountOfType(String message, int count, CDOModel model, String kind) throws EolModelElementTypeNotFoundException {
		assertEquals(message, count, model.getAllOfType(kind).size());
	}

	private void createBranch(String branchName) {
		IJVMConnector connector = JVMUtil.getConnector(IPluginContainer.INSTANCE, "local");
		try {
			CDONet4jSessionConfiguration sessionConfig = CDONet4jUtil.createNet4jSessionConfiguration();
			sessionConfig.setConnector(connector);
			sessionConfig.setRepositoryName(repo.getName());

			CDONet4jSession session = sessionConfig.openNet4jSession();
			try {
				session.getBranchManager().getMainBranch().createBranch(branchName);
			} finally {
				session.close();
			}
		} finally {
			connector.close();
		}
	}

	private CDOModel createModel(String path) {
		CDOModel model = new CDOModel();
		model.setServerURL("jvm://local");
		model.setRepositoryName("repo");
		model.setModelPath(path);
		return model;
	}

	private Object eol(CDOModel model, String eolScript) throws Exception {
		EolModule eol = new EolModule();
		eol.getContext().getModelRepository().addModel(model);
		eol.parse(eolScript);
		return eol.execute();
	}

	private void registerTreeMetamodel() throws IOException {
		registerMetamodel("metamodels/Tree.ecore");
	}

	private void registerMetamodel(String metamodelPath) throws IOException {
		File metamodelFile = FileUtil.getFileStandalone(metamodelPath, getClass());
		Resource r = EmfUtil.createResource(URI.createFileURI(metamodelFile.getCanonicalPath()));
		r.load(null);
		EPackage pkg = (EPackage) r.getContents().get(0);
		EPackage.Registry.INSTANCE.put(pkg.getNsURI(), pkg);
	}
}
