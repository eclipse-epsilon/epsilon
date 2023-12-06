/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.formatter.CompositeFormatter;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.formatter.NullFormatter;
import org.eclipse.epsilon.egl.test.MockContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestTemplateFactory.ResolutionTests.class, TestTemplateFactory.DefaultFormatterTests.class, TestTemplateFactory.InvalidTemplateTests.class})
public class TestTemplateFactory {
		
	public static abstract class TemplateFactoryTests {
		protected EglTemplateFactory tf = new EglTemplateFactory(new MockContext()); 
		
		@Before
		public void setUp() {
			tf = new EglTemplateFactory(new MockContext());
		}
	}
	
	public static class InvalidTemplateTests extends TemplateFactoryTests {
		
		@Test(expected = EglRuntimeException.class)
		public void testTemplateWithEndOfComment() throws Exception {
			EglTemplate template = tf.prepare("*]");
			template.process();
		}
	}
	
	public static class ResolutionTests extends TemplateFactoryTests {
		
		@Test
		public void testResolveAbsolute() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/foo/bar.txt";
			
			assertEquals(new URI(expected), tf.resolveTemplate(expected));
		}
		
		@Test
		public void testResolveRelativeToRoot() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/foo/bar.txt";
			final String root     = "file:///c:/foo/";
			final String relative = "bar.txt"; 
			
			tf.setRoot(new URI(root));
			
			assertEquals(new URI(expected), tf.resolveTemplate(relative));
		}
		
		@Test
		public void testResolveRelativeToRootComplex() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/foo/b%20a%20r.txt";
			final String root     = "file:///c:/foo/baz/";
			final String relative = "..\\b a r.txt"; 
			
			tf.setRoot(new URI(root));
			
			assertEquals(new URI(expected), tf.resolveTemplate(relative));
		}
		
		@Test
		public void testResolveRelativeToTemplateRoot() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/foo/bar/b%20a%20z.txt";
			final String root     = "file:///c:/foo/";
			final String template = "bar";
			final String relative = "b a z.txt"; 
			
			tf.setRoot(new URI(root));
			tf.setTemplateRoot(template);
			
			assertEquals(new URI(expected), tf.resolveTemplate(relative));
		}
		
		@Test
		public void testResolveRelativeToTemplateRootWithSlash() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/foo/bar/b%20a%20z.txt";
			final String root     = "file:///c:/foo/";
			final String template = "bar/";
			final String relative = "b a z.txt"; 
			
			tf.setRoot(new URI(root));
			tf.setTemplateRoot(template);
			
			assertEquals(new URI(expected), tf.resolveTemplate(relative));
		}
		
		@Test
		public void testResolveRelativeToTemplateRootComplex() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/foo/bar/f%20o%20o/b%20a%20z.txt";
			final String root     = "file:///c:/foo/";
			final String template = "bar\\f o o";
			final String relative = "b a z.txt"; 
			
			tf.setRoot(new URI(root));
			tf.setTemplateRoot(template);
			
			assertEquals(new URI(expected), tf.resolveTemplate(relative));
		}
		
		@Test
		public void testResolveRelativeToTemplateRootAbsolute() throws EglRuntimeException, URISyntaxException {
			final String expected = "file:///c:/abs/b%20a%20r/b%20a%20z.txt";
			final String root     = "file:///c:/foo/";
			final String template = "file:///c:/abs/";
			final String relative = "b a r\\b a z.txt"; 
			
			tf.setRoot(new URI(root));
			tf.setTemplateRoot(template);
			
			assertEquals(new URI(expected), tf.resolveTemplate(relative));
		}
	}
	
	public static class DefaultFormatterTests extends TemplateFactoryTests {
		
		@Test
		public void whenNoDefaultFormatterIsSpecifiedThenNullFormatterIsPassedToSubtemplates() throws Exception {
			assertEquals(new NullFormatter(), tf.prepare("Some EGL code").getFormatter());
		}
		
		@Test
		public void specifiedDefaultFormatterIsPassedToSubtemplates() throws Exception {
			final Formatter defaultFormatter = mock(Formatter.class);
			tf.setDefaultFormatter(defaultFormatter);
			
			assertEquals(defaultFormatter, tf.prepare("Some EGL code").getFormatter());
		}
		
		@Test
		public void allSpecifiedDefaultFormattersArePassedToSubtemplates() throws Exception {
			final Formatter firstFormatter  = mock(Formatter.class);
			final Formatter secondFormatter = mock(Formatter.class);
			
			tf.setDefaultFormatters(firstFormatter, secondFormatter);
			
			assertEquals(new CompositeFormatter(firstFormatter, secondFormatter), tf.prepare("Some EGL code").getFormatter());
		}
	}
}
