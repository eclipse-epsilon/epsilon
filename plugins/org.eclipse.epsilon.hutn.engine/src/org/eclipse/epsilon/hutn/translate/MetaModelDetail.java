/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.translate;

class MetaModelDetail {

	private final Detail nsUriDetail;
	private final Detail configFileDetail;
	
	public MetaModelDetail(Detail nsUriDetail, Detail configFileDetail) {
		this.nsUriDetail      = nsUriDetail;
		this.configFileDetail = configFileDetail;
	}
	
	public Detail getNsUriDetail() {
		return nsUriDetail;
	}
	
	public Detail getConfigFileDetail() {
		return configFileDetail;
	}	
}
