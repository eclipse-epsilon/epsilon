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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.picto.dom.Patch;
import org.eclipse.epsilon.picto.transformers.ExceptionContentTransformer;
import org.eclipse.epsilon.picto.transformers.PatchContentTransformer;
import org.eclipse.epsilon.picto.transformers.ViewContentTransformer;
import org.eclipse.epsilon.picto.transformers.ViewContentTransformerExtensionPointManager;

public class ViewContent {
	
	protected String format;
	protected String text;
	protected List<Layer> layers;
	protected boolean active;
	protected String label;
	protected List<Patch> patches;
	protected Set<java.net.URI> baseUris;
	protected ViewContent next = undefined;
	protected ViewContent previous = null;
	protected File file;
	protected static final ViewContent undefined = new ViewContent("We shouldn't be here","xxx", null, Collections.emptyList(), Collections.emptyList(), Collections.emptySet());
	
	protected static List<ViewContentTransformer> viewContentTransformers;
	
	public static List<ViewContentTransformer> getViewContentTransformers() {
		if (viewContentTransformers == null) {
			viewContentTransformers = new ArrayList<>();
			viewContentTransformers.add(new PatchContentTransformer());
			viewContentTransformers.addAll(new ViewContentTransformerExtensionPointManager().getExtensions());
		}
		return viewContentTransformers;
	}
	
	/**
	 * 
	 * @param format
	 * @param text
	 * @param other
	 * @since 2.2
	 */
	public ViewContent(String format, String text, ViewContent other) {
		this(format, text,
			other != null ? other.getFile() : null,
			other != null ? other.getLayers() : new ArrayList<>(),
			other != null ? other.getPatches() : new ArrayList<>(),
			other != null ? other.getBaseUris() : new LinkedHashSet<>()
		);
	}
	
	public ViewContent(String format, String text) {
		this(format, text, null, Collections.emptyList(), Collections.emptyList(), Collections.emptySet());
	}
	
	public ViewContent(String format, String text, File file, List<Layer> layers, List<Patch> patches, Set<java.net.URI> baseUris) {
		this.format = format;
		this.text = text;
		this.patches = patches;
		this.layers = layers;
		this.file = file;
		this.baseUris = baseUris;
		setLabel();
	}
	
	protected void setLabel() {
		getViewContentTransformers().stream()
			.filter(vct -> vct.canTransform(this) && !(vct instanceof PatchContentTransformer))
			.findAny()
			.ifPresent(vct -> this.label = vct.getLabel(this));
	}
	
	public String getFormat() {
		return format;
	}
	
	public String getText() {
		return text;
	}
	
	public ViewContent getFinal(PictoView pictoView) {
		ViewContent finalView = this;
		for (ViewContent temp; (temp = finalView.getNext(pictoView)) != null; finalView = temp);
		return finalView;
	}
	
	public ViewContent getNext(PictoView pictoView) {
		if (next == undefined) {
			for (ViewContentTransformer viewContentTransformer : getViewContentTransformers()) {
				if (viewContentTransformer.canTransform(this)) {
					try {
						next = viewContentTransformer.transform(this, pictoView);
						if (next != null) next.setPrevious(this);
					}
					catch (Exception e) {
						next = new ExceptionContentTransformer().getViewContent(e, pictoView);
					}
					break;
				}
			}
		}
		if (next == undefined) next = null;
		return next;
	}
	
	public void setPrevious(ViewContent previous) {
		this.previous = previous;
	}
	
	public ViewContent getPrevious() {
		return previous;
	}
	
	public List<ViewContent> getAllPrevious() {
		List<ViewContent> allPrevious = new ArrayList<>();
		ViewContent p = previous;
		while (p != null) {
			allPrevious.add(previous);
			p = p.getPrevious();
		}
		return allPrevious;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<Patch> getPatches() {
		return patches;
	}
	
	public List<Layer> getLayers() {
		return layers;
	}
	
	public Set<java.net.URI> getBaseUris() {
		return baseUris;
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String toString() {
		return text;
	}

	public void setNext(ViewContent newContent) {
		next = newContent;
	}

	public ViewContent getSourceContent(PictoView pictoView) {
		return new ViewContent("text", text, file, layers, patches, baseUris).getNext(pictoView);
	}
	
	/**
	 * 
	 * @return
	 * @since 2.2
	 */
	public boolean isImage() {
		return StringUtil.isOneOf(getFormat().toLowerCase(), "svg", "png", "bmp", "gif", "jpg", "tif", "jpeg", "tiff");
	}
}
