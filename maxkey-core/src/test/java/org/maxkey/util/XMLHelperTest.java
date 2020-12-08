package org.maxkey.util;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.junit.Test;
import org.maxkey.pretty.PrettyFactory;
import org.maxkey.pretty.impl.XMLHelper;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

import net.shibboleth.utilities.java.support.collection.LazyMap;

public class XMLHelperTest {

	@Test
	public void testSqlFormat()  {
		String sqlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><data><name>maxkey</name><age v=\"20\"/></data></xml>";
		System.out.println(XMLHelper.prettyPrintXML(sqlString));
		System.out.println(XMLHelper.transformer(sqlString));
	}
    
}
