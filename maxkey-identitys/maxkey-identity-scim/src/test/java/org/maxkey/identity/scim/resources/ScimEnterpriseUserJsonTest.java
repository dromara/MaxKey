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
 

package org.maxkey.identity.scim.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.util.JsonUtils;

public class ScimEnterpriseUserJsonTest {

    public static void main(String[] args) {
       
        // TODO Auto-generated method stub
        EnterpriseUser u = new EnterpriseUser();
        u.setUserName("UserName");
        u.setExternalId("UserName");
        u.setId("1111111111111");
        
        Meta meta = new Meta();
        meta.setVersion("W\\/\"f250dd84f0671c3\"");
        meta.setCreated(new Date());
        meta.setLocation("https://example.com/v2/Users/2819c223...");
        meta.setResourceType("User");
        meta.setLastModified(new Date());
        u.setMeta(meta);
        
        UserName un=new UserName();
        un.setFamilyName("Jensen");
        un.setFormatted("Ms. Barbara J Jensen, III");
        un.setGivenName("Barbara");
        un.setHonorificPrefix("Ms.");
        un.setHonorificSuffix("III");
        un.setMiddleName("Jane");
        u.setName(un);
        
        List<UserPhoneNumber> UserPhoneNumberList = new ArrayList<UserPhoneNumber>();
        UserPhoneNumber pn =new UserPhoneNumber();
        pn.setValue("555-555-8377");
        pn.setType(UserPhoneNumber.UserPhoneNumberType.WORK);
        
        UserPhoneNumber pnh =new UserPhoneNumber();
        pnh.setValue("555-555-8377");
        pnh.setType(UserPhoneNumber.UserPhoneNumberType.HOME);
        UserPhoneNumberList.add(pnh);
        
        UserPhoneNumberList.add(pn);
        
        u.setPhoneNumbers(UserPhoneNumberList);
        
        List<UserEmail> ueList = new ArrayList<UserEmail>();
        UserEmail ue =new UserEmail();
        ue.setValue("bjensen@example.com");
        ue.setType(UserEmail.UserEmailType.WORK);
        ueList.add(ue);
        u.setEmails(ueList);
        
        Enterprise ent =new Enterprise();
        ent.setCostCenter("1111");
        ent.setDepartment("de");
        ent.setEmployeeNumber("k200908");
        u.setEnterprise(ent);
        
        System.out.println(
                (new JsonPretty()).format(JsonUtils.object2Json(u)));
    }

}
