/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.io.File;
import java.net.URI;
import java.util.Objects;
import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class Import extends AbstractModuleElement {
	
	private IEolModule parentModule;
	private IModule importedModule;
	private boolean loaded = false;
	private boolean found = false;
	protected StringLiteral pathLiteral;

	public IEolModule getParentModule() {
		return parentModule;
	}

	public void setParentModule(IEolModule parentModule) {
		this.parentModule = parentModule;
	}
	
	public void setImportedModule(IModule importedModule) {
		this.importedModule = importedModule;
	}
	
	public IModule getImportedModule() {
		return importedModule;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		pathLiteral = (StringLiteral) module.createAst(cst.getFirstChild(), this);
	}
	
	public void load(URI baseUri) {
		try {
			final File file = new File(getPath());
			URI uri;
			
			if (file.isAbsolute()) {
				if (!file.exists()) return;
				uri = file.toURI();
			} else {
				uri = UriUtil.resolve(getPath(), baseUri);
			}
			
			// Detect and handle circular imports gracefully
			for (
				IEolModule ancestor = parentModule;
				ancestor != null && !found;
				ancestor = ancestor.getParentModule()
			) {
				if (Objects.equals(ancestor.getSourceUri(), uri)) {
					found = true;
					importedModule = ancestor;
				}
			}
			if (!found) {
				try {
					importedModule.parse(uri);
				}
				catch (Exception e) {
					// Useful for plugin developers: fall back on platform:/resource if platform:/plugin does not work
					if ("platform".equals(uri.getScheme()) && uri.getPath().startsWith("/plugin/")) {
						final String sNewURI = uri.toString().replaceFirst("/plugin/", "/resource/");
						uri = new URI(sNewURI);
						importedModule.parse(uri);
					}
					else {
						throw e;
					}
				}
			}
			
			found = true;
			loaded = importedModule.getParseProblems().isEmpty();
		}
		catch (Exception ex) {
			// Ignore the exception. The import's loaded flag is still false
			// and it's up to the importing module to do something about it.
		}
	}
	
	@Override
	public String toString() {
		return "import '" + getPath() + "'";
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	
	public boolean isFound() {
		return found;
	}
	
	@Override
	public IModule getModule() {
		return importedModule;
	}
	
	public StringLiteral getPathLiteral() {
		return pathLiteral;
	}
	
	public void setPathLiteral(StringLiteral pathLiteral) {
		this.pathLiteral = pathLiteral;
	}
	
	public String getPath() {
		return pathLiteral.getValue();
	}
	
	public void setContext(IEolContext context) {
		if (importedModule instanceof IEolModule) {
			IEolModule module = (IEolModule) importedModule;
			if (module.getContext() != context) {
				module.setContext(context);
				for (Import import_ : module.getImports()) {
					import_.setContext(context);
				}
			}
		}
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
