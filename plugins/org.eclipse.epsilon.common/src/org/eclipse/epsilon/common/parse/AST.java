/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.parse;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.eclipse.epsilon.common.module.IModule;

public class AST extends CommonTree {

	protected URI uri;
	protected Integer line = null;
	protected Integer column = null;
	protected Region region;
	protected AST annotations;
	protected boolean imaginary;
	protected List<Token> extraTokens = new ArrayList<>();
	protected List<Token> commentTokens = new ArrayList<>();
	protected List<AST> descendants = null;
	protected IModule module;
	protected HashMap<String, Object> properties = null;
	
	public AST() {
		super();
	}
	
	public void build() {}
	
	public AST(CommonTree commontree) {
		super(commontree);
		
		if (commontree.getChildren() != null) {
			for (Object child : commontree.getChildren()) {
				addChild((CommonTree)child);
			}
		}
	}
	
	public AST(Token token, AST real) {
		super(token);
		this.region = real.getRegion();
		this.uri = real.getUri();
		this.module = real.getModule();
	}
	
	public AST(Token token) {
		super(token);
	}
	
	public AST(Token token, URI uri) {
		super(token);
		this.uri = uri;
	}
	
	public AST(Token token, URI uri, IModule module) {
		super(token);
		this.uri = uri;
		this.module = module;
	}
	
	public IModule getModule() {
		return module;
	}
	
