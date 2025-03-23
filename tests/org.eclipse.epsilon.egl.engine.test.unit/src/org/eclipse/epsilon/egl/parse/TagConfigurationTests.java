package org.eclipse.epsilon.egl.parse;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.egl.EglModule;
import org.junit.Test;

public class TagConfigurationTests {
	
	@Test
	public void testSquareBrackets() throws Exception {
		EglModule module = new EglModule();
		module.getTemplateFactory().setTagConfiguration(
				new EglTagConfiguration("[[", "]]", "[[*", "*]]"));
		
		module.parse("[[* Comment *]][[ for (i in 1.to(2)){ ]] [[= i ]] [[ } ]][[* Comment *]]");
		
		assertEquals(" 1 2", module.execute());
	}
	
	@Test
	public void testAspLikeBrackets() throws Exception {
		EglModule module = new EglModule();
		module.getTemplateFactory().setTagConfiguration(
				new EglTagConfiguration("<%", "%>", "<*", "*>"));
		
		module.parse("<* Comment *><% for (i in 1.to(2)){ %> <%= i %> <% } %><* Comment *>");
		
		assertEquals(" 1 2", module.execute());
	}
	
}
