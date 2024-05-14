/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.epsilon.eol.execute.context.SingleFrame;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Tracks all the information needed to navigate the state of a suspended
 * program. Users should call {@link #suspended()} whenever the program
 * suspends, to allow it to reset.
 */
public class SuspendedState {

	private final AtomicInteger nextReference = new AtomicInteger();
	private final BiMap<Integer, SingleFrame> frames = HashBiMap.create();

	public void suspended() {
		synchronized (frames) {
			nextReference.set(0);
			frames.clear();
		}
	}

	public int getReference(SingleFrame sc) {
		synchronized (frames) {
			Integer reference = frames.inverse().get(sc);
			if (reference == null) {
				reference = nextReference.incrementAndGet();
				frames.put(reference, sc);
			}
			return reference;
		}
	}

	public SingleFrame getFrame(int reference) {
		synchronized (frames) {
			return frames.get(reference);
		}
	}

}
