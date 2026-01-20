/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - additional tests
 ******************************************************************************/
package org.eclipse.epsilon.epl.engine.test.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.dom.NoMatch;
import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EplTests {
	
	private static File TEST_SCRIPT;
	
	static {
		try {
			TEST_SCRIPT = FileUtil.getFileStandalone(EplAcceptanceTestUtil.scriptsRoot+"test.epl", EplAcceptanceTestUtil.class);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static IModel getTestModel() throws Exception {
		return newModel("test.xml");
	}
	
	@Parameter
	public Supplier<? extends IEplModule> moduleGetter;
	
	private IEplModule module;
	private PatternMatchModel patternMatchModel;
	
	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEplModule>> modules() {
		// Shouldn't take long to run, so test everything.
		return EplAcceptanceTestUtil.modules(true);
	}
	
	// Using a Collector allows us to capture the errors and print more useful diagnostic info.
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	private static IModel newModel(String modelName) throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setFile(FileUtil.getFileStandalone(EplAcceptanceTestUtil.modelsRoot+modelName, EplAcceptanceTestUtil.class));
		model.setName(FileUtil.removeExtension(model.getFile().getName()));
		model.setCachingEnabled(false);
		model.load();
		return model;
	}
	
	public static Path getTestScript(IEplModule module) {
		module.getContext().getFrameStack().putGlobal(
			Variable.createReadOnlyVariable("blackboard", ConcurrencyUtils.concurrentMap())
		);
		return TEST_SCRIPT.toPath();
	}
	
	Map<String, String> loadEPL() throws Exception {
		module = moduleGetter.get();
		
		Map<String, String> blackboard = ConcurrencyUtils.concurrentMap();
		module.getContext().getFrameStack().putGlobal(
			Variable.createReadOnlyVariable("blackboard", blackboard)
		);
		
		module.parse(TEST_SCRIPT);
		module.getContext().getModelRepository().addModel(getTestModel());
		
		return blackboard;
	}
	
	private void assertMatchesCollector(int expectedMatches, String name, Consumer<Collection<?>> code) throws EolModelElementTypeNotFoundException {
		Collection<?> allMatches = patternMatchModel.getAllOfType(name);
		try {
			code.accept(allMatches);
		}
		catch (AssertionError error) {
			System.err.println("Expected "+expectedMatches+" for '"+name+"', but was "+allMatches.size());
			collector.addError(error);
		}
	}
	
	void assertNumberOfMatches(int numMatches, String name) throws EolModelElementTypeNotFoundException {
		assertMatchesCollector(numMatches, name, allMatches ->
			assertEquals(numMatches, allMatches.size())
		);
	}
	
	void assertNoMatch(int numMatches, String name, boolean all) throws EolModelElementTypeNotFoundException {
		assertMatchesCollector(numMatches, name, allMatches -> {
			assertEquals(numMatches, allMatches.size());
			Predicate<? super Object> isNoMatch = v -> v instanceof NoMatch;
			
			for (Object match : allMatches) {
				assert match instanceof PatternMatch;
				Stream<Object> bindings = ((PatternMatch)match).getRoleBindings().values().stream();
				assertTrue(all ? bindings.allMatch(isNoMatch) : bindings.anyMatch(isNoMatch));
			}
		});
	}
	
	
	@Test
	public void testEpl() throws Exception {
		Map<String, String> blackboard = loadEPL();
		patternMatchModel = (PatternMatchModel) module.execute();
		
		assertNumberOfMatches(2, "B");
		assertNumberOfMatches(4, "BC");
		assertNumberOfMatches(2, "BfromAll");
		assertNumberOfMatches(2, "BfromReturnAll");
		assertNumberOfMatches(2, "Bmatch");
		assertNumberOfMatches(0, "NoB");
		assertNumberOfMatches(0, "NoBguardReturn");
		assertNumberOfMatches(2, "Bguard");
		assertNumberOfMatches(2, "BCinactive");
		assertNumberOfMatches(4, "BCactive");
		assertEquals("xx", blackboard.get("Bonmatch"));
		assertEquals("xx", blackboard.get("Bnomatch"));
		assertEquals("xxxx", blackboard.get("BCdo"));
		assertNumberOfMatches(0, "BnoC");
		assertNumberOfMatches(2, "BnonoC");
		assertNumberOfMatches(0, "Binactive");
		assertNumberOfMatches(0, "BinactiveCfromAll");
		assertNumberOfMatches(0, "BnoCinactiveB");
		assertNumberOfMatches(1, "B2Csubset");
		assertNumberOfMatches(1, "AccessSelf");
		assertNumberOfMatches(2, "NegativeAccessPredecessor");
		assertNumberOfMatches(0, "NegativeAccessSelf");
		assertNumberOfMatches(2, "MultipleInactive");
		assertNumberOfMatches(2, "CardinalityMany");
		assertNumberOfMatches(1, "CardinalityRange");
		assertNumberOfMatches(0, "CardinalityZero");
		assertNumberOfMatches(1, "CardinalityNegative");
		assertNumberOfMatches(2, "OptionalActive");
		assertNumberOfMatches(2, "OptionalInactiveMultipleDomains");
		//assertNumberOfMatches(?, "OptionalNegativeGuard");		// TODO: implement
		//assertNumberOfMatches(?, "OptionalCardinality");			// TODO: implement
		//assertNumberOfMatches(?, "KitchenSink");					// TODO: implement
	}
	
	@Test
	public void testOneLoop() throws Exception {
		PatternMatchModel m = testRepeatWhileMatches("pattern P t : t_tree { onmatch { counter.increment(); delete t; } }", 1, 1);
		assertEquals(1, m.getMatches().size());
	}
	
	@Test
	public void testNoMatchesInLastLoop() throws Exception {
		PatternMatchModel m = testRepeatWhileMatches("pattern P t : t_tree { onmatch { counter.increment(); delete t; } }", 2, 1);
		assertEquals(0, m.getMatches().size());
	}
	
	@Test
	public void testTwoMaxLoops() throws Exception {
		PatternMatchModel m = testRepeatWhileMatches("pattern P t : t_tree { onmatch { counter.increment(); } }", 2, 2);
		assertEquals(1, m.getMatches().size());
	}
	
	@Test(expected = EolInternalException.class)
	public void testInfiniteLoop() throws Exception {
		testRepeatWhileMatches("pattern P t : t_tree { onmatch { counter.increment(); } }", -1, 0);
	}
	
	@Test
	public void testCountMatches() throws Exception {
		PatternMatchModel m = testRepeatWhileMatches("pattern P t : t_tree { onmatch { counter.increment(); } }", "<tree><tree/></tree>", 1, 2);
		assertEquals(2, m.getAllOfType("P").size());
		assertEquals(2, m.getMatches().size());
	}
	
	public PatternMatchModel testRepeatWhileMatches(String epl, int maxLoops, int expectedLoops) throws Exception {
		return testRepeatWhileMatches(epl, "<tree/>", maxLoops, expectedLoops);
	}
	
	public PatternMatchModel testRepeatWhileMatches(String epl, String xml, int maxLoops, int expectedLoops) throws Exception {
		
		PlainXmlModel model = new PlainXmlModel();
		model.setXml(xml);
		model.load();
		
		module = moduleGetter.get();
		module.setRepeatWhileMatches(true);
		module.setMaxLoops(maxLoops);
		
		module.parse(epl);
		Counter counter = new Counter();
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("counter", counter));
		module.getContext().getModelRepository().addModel(model);
		PatternMatchModel patternMatchModel = (PatternMatchModel) module.execute();
		
		assertEquals(expectedLoops, counter.count);
		return patternMatchModel;
	}
	
	class Counter {
		int count;
		
		public void increment() throws InfiniteLoopException {
			if (count++ > 5) throw new InfiniteLoopException();
		}
	}
	
	class InfiniteLoopException extends Exception {};
}
