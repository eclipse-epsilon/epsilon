/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget.examples.statemachine.eol.widget;

import org.eclipse.epsilon.sirius.widget.eol.AbstractEolEditorWidget;
import org.eclipse.epsilon.sirius.widget.examples.statemachine.NamedElement;
import org.eclipse.epsilon.sirius.widget.examples.statemachine.State;
import org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition;

public class StatemachineWidget extends AbstractEolEditorWidget {

	private NamedElement namedElement;

	@Override
	public void load() {
		if (namedElement == null) {
			namedElement = (NamedElement) this.variableManager.getVariables().get("self");
		}

		if (namedElement instanceof State) {
			if (((State) namedElement).getAction() != null) {
				viewer.getTextWidget().setText(((State) namedElement).getAction());
			}
		} else if (namedElement instanceof Transition) {
			if (((Transition) namedElement).getGuard() != null) {
				viewer.getTextWidget().setText(((Transition) namedElement).getGuard());
			}
		}

	}

	@Override
	public void save(String text) {
		this.editingContextAdapter.performModelChange(() -> {
			if (namedElement instanceof State) {
				((State) namedElement).setAction(text);
			} else if (namedElement instanceof Transition) {
				((Transition) namedElement).setGuard(text);
			}
		});

	}
}
