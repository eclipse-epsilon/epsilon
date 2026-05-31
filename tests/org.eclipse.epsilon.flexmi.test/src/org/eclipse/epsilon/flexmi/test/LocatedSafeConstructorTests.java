package org.eclipse.epsilon.flexmi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.io.FileReader;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.flexmi.yaml.LocatedList;
import org.eclipse.epsilon.flexmi.yaml.LocatedMap;
import org.eclipse.epsilon.flexmi.yaml.LocatedSafeConstructor;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;

public class LocatedSafeConstructorTests {

	private Yaml yaml;

	@Before
	public void setUp() {
		yaml = new Yaml(new LocatedSafeConstructor(new LoaderOptions()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void locatedMaps() throws Exception {
		File f = FileUtil.getFileStandalone("models/yaml/located-map.yaml", getClass());
		f.deleteOnExit();

		try (FileReader fr = new FileReader(f)) {
			LocatedMap<Object, Object> lm = yaml.load(fr);

			assertKeyNodeStartsInLine(lm.getLocation("a"), 1);
			assertKeyNodeStartsInLine(lm.getLocation("b"), 2);
			assertKeyNodeStartsInLine(lm.getLocation("c"), 3);
			
			LocatedMap<Object, Object> lmC = (LocatedMap<Object, Object>) lm.get("c");
			// The actual value of the "c" key starts from line 4
			assertNodeStartsInLine(lmC.getLocation(), 4);
			assertKeyNodeStartsInLine(lmC.getLocation("d"), 4);
			assertKeyNodeStartsInLine(lmC.getLocation("e"), 5);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void locatedMapsWithAnchorsAndMerges() throws Exception {
		File f = FileUtil.getFileStandalone("models/yaml/located-map-anchors.yaml", getClass());
		f.deleteOnExit();

		try (FileReader fr = new FileReader(f)) {
			LocatedMap<Object, Object> lm = yaml.load(fr);
			assertKeyNodeStartsInLine(lm.getLocation("d"), 4);

			// We preserve the original lines of the merged keys
			LocatedMap<Object, Object> lmD = (LocatedMap<Object, Object>) lm.get("d");
			assertKeyNodeStartsInLine(lmD.getLocation("b"), 2);
			assertKeyNodeStartsInLine(lmD.getLocation("c"), 3);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void locatedMapsWithCycles() throws Exception {
		File f = FileUtil.getFileStandalone("models/yaml/located-map-cyclic.yaml", getClass());
		f.deleteOnExit();

		try (FileReader fr = new FileReader(f)) {
			LocatedMap<Object, Object> lm = yaml.load(fr);
			assertKeyNodeStartsInLine(lm.getLocation("a"), 1);

			// First level
			LocatedMap<Object, Object> lmA = (LocatedMap<Object, Object>) lm.get("a");
			assertKeyNodeStartsInLine(lmA.getLocation("b"), 2);
			assertKeyNodeStartsInLine(lmA.getLocation("c"), 3);
	
			// We follow the cycle
			LocatedMap<Object, Object> lmC = (LocatedMap<Object, Object>) lmA.get("c");
			assertSame(lmA, lmC);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void locatedSeq() throws Exception {
		File f = FileUtil.getFileStandalone("models/yaml/located-seq.yaml", getClass());
		f.deleteOnExit();

		try (FileReader fr = new FileReader(f)) {
			LocatedList<Object> ll = yaml.load(fr);
			assertNodeStartsInLine(ll.getLocation(), 1);
			assertNodeStartsInLine(ll.getLocation(0), 1);
			assertNodeStartsInLine(ll.getLocation(1), 2);
			assertNodeStartsInLine(ll.getLocation(2), 5);

			LocatedMap<Object, Object> lm1 = (LocatedMap<Object, Object>) ll.get(1);
			assertKeyNodeStartsInLine(lm1.getLocation("y"), 3);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void locatedSeqWithMerge() throws Exception {
		File f = FileUtil.getFileStandalone("models/yaml/located-seq-merge.yaml", getClass());
		f.deleteOnExit();

		try (FileReader fr = new FileReader(f)) {
			LocatedList<Object> ll = yaml.load(fr);

			LocatedMap<Object, Object> lm1 = (LocatedMap<Object, Object>) ll.get(2);
			assertKeyNodeStartsInLine(lm1.getLocation("x"), 7);
			assertKeyNodeStartsInLine(lm1.getLocation("y"), 4);
		}
	}

	protected void assertKeyNodeStartsInLine(NodeTuple nt, int expectedLine) {
		assertNodeStartsInLine(nt.getKeyNode(), expectedLine);
	}

	protected void assertNodeStartsInLine(Node n, int expectedLine) {
		// Note: SnakeYAML starts lines at 0, not 1
		assertEquals(String.format("Node %s should start on line %d", n, expectedLine),
			expectedLine, n.getStartMark().getLine() + 1);
	}

}
