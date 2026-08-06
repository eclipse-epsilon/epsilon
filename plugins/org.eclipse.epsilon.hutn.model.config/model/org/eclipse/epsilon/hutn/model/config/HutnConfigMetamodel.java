/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.model.config;

import java.io.File;
import java.io.IOException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.util.FileUtil;

public class HutnConfigMetamodel {

	private HutnConfigMetamodel() {}
	
	public static File getMetaModelFile() throws IOException {
		return FileUtil.getFileStandalone("HUTNConfig.ecore", HutnConfigMetamodel.class);
	}
	
	public static URI getMetaModelUri() throws IOException {
		return URI.createFileURI(FileUtil.getFileStandalone("HUTNConfig.ecore", HutnConfigMetamodel.class).getAbsolutePath());
	}
	
	public static File getDefaultConfigModelFile() throws IOException {
		return FileUtil.getFileStandalone("DefaultConfig.model", HutnConfigMetamodel.class);
	}
	
	public static URI getDefaultConfigModelUri() throws IOException {
		return URI.createFileURI(FileUtil.getFileStandalone("DefaultConfig.model", HutnConfigMetamodel.class).getAbsolutePath());
	}
}
