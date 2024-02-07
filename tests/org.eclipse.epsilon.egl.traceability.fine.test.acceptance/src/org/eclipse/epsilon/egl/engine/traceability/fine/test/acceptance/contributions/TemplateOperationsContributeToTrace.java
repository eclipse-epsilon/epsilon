package org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.EglFineGrainedTraceabilityAcceptanceTest;
import org.junit.Test;

public class TemplateOperationsContributeToTrace extends EglFineGrainedTraceabilityAcceptanceTest {
	
	@Test
	public void testTemplateOperationWithoutIndentation() throws Exception {
		
		String staticText = "Some static text\n".replaceAll("\n", NEWLINE);
		
		String egl = staticText +
				"[%=t()%]\n  " + 
				"[%@template   \n" +
				"operation t(){%]\n" +
				"[%=EClass.all.first.name%]\n" +
				"[%}%]".replaceAll("\n", NEWLINE);
		
		EClass   person = anEClass().named("Person").build();
		EPackage model  = aMetamodel().with(person).build();
		generateTrace(egl, model);
		
		trace.assertEquals(staticText.length(), "Trace.all.first.traceLinks.first().destination.region.offset");
	}
	
	@Test
	public void testTemplateOperationWithIndentation() throws Exception {
		
		String staticText = "Some static text\n".replaceAll("\n", NEWLINE);
		
		String egl = staticText +
				"\t[%=t()%]\n  " + 
				"[%@template   \n" +
				"operation t(){%]\n" +
				"[%for (c in EClass.all){%]" +
				"[%=c.name%]\n" +
				"[%}}%]".replaceAll("\n", NEWLINE);
		
		EClass person = anEClass().named("Person").build();
		EClass task = anEClass().named("Task").build();
		EPackage model = aMetamodel().with(person).with(task).build();
		generateTrace(egl, model);
		
		trace.assertEquals(staticText.length() + 1, "Trace.all.first.traceLinks.first().destination.region.offset");
		trace.assertEquals(staticText.length() + 1 + person.getName().length() + 1 + NEWLINE.length(), "Trace.all.first.traceLinks.second().destination.region.offset");
	}

	@Test
	public void testNestedTemplateOperationWithIndentation() throws Exception {
		String staticText = "Some static text\n".replaceAll("\n", NEWLINE);
		
		String egl = staticText +
				"	[%=t()%] \n"
				+ "[%@template   \n"
				+ "operation t(){%]\n"
				+ "[%for (c in EClass.all){%][%=t2(c)%]\n"
				+ "[%}}\n"
				+ "\n"
				+ "@template\n"
				+ "operation t2(c){%]\n"
				+ "EClass [%=c.name%]\n"
				+ "[%}%]\n".replaceAll("\n", NEWLINE);

		EClass person = anEClass().named("Person").build();
		EClass task = anEClass().named("Task").build();
		EPackage model = aMetamodel().with(person).with(task).build();
		generateTrace(egl, model);

		final int startOfFirstEClassName = staticText.length() + 1 + "EClass ".length();
		final int startOfSecondEClassName = startOfFirstEClassName + person.getName().length() + 1 + NEWLINE.length() + "EClass ".length();

		trace.assertEquals(startOfFirstEClassName, "Trace.all.first.traceLinks.first().destination.region.offset");
		trace.assertEquals(person.getName(), "Trace.all.first.traceLinks.first().destination.region.text");
		trace.assertEquals(startOfSecondEClassName, "Trace.all.first.traceLinks.second().destination.region.offset");
		trace.assertEquals(task.getName(), "Trace.all.first.traceLinks.second().destination.region.text");
	}

}
