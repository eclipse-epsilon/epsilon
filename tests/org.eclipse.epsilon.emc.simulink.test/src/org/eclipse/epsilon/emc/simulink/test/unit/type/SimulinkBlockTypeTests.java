/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit.type;

import org.eclipse.epsilon.emc.simulink.test.util.AbstractTypeTests;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimulinkBlockTypeTests extends AbstractTypeTests {

	private static final String FULL_PATH_GAIN_SIMULINK_BLOCK = "`simulink/Math Operations/Gain`";
	private static final String GAIN_SIMULINK_BLOCK = "Gain";
	
	@Test
	public void testTypeSimulinkBlock(){
		eol = Type.TYPE.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, GAIN_SIMULINK_BLOCK);
	}
	
	@Test
	public void testIsTypeOfSimulinkBlock(){
		eol = Type.IS_TYPE_OF.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, GAIN_SIMULINK_BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testIsKindOfSimulinkBlock(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, GAIN_SIMULINK_BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testAllSimulinkBlock(){
		eol = Type.ALL.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, GAIN_SIMULINK_BLOCK, 1);
	}

	@Test
	public void testAllOfTypeSimulinkBlock(){
		eol = Type.ALL_OF_TYPE.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, GAIN_SIMULINK_BLOCK, 1);
	}

	@Test
	public void testAllOfKindSimulinkBlock(){
		eol = Type.ALL_OF_KIND.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, GAIN_SIMULINK_BLOCK, 1);
	}
	
	/** BLOCK **/
	
	@Test
	public void testTypeBlock(){
		eol = Type.TYPE.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, BLOCK);
	}
	
	@Test
	public void testIsTypeOfBlock(){
		eol = Type.IS_TYPE_OF.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, BLOCK, Boolean.FALSE.toString());
	}

	@Test
	public void testIsKindOfBlock(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testAllBlock(){
		eol = Type.ALL.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, BLOCK, 1);
	}

	@Test
	public void testAllOfTypeBlock(){
		eol = Type.ALL_OF_TYPE.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, BLOCK, 0);
	}

	@Test
	public void testAllOfKindBlock(){
		eol = Type.ALL_OF_KIND.eol(FULL_PATH_GAIN_SIMULINK_BLOCK, BLOCK, 1);
	}
	
}
