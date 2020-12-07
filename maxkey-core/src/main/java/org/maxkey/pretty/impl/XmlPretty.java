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
 

package org.maxkey.pretty.impl;

import org.maxkey.pretty.Pretty;
import org.w3c.dom.Node;

public class XmlPretty implements Pretty{

	public XmlPretty() {

	}
	
	@Override
	public  String format(String xmlString){
		try{
			return XMLHelper.prettyPrintXML(xmlString);
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

}
