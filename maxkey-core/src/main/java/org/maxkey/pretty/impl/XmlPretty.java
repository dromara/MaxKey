package org.maxkey.pretty.impl;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.maxkey.pretty.Pretty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlPretty implements Pretty{

	public XmlPretty() {

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
			return org.opensaml.xml.util.XMLHelper.prettyPrintXML(node);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
