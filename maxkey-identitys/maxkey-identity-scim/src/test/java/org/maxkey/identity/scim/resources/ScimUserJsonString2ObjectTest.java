package org.maxkey.identity.scim.resources;

import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.util.JsonUtils;

public class ScimUserJsonString2ObjectTest {

    public static void main(String[] args) {

        String userJsonString = ReadJson2String.read("ScimUserJsonString.json");
        User u  = JsonUtils.json2Object(userJsonString, User.class);
        System.out.println(
                (new JsonPretty()).format(JsonUtils.object2Json(u)));
    }

}
