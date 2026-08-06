package org.eclipse.epsilon.egx.engine.test.acceptance.performance;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Performance tests don't need to be part of the test suite")
public class EgxPerformanceTests {

	static File manyProtectedRegionEGX;
	static Path outputRoot;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		outputRoot = Paths.get(FileUtil.getFileStandalone("", EgxPerformanceTests.class).toURI())
				.resolve("" + Math.random());

		if (!Files.exists(outputRoot)) {
			Files.createDirectory(outputRoot);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws IOException {
		Files.walk(outputRoot).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
	}

	@Test
	public void test1000ProtectedRegions() throws Exception {
		executeAndMeasureEgx(1000);
	}

	@Test
	public void test2000ProtectedRegions() throws Exception {
		executeAndMeasureEgx(2000);
	}

	@Test
	public void test3000ProtectedRegions() throws Exception {
		executeAndMeasureEgx(3000);
	}

	@Test
	public void test4000ProtectedRegions() throws Exception {
		executeAndMeasureEgx(4000);
	}

	@Test
	public void test5000ProtectedRegions() throws Exception {
		executeAndMeasureEgx(5000);
	}

	private void executeAndMeasureEgx(int numberOfProtectedRegions) throws Exception {
		final Path egxPath = generateEgxAndEgl(numberOfProtectedRegions);

		EcorePlugin.INSTANCE.log("Executing " + egxPath);

		// Execute once for the initial generation
		long start = System.currentTimeMillis();
		IEgxModule module = new EgxModule(outputRoot);
		module.parse(egxPath);
		module.execute();

		long firstExecution = System.currentTimeMillis();

		// Execute again so the protected regions need to be merged
		module = new EgxModule(outputRoot);
		module.parse(egxPath);
		module.execute();
		long secondExecution = System.currentTimeMillis();

		long firstExecutionTime = firstExecution - start;
		long secondExecutionTime = secondExecution - firstExecution;
		long executionPerProtectedRegion = secondExecutionTime / numberOfProtectedRegions;

		EcorePlugin.INSTANCE.log("First execution took " + firstExecutionTime + " ms");
		EcorePlugin.INSTANCE.log("Second execution took " + secondExecutionTime + " ms");
		EcorePlugin.INSTANCE.log("Took " + executionPerProtectedRegion + " ms per protected region");

		assertTrue("Slow execution detected", executionPerProtectedRegion < 5);
	}

	private Path generateEgxAndEgl(int numberOfProtectedRegions) throws IOException {
		final Path outputPath = outputRoot.resolve(numberOfProtectedRegions + ".egx");

		final StringBuilder builder = new StringBuilder();
		builder.append("rule generate" + numberOfProtectedRegions + " {\n");
		builder.append("  template : \"" + numberOfProtectedRegions + ".egl\"\n");
		builder.append("  target : \"" + numberOfProtectedRegions + ".txt\"\n");
		builder.append("}\n");

		Files.write(outputPath, builder.toString().getBytes());

		generateEgl(numberOfProtectedRegions);

		return outputPath;
	}

	private Path generateEgl(int numberOfProtectedRegions) throws IOException {
		final Path outputPath = outputRoot.resolve(numberOfProtectedRegions + ".egl");

		final StringBuilder builder = new StringBuilder();
		builder.append("[% out.setContentType('Java'); %]\n");
		builder.append("This file containes " + numberOfProtectedRegions + " protected regions.\n");

		for (int i = 0; i < numberOfProtectedRegions; i++) {
			builder.append("[%=out.startPreserve('region" + i + "', false)%]\n");
			builder.append("[%=out.stopPreserve()%]\n");
		}

		Files.write(outputPath, builder.toString().getBytes());

		return outputPath;
	}
}
