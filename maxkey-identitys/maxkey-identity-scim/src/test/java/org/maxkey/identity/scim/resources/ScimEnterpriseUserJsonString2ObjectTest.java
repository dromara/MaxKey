package org.maxkey.identity.scim.resources;

import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.util.JsonUtils;

public class ScimEnterpriseUserJsonString2ObjectTest {

    public static void main(String[] args) {
       
        String userJsonString = ReadJson2String.read("ScimEnterpriseUserJsonString.json");
        EnterpriseUser u  = JsonUtils.json2Object(userJsonString, EnterpriseUser.class);
        
        System.out.println(
                (new JsonPretty()).format(JsonUtils.object2Json(u)));
        System.out.println(u.getEnterprise().getCostCenter());
    }

}
