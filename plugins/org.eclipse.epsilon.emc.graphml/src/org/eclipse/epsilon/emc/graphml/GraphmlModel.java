/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml;

import java.io.File;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.muddle.MuddleFactory;
import org.eclipse.epsilon.emc.muddle.MuddleModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class GraphmlModel extends MuddleModel {
	
	public static final String PROPERTY_FILE = "file";
	public static final String PROPERTY_URI = "uri";
	protected File file = null;
	protected String uri = null;
	
	public GraphmlModel() {
		
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver)
			throws EolModelLoadingException {
		super.load(properties, resolver);
		
		String filePath = properties.getProperty(GraphmlModel.PROPERTY_FILE);
		
		if (!StringUtil.isEmpty(filePath)) {
			file = new File(resolver.resolve(filePath));
		}
		else {
			uri = properties.getProperty(GraphmlModel.PROPERTY_URI);
		}
		
		load();
	}
	
	@Override
	public void load() throws EolModelLoadingException {
		if (readOnLoad) {
			GraphmlImporter importer = new GraphmlImporter();
			try {
				muddle = uri != null ? importer.importGraph(uri) : importer.importGraph(file);
			}
			catch (Exception e) {
				throw new EolModelLoadingException(e, this);
			}
		}
		else {
			muddle = MuddleFactory.eINSTANCE.createMuddle();
		}
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}
}
