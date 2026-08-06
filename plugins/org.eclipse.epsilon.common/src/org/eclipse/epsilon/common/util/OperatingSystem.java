/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - CPU info
******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public enum OperatingSystem {
	
	WINDOWS,
	UNIX,
	LINUX,
	SOLARIS,
	MAC,
	OTHER;
	
	public static OperatingSystem getOSFamily() {
		switch (System.getProperty("os.name").substring(0, 3).toLowerCase()) {
			case "win": return WINDOWS;
			case "lin": return LINUX;
			case "uni": return UNIX;
			case "mac": case "ios": case "dar": return MAC;
			case "sun": case "sol": return SOLARIS;
			default: return OTHER;
		}
	}
	
	public static boolean isARM64() {
		return "aarch64".equals(System.getProperty("os.arch"));
	}
	
	/**
	 * 
	 * @since 1.6
	 * @return
	 */
	public static String getJavaVersion() {
		return System.getProperty("java.vm.name")+" "+System.getProperty("java.vm.version");
	}
	
	/**
	 * 
	 * @since 1.6
	 * @return
	 */
	public static String getOsNameAndVersion() {
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Windows")) return osName;
		else return osName+" "+System.getProperty("os.version");
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public static boolean isMac() {
		return getOSFamily() == MAC;
	}
	
    public static boolean isWindows() {
        return getOSFamily() == WINDOWS;
    }
    
    public static boolean isUnix() {
        return !isWindows();
    }

	/**
	 * Executes the given command(s) natively.
	 * @param args The commands.
	 * @return The output.
	 * @throws IOException
	 * @since 1.6
	 */
	public static String executeCommand(String... args) throws IOException {
		Process process = new ProcessBuilder(args).redirectErrorStream(true).start();
		StringBuilder processOutput = new StringBuilder();

        try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        	for (String line;
            	(line = processOutputReader.readLine()) != null;
            	processOutput.append(line + System.lineSeparator())
            );
            try {
				process.waitFor();
			}
			catch (InterruptedException ex) {
				throw new IOException(ex);
			}
        }

        return processOutput.toString().trim();
	}
}

