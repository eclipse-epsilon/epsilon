/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.internal.eunit.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * PrintStream which performs every write twice: once to the original output
 * stream, and another to an internal byte array, so the full output can be
 * later collected and processed transparently.
 * 
 * Since {@link ByteArrayOutputStream#close()} and
 * {@link ByteArrayOutputStream#flush()} do not do anything, they are not called
 * from this class.
 * 
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class ByteBufferTeePrintStream extends PrintStream {

	private final ByteArrayOutputStream bosStream = new ByteArrayOutputStream();

	public ByteBufferTeePrintStream(OutputStream out) {
		super(out);
	}

	public ByteBufferTeePrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	public ByteBufferTeePrintStream(File file) throws FileNotFoundException {
		super(file);
	}

	public ByteBufferTeePrintStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}

	public ByteBufferTeePrintStream(String fileName, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
	}

	public ByteBufferTeePrintStream(File file, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
	}

	public ByteBufferTeePrintStream(OutputStream out, boolean autoFlush,
			String encoding) throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
	}

	public void write(int b) {
		super.write(b);
		bosStream.write(b);
	}

	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
		bosStream.write(buf, off, len);
	}

	public void write(byte[] buf) throws IOException {
		super.write(buf);
		bosStream.write(buf);
	}

	/**
	 * Returns the bytes collected by this output stream.
	 */
	public byte[] getBytes() {
		return bosStream.toByteArray();
	}
}
