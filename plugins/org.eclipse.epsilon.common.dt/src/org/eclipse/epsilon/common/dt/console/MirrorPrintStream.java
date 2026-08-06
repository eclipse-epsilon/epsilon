/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
**********************************************************************/
package org.eclipse.epsilon.common.dt.console;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * MirrorPrintStream is a PrintStream that mirrors all print/write operations into a file.  
 * 
 * Only the write(), check() and close() methods need overriding given that any of the print() and
 * println() methods must go through these.
 * 
 * @author Horacio Hoyos Rodriguez
 * @since 1.6
 */
public class MirrorPrintStream extends PrintStream {
	
	/** The file stream. */
	protected PrintStream fileStream;

	/** The file name. */
	protected String fileName;
	
	/** The trouble flag. */
	private boolean trouble = false;

	
	public MirrorPrintStream(OutputStream os, boolean flush) {
		super(os, flush);
	}
	
	/**
	 * Instantiates a MirrorPrintStream with autoflush.
	 *
	 * @param os the os
	 */
	public MirrorPrintStream(OutputStream os) {
		super(os, true);
	}

	/** Return true if either stream has an error. */
	@Override
	public boolean checkError() {
		boolean error = trouble || super.checkError();
		return fileStream == null ? error : error || fileStream.checkError();
	}

	@Override
	public void write(int x) {
		if (fileStream != null) fileStream.write(x);
		super.write(x);
	}

	@Override
	public void write(byte[] x, int o, int l) {
		if (fileStream != null) fileStream.write(x, o, l);
		super.write(x, o, l);
	}
	

	@Override
	public void close() {
		disableMirror();
		super.close();
	}

	@Override
	public void flush() {
		if (fileStream != null) fileStream.flush();
		super.flush();
	}
	
	/**
	 * Enable mirroring to the given file. If there is an error the MirrorPrintStream will be
	 * flagged as having an error.
	 * By default bytes will be written to the beginning of the file
	 * @param fileName				The name of the file to direct output.
	 * @see #mirrorToFile(String, boolean)
	 */
	public void mirrorToFile(String fileName) {
		mirrorToFile(fileName, false);
	}
	
	/**
	 * Enable mirroring to the given file. If there is an error the MirrorPrintStream will be
	 * flagged as having an error.
	 *
	 * @param fileName 			The name of the file to direct output.
	 * @param append 			if true, then bytes will be written to the end of the file rather
	 * 							than the beginning
	 */
	public void mirrorToFile(String fileName, boolean append) {
		this.fileName = fileName;
		try {
			this.fileStream = new PrintStream(new FileOutputStream(fileName, append), true);
			clearError();
		} catch (FileNotFoundException e) {
			this.trouble = true;
		}
	}
	
	/**
	 * Disable the mirror operation.
	 */
	public void disableMirror() {
		this.fileName = "";
		if (fileStream != null) {
			fileStream.close();
			fileStream = null;
		}
	}
	
	/**
     * Sets the error state of the stream to <code>true</code>.
     *
     * <p> This method will cause subsequent invocations of {@link
     * #checkError()} to return <tt>true</tt> until {@link
     * #clearError()} is invoked.
     */
    @Override
	protected void setError() {
        trouble = true;
    }

    /**
     * Clears the internal error state of this stream.
     *
     * <p> This method will cause subsequent invocations of {@link
     * #checkError()} to return <tt>false</tt> until another write
     * operation fails and invokes {@link #setError()}.
     */
    @Override
	protected void clearError() {
        trouble = false;
    }
}