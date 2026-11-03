/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.test;

import static org.eclipse.epsilon.coverage.test.CoverageTestUtil.*;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.coverage.CoverageRecorder;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Runs programs in which every statement/expression is executed and checks
 * that no probe is reported as missed (i.e. that there are no probes which
 * are never seen by the recorder, even when the code they represent runs).
 */
public class FullCoverageTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	protected static final String XML = "<root><a name='abc'/><a name='defg'/></root>";

	protected PlainXmlModel model(String name, String xml) throws Exception {
		PlainXmlModel model = new PlainXmlModel();
		model.setName(name);
		model.setXml(xml);
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		return model;
	}

	protected void assertFullCoverage(IEolModule module, String fileName, String code, PlainXmlModel... models) throws Exception {
		File file = write(folder.getRoot(), fileName, code);
		module.parse(file);
		assertTrue(module.getParseProblems().toString(), module.getParseProblems().isEmpty());
		for (PlainXmlModel model : models) module.getContext().getModelRepository().addModel(model);
		CoverageRecorder recorder = CoverageRecorder.attach(module);
		module.execute();
		CoverageSession session = recorder.createCoverageSession();
		assertFullyCovered(getFile(session, file));
	}

	@Test
	public void testEvl() throws Exception {
		assertFullCoverage(new EvlModule(), "full.evl",
			"pre { var limit = 1; }\n" +
			"context t_a {\n" +
			"  guard : self.a_name.isDefined()\n" +
			"  constraint Long {\n" +
			"    guard : true\n" +
			"    check : self.a_name.length() > 10\n" +
			"    message : 'Too short: ' + self.a_name\n" +
			"  }\n" +
			"  critique Named {\n" +
			"    check : self.satisfies('Long') or true\n" +
			"  }\n" +
			"}\n" +
			"post { limit.println(); }\n",
			model("M", XML));
	}

	@Test
	public void testEtl() throws Exception {
		assertFullCoverage(new EtlModule(), "full.etl",
			"pre { var count = 0; }\n" +
			"rule A2B\n" +
			"  transform s : Source!t_a\n" +
			"  to t : Target!t_b {\n" +
			"  guard : s.a_name.isDefined()\n" +
			"  t.a_name = s.a_name;\n" +
			"  count = count + 1;\n" +
			"}\n" +
			"post { count.println(); Source!t_a.all.first().equivalent().println(); }\n",
			model("Source", XML), model("Target", "<root/>"));
	}

	@Test
	public void testEcl() throws Exception {
		assertFullCoverage(new EclModule(), "full.ecl",
			"rule Match\n" +
			"  match l : Left!t_a\n" +
			"  with r : Right!t_a {\n" +
			"  guard : l.a_name.isDefined()\n" +
			"  compare : l.a_name = r.a_name\n" +
			"  do { l.a_name.println(); }\n" +
			"}\n",
			model("Left", XML), model("Right", XML));
	}

	@Test
	public void testEpl() throws Exception {
		assertFullCoverage(new EplModule(), "full.epl",
			"pattern Pair\n" +
			"  first : M!t_a,\n" +
			"  second : M!t_a {\n" +
			"  match : first.a_name.length() < second.a_name.length()\n" +
			"  do { first.a_name.println(); }\n" +
			"}\n",
			model("M", XML));
	}

	@Test
	public void testEgx() throws Exception {
		write(folder.getRoot(), "t.egl", "[%=a.a_name%] [%=extra%]\n");
		EgxModule module = new EgxModule(new EglTemplateFactory());
		assertFullCoverage(module, "full.egx",
			"pre { var extra = 'x'; }\n" +
			"rule A2Text transform a : M!t_a {\n" +
			"  guard : a.a_name.isDefined()\n" +
			"  parameters : Map{'extra' = extra}\n" +
			"  template : 't.egl'\n" +
			"  target : 'out/' + a.a_name + '.txt'\n" +
			"}\n",
			model("M", XML));
	}
}
