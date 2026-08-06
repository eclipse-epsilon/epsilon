/*******************************************************************************
\ * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.fine.emf;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.egl.dt.extensions.fineGrainedTracePostprocessor.IFineGrainedTracePostprocessor;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.ModelLocation;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkFactory;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkPackage;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Trace;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;

public class Pojo2Emf implements IFineGrainedTracePostprocessor {
	
	@Override
	public void postprocess(org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace trace) {
		new InMemoryEmfModel("Trace", EmfUtil.createResource(transform(trace)), TextlinkPackage.eINSTANCE).store(trace.getDestination());
	}	
		
	private Trace transform(org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace original) {
		final Trace transformed = TextlinkFactory.eINSTANCE.createTrace();
		transformed.getTraceLinks().addAll(transform(original.traceLinks));
		return transformed;
	}
	
	private Collection<? extends TraceLink> transform(Collection<org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink> original) {
		final Collection<TraceLink> transformed = new LinkedList<>();
		
		for (org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink element : original) {
			transformed.add(transform(element));
		}
		
		return transformed;
	}
	
	private TraceLink transform(org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink original) {
		final TraceLink transformed = TextlinkFactory.eINSTANCE.createTraceLink();
		transformed.setSource(transform(original.source));
		transformed.setDestination(transform(original.destination));
		return transformed;
	}

	private ModelLocation transform(org.eclipse.epsilon.egl.engine.traceability.fine.trace.ModelLocation original) {
		if (!(original.modelElement instanceof EObject))
			throw new IllegalArgumentException("Cannot translate model locations whose model element is: " + original.modelElement);
			
		final EmfModelLocation transformed = TextlinkFactory.eINSTANCE.createEmfModelLocation();
		transformed.setModelElement((EObject)original.modelElement);
		transformed.setPropertyName(original.propertyName);
		return transformed;					
	}

	private TextLocation transform(org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation original) {
		final TextLocation transformed = TextlinkFactory.eINSTANCE.createTextLocation();
		transformed.setResource(original.resource);
		transformed.setRegion(transform(original.region));
		return transformed;
	}

	private Region transform(org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region original) {
		final Region transformed = TextlinkFactory.eINSTANCE.createRegion();
		transformed.setOffset(original.offset);
		transformed.setLength(original.length);
		transformed.setText(original.text);
		return transformed;
	}
}
