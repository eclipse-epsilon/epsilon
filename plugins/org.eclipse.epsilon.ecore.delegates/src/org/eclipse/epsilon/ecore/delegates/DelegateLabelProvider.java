/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;

/**
 * A {@link SubstitutionLabelProvider} that can delegate to another
 * 
 * @since 2.5
 */
public interface DelegateLabelProvider extends SubstitutionLabelProvider {
	
	/**
	 * Delegate.
	 *
	 * @param delegate the delegate
	 * @return the delegate label provider
	 */
	DelegateLabelProvider delegate(SubstitutionLabelProvider delegate);
}
