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
