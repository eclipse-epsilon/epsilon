/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.epsilon.egl.doc.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GraphVizUtil {

	private final String pathToDot;
	
	public GraphVizUtil(String pathToDot) {
		this.pathToDot = pathToDot; 
	}
	
	public boolean generatePng(String inputFile, String outputFile) {
		try {
			final Process dot = Runtime.getRuntime().exec(
					new String[]{pathToDot,
			                     "-Tpng", 
			                     inputFile, 
			                     "-o", 
			                     outputFile});
			
			dot.waitFor();
			
			String errors = "";
			final BufferedReader br = new BufferedReader(
					new InputStreamReader(dot.getErrorStream()));
			try {
				String line;
				while ((line = br.readLine()) != null) {
					errors += line + System.getProperty("line.separator");
				}
			} finally {
				br.close();
			}
			
			if (dot.exitValue() != 0) {
				System.err.println("Error encountered whilst generating png.");
				System.err.println("\tpathToDot: " +pathToDot);
				System.err.println("\tinputFile: " +inputFile);
				System.err.println("\toutputFile: "+outputFile);
				System.err.println("Dot returned with exit code "+dot.exitValue());
				System.err.println("\t"+errors);
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			System.err.println("Error encountered whilst generating png.");
			System.err.println("\tpathToDot: " +pathToDot);
			System.err.println("\tinputFile: " +inputFile);
			System.err.println("\toutputFile: "+outputFile);
			System.err.println("Caused by:");
			e.printStackTrace();
			
			return false;
		}
	}
}
