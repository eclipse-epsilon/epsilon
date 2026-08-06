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

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;
import org.eclipse.epsilon.picto.XmlHelper;
import org.eclipse.epsilon.picto.transformers.elements.AbsoluteLinkElementTransformer;
import org.eclipse.epsilon.picto.transformers.elements.AbsolutePathElementTransformer;
import org.eclipse.epsilon.picto.transformers.elements.HtmlElementTransformer;
import org.eclipse.epsilon.picto.transformers.elements.HtmlElementTransformerExtensionPointManager;
import org.eclipse.epsilon.picto.transformers.elements.MermaidRendererHeadAppender;
import org.eclipse.epsilon.picto.transformers.elements.MetroHeadAppender;
import org.eclipse.epsilon.picto.transformers.elements.PictoViewElementTransformer;
import org.eclipse.epsilon.picto.transformers.elements.RenderCodeElementTransformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HtmlContentTransformer implements ViewContentTransformer {
	
	protected List<HtmlElementTransformer> htmlElementTransformers;
	
	public HtmlContentTransformer() {
		htmlElementTransformers = new ArrayList<>();
		htmlElementTransformers.addAll(Arrays.asList(
			new AbsolutePathElementTransformer("img",  "src"),
			new AbsolutePathElementTransformer("link",  "href"),
			new AbsolutePathElementTransformer("script",  "src"),
			new AbsoluteLinkElementTransformer("a",  "href"),
			new PictoViewElementTransformer(), 
			new RenderCodeElementTransformer(),
			new MermaidRendererHeadAppender(),
			new MetroHeadAppender()
		));
		htmlElementTransformers.addAll(new HtmlElementTransformerExtensionPointManager().getExtensions());
	}
	
	protected XmlHelper xmlHelper = new XmlHelper();
	
	@Override
	public boolean canTransform(ViewContent content) {
		return StringUtil.isOneOf(content.getFormat().toLowerCase(), "html", "htm");
	}

	@Override
	public String getLabel(ViewContent content) {
		return "HTML";
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		
		if (content instanceof FinalViewContent) return null;
		
		String text = content.getText();
		
		try {
			Document document = xmlHelper.parse(text);
			addZoom(document);
			
			for (HtmlElementTransformer htmlElementTransformer : htmlElementTransformers) {
				
				htmlElementTransformer.setPictoView(pictoView);
				htmlElementTransformer.setViewContent(content);
				
				NodeList nodeList = getElements(document, htmlElementTransformer.getXPath());
				int length = nodeList.getLength();
				for (int i = 0; i < length; i++) {
					htmlElementTransformer.transform((Element) nodeList.item(i));
				}
			}
			
			return new FinalViewContent("html", "<!DOCTYPE html>"+xmlHelper.getXml(document), content);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	protected void addZoom(Document document) {
		Element html, body;
		Element root = document.getDocumentElement();
		
		// Create/get the html element
		if (root.getNodeName().equalsIgnoreCase("html")) html = root;
		else {
			html = document.createElement("html");
		}
		
		// Create/get the body element
		body = getElementByName(html, "body");
		if (body == null) {
			body = document.createElement("body");
			html.appendChild(body);
		}
		
		body.setAttribute("style", "zoom:${picto-zoom};" + body.getAttribute("style"));
	}
	
	protected Element getElementByName(Element parent, String name) {
		NodeList nodeList = parent.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equalsIgnoreCase(name)) return (Element) node;
		}
		return null;
	}
	
	protected NodeList getElements(Document document, String xpath) throws Exception {
		XPath xPath = XPathFactory.newInstance().newXPath();
		return (NodeList) xPath.compile(xpath).evaluate(document, XPathConstants.NODESET);

	}
	
	static final class FinalViewContent extends ViewContent {
		public FinalViewContent(String format, String text, ViewContent content) {
			super(format, text, content);
		}
	}
	
	
	/**
	 * Converts the HTML to the desired format using Pandoc.
	 * 
	 * @param html The raw HTML text.
	 * @param outputExt The output file format.
	 * @param additionalArgs Other arguments to pass to Pandoc.
	 * @return The raw output of the file as a String.
	 * @throws IOException If invoking Pandoc fails.
	 */
	public static String pandocRaw(String html, String outputExt, Object... additionalArgs) throws IOException {
		Path htmlDocument = ExternalContentTransformation.createTempFile("html", html.getBytes());
		ExternalContentTransformation ect = invokePandoc(htmlDocument, outputExt, additionalArgs);
		return new String(ect.call());
	}
	
	/**
	 * Converts the HTML to the desired format using Pandoc.
	 * 
	 * @param html The raw HTML text.
	 * @param outputExt The output file format.
	 * @param additionalArgs Other arguments to pass to Pandoc.
	 * @return The output file.
	 * @throws IOException If invoking Pandoc fails.
	 */
	public static Path pandoc(String html, String outputExt, Object... additionalArgs) throws IOException {
		return pandoc(ExternalContentTransformation.createTempFile("html", html.getBytes()), outputExt, additionalArgs);
	}
	
	/**
	 * Converts the HTML to the desired format using Pandoc.
	 * 
	 * @param document The HTML file.
	 * @param outputExt The output file format.
	 * @param additionalArgs Other arguments to pass to Pandoc.
	 * @return The output file.
	 * @throws IOException If invoking Pandoc fails.
	 */
	public static Path pandoc(Path document, String outputExt, Object... additionalArgs) throws IOException {
		ExternalContentTransformation ect = invokePandoc(document, outputExt, additionalArgs);
		ect.call();
		return ect.getOutputFile();
	}
	
	/**
	 * Converts the HTML to the desired format using Pandoc.
	 * 
	 * @param html The HTML file.
	 * @param outputExt The output file format.
	 * @param additionalArgs Other arguments to pass to Pandoc.
	 * @return The transformation, prior to running it.
	 * @throws IOException If invoking Pandoc fails.
	 */
	protected static ExternalContentTransformation invokePandoc(Path document, String outputExt, Object... additionalArgs) throws IOException {
		Path htmlAbsolute = document.toAbsolutePath();
		String fileName = htmlAbsolute.getFileName().toString();
		fileName = fileName.substring(0, fileName.lastIndexOf('.')+1);
		Path outputLocation = htmlAbsolute.getParent().resolve(fileName + outputExt);
		Object[] basicArgs = {"-o", outputLocation, htmlAbsolute}, allArgs;
		if (additionalArgs != null && additionalArgs.length > 0) {
			allArgs = new Object[basicArgs.length + additionalArgs.length];
			for (int i = 0; i < allArgs.length; i++) {
				allArgs[i] = i < basicArgs.length ? basicArgs[i] : additionalArgs[i - basicArgs.length];
			}
		}
		else {
			allArgs = basicArgs;
		}
		
		return new ExternalContentTransformation(outputLocation, "pandoc", allArgs);
	}
	
	/**
	 * Converts HTML to PDF using wkhtmltopdf.
	 * 
	 * @param html The raw HTML text.
	 * @return The output PDF file.
	 * @throws IOException If invoking wkhtmltopdf fails.
	 */
	public static Path wkhtmltopdf(String html) throws IOException {
		return wkhtmltopdf(ExternalContentTransformation.createTempFile("html", html.getBytes()));
	}
	
	/**
	 * Converts HTML to PDF using wkhtmltopdf.
	 * 
	 * @param html The HTML file.
	 * @return The output PDF file.
	 * @throws IOException If invoking wkhtmltopdf fails.
	 */
	public static Path wkhtmltopdf(Path document) throws IOException {
		Path htmlAbsolute = document.toAbsolutePath();
		String fileName = htmlAbsolute.getFileName().toString();
		fileName = fileName.substring(0, fileName.lastIndexOf('.')+1);
		Path pdfLocation = htmlAbsolute.getParent().resolve(fileName + "pdf");
		ExternalContentTransformation ect = new ExternalContentTransformation(
			pdfLocation, "wkhtmltopdf", "--enable-local-file-access", document, pdfLocation
		);
		ect.call();
		return pdfLocation;
	}
}
