/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget.examples.statemachine.design;

import org.eclipse.epsilon.sirius.widget.examples.statemachine.StateMachine;

/**
 * The services class used by VSM.
 */
public class Services {
	public void execute(StateMachine self) { 		
    	 StatemachineJob j = new StatemachineJob("Running State Machine", self.getInitialState());
    	 j.setUser(true);
    	 j.schedule();
    }
}
