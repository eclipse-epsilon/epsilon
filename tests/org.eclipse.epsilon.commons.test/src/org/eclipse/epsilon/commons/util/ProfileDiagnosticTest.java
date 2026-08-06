/*********************************************************************
* Copyright (c) 2018 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.commons.util;

import static org.eclipse.epsilon.common.util.profiling.ProfileDiagnostic.MemoryUnit.*;
import static org.junit.Assert.*;
import org.eclipse.epsilon.common.util.profiling.ProfileDiagnostic.MemoryUnit;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ProfileDiagnosticTest {

	static final double PRECISION = 0.0001;
	
	@Test
	public void testIdempotence() {
		long memory = Runtime.getRuntime().maxMemory();
		assertEquals(memory, (long) MemoryUnit.convertFromBytes(BYTES, memory));
		assertEquals(memory, MemoryUnit.convertUnits(MemoryUnit.BYTES, BYTES, memory), PRECISION);
	}
	
	@Test
	public void testMemoryUnitUpConversion() {
		assertEquals(4d, convertUnits(KB, MB, 4096), PRECISION);
		assertEquals(0.29296875, convertUnits(KB, MB, 300d), PRECISION);
		assertEquals(0.517578125, convertUnits(KB, MB, 530d), PRECISION);
		assertEquals(3d, convertUnits(BYTES, GB, 3221225472d), PRECISION);
		assertEquals(0.2425, convertUnits(BYTES, GB, 260382392.32), PRECISION);
	}

	@Test
	public void testMemoryUnitDownConversion() {
		assertEquals(4194304d, convertUnits(MB, KB, 4096d), PRECISION);
		assertEquals(4096d, convertUnits(MB, KB, 4), PRECISION);
		assertEquals(300d, convertUnits(MB, KB, 0.29296875), PRECISION);
		assertEquals(530d, convertUnits(MB, KB, 0.517578125), PRECISION);
		assertEquals(3221225472d, convertUnits(GB, BYTES, 3d), PRECISION);
		assertEquals(260382392.32, convertUnits(GB, BYTES, 0.2425d), PRECISION);
	}
	
	@Test
	public void testMemoryUnitNoConversion() {
		double[] memInputs = new double[]{
			0, 1024, 1023, 1025, -1024, -1023, -1025, -2530, 2530, 4096, 4095, 16385, 1, 2147483647, 128, 255, 8
		};
		
		for (MemoryUnit unit : MemoryUnit.values()) {
			for (double input : memInputs) {
				assertEquals(input, convertUnits(unit, unit, input), PRECISION);
			}
		}
	}
}
