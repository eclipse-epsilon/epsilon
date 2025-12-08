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
	public void testTemplateOperationWithPropertyAccess() throws Exception {
		
		String staticText = "Some static text\n".replaceAll("\n", NEWLINE);
		
		String egl = staticText +
				"[%=t()%]\n  " + 
				"[%@template   \n" +
				"operation t(){ \n" + 
				"var name = EClass.allInstances().first().name;%]\n" +
				staticText + "\n" +
				"[%=name%]\n" +
				"[%}%]".replaceAll("\n", NEWLINE);
		
		EClass   person = anEClass().named("Person").build();
		EPackage model  = aMetamodel().with(person).build();
		generateTrace(egl, model);
		
		trace.assertEquals(staticText.length(), "Trace.all.first.traceLinks.first().destination.region.offset");
		trace.assertEquals(staticText.length() + 1 + "Person".length(), "Trace.all.first.traceLinks.first().destination.region.length");
	}

	@Test
	public void testRecursiveTemplateOperation() throws Exception {
		
		// Generates
		// A
		// B
		// C
		// No spaces - only new lines between A, B and C
		String egl =
				"[%=EClass.all.last().tree()%]" + 
				"[%@template\n" +
				"operation EClass tree(){%]\n" + 
				"[%for (s in self.eSuperTypes){%]\n" +
				"[%=s.tree()%]\n" +
				"[%}%]\n" +
				"[%=self.name%]\n" +
				"[%}%]".replaceAll("\n", NEWLINE);
		
		EClass   a = anEClass().named("A").build();
		EClass   b = anEClass().named("B").build();
		EClass   c = anEClass().named("C").build();
		c.getESuperTypes().add(b);
		b.getESuperTypes().add(a);
		
		EPackage model  = aMetamodel().with(a).with(b).with(c).build();
		generateTrace(egl, model);

		// traceLinks(0) = A.name
		trace.assertEquals(0, "Trace.all.first.traceLinks.println().at(0).destination.region.offset");
		trace.assertEquals(1, "Trace.all.first.traceLinks.at(0).destination.region.length");

		// traceLinks(1) = A.eSuperTypes
		trace.assertEquals(0, "Trace.all.first.traceLinks.println().at(1).destination.region.offset");
		trace.assertEquals(1, "Trace.all.first.traceLinks.at(1).destination.region.length");

		// traceLinks(2) = B.name
		trace.assertEquals(2, "Trace.all.first.traceLinks.println().at(2).destination.region.offset");
		trace.assertEquals(1, "Trace.all.first.traceLinks.at(2).destination.region.length");	

		// traceLinks(3) = B.eSuperTypes
		trace.assertEquals(0, "Trace.all.first.traceLinks.println().at(3).destination.region.offset");
		trace.assertEquals(3, "Trace.all.first.traceLinks.at(3).destination.region.length");
				
		// traceLinks(4) = C.name
		trace.assertEquals(4, "Trace.all.first.traceLinks.println().at(4).destination.region.offset");
		trace.assertEquals(1, "Trace.all.first.traceLinks.at(4).destination.region.length");		
	
		// traceLinks(5) = C.eSuperTypes
		trace.assertEquals(0, "Trace.all.first.traceLinks.println().at(5).destination.region.offset");
		trace.assertEquals(5, "Trace.all.first.traceLinks.at(5).destination.region.length");
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
