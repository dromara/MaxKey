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
 

package org.maxkey.json.util;

import org.maxkey.domain.Groups;
import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.util.JsonUtils;

public class JsonUtilsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Groups g=new Groups("test");
		g.setName("jjjj");
		
		JsonPretty jp=new JsonPretty();
		String json=jp.format(g);
		System.out.println(json);
		Groups newg=JsonUtils.gson2Object(json, Groups.class);
		System.out.println(newg.getName());
	}

}
