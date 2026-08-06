/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util;

public class SearchPreferences {

	public SearchPreferences() {}
	
	protected Boolean followLinks = false;
	protected Boolean includeCommented = true;
	protected String lookUnderMasks = "on";
	
	public void setFollowLinks(Boolean followLinks) {
		this.followLinks = followLinks;
	}
	
	public Boolean isFollowLinks() {
		return followLinks;
	}
	
	public String followLinks(){
		return (followLinks) ? "on" : "off";
	}
	
	/** 
	 * @param lookUnderMasks Any of: graphical, none, functional, all, on, off
	 */
	public void setLookUnderMasks(String lookUnderMasks) {
		this.lookUnderMasks = lookUnderMasks;
	}	
	
	public String getLookUnderMasks() {
		return lookUnderMasks;
	}
	
	public void setIncludeCommented(Boolean includeCommented) {
		this.includeCommented = includeCommented;
	}
	
	public Boolean isIncludeCommented() {
		return includeCommented;
	}
	
	public String includeCommented() {
		return includeCommented ? "on" : "off";
	}
	
	public String searchStatement() {
		return String.format("'LookUnderMasks','%s','FollowLinks','%s', 'IncludeCommented','%s'",getLookUnderMasks(), followLinks(), includeCommented()); 
	}
	
}
