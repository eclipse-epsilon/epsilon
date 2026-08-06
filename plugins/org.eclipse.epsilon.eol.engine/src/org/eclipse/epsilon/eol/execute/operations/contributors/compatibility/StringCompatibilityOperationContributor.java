/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors.compatibility;

import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class StringCompatibilityOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof String;
	}
	
	@Override
	protected String getTarget() {
		return (String) super.getTarget();
	}

	/**
	 * In the previous version of EOL, EolString.replace
	 * mapped to String.replaceAll.
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public String replace(String regex, String replacement) {
		return getTarget().replaceAll(regex, replacement);
	}
	
	/**
	 * @param lit The String to replace.
	 * @param replacement The substitute String.
	 * @return {@link java.lang.String#replace(CharSequence, CharSequence)}
	 * @since 1.6
	 */
	public String replaceLiteral(String lit, String replacement) {
		return getTarget().replace(lit, replacement);
	}

	/**
	 * Java charAt returns a 'char': keep it for backwards compatibility (e.g.
	 * Ecore2Thrift needs it), but also provide a 'characterAt' EOL version since
	 * EOL does not have that primitive type.
	 */
	public String characterAt(int index) {
		return getTarget().charAt(index) + "";
	}
}
