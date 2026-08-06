/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.ins.InsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;

public class MarkdownContentTransformer implements ViewContentTransformer {
	
	@Override
	public boolean canTransform(ViewContent content) {
		return StringUtil.isOneOf(content.getFormat().toLowerCase(), "markdown", "md");
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		return new ViewContent("html",
			pictoView.getViewRenderer().getHtml(markdownToHtml(content.getText())),
			content
		);
	}
	
	@Override
	public String getLabel(ViewContent content) {
		return "Markdown";
	}
	
	/**
	 * 
	 * @param md
	 * @return
	 * @since 2.2
	 */
	public static String markdownToHtml(String md) {
		List<org.commonmark.Extension> extensions = Arrays.asList(
			TablesExtension.create(), 
			YamlFrontMatterExtension.create(),
			InsExtension.create(),
			ImageAttributesExtension.create(), 
			StrikethroughExtension.create()
		);
		Parser parser = Parser.builder().extensions(extensions).build();
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
		StringWriter writer = new StringWriter();
		Node document = parser.parse(md);
		renderer.render(document, writer);
		return writer.toString();
	}
	
}
