/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.test.models;

import java.io.File;
import java.io.IOException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.util.FileUtil;

public abstract class Families {

	private Families() {}
	
	public static File getMetaModelFile() throws IOException {
		return FileUtil.getFileStandalone("Families.ecore", Families.class);
	}
	
	public static URI getMetaModelUri() throws IOException {
		return URI.createFileURI(getMetaModelFile().getAbsolutePath());
	}
	
	public static File getBankAccountsModelFile() throws IOException {
		return FileUtil.getFileStandalone("BankAccounts.model", Families.class);
	}
	
	public static URI getBankAccountsModelUri() throws IOException {
		return URI.createFileURI(getBankAccountsModelFile().getAbsolutePath());
	}
	
	
	public static File getProjectDirectory() throws IOException {
		File file = getMetaModelFile();
		
		while (file.isFile() || file.listFiles(f -> ".project".equals(f.getName())).length == 0) {
			file = file.getParentFile();
			
			if (file == null) {
				throw new IllegalStateException("Could not find an Eclipse project in parent directories.");
			}
		}
		
		return file;
	}
	
	public static URI getProjectDirectoryUri() throws IOException {
		return URI.createFileURI(getProjectDirectory().getAbsolutePath() + "/");
	}
}
