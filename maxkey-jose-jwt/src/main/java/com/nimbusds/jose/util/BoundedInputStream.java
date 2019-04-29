/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.nimbusds.jose.util;


import java.io.IOException;
import java.io.InputStream;


/**
 * Size-bounded input stream. Adapted from Apache Commons IO. Throws an
 * {@link IOException} if the input size limit is exceeded.
 *
 * @version 2016-11-28
 */
public class BoundedInputStream extends InputStream {
	
	
	/**
	 * The wrapped input stream.
	 */
	private final InputStream in;
	
	
	/**
	 * The limit, -1 if none.
	 */
	private final long max;
	
	
	/**
	 * The current input stream position.
	 */
	private long pos;
	
	
	/**
	 * Marks the input stream.
	 */
	private long mark;
	
	
	/**
	 * If {@link #close()} is to be propagated to the underlying input
	 * stream.
	 */
	private boolean propagateClose;
	
	
	/**
	 * Creates a new bounded input stream.
	 *
	 * @param in   The input stream to wrap.
	 * @param size The maximum number of bytes to return, -1 if no limit.
	 */
	public BoundedInputStream(final InputStream in, final long size) {
		this.pos = 0L;
		this.mark = -1L;
		this.propagateClose = true;
		this.max = size;
		this.in = in;
	}
	
	
	/**
	 * Creates a new unbounded input stream.
	 *
	 * @param in The input stream to wrap.
	 */
	public BoundedInputStream(final InputStream in) {
		this(in, -1L);
	}
	
	
	/**
	 * Returns the maximum number of bytes to return.
	 *
	 * @return The maximum number of bytes to return, -1 if no limit.
	 */
	public long getLimitBytes() {
		return max;
	}
	
	
	@Override
	public int read() throws IOException {
		if (this.max >= 0L && this.pos >= this.max) {
			throw new IOException("Exceeded configured input limit of " + this.max + " bytes");
		} else {
			int result = this.in.read();
			++this.pos;
			return result; // data or -1 on EOF
		}
	}
	
	
	@Override
	public int read(byte[] b) throws IOException {
		return this.read(b, 0, b.length);
	}
	
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if(this.max >= 0L && this.pos >= this.max) {
			throw new IOException("Exceeded configured input limit of " + this.max + " bytes");
		} else {
			int bytesRead = this.in.read(b, off, len);
			
			if(bytesRead == -1) {
				return -1;
			} else {
				this.pos += (long)bytesRead;
				
				if (this.max >= 0L && this.pos >= this.max)
					throw new IOException("Exceeded configured input limit of " + this.max + " bytes");
				
				return bytesRead;
			}
		}
	}
	
	
	@Override
	public long skip(long n) throws IOException {
		long toSkip = this.max >= 0L?Math.min(n, this.max - this.pos):n;
		long skippedBytes = this.in.skip(toSkip);
		this.pos += skippedBytes;
		return skippedBytes;
	}
	
	
	@Override
	public int available() throws IOException {
		return this.max >= 0L && this.pos >= this.max?0:this.in.available();
	}
	
	
	@Override
	public String toString() {
		return this.in.toString();
	}
	
	
	@Override
	public void close() throws IOException {
		if(this.propagateClose) {
			this.in.close();
		}
	}
	
	
	@Override
	public synchronized void reset() throws IOException {
		this.in.reset();
		this.pos = this.mark;
	}
	
	
	@Override
	public synchronized void mark(int readlimit) {
		this.in.mark(readlimit);
		this.mark = this.pos;
	}
	
	
	@Override
	public boolean markSupported() {
		return this.in.markSupported();
	}
	
	
	/**
	 * Indicates whether the {@link #close()} method should propagate to
	 * the underling InputStream.
	 *
	 * @return {@code true} if calling {@link #close()} propagates to the
	 *         {@link #close()} method of the underlying stream or
	 *         {@code false} if it does not.
	 */
	public boolean isPropagateClose() {
		return this.propagateClose;
	}
	
	
	/**
	 * Set whether the {@link #close()} method should propagate to the
	 * underling InputStream.
	 *
	 * @param propagateClose {@code true} if calling {@link #close()}
	 *                       propagates to the {@link #close()} method of
	 *                       the underlying stream or {@code false} if it
	 *                       does not.
	 */
	public void setPropagateClose(boolean propagateClose) {
		this.propagateClose = propagateClose;
	}
}
