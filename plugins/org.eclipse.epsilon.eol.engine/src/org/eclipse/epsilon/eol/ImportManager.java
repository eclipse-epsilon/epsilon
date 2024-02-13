package org.eclipse.epsilon.eol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.eol.dom.Import;

public class ImportManager implements IImportManager {
	
	protected Map<URI, IModule> cache = new HashMap<>();

	@Override
	public void loadModuleForImport(Import import_, Class<? extends IModule> moduleImplClass, URI baseURI) throws URISyntaxException {
		String importPath = import_.getPath();

		final URI importUri = UriUtil.resolve(importPath, baseURI).normalize();
		final IEolModule parentModule = import_.getParentModule();
		
		IModule module = cache.get(importUri);
		if (module != null) {
			import_.setImportedModule(module);
			import_.setLoaded(true);
		}
		else {
			try {
				module = moduleImplClass.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			/*
			 * The module must be added to the cache before import_.load is called,
			 * otherwise there will be infinite recursion in the case of circular import.
			 */
			cache.put(importUri, module);

			/*
			 * If possible, ensure child modules share this import manager so the cache is
			 * most effective.
			 */
			if (module instanceof IEolModule) {
				IEolModule eolModule = (IEolModule) module;
				eolModule.setImportManager(this);

				/*
				 * Parent module must be set, to allow things like ETL overriding the way in
				 * which ::= works in imported EOL modules.
				 */
				eolModule.setParentModule(parentModule);
			}
			
			import_.setImportedModule(module);

			// Use existing import loading logic
			import_.load(baseURI);
		}
	}

}
