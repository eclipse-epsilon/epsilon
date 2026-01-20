package org.eclipse.epsilon.egl.test.acceptance.outdentation;

import static org.eclipse.epsilon.common.util.FileUtil.getFileStandalone;

import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.junit.Test;

public class OutdentationTests {
	
	@Test
	public void testFor() throws Exception {
		test("For");
	}
	
	@Test
	public void testNestedFor() throws Exception {
		test("NestedFor");
	}
	
	@Test
	public void testForDoubleOutdentation() throws Exception {
		test("ForDoubleOutdentation");
	}
	
	@Test
	public void testForIf() throws Exception {
		test("ForIf");
	}
	
	@Test
	public void testIfIndented() throws Exception {
		test("IfIndented");
	}
	
	@Test
	public void testIfElseIf() throws Exception {
		test("IfElseIf");
	}
	
	@Test
	public void testSwitch() throws Exception {
		test("Switch");
	}
	
	@Test
	public void testTemplateOperation() throws Exception {
		test("TemplateOperation");
	}
	
	@Test
	public void testForIfDoubleOutdentation() throws Exception {
		test("ForIfDoubleOutdentation");
	}
	
	@Test
	public void testBreak() throws Exception {
		test("Break");
	}
	
	@Test
	public void testSpaces() throws Exception {
		test("Spaces");
	}
	
	@Test
	public void testForAndTemplateOperation() throws Exception {
		test("ForAndTemplateOperation");
	}
	
	@Test
	public void testForAndNestedTemplateOperation() throws Exception {
		test("ForAndNestedTemplateOperation");
	}
	
	protected void test(String name) throws Exception {
		AcceptanceTestUtil.test(
				getFileStandalone(name + ".egl", OutdentationTests.class),
				getFileStandalone(name + ".txt", OutdentationTests.class)
		);
	}
	
}
