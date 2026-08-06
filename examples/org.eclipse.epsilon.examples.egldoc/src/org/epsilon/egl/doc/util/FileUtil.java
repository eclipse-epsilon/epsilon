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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;


public abstract class FileUtil {

	private FileUtil() {}
	
	public static void copyFile(URI source, File target) throws IOException {
		if (target.exists() && target.isDirectory())
			throw new IllegalArgumentException(target + " is not a file");
		
		InputStream  in  = null;
		OutputStream out = null;
		
		try {
			target.getParentFile().mkdirs();
			target.createNewFile();
			
			in  = new BufferedInputStream(source.toURL().openStream());
			out = new BufferedOutputStream(new FileOutputStream(target));
			
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		
		} finally {
			if (in!=null)  in.close();
			if (out!=null) out.close();
		}
	}
	
	public static void copyFile(File source, File target) throws IOException {
		if (!source.exists() || !source.isFile())
			throw new IllegalArgumentException(source + " is not a file");
		
		if (target.exists() && target.isDirectory())
			throw new IllegalArgumentException(target + " is not a file");
		
		FileChannel sourceChannel = null;
		FileChannel targetChannel = null;
		
		try {
			target.getParentFile().mkdirs();
			target.createNewFile();
			
			sourceChannel = new FileInputStream(source).getChannel();
			targetChannel = new FileOutputStream(target).getChannel();
			
			sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		
		} finally {
			if (sourceChannel != null) sourceChannel.close();
			if (targetChannel != null) targetChannel.close();
		}
	}
	
	public static void copyDir(File source, File target) throws IOException {
		if (!source.exists() || !source.isDirectory())
			throw new IllegalArgumentException(source + " is not a directory");
		
		if (target.exists() && !target.isDirectory())
			throw new IllegalArgumentException(target + " is not a directory");
		
		target.mkdirs();
		
		for (File child : source.listFiles()) {
			if (child.isDirectory()) {
				copyDir(child, determineTargetFileName(source, child, target));
			} else if (child.isFile()) {
				copyFile(child, determineTargetFileName(source, child, target));
			}
		}
	}

	private static File determineTargetFileName(File source, File child, File target) {
		return new File(target.getAbsolutePath(),
		                child.getAbsolutePath().substring(source.getAbsolutePath().length()));
	}

	public static void moveFileToDir(File source, File target) throws IOException {
		copyFile(source, new File(target, source.getName()));
		source.delete();
	}
}
