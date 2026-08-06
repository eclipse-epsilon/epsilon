/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - equals() and hashCode
 ******************************************************************************/
package org.eclipse.epsilon.common.module;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.antlr.runtime.Token;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.Region;

public abstract class AbstractModuleElement implements ModuleElement {
	
	protected ArrayList<Comment> comments = new ArrayList<>(0);
	protected ArrayList<ModuleElement> children = new ArrayList<>(3);
	protected ModuleElement parent;
	protected URI uri;
	protected IModule module;
	protected Region region = new Region();
	protected LinkedHashMap<String, Object> data = new LinkedHashMap<>();
	
	@Override
	public void build(AST cst, IModule module) {
		List<Token> commentTokens = cst.getCommentTokens();
		
		comments.ensureCapacity(commentTokens.size());
		
		for (Token commentToken : commentTokens) {
			Comment comment = new Comment(commentToken);
			comment.setUri(cst.getUri());
			comments.add(comment);
		}
	}
	
	@Override
	public List<Comment> getComments() {
		return comments;
	}
	
	public String getDebugInfo() {
		return "";
	}
	
	@Override
	public List<ModuleElement> getChildren() {
		return children;
	}

	@Override
	public void setUri(URI uri) {
		this.uri = uri;
	}

	@Override
	public void setModule(IModule module) {
		this.module = module;
	}

	@Override
	public Region getRegion() {
		return region;
	}

	@Override
	public IModule getModule() {
		return module;
	}

	@Override
	public File getFile() {
		if (uri != null && "file".equals(uri.getScheme())) {
			return new File(uri);
		}
		return null;
	}

	@Override
	public URI getUri() {
		return uri;
	}
	
	@Override
	public void setRegion(Region region) {
		this.region = region;
	}
	
	@Override
	public void setParent(ModuleElement parent) {
		(this.parent = parent).getChildren().add(this);
	}
	
	@Override
	public ModuleElement getParent() {
		return parent;
	}

	@Override
	public String toString() {
		String str = getClass().getSimpleName();
		boolean uriNotNull = uri != null;
		
		if (uriNotNull) {
			str += " ("+uri;
		}
		if (region != null) {
			str += " @ "+region;
		}
		if (uriNotNull) {
			str += ')';
		}
		
		return str;
	}
	
	@Override
	public Map<String, Object> getData() {
		return data;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(uri, region, getParent());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object ame) {
		if (this == ame) return true;
		if (ame == null || this.getClass() != ame.getClass())
			return false;
		
		AbstractModuleElement other = (AbstractModuleElement) ame;
		return
			Objects.equals(this.uri, other.uri) &&
			Objects.equals(this.region, other.region);
	}
}
