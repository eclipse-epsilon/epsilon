/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor.hyperlinks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.epsilon.egl.dt.traceability.editor.TextLinkModel;
import org.eclipse.epsilon.egl.dt.traceability.editor.hyperlinks.TextLinkHyperlinkDetector.HyperlinkSpec;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkFactory;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TextLinkHyperlinkDetectorTests.FilteringTests.class, TextLinkHyperlinkDetectorTests.SpecCreationTests.class})
public class TextLinkHyperlinkDetectorTests {

	public static class FilteringTests {
		private final DocumentLocation hoverLocation = mock(DocumentLocation.class);
		private final TextLinkModel model = mock(TextLinkModel.class);
		private final TraceLink traceLink = createTraceLink();
		private final TextLinkHyperlinkDetector hyperlinkDetector = new TextLinkHyperlinkDetector() {
			@Override
			boolean isActive(TraceLink candidate) {
				if (candidate == traceLink) return true;
				else return super.isActive(candidate);
			}
		};
		
		private org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region destRegion;
	
		@Before
		public void setup() {
			when(model.getTraceLinks()).thenReturn(Collections.singleton(traceLink));
			destRegion = traceLink.getDestination().getRegion();
		}
		
		@Test
		public void filterIncludesTraceLinksWhoseDestinationsContainHoverLocation() throws Exception {
			when(hoverLocation.isIn(destRegion)).thenReturn(true);
			assertTrue(filter().contains(traceLink));
		}
	
		@Test
		public void filterExcludesTraceLinksWhoseDestinationsDoNotContainHoverLocation() throws Exception {
			when(hoverLocation.isIn(destRegion)).thenReturn(false);
			assertTrue(filter().isEmpty());
		}
	
		
		private TraceLink createTraceLink() {
			final TraceLink traceLink = TextlinkFactory.eINSTANCE.createTraceLink();
			traceLink.setDestination(TextlinkFactory.eINSTANCE.createTextLocation());
			traceLink.getDestination().setRegion(TextlinkFactory.eINSTANCE.createRegion());
			return traceLink;
		}
		
		private Collection<TraceLink> filter() throws EolModelElementTypeNotFoundException {
			return hyperlinkDetector.matchingTraceLinksFor(hoverLocation, model);
		}
	}

	public static class SpecCreationTests {
		
		@Test
		public void simple() {
			final TraceLink traceLink = createTraceLink(2, 4);
			final Collection<TraceLink> traceLinks = Collections.singleton(traceLink);
			
			Collection<HyperlinkSpec> result = new TextLinkHyperlinkDetector().createHyperlinkSpecsFor(traceLinks);
			
			assertEquals(new org.eclipse.jface.text.Region(2, 4), result.iterator().next().region);
			assertEquals(traceLink.getSource(), result.iterator().next().source);
		}
		
		private TraceLink createTraceLink(int offset, int length) {
			final TraceLink traceLink = TextlinkFactory.eINSTANCE.createTraceLink();
			traceLink.setSource(TextlinkFactory.eINSTANCE.createEmfModelLocation());
			traceLink.setDestination(TextlinkFactory.eINSTANCE.createTextLocation());
			traceLink.getDestination().setRegion(createRegion(offset, length));
			return traceLink;
		}

		private org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region createRegion(int offset, int length) {
			org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region region = TextlinkFactory.eINSTANCE.createRegion();
			region.setOffset(offset);
			region.setLength(length);
			return region;
		}
	}
}
