package org.eclipse.epsilon.picto.trace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.picto.transformers.elements.AppendingElementTransformer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TraceToolbarAppender extends AppendingElementTransformer {
	
	protected Path path;
		
	@Override
	public String getXPath() {
		return "//body[1]";
	}
	
	@Override
	protected void append(Element root, Document document) throws DOMException {
		try {
			Path path = Files.createTempDirectory("picto-toolbar");
			Files.copy(TraceToolbarAppender.class.getResourceAsStream("picto-trace-toolbar.css"), path.resolve("picto-trace-toolbar.css"));
			Files.copy(TraceToolbarAppender.class.getResourceAsStream("picto-trace-toolbar.js"), path.resolve("picto-trace-toolbar.js"));
			Files.copy(TraceToolbarAppender.class.getResourceAsStream("edit.png"), path.resolve("edit.png"));
			Files.copy(TraceToolbarAppender.class.getResourceAsStream("delete.png"), path.resolve("delete.png"));
			Files.copy(TraceToolbarAppender.class.getResourceAsStream("show.png"), path.resolve("show.png"));
			
			Element include = document.createElement("script");
			include.setAttribute("src", path.toString() + "/picto-trace-toolbar.js");
			root.appendChild(include);
			
			Element css = document.createElement("link");
			css.setAttribute("rel", "stylesheet");
			css.setAttribute("href", path.toString() + "/picto-trace-toolbar.css");
			root.appendChild(css);
		}
		catch (IOException ex) {
			LogUtil.log(ex);
		}
	}
	
}