	public void setModule(IModule module) {
		this.module = module;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public AST setAnnotationsAst(AST annotations) {
		this.annotations = annotations;
		return this;
	}

	public AST getAnnotationsAst() {
		return annotations;
	}
	
	public HashMap<String, Object> getProperties() {
		if (properties == null) {
			properties = new HashMap<>();
		}
		return properties;
	}
	
	/**
	 * Returns the basename of the URI of this AST, which is the last segment of
	 * its URI. {@link #getFile()} returns <code>null</code> for non-
	 * <code>file:///</code> URIs, which is correct in its own regard, but may
	 * produce NPEs in our case.
	 */
	public String getBasename() {
		final String uriPath = getUri().getPath();
		final int lastSlash = uriPath.lastIndexOf("/");
		final String filename = lastSlash == -1 ? uriPath : uriPath.substring(lastSlash + 1);
		return filename;
	}

	public File getFile() {
		if (uri != null && "file".equals(uri.getScheme())) {
			return new File(uri);
		}
		return null;
	}

	public URI getUri() {
		return uri;
	}

	// Methods for compatibility with ANTLR2

	private AST cast(Tree tree) {
		if (tree instanceof AST) {
			return (AST)tree;
		
		} else if (tree instanceof CommonTree){
			return new AST((CommonTree)tree);
		}
		
		throw new IllegalArgumentException("Cannot cast " + tree.getClass().getSimpleName() + " to AST.");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AST> getChildren() {
		List<?> children = super.getChildren();
		if (children == null) return Collections.emptyList();
		else return (List<AST>) children;
	}
	
	@Override
	public AST getParent() {
		return (AST) parent;
	}
	
	public AST setLine(int line) {
		this.line = line;
		return this;
	}
	
	@Override
	public int getLine() {
		if (this.line == null) {
			return super.getLine();
		}
		else {
			return line;
		}
	}
	
	public AST setColumn(int column) {
		this.column = column;
		return this;
	}
	
	public int getColumn() {
		if (column == null) {
			return super.getCharPositionInLine();
		}
		else {
			return column;
		}
	}
	
	public AST getNextSibling() {
		if (parent == null)
			return null;
		List<?> siblings = parent.getChildren();
		if (this.childIndex >= siblings.size() - 1) {
			return null;
		} else {
			return cast((Tree) siblings.get(this.childIndex + 1));
		}
	}
	
	@SuppressWarnings("all")
	public AST setNextSibling(AST sibling) {
		if (parent != null && sibling != null) {
			((List)parent.getChildren()).add(this.childIndex, sibling);
		}
		return this;
	}
	
	public boolean hasChildren() {
		return getChildCount() > 0;
	}
	
	@Override
	public AST getChild(int i) {
		return cast(super.getChild(i));
	}
	
	public int getNumberOfChildren() {
		return this.getChildCount();
	}

	public AST getFirstChild() {
		if (this.getChildCount() > 0) {
			return cast(this.getChild(0));
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("all")
	public AST setFirstChild(AST child) {
		if (getChildCount() == 0) {
			addChild(child);
		} else { 
			children.add(0, child);
			freshenParentAndChildIndexes();
		}
		return this;
	}
	
	public AST getSecondChild() {
		if (this.getChildCount() > 1) {
			return cast(this.getChild(1));
		} else {
			return null;
		}
	}
	
	public AST getThirdChild() {
		if (this.getChildCount() > 2) {
			return cast(this.getChild(2));
		} else {
			return null;
		}		
	}
	
	public AST getFourthChild() {
		if (this.getChildCount() > 3) {
			return cast(this.getChild(3));
		} else {
			return null;
		}		
	}
	
	public AST getLastChild() {
		if (this.getChildCount() > 0) {
			return cast(this.getChild(this.getChildCount() - 1));
		} else {
			return null;
		}
	}
	
	public Region getRegion() {
		
		if (region == null) {
			region = new Region();
			
			Position startPosition = new Position();
			if (!isImaginary()) {
				startPosition.setLine(this.getLine());
				startPosition.setColumn(this.getColumn());
			}
			else {
				startPosition.setLine(100000);
				startPosition.setColumn(0);
			}
			region.setStart(startPosition);
			
			Position endPosition = new Position();
			if (!isImaginary()) {
				endPosition.setLine(this.getLine());
				endPosition.setColumn(this.getColumn() + ((CommonToken)getToken()).getStopIndex() - ((CommonToken)getToken()).getStartIndex() + 1);
			}
			else {
				endPosition.setLine(-1);
				endPosition.setColumn(0);
			}
			region.setEnd(endPosition);
			
			for (AST child : getChildren()) {
				Region childRegion = child.getRegion();
				if (childRegion.getStart().isBefore(region.getStart())) {
					region.setStart(childRegion.getStart());
				}
				if (childRegion.getEnd().isAfter(region.getEnd())) {
					region.setEnd(childRegion.getEnd());
				}
			}
			
			if (!isImaginary()) {
				for (Token token : getExtraTokens()) {
					if (token == null) continue;
					Position tokenStartPosition = new Position();
					tokenStartPosition.setLine(token.getLine());
					tokenStartPosition.setColumn(token.getCharPositionInLine());
					
					Position tokenEndPosition = new Position();
					tokenEndPosition.setLine(token.getLine());
					tokenEndPosition.setColumn(token.getCharPositionInLine() + token.getText().length());
					
					if (tokenStartPosition.isBefore(region.getStart())) {
						region.setStart(tokenStartPosition);
					}
					if (tokenEndPosition.isAfter(region.getEnd())) {
						region.setEnd(tokenEndPosition);
					}
				}
			}
			
		}
		
		
		return region;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public List<AST> getDescendants() {
		if (descendants == null) {
			descendants = new ArrayList<>();
			collectDescendants(this, descendants, false);
		}
		return descendants;
	}
	
	protected void collectDescendants(AST ast, List<AST> descendants, boolean addAst) {
		if (addAst) descendants.add(ast);
		for (AST child : ast.getChildren()) {
			collectDescendants(child, descendants, true);
		}
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public void setImaginary(boolean imaginary) {
		this.imaginary = imaginary;
	}
	
	public boolean isImaginary() {
		return imaginary || getToken() == null;
	}
	
	public List<Token> getExtraTokens() {
		return extraTokens;
	}
	
	public void setExtraTokens(List<Token> extraTokens) {
		this.extraTokens = extraTokens;
	}
	
	public List<Token> getCommentTokens() {
		return commentTokens;
	}
	
	public void setCommentTokens(List<Token> comments) {
		this.commentTokens = comments;
	}
	
	public String toExtendedStringTreeItem() {
		return getText() + " " + getClass().getSimpleName() + " " + getRegion();
	}
	
	public String toExtendedStringTree() {
		return toExtendedStringTree(0);
	}
	
	protected String toExtendedStringTree(int indent) {
		String toString = "";
		for (int i=0;i<indent;i++) {
			toString += "  ";
		}
		toString += this.toExtendedStringTreeItem() + "\n";
		for (AST child : getChildren()) {
			toString += child.toExtendedStringTree(indent + 1);
		}
		return toString;
	}
}
