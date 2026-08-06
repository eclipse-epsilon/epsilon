/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class MathjaxContentTransformer implements ViewContentTransformer {

	@Override
	public boolean canTransform(ViewContent content) {
		return "mathjax".equalsIgnoreCase(content.getFormat());
	}

	@Override
	public String getLabel(ViewContent content) {
		return "MathJax";
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		String html = null, tex = content.getText();
		try {
			html = texToRawSvg(tex);
		}
		catch (IOException iox) {
			html = "<div>" +
			"<script src=\"https://polyfill.io/v3/polyfill.min.js?features=es6\"></script>\n" +
			"<script>\n" + 
			"MathJax = {\n" + 
			"  tex: {\n" + 
			"    inlineMath: [['$', '$'], ['\\\\(', '\\\\)']]\n" + 
			"  },\n" + 
			"  svg: {\n" + 
			"    fontCache: 'global'\n" + 
			"  }\n" + 
			"};\n</script>\n"+
			"<script src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js\"></script>\n" +
			"<p>"+tex+"</p></div>";
		}
		
		return new ViewContent("svg", html, content);
	}
	
	
	/**
	 * Converts TeX math to SVG as String.
	 * 
	 * @param tex The tex
	 * @return The SVG as string
	 * @throws IOException If converison fails.
	 * @see #tex2svg(String)
	 */
	public static String texToRawSvg(String tex) throws IOException {
		return new String(Files.readAllBytes(tex2svg(tex)));
	}
	
	/**
	 * Converts the TeX math to SVG file.
	 * 
	 * @param tex The mathematics.
	 * @return The path of the generated SVG.
	 * @throws IOException If the conversion goes wrong.
	 */
	public static Path tex2svg(String tex) throws IOException {
		Path output = ExternalContentTransformation.createTempFile("svg");
		ExternalContentTransformation ect = convertTex(tex, output);
		ect.call();
		return output;
	}
	
	protected static ExternalContentTransformation convertTex(String tex, Path output) {
		String program = "tex2" + FileUtil.getExtension(output.getFileName().toString());
		program = ExternalContentTransformation.resolveNodeProgram(program);
		return new ExternalContentTransformation(output, program, tex.trim(), ">", output);
	}
}
