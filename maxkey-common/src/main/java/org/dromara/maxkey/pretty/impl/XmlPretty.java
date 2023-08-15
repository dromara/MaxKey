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
 

package org.dromara.maxkey.pretty.impl;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dromara.maxkey.pretty.Pretty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlPretty implements Pretty{

	static XmlPretty instance ;
	
	public XmlPretty() {

	}
	
	public static XmlPretty getInstance() {
		if (null == instance) {
			synchronized (JsonPretty.class) {
				if (instance == null) {
					instance = new XmlPretty();
				}
			}
		}
		return instance;
	}
	
	@Override
	public  String format(String xmlString){
		try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
			return format(document);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public  String format(Node node){
		try{
			return XMLHelper.prettyPrintXML(node);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String formatln(String source) {
		return LINE_BREAK + format(source);
	}
}
