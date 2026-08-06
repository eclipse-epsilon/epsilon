/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.transactions;

import java.util.List;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

public abstract class RemoveActiveTransactionTask extends EpsilonTask {
	
	protected String name;
	
	@Override
	public void executeImpl() throws BuildException {
		NamedTransactionSupport theTransactionSupport = null;
		List<NamedTransactionSupport> activeTansactions = getActiveTransactions();
		for (int i = activeTansactions.size()-1; i >= 0; i--) {
			NamedTransactionSupport transactionSupport = activeTansactions.get(i);
			if (transactionSupport.getName().equals(name)) {
				theTransactionSupport = transactionSupport;
				break;
			}
		}
		if (theTransactionSupport == null) throw new BuildException("Transaction " + name + " not found");
		manageTransaction(theTransactionSupport);
		getActiveTransactions().remove(theTransactionSupport);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected abstract void manageTransaction(NamedTransactionSupport transactionSupport);
}
