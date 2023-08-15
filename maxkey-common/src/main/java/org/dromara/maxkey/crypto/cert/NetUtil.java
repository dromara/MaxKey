/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

/*
 * NetUtil.java
 */

package org.dromara.maxkey.crypto.cert;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Networking utilities.
 * 
 * @author Ville Skyttä
 */
public final class NetUtil
{
	/** Logger */
	private static final Logger _logger = LoggerFactory.getLogger(NetUtil.class);

	//  make this configurable
	private static final int CONNECT_TIMEOUT = 10000;

	//  make this configurable
	private static final int READ_TIMEOUT = 20000;

	/**
	 * Private to prevent construction.
	 */
	private NetUtil()
	{
		// Nothing to do
	}

	/**
	 * Open an input stream to a GET(-like) operation on an URL.
	 * 
	 * @param url The URL
	 * @return Input stream to the URL connection
	 * @throws IOException If an I/O error occurs
	 */
	public static InputStream openGetStream(URL url)
	    throws IOException
	{
		URLConnection conn = url.openConnection();

		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);

		//  User-Agent?

		return conn.getInputStream();
	}

	/**
	 * Open an input stream to a POST(-like) operation on an URL.
	 * 
	 * @param url The URL
	 * @param content Content to POST
	 * @param contentType Content type
	 * @return Input stream to the URL connection
	 * @throws IOException If an I/O error occurs
	 */
	public static InputStream openPostStream(URL url, byte[] content, String contentType)
	    throws IOException
	{
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);

		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);

		//  User-Agent?

		if (contentType != null)
		{
			conn.setRequestProperty("Content-Type", contentType);
		}

		conn.setRequestProperty("Content-Length", String.valueOf(content.length));

		OutputStream out = conn.getOutputStream();
		try
		{
			out.write(content);
		}
		finally
		{
			out.close();
		}

		return conn.getInputStream();
	}

	/**
	 * Download the given URL to a temporary local file. The temporary file is marked for deletion at exit.
	 * 
	 * @param url
	 * @return URL pointing to the temporary file, <code>url</code> itself if it's a file: one.
	 * @throws IOException
	 */
	public static URL download(URL url)
	    throws IOException
	{
		if ("file".equals(url.getProtocol()))
		{
			return url;
		}

		InputStream in = openGetStream(url);
		File tempFile = null;
		OutputStream out = null;

		try
		{
			tempFile = Files.createTempFile("portecle",null).toFile();
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
			byte[] buf = new byte[2048];
			int n;
			while ((n = in.read(buf)) != -1)
			{
				out.write(buf, 0, n);
			}
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
			}
			finally
			{
				if (tempFile != null && !tempFile.delete())
				{
					_logger.info("Could not delete temporary file " + tempFile);
				}
			}
			throw e;
		}
		finally
		{
			in.close();
		}

		tempFile.deleteOnExit();

		return tempFile.toURI().toURL();
	}

	/**
	 * Creates a URL pointing to a URL, URI or a File object.
	 * 
	 * @param obj Object to create a URI to
	 * @return URL
	 * @throws ClassCastException if obj is not a supported object
	 * @throws MalformedURLException if converting obj to a URL fails
	 */
	public static URL toURL(Object obj)
	    throws MalformedURLException
	{
		if (obj instanceof File)
		{
			return ((File) obj).toURI().toURL();
		}
		else if (obj instanceof URI)
		{
			return ((URI) obj).toURL();
		}
		else
		{
			return (URL) obj;
		}
	}
}
