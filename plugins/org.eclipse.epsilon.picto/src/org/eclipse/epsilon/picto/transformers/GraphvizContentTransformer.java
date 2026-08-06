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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;

public class GraphvizContentTransformer implements ViewContentTransformer {
	
	@Override
	public boolean canTransform(ViewContent content) {
		return content.getFormat().toLowerCase().startsWith("graphviz-");
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		String[] parts = content.getFormat().split("-");
		String program = parts[1].trim();
		ExternalContentTransformation ect = graphviz(program, content.getText(), "svg");
		ect.run();
		String text = new String(ect.getResult());
		String format = ect.getOutputFile().toFile().exists() ? "svg" : "exception";
		return new ViewContent(format, text, content);
	}
	
	@Override
	public String getLabel(ViewContent content) {
		return "Graphviz";
	}
	
	public static Path graphvizToSvg(String program, String graphviz) throws IOException {
		return graphvizToImage(program, graphviz, "svg");
	}
	
	public static String graphvizToRawSvg(String program, String graphviz) throws IOException {
		return new String(graphviz(program, graphviz, "svg").call());
	}
	
	public static Path graphvizToImage(String program, String graphviz, String imageType) throws IOException {
		ExternalContentTransformation ect = graphviz(program, graphviz, imageType);
		ect.call();
		return ect.getOutputFile();
	}
	
	protected static ExternalContentTransformation graphviz(String program, String graphviz, String imageType) throws IOException {
		Path
			temp = ExternalContentTransformation.createTempFile(program, graphviz.getBytes()),
			image = ExternalContentTransformation.createTempFile(imageType, null);
		
		if (OperatingSystem.isMac()) {
			
			// For Mac users on Apple Silicon, Homebrew installs to /opt/homebrew/bin
			String[] installationFolders = new String[] { "/usr/local/bin/", "/opt/homebrew/bin/" };
			
			for (String installationFolder : installationFolders) {
				File programFile = new File(installationFolder + program);
				if (programFile.exists() && programFile.canRead() && programFile.canExecute()) {
					program = programFile.getAbsolutePath();
					break;
				}
			}
		}
		else if (OperatingSystem.isUnix()) {
			program = "/usr/bin/" + program;
		}
		else if (OperatingSystem.isWindows()) {
			program += ".exe";
		}
		
		return new ExternalContentTransformation(
			image, program,
			image, "-T" + imageType, temp, "-o", image
		);
	}
}
