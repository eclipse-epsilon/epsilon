package org.eclipse.epsilon.eol;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.dom.Import;

public interface IImportManager {
	
	void loadModuleForImport(Import importAst, Class<? extends IModule> moduleImplClass, URI baseURI) throws URISyntaxException;

}
