/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.test;

import static org.eclipse.epsilon.coverage.test.CoverageTestUtil.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.eclipse.epsilon.coverage.CoverageLines.Status;
import org.eclipse.epsilon.coverage.CoverageRecorder;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.CoverageStatistics;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.concurrent.EolModuleParallel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CoverageRecorderTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	protected CoverageSession run(IEolModule module, File file) throws Exception {
		module.parse(file);
		assertTrue(module.getParseProblems().toString(), module.getParseProblems().isEmpty());
		CoverageRecorder recorder = CoverageRecorder.attach(module);
		module.execute();
		return recorder.createCoverageSession("test");
	}

	@Test
	public void testAllEolConstructsCovered() throws Exception {
		String code =
			"import 'lib.eol';\n" +
			"var x : Integer = 1;\n" +
			"x = x + 1;\n" +
			"x += 2;\n" +
			"x++;\n" +
			"var s : Sequence(String) = Sequence{'a', 'b'};\n" +
			"s[0] = 'c';\n" +
			"var m = Map{'k' = 1};\n" +
			"m.put('j', 2);\n" +
			"var t = new Tuple;\n" +
			"t.name = 'n';\n" +
			"t.name.length().println();\n" +
			"var l = new Native('java.util.ArrayList');\n" +
			"l.add(1);\n" +
			"for (i in Sequence{1..3}) { if (i == 2) continue; if (i == 3) break; i.println(); }\n" +
			"while (x > 0) { x = x - 1; }\n" +
			"for (v in Sequence{0, 1}) { switch (v) { case 0: 'zero'.println(); default: 'other'.println(); } }\n" +
			"for (v in Sequence{0, 1}) { var y = v > 0 ? 1 : 2; }\n" +
			"var z = null ?: 3;\n" +
			"var b = (false or not (true and false)) and (true xor false) implies true;\n" +
			"s.select(e | e <> 'z').collect(e | e.toUpperCase()).println();\n" +
			"x.isTypeOf(Integer).println();\n" +
			"(-x).println();\n" +
			"5.twice().println();\n" +
			"libOp().println();\n" +
			"operation Integer twice() : Integer { return self * 2; }\n";
		write(folder.getRoot(), "lib.eol", "operation libOp() {\n  return 'lib';\n}\n");
		File file = write(folder.getRoot(), "main.eol", code);

		CoverageSession session = run(new EolModule(), file);
		assertFullyCovered(getFile(session, file));
		assertFullyCovered(getFile(session, new File(folder.getRoot(), "lib.eol")));
	}

	@Test
	public void testBranchesAndOperations() throws Exception {
		String code =
			"var x = 1;\n" +
			"if (x > 0) {\n" +
			"  'positive'.println();\n" +
			"}\n" +
			"else {\n" +
			"  'negative'.println();\n" +
			"}\n" +
			"if (x > 0) 'a'.println(); else 'b'.println();\n" +
			"operation unused() {\n" +
			"  return 1;\n" +
			"}\n";
		File file = write(folder.getRoot(), "branches.eol", code);
		SourceFile sourceFile = getFile(run(new EolModule(), file), file);

		assertLine(sourceFile, lineOf(code, "var x"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "if (x > 0) {"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "'positive'"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "'negative'"), Status.NONE);
		assertLine(sourceFile, lineOf(code, "'a'"), Status.PARTIAL);
		assertLine(sourceFile, lineOf(code, "return 1"), Status.NONE);
		assertLine(sourceFile, lineOf(code, "operation unused"), null);

		CoverageElement unused = sourceFile.getElements().stream()
			.filter(e -> "Operation unused".equals(e.getLabel())).findFirst().orElse(null);
		assertNotNull(unused);
		assertFalse(unused.isProbe());
		assertEquals(0, new CoverageStatistics().get(unused).getCovered());
		assertTrue(new CoverageStatistics().get(unused).getTotal() > 0);
	}

	@Test
	public void testHitCounts() throws Exception {
		String code = "for (i in Sequence{1..5}) {\n  i.println();\n}\n";
		File file = write(folder.getRoot(), "loop.eol", code);
		SourceFile sourceFile = getFile(run(new EolModule(), file), file);
		CoverageElement println = getProbes(sourceFile).stream()
			.filter(e -> e.getStartLine() == 2 && "ExpressionStatement".equals(e.getKind())).findFirst().get();
		assertEquals(5, println.getHits());
	}

	@Test
	public void testParallel() throws Exception {
		String code =
			"var s = Sequence{1..100}.parallelSelect(i |\n" +
			"  i > 50\n" +
			");\n" +
			"s.size().println();\n";
		File file = write(folder.getRoot(), "parallel.eol", code);
		SourceFile sourceFile = getFile(run(new EolModuleParallel(), file), file);
		assertFullyCovered(sourceFile);
		CoverageElement condition = getProbes(sourceFile).stream()
			.filter(e -> e.getStartLine() == 2 && "GreaterThanOperatorExpression".equals(e.getKind())).findFirst().get();
		assertEquals(100, condition.getHits());
	}

	@Test
	public void testEgl() throws Exception {
		String code =
			"[%var x = 1;%]\n" +
			"Static text\n" +
			"[%if (x > 1) {%]\n" +
			"Not generated\n" +
			"[%} else {%]\n" +
			"Generated [%=x%]\n" +
			"[%}%]\n";
		File file = write(folder.getRoot(), "template.egl", code);
		SourceFile sourceFile = getFile(run(new EglModule(), file), file);
		assertLine(sourceFile, lineOf(code, "Static text"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "Not generated"), Status.NONE);
		assertLine(sourceFile, lineOf(code, "Generated [%=x%]"), Status.FULL);
	}

	@Test
	public void testEgxInvokesEgl() throws Exception {
		String template = "Hello [%=name%]\n[%if (false) {%]\nNever\n[%}%]\n";
		File templateFile = write(folder.getRoot(), "hello.egl", template);
		File egx = write(folder.getRoot(), "main.egx",
			"rule Hello {\n" +
			"  parameters : Map{'name' = 'world'}\n" +
			"  template : 'hello.egl'\n" +
			"  target : 'out/hello.txt'\n" +
			"}\n");
		EgxModule module = new EgxModule(new EglTemplateFactory());
		CoverageSession session = run(module, egx);

		assertFullyCovered(getFile(session, egx));
		SourceFile templateCoverage = getFile(session, templateFile);
		assertLine(templateCoverage, lineOf(template, "Hello"), Status.FULL);
		assertLine(templateCoverage, lineOf(template, "Never"), Status.NONE);
	}

	protected PlainXmlModel createXmlModel(String name, String xml) throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setName(name);
		model.setXml(xml);
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		return model;
	}

	@Test
	public void testEvl() throws Exception {
		String code =
			"pre {\n" +
			"  var limit = 1;\n" +
			"}\n" +
			"context t_a {\n" +
			"  guard : self.a_name.isDefined()\n" +
			"  constraint HasName {\n" +
			"    check : self.a_name.length() > limit\n" +
			"    message : 'Name too short: ' + self.a_name\n" +
			"  }\n" +
			"  constraint Unsatisfied {\n" +
			"    check : self.a_name.length() > 10\n" +
			"    message : 'Name too short'\n" +
			"  }\n" +
			"}\n";
		File file = write(folder.getRoot(), "constraints.evl", code);
		EvlModule module = new EvlModule();
		module.getContext().getModelRepository().addModel(createXmlModel("M", "<root><a name='abc'/><a name='defg'/></root>"));
		SourceFile sourceFile = getFile(run(module, file), file);

		assertLine(sourceFile, lineOf(code, "var limit"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "guard"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "> limit"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "'Name too short: '"), Status.NONE);
		assertLine(sourceFile, lineOf(code, "> 10"), Status.FULL);
		assertLine(sourceFile, lineOf(code, "message : 'Name too short'"), Status.FULL);
	}

	@Test
	public void testEtl() throws Exception {
		String code =
			"rule A2B\n" +
			"  transform s : Source!t_a\n" +
			"  to t : Target!t_b {\n" +
			"  guard : s.a_name <> 'skip'\n" +
			"  t.a_name = s.a_name;\n" +
			"}\n" +
			"rule Unused\n" +
			"  transform s : Source!t_c\n" +
			"  to t : Target!t_d {\n" +
			"  t.a_name = s.a_name;\n" +
			"}\n";
		File file = write(folder.getRoot(), "transformation.etl", code);
		EtlModule module = new EtlModule();
		module.getContext().getModelRepository().addModel(createXmlModel("Source", "<root><a name='x'/><a name='skip'/></root>"));
		module.getContext().getModelRepository().addModel(createXmlModel("Target", "<root/>"));
		SourceFile sourceFile = getFile(run(module, file), file);

		assertLine(sourceFile, lineOf(code, "guard"), Status.FULL);
		assertLine(sourceFile, 5, Status.FULL);
		assertLine(sourceFile, 10, Status.NONE);
	}

	@Test
	public void testSaveLoadMerge() throws Exception {
		String code = "var x = 1;\nif (x > 0) {\n  'a'.println();\n}\n";
		File file = write(folder.getRoot(), "simple.eol", code);
		CoverageSession first = run(new EolModule(), file);
		CoverageSession second = run(new EolModule(), file);

		File ecv = new File(folder.getRoot(), "simple.ecv");
		CoverageResources.save(first, ecv);
		CoverageSession loaded = CoverageResources.load(ecv);
		assertEquals(first.getFiles().size(), loaded.getFiles().size());
		assertEquals(getProbes(first).size(), getProbes(loaded).size());
		assertNotNull(loaded.getFiles().get(0).getContentHash());

		CoverageSession merged = CoverageResources.merge(loaded, second);
		assertEquals(1, merged.getFiles().size());
		List<CoverageElement> probes = getProbes(merged);
		assertEquals(getProbes(first).size(), probes.size());
		for (CoverageElement probe : probes) {
			assertEquals(2, probe.getHits());
		}
	}

	@Test
	public void testSharedRecorderAcrossModules() throws Exception {
		File file = write(folder.getRoot(), "shared.eol", "'a'.println();\nif (false) {\n  'b'.println();\n}\n");
		CoverageRecorder recorder = new CoverageRecorder();
		for (int i = 0; i < 2; i++) {
			EolModule module = new EolModule();
			module.parse(file);
			recorder.attachTo(module);
			module.execute();
		}
		CoverageSession session = recorder.createCoverageSession();
		assertEquals(1, session.getFiles().size());
		SourceFile sourceFile = getFile(session, file);
		assertLine(sourceFile, 1, Status.FULL);
		assertLine(sourceFile, 3, Status.NONE);
		CoverageElement first = getProbes(sourceFile).stream().filter(e -> e.getStartLine() == 1).findFirst().get();
		assertEquals(2, first.getHits());
	}
}
