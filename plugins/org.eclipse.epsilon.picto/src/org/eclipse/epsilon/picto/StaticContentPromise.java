/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

import java.io.File;
import java.nio.file.Files;

public class StaticContentPromise implements ContentPromise {

	protected String content;
	protected File file;
	
	public StaticContentPromise(String content) {
		this.content = content;
	}
	
	public StaticContentPromise(String content, File file) {
		this.content = content;
		this.file = file;
	}
	
	public StaticContentPromise(File file) {
		this.file = file;
	}
	
	@Override
	public String getContent() throws Exception {
		if (content != null) return content;
		if (file != null) {
			content = new String(Files.readAllBytes(file.toPath()));
		}
		return content;
	}
	
	public File getFile() {
		return file;
	}
	
}