/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.examples;


public class WebGitFile {
	
	protected String server;
	protected String url;
	protected WebGitFolder parent = null;
	
	public WebGitFile(String server, String url, WebGitFolder parent) {
		this.server = server;
		this.url = url;
		this.parent = parent;
	}
	
	public String getName() {
		return url.substring(url.lastIndexOf("/")+1);
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getRelativePath() {
		WebGitFolder root = getParent();
		if (root == null) return "";
		while (root.getParent() != null) root = root.getParent();
		
		return url.substring(root.url.length());
	}

	public WebGitFolder getParent() {
		return parent;
	}
}
