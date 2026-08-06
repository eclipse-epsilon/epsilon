/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.exceptions;

import static org.eclipse.epsilon.egl.util.FileUtil.FILE_SEP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.junit.BeforeClass;
import org.junit.Test;

public class Exceptions {

	private static File StopPreserve;
	private static File StartPreserve4;
	private static File StartPreserve2;
	private static File StartPreserve2NoCT;
	private static File SetContentType;
	
	private static File Exception;
	private static File Load;
	private static File Process;
	private static File ProcessDeep;
	private static File ProtectedAndControlled;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		StopPreserve       = FileUtil.getFileStandalone("StopPreserve.egl",       Exceptions.class);
		StartPreserve4     = FileUtil.getFileStandalone("StartPreserve4.egl",     Exceptions.class);
		StartPreserve2     = FileUtil.getFileStandalone("StartPreserve2.egl",     Exceptions.class);
		StartPreserve2NoCT = FileUtil.getFileStandalone("StartPreserve2NoCT.egl", Exceptions.class);
		SetContentType     = FileUtil.getFileStandalone("SetContentType.egl",     Exceptions.class);
		
		Exception          = FileUtil.getFileStandalone("Exception.egl",          Exceptions.class);
		Load               = FileUtil.getFileStandalone("Load.egl",               Exceptions.class);
		Process            = FileUtil.getFileStandalone("Process.egl",            Exceptions.class);
		ProcessDeep        = FileUtil.getFileStandalone("ProcessDeep.egl",        Exceptions.class);
		ProtectedAndControlled        = FileUtil.getFileStandalone("ProtectedAndControlled.egl",        Exceptions.class);
	}
	
	
	// OutputBuffer tests \\
	
	@Test
	public void stopPreserve() throws Exception {
		try {
			AcceptanceTestUtil.run(StopPreserve);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("There is no current region to stop.", e.getReason());
			assertEquals(1, e.getLine());
			assertEquals(7, e.getColumn());
		}
	}
	
	@Test
	public void startPreserve4() throws Exception {
		try {
			AcceptanceTestUtil.run(StartPreserve4);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("The current region must be stopped before another region may begin.", e.getReason());
			assertEquals(3, e.getLine());
			assertEquals(5, e.getColumn());
		}
	}
	
	@Test
	public void startPreserve2() throws Exception {
		try {
			AcceptanceTestUtil.run(StartPreserve2);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			e.printStackTrace();
			assertEquals("The current region must be stopped before another region may begin.", e.getReason());
			assertEquals(5, e.getLine());
			assertEquals(5, e.getColumn());
		}
	}
	
	@Test(expected=EglRuntimeException.class)
	public void protectedAndControlled() throws Exception {
		AcceptanceTestUtil.run(ProtectedAndControlled);
		fail("Expected EglRuntimeException");
	}
	
	@Test
	public void startPreserve2NoCT() throws Exception {
		try {
			AcceptanceTestUtil.run(StartPreserve2NoCT);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("A content type must be specified before using startPreserve(id, enabled).", e.getReason());
			assertEquals(2, e.getLine());
			assertEquals(5, e.getColumn());
		}
	}
	
	@Test
	public void setContentType() throws Exception {
		try {
			AcceptanceTestUtil.run(SetContentType);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("'NotAContentType' is not a recognised content type.", e.getReason());
			assertEquals(2, e.getLine());
			assertEquals(5, e.getColumn());
		}
	}
	
	
		
	// EglTemplate tests \\
	
	@Test
	public void templateThatThrowsAnException() throws Exception {
		try {
			AcceptanceTestUtil.run(Exception);
			fail("Expected EglRuntimeException");
		}
		catch (EglRuntimeException e) {
			assertTrue(typeOfCauses(e).contains(ArithmeticException.class));
		}
	}
	
	private static Collection<Class<? extends Throwable>> typeOfCauses(Exception e) {
		final Collection<Class<? extends Throwable>> causes = new HashSet<>();
		
		Throwable current = e;
		while (current.getCause() != null) {
			causes.add(current.getCause().getClass());
			current = current.getCause();
		}

		return causes;
	}


	@Test
	public void load() throws Exception {
		try {
			AcceptanceTestUtil.run(Load);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("Template not found 'include" + FILE_SEP + "NotHere.egl'", e.getReason());
			assertEquals(3, e.getLine());
			assertEquals(16, e.getColumn());
		}
	}
	
	@Test
	public void process() throws Exception {
		try {
			AcceptanceTestUtil.run(Process);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("There is no current region to stop.", e.getReason());
			assertEquals(3, e.getLine());
			assertEquals(3, e.getColumn());
		}
	}
	
	@Test
	public void processDeep() throws Exception {
		try {
			AcceptanceTestUtil.run(ProcessDeep);
			fail("Expected EglRuntimeException");
			
		} catch (EglRuntimeException e) {
			assertEquals("There is no current region to stop.", e.getReason());
			assertEquals(3, e.getLine());
			assertEquals(3, e.getColumn());
		}
	}
}

