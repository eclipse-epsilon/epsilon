/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.status.StatusMessage;
import org.eclipse.epsilon.egl.test.models.Model;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.eclipse.epsilon.egl.util.StringUtil;
import org.eclipse.epsilon.eol.models.IModel;

public class AcceptanceTestUtil {
	
	private AcceptanceTestUtil() {}
	
	private static IEglContext context;
	
	public static void test(File program, File expected, Model... models) throws Exception {
		test(program, FileUtil.read(expected), models);
	}
	
	public static void test(URI program, File expected, Model... models) throws Exception {
		test(program, FileUtil.read(expected), models);
	}
	
	public static void test(Object program, String expected, Model... models) throws Exception {
		final String actual = run(program, models);
		assertEquals(StringUtil.normalizeNewlines(expected), StringUtil.normalizeNewlines(actual));
	}
	
	public static String run(Object program, Model... models) throws Exception {
		return run(new EglFileGeneratingTemplateFactory(), program, models);
	}
	
	public static String run(Object program, IModel model) throws Exception {
		return run(new EglFileGeneratingTemplateFactory(), program, model);
	}
	
	private static EglTemplate current;
	
	public static String run(EglTemplateFactory factory, Object program, Model... modelSpecs) throws Exception {
		final IModel[] models = new IModel[modelSpecs.length];
		for (int i = 0; i < modelSpecs.length; i++) {
			models[i] = modelSpecs[i].loadEmfModel();
		}
		return run(factory, program, models);
	}
	
	public static void parse(Object program) throws Exception {
		setup(new EglFileGeneratingTemplateFactory(), program);
	}
	
	@SuppressWarnings("restriction")
	private static void setup(EglTemplateFactory factory, Object program, IModel... models) throws Exception {
		factory.getContext().getModelRepository().addModels(models);
		current = loadTemplate(factory, program);
		context = current.getModule().getContext();
		current.getParseProblems().forEach(System.err::println);
	}
	
	public static String run(EglTemplateFactory factory, Object program, IModel... models) throws Exception {
		setup(factory, program, models);
		final String result = current.process();
		report();
		return result;
	}
	
	public static void generate(EglTemplateFactory factory, Object program, String destination, IModel... models) throws Exception {
		setup(factory, program, models);
		((EglFileGeneratingTemplate)current).generate(destination);
		report();
	}

	private static EglTemplate loadTemplate(EglTemplateFactory factory, Object program) throws Exception {
		if (program instanceof File) {
			final File file = (File)program;
			factory.initialiseRoot(file.getParentFile().toURI());
			return factory.load(file.getName());
		}
		else if (program instanceof URI) {
			return factory.load((URI)program);
		}
		else if (program instanceof String) {
			return factory.prepare((String)program);
		}
		else {
			throw new IllegalArgumentException("Cannot run a program of type: " + program.getClass().getCanonicalName());
		}
	}
	
	public static Collection<ParseProblem> getParseProblems() {
		return current.getParseProblems();
	}
	
	private static void report() {
		for (StatusMessage message : context.getStatusMessages()) {
			System.out.println(message);
		}
	}
	
	public static List<StatusMessage> getStatusMessages() {
		return context.getStatusMessages();
	}
	
	public static Template getTrace() {
		return context.getTrace();
	}
}
