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
import org.junit.Test;

public class StateflowBlockTypeTests extends AbstractTypeTests {

	private static final String FULL_PATH_STATE_STATEFLOW_BLOCK = "`Stateflow.State`(chart)";
	private static final String FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED = "`Stateflow.State`";
	private static final String FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY = "Stateflow.State";
	private static final String STATE_STATEFLOW_BLOCK = "State";
	private static final String STATEFLOW_BLOCK = "Stateflow";

	@Test
	public void testStateflowType(){
		eol = Type.TYPE.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY);
	}
	
	@Test
	public void testStateflowIsTypeOf(){
		eol = Type.IS_TYPE_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY, Boolean.TRUE.toString());
	}

	@Test
	public void testStateflowIsKindOf(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, Boolean.TRUE.toString());
	}
	
	@Test
	public void testStateflowIsKindOfSF(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATEFLOW_BLOCK, Boolean.TRUE.toString());
	}
	
	@Test
	public void testStateflowIsKindOfBlock(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK, BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testStateflowAll(){
		eol = Type.ALL.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, 1);
	}

	@Test
	public void testStateflowAllOfType(){
		eol = Type.ALL_OF_TYPE.eol(FULL_PATH_STATE_STATEFLOW_BLOCK, STATE_STATEFLOW_BLOCK, 1);
	}

	@Test
	public void testStateflowAllOfKind(){
		eol = Type.ALL_OF_KIND.eol(FULL_PATH_STATE_STATEFLOW_BLOCK, STATE_STATEFLOW_BLOCK, 1);
	}	
	
	/** EMPTY CONSTRUCTOR */
	
	@Test
	public void testStateflowEmptyConstructorType(){
		eol = Type.TYPE.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, 1);
	}
	
	@Test
	public void testStateflowEmptyConstructorIsTypeOf(){
		eol = Type.IS_TYPE_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testStateflowEmptyConstructorIsKindOf(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testStateflowEmptyConstructorAll(){
		eol = Type.ALL.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, 1);
	}

	@Test
	public void testStateflowEmptyConstructorAllOfType(){
		eol = Type.ALL_OF_TYPE.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, 1);
	}

	@Test
	public void testStateflowEmptyConstructorAllOfKind(){
		eol = Type.ALL_OF_KIND.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, STATE_STATEFLOW_BLOCK, 1);
	}	
	
	/** BLOCK **/
	
	@Test
	public void testStateflowBlockType(){
		eol = Type.TYPE.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, BLOCK);
	}
	
	@Test
	public void testStateflowIsTypeOfBlock(){
		eol = Type.IS_TYPE_OF.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, BLOCK, Boolean.FALSE.toString());
	}
	
	@Test
	public void testStateflowIsKindOfState(){
		eol = Type.IS_KIND_OF.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, STATEFLOW_BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testStateflowAllBlock(){
		eol = Type.ALL.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, BLOCK, 1);
	}

	@Test
	public void testStateflowAllOfTypeBlock(){
		eol = Type.ALL_OF_TYPE.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, BLOCK, 0);
	}

	@Test
	public void testStateflowAllOfKindBlock(){
		eol = Type.ALL_OF_KIND.eolNotEmptyConstructor(FULL_PATH_STATE_STATEFLOW_BLOCK, BLOCK, 1);
	}
	
	/** BLOCK EMPTY CONSTRUCTOR **/
	
	@Test
	public void testStateflowEmptyConstructorBlockType(){
		eol = Type.TYPE.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, BLOCK);
	}
	
	@Test
	public void testStateflowEmptyConstructorIsTypeOfBlock(){
		eol = Type.IS_TYPE_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, BLOCK, Boolean.FALSE.toString());
	}

	@Test
	public void testStateflowEmptyConstructorIsKindOfBlock(){
		eol = Type.IS_KIND_OF.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, BLOCK, Boolean.TRUE.toString());
	}

	@Test
	public void testStateflowEmptyConstructorAllBlock(){
		eol = Type.ALL.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, BLOCK, 1);
	}

	@Test
	public void testStateflowEmptyConstructorAllOfTypeBlock(){
		eol = Type.ALL_OF_TYPE.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, BLOCK, 0);
	}

	@Test
	public void testStateflowEmptyConstructorAllOfKindBlock(){
		eol = Type.ALL_OF_KIND.eol(FULL_PATH_STATE_STATEFLOW_BLOCK_EMPTY_QUOTED, BLOCK, 1);
	}
}
