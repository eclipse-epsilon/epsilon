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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebGitFolder extends WebGitFile {
	
	protected List<WebGitFile> children = null;
	
	public static void main(String[] args) throws Exception {

		WebGitFolder folder = new WebGitFolder(
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.emc.contactsmodel/");
		
		for (WebGitFile f : folder.getChildren()) {
			System.err.println(f.getRelativePath() + " - " + f.getClass().getSimpleName());
			if (f instanceof WebGitFolder) {
				for (WebGitFile f2 : ((WebGitFolder)f).getChildren()) {
					System.err.println(">" + f2.getRelativePath() + " - " + f2.getClass().getSimpleName());
				}
			}
		}

	}
	
	public WebGitFolder(String server, String url, WebGitFolder parent) {
		super(server, url, parent);
		if (!url.endsWith("/")) url += "/";		
	}
	
	public WebGitFolder(String server, String url) {
		super(server, url, null);
		if (!url.endsWith("/")) url += "/";
	}

	public List<WebGitFile> getChildren() throws Exception {
		
		if (children == null) {
			
			children = new ArrayList<>();
			List<String> childFileNames = getChildrenFileNames(url);
			
			for (String childFileName : childFileNames) {
				
				if (childFileName.startsWith("..") || childFileName.equals("/")) continue;
				
				if (childFileName.endsWith("/")) children.add(new WebGitFolder(server, childFileName, this));
				else children.add(new WebGitFile(server, childFileName, this));
			}
		}
		
		return children;
	}

	public String getName() {
		String temp = url.substring(0, url.length()-1);
		return temp.substring(temp.lastIndexOf("/")+1);
	}
	
	protected List<String> getChildrenFileNames(String url) throws Exception {
		ArrayList<String> childFileNames = new ArrayList<>();
		String text = getText(server + url);
		Pattern links = Pattern.compile("href='(.*?)'");
		Matcher matcher = links.matcher(text);
		boolean first = true;
		while (matcher.find()) {
			if (!first) { // skip ../
				childFileNames.add(matcher.group(1));
			}
			first = false;
		}
		return childFileNames;
	}
	
	protected String getText(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			return response.toString();
		}
	}
}
