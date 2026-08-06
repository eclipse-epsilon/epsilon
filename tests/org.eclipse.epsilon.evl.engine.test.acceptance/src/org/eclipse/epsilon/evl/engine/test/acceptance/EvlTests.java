/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.engine.test.acceptance;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallel;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.dom.ConstraintSelectTransfomer;
import org.eclipse.epsilon.evl.dom.GlobalConstraintContext;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 * @since 1.6 (heavily modified)
 */
@RunWith(Parameterized.class)
public class EvlTests {
	
	private static IModel TEST_MODEL, OPTIMISED_MODEL;
	
	static {
		try {
			TEST_MODEL = newTestModel(false);
			OPTIMISED_MODEL = newTestModel(true);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Parameter
	public Supplier<? extends IEvlModule> moduleGetter;
	
	@Rule
    public TestName testName = new TestName();
	
	private IEvlModule module;
	
	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEvlModule>> modules() {
		// Shouldn't take long to run, so test everything.
		return EvlAcceptanceTestUtil.modules();
	}
	
	public static IModel newTestModel() throws Exception {
		return setUpModel("test.xml");
	}
	
	private static IModel setUpModel(String modelName) throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setFile(FileUtil.getFileStandalone(EvlAcceptanceTestUtil.modelsRoot+modelName, EvlAcceptanceTestUtil.class));
		model.setName(FileUtil.removeExtension(model.getFile().getName()));
		model.setConcurrent(true);
		model.setCachingEnabled(false);
		model.load();
		return model;
	}
	
	public static IModel getTestModel(final boolean optimised) throws Exception {
		return optimised ? OPTIMISED_MODEL : TEST_MODEL;
	}
	
	public static IModel newTestModel(boolean optimised) throws Exception {
		return setUpModel(optimised ? "optimised.xml" : "test.xml");
	}
	
	public static File getTestScript(IEvlModule module) throws Exception {
		return getTestScript("test", module);
	}
	
	private static File getTestScript(String scriptName, IEvlModule module) throws Exception {
		if ("test".equals(scriptName)) {
			FrameStack frameStack = module.getContext().getFrameStack();
			frameStack.putGlobal(
				Variable.createReadOnlyVariable("frameStack", frameStack),
				Variable.createReadOnlyVariable("blackboard", new HashMap<>())
			);
		}
		return FileUtil.getFileStandalone(EvlAcceptanceTestUtil.scriptsRoot+scriptName + ".evl", EvlAcceptanceTestUtil.class);
	}
	
	public static void loadEVL(IEvlModule module, boolean optimised) throws Exception {
		loadEVL(module, optimised, optimised ? "optimised" : "test");
	}
	
	public static void loadEVL(IEvlModule module, boolean optimised, String scriptName) throws Exception {
		module.parse(getTestScript(scriptName, module));
		module.getContext().getModelRepository().addModel(getTestModel(optimised));
	}
	
	private IEvlModule loadEVL(boolean optimised) throws Exception {
		module = moduleGetter.get();
		loadEVL(module, optimised);
		if (optimised) {
			module.getContext().setModule(module);
		}
		return module;
	}
	
	private IEvlModule loadEVL(String scriptName) throws Exception {
		loadEVL(module = moduleGetter.get(), false, scriptName);
		return module;
	}
	
	private void assertUnsatisfiedConstraints(int number, String contextName, String constraintName) {
		assertUnsatisfiedConstraints(number, contextName, constraintName, module.getContext());
	}
	
	public static void assertUnsatisfiedConstraints(int number, String contextName, String constraintName, IEvlContext context) {
		int matches = 0;
		for (UnsatisfiedConstraint uc : context.getUnsatisfiedConstraints()) {
			Constraint ucConstraint = uc.getConstraint();
			String contextTypeName = ucConstraint.getConstraintContext().getTypeName();
			
			if (
				(
					(contextName == null && ucConstraint.getConstraintContext() instanceof GlobalConstraintContext)
					|| Objects.equals(contextTypeName, contextName)
				) &&
				(constraintName == null || Objects.equals(ucConstraint.getName(), constraintName))
			) {
				matches++;
			}
		}
		assertEquals(number, matches);
	}
	
	public static void testUnsatisfiedConstraintsForTestScriptAndModel(IEvlContext context) throws EolRuntimeException {
		assertUnsatisfiedConstraints(0, null, "CanAccessPre", context);
		assertUnsatisfiedConstraints(1, null, "EolTest", context);
		assertUnsatisfiedConstraints(0, "t_a", "GuardVariableInvisibleInBlock", context);
		assertUnsatisfiedConstraints(0, "t_a", "NeverChecked", context);
		assertUnsatisfiedConstraints(0, "t_a", "dependedOn", context);
		assertUnsatisfiedConstraints(2, "t_b", "EolTest2", context);
		assertUnsatisfiedConstraints(2, "t_b", "AlwaysFalse", context);
		assertUnsatisfiedConstraints(0, "t_b", "AlwaysTrue", context);
		assertUnsatisfiedConstraints(2, "t_b", "ElementOperationInConstraint", context);
		assertUnsatisfiedConstraints(0, "t_b", "NeverChecked", context);
		assertUnsatisfiedConstraints(0, "t_b", "RequiresNonLazyConstraint", context);
		assertUnsatisfiedConstraints(0, "t_b", "LazyWithGuard", context);
		assertUnsatisfiedConstraints(2, "t_b", "RequiresLazyConstraint", context);
		assertUnsatisfiedConstraints(2, "t_b", "RequiresContextlessLazy", context);
		assertUnsatisfiedConstraints(2, "t_b", "InsaneLazyChain", context);
		assertUnsatisfiedConstraints(2, "t_b", "duplicate", context);
		assertUnsatisfiedConstraints(2, "t_b", "dependedOn", context);
		assertUnsatisfiedConstraints(0, "t_b", "SatisfiesGuardAmbigious", context);
		assertUnsatisfiedConstraints(2, "t_b", "SatisfiesCheckAmbigious", context);
		assertUnsatisfiedConstraints(2, "t_c", "WrongType", context);
		assertUnsatisfiedConstraints(0, "t_c", "AlwaysTrueOperation", context);
		assertUnsatisfiedConstraints(2, "t_c", "AlwaysFalseOperation", context);
		assertUnsatisfiedConstraints(2, "t_c", "SatisfiesOneLazyAndNonLazy", context);
		assertUnsatisfiedConstraints(2, "t_c", "SatisfiesAllLazyAndNonLazy", context);
		assertUnsatisfiedConstraints(0, "t_c", "NeverCalledLazyGuard", context);
		assertUnsatisfiedConstraints(2, "t_c", "LazyAlwaysFalseOperation", context);
		assertUnsatisfiedConstraints(0, "t_c", "ExtendedPropertyCanBeAccessed", context);
		assertUnsatisfiedConstraints(2, "t_c", "duplicate", context);
		assertUnsatisfiedConstraints(0, "t_c", "duplicateTC", context);
		assertUnsatisfiedConstraints(0, "t_c", "dependedOn", context);
		assertUnsatisfiedConstraints(1, null, "LazyContextlessCallsLazy", context);
		assertUnsatisfiedConstraints(1, null, "LazyContextlessDependedOn", context);
		assertUnsatisfiedConstraints(0, null, "LazyContextlessNeverCalled", context);
		assertUnsatisfiedConstraints(0, null, "ImportedOperationWithoutContext", context);
		assertUnsatisfiedConstraints(1, null, "duplicate", context);
		assertUnsatisfiedConstraints(0, null, "dependedOn", context);
		
		for (UnsatisfiedConstraint uc : context.getUnsatisfiedConstraints()) {
			uc.getMessage();
			for (FixInstance fix : uc.getFixes()) {
				fix.getTitle();
				fix.getFix().execute(uc.getInstance(), context);
			}
		}
		
		assertEquals("true", ((Map<?,?>)context.getFrameStack().get("blackboard").getValue()).get("fix-do-executed"));
	}
	
	@Test
	public void testUnsatisfiedConstraints() throws Exception {
		(module = loadEVL(false)).execute();
		testUnsatisfiedConstraintsForTestScriptAndModel(module.getContext());
	}
	
	@Test
	public void testCanBeTransformedEVL() throws Exception {
		module = loadEVL(true);

		final Set<String> canBeTransformed = new HashSet<>(Arrays.asList(
				"NoGuardAlwaysTrue", "NoGuardAlwaysFalse", "GuardedEmpty", "GuardedNonEmpty",
				"SingleReturnBlockGuard", "SingleReturnBlockCheck"));
		final Set<String> cannotBeTransformed = new HashSet<>(Arrays.asList(
				"Satisfies", "SatisfiesAll", "SatisfiesOne", "ComplexBlockGuard", "ComplexBlockCheck"));

		final ConstraintSelectTransfomer transformer = new ConstraintSelectTransfomer();
		assertEquals(canBeTransformed.size() + cannotBeTransformed.size(), module.getConstraints().size());
		
		for (Constraint c : module.getConstraints()) {
			String cName = c.getName();
			if (canBeTransformed.contains(cName)) {
				assertTrue("Constraint " + cName + " should be optimisable", transformer.canBeTransformed(c));
			}
			else if (cannotBeTransformed.contains(cName)) {
				assertFalse("Constraint " + cName + " should not be optimisable", transformer.canBeTransformed(c));
			}
			else {
				fail("Unexpected constraint " + cName);
			}
		}
	}

	/**
	 * Tests the constraint optimization manually, without the {@link ConstraintContext#checkAll} integration.
	 */
	@Test
	public void testOptimizedConstraints() throws Exception {
		module = loadEVL(true);

		final ConstraintSelectTransfomer transformer = new ConstraintSelectTransfomer();
		final Map<String, Constraint> constraintByName = new HashMap<>();
		for (Constraint c : module.getConstraints()) {
			constraintByName.put(c.getName(), c);
		}

		final Map<String, Integer> expectedFound = new HashMap<>();
		expectedFound.put("NoGuardAlwaysTrue", 0);
		expectedFound.put("NoGuardAlwaysFalse", 4);
		expectedFound.put("GuardedEmpty", 0);
		expectedFound.put("GuardedNonEmpty", 1);
		expectedFound.put("SingleReturnBlockGuard", 1);
		expectedFound.put("SingleReturnBlockCheck", 1);

		for (Entry<String, Integer> entry : expectedFound.entrySet()) {
			final String constraintName = entry.getKey();
			final int expectedInvalid = entry.getValue();

			final ExecutableBlock<?> transformed = transformer.transformIntoSelect(constraintByName.get(constraintName));
			final Collection<?> results = (Collection<?>) transformed.execute(module.getContext());
			assertEquals("Rule " + constraintName + " should find " + expectedInvalid + " invalid objects", expectedInvalid, results.size());
		}
	}

	/**
	 * Tests the constraint optimization from end to end, including the integration.
	 */
	@Test
	public void testOptimizedExecution() throws Exception {
		(module = loadEVL(true)).execute();
		
		assertUnsatisfiedConstraints(0, "t_b", "NoGuardAlwaysTrue");
		assertUnsatisfiedConstraints(4, "t_b", "NoGuardAlwaysFalse");
		assertUnsatisfiedConstraints(0, "t_b", "GuardedEmpty");
		assertUnsatisfiedConstraints(1, "t_b", "GuardedNonEmpty");
		assertUnsatisfiedConstraints(1, "t_b", "SingleReturnBlockGuard");
		assertUnsatisfiedConstraints(1, "t_b", "SingleReturnBlockCheck");
		assertUnsatisfiedConstraints(0, "t_b", "Satisfies");
		assertUnsatisfiedConstraints(0, "t_b", "SatisfiesAll");
		assertUnsatisfiedConstraints(4, "t_b", "SatisfiesOne");
		assertUnsatisfiedConstraints(2, "t_b", "ComplexBlockGuard");
		assertUnsatisfiedConstraints(2, "t_b", "ComplexBlockCheck");
	}
	
	/**
	 * Tests whether the program halts when encountering an issue with the script.
	 */
	@Test
	public void testInvalidExecution() throws Exception {
		module = loadEVL("invalid");
		try {
			module.execute();
		}
		catch (EolIllegalPropertyException ex) {
			String reason = ex.getReason();
			String message = ex.getMessage();
			assertTrue("Exception must have reason "+module.getClass().getName(), reason != null && reason.length() > 0);
			assertTrue(
				"Exception must have Epsilon trace " +
					System.lineSeparator() + module.getClass().getName() + System.lineSeparator() + ex
				,
				!StringUtil.isEmpty(message) &&
				!message.equalsIgnoreCase("Unknown reason") &&
				message.split(System.lineSeparator()).length >= 1
			);
			return;
		}
		fail("No exception thrown! "+module.getClass().getName());
	}
	
	@Before
	public void assumeLegal() throws Exception {
		module = moduleGetter.get();
		if (testName.getMethodName().contains("ShortCircuit")) {
			assumeTrue(module.getClass().equals(EvlModule.class));
			assumeFalse(module instanceof EvlModuleParallel);
		}
	}
	
	@Test
	public void testGlobalShortCircuitExecution() throws Exception {
		(module = loadEVL("shortCircuit")).getContext().setShortCircuit(true);
		assertEquals(1, module.execute().size());
		assertUnsatisfiedConstraints(1, "t_a", "TerminateOnFailNoAnnotation");
	}
	
	@Test
	public void testAnnotatedShortCircuitExecution() throws Exception {
		(module = loadEVL("shortCircuit")).getContext().setShortCircuit(false);
		assertEquals(24, module.execute().size());
		assertUnsatisfiedConstraints(1, "t_c", "TerminateOnFailWithAnnotation");
		assertUnsatisfiedConstraints(0, "t_c", "NeverCheckedAfterTerminate");
	}
	
	@Test
	public void testExecuteReturn() throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setXml("<tree/>");
		model.load();
		EvlModule module = new EvlModule();
		module.parse("context t_tree { constraint test { check: false }}");
		module.getContext().getModelRepository().addModel(model);
		assertEquals(1, module.execute().size());
	}
	
}
