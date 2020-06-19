package org.maxkey.identity.scim.resources;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.util.JsonUtils;

public class ScimGroupJsonString2ObjectTest {
    public static void main(String[] args) {
        String userJsonString = ReadJson2String.read("ScimGroupJsonString.json");
        Group g  = JsonUtils.json2Object(userJsonString, Group.class);

        
        System.out.println(
                (new JsonPretty()).format(JsonUtils.object2Json(g)));
    }
}
