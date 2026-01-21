package org.eclipse.epsilon.picto.trace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.picto.transformers.elements.AppendingElementTransformer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Rename to TraceToolbarAppender? The same with the js and css files
public class PictoToolbarAppender extends AppendingElementTransformer {
	
	@Override
	public String getXPath() {
		return "//body[1]";
	}
	
	@Override
	protected void append(Element root, Document document) throws DOMException {
		
		try {
			Path path = Files.createTempDirectory("picto-toolbar");
			Files.copy(PictoToolbarAppender.class.getResourceAsStream("picto-toolbar.css"), path.resolve("picto-toolbar.css"));
			Files.copy(PictoToolbarAppender.class.getResourceAsStream("picto-toolbar.js"), path.resolve("picto-toolbar.js"));
			Files.copy(PictoToolbarAppender.class.getResourceAsStream("edit.png"), path.resolve("edit.png"));
			Files.copy(PictoToolbarAppender.class.getResourceAsStream("delete.png"), path.resolve("delete.png"));
			Files.copy(PictoToolbarAppender.class.getResourceAsStream("locate.png"), path.resolve("locate.png"));
			
			Element include = document.createElement("script");
			include.setAttribute("src", path.toString() + "/picto-toolbar.js");
			root.appendChild(include);
			
			Element css = document.createElement("link");
			css.setAttribute("rel", "stylesheet");
			css.setAttribute("href", path.toString() + "/picto-toolbar.css");
			root.appendChild(css);
			
		} catch (IOException e) {
			LogUtil.log(e);
		}
	}
	
}
