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
