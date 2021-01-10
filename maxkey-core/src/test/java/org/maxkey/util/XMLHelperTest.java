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
