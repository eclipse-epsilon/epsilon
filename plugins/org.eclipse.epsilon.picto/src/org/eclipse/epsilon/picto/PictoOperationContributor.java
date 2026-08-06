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

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.picto.transformers.ExternalContentTransformation;

public class PictoOperationContributor extends OperationContributor {
	
	protected IEgxModule module;
	protected static Map<java.net.URI, Path> cache = new HashMap<>();
	
	public PictoOperationContributor(IEgxModule module) {
		this.module = module;
	}
	
	@Override
	public boolean contributesTo(Object target) {
		return target == EolNoType.NoInstance;
	}
	
	// For backwards compatibility
	public String getImage(String path) {
		return getFile(path).toString();
	}
	
	public String getURI(String path) {
		return getURI(path, false);
	}
	
	public String getURI(String path, boolean timestamp) {
		try {
			String uri = getFile(path).toUri().toString();
			if (timestamp) uri += "?" + System.currentTimeMillis();
			return uri;
		}
		catch (Exception ex) {}
		return path;
	}
	
	public Path getFile(String path) {
		if (module.getFile() != null) {
			return Paths.get(module.getFile().getParent(), path).toAbsolutePath();
		}
		else if (module.getUri() != null) {
			try {
				URI imageUri = module.getUri().resolve(path);

				Path tempImagePath;
				synchronized (cache) {
					tempImagePath = cache.get(imageUri);
					if (tempImagePath == null) try (InputStream in = imageUri.toURL().openStream()) {
						String extension = FileUtil.getExtension(path);
						Path temp = ExternalContentTransformation.createTempFile(extension);
						Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
						cache.put(imageUri, tempImagePath = temp.toAbsolutePath());
					}
				}
				return tempImagePath;
			}
			catch (Exception e) {}
		}
		return Paths.get(path);
	}
}
