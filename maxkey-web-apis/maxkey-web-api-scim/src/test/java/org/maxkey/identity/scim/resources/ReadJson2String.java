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

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ReadJson2String {

    public static String read(String jsonFileName) {
        try {
            List<String> list = FileUtils.readLines(new File(ReadJson2String.class.getResource(jsonFileName).getFile()),"UTF-8");
            StringBuilder sb = new StringBuilder();
            for (String ss : list) {
                sb.append(ss);
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
