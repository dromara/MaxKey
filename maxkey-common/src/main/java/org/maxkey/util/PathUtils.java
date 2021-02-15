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
 

package org.maxkey.util;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtils {
    private static final Logger _logger = LoggerFactory.getLogger(PathUtils.class);
    private static PathUtils instance = null;
    private String classPath;
    private String appPath;
    public static String WEB_INFO = "/WEB-INF/";
    private static final String PATH_FILE_NAME = "PathUtils.properties";

    /**
     * getInstance .
     * @return
     */
    public static synchronized PathUtils getInstance() {
        if (instance == null) {
            instance = new PathUtils();
            PathUtils._logger.debug("getInstance()" + " new PathUtils instance");
        }
        return instance;
    }

    /**
     * PathUtils.
     */
    public PathUtils() {
        try {
            classPath = java.net.URLDecoder.decode(
                    PathUtils.class.getResource(PATH_FILE_NAME).getFile(),
                    "UTF-8"
            );
            _logger.debug("PathUtils() PathUtils " + classPath);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String fileProtocol = PathUtils.class.getResource(PATH_FILE_NAME).getProtocol();
        _logger.info("Properties Protocol:" 
                + PathUtils.class.getResource("PathUtils.properties").getProtocol());

        if (fileProtocol.equalsIgnoreCase("file") && classPath.indexOf("file:") == 0) {
            classPath = classPath.substring(5, classPath.length());
        } else if (fileProtocol.equalsIgnoreCase("jar") && classPath.indexOf("file:") == 0) {
            // file:/Server/webapps/app
            classPath = classPath.substring(5, classPath.length());
        } else if (fileProtocol.equalsIgnoreCase("wsjar") && classPath.indexOf("file:") == 0) {
            classPath = classPath.substring(5, classPath.length());
        } else if (classPath.equalsIgnoreCase("file:")) {
            classPath = classPath.substring(5, classPath.length());
        }

        _logger.info("PathUtils  Class Path  : " + classPath);
        classPath = classPath.substring(0, 
                classPath.indexOf("/org/maxkey/util/" + PATH_FILE_NAME));
        if (classPath.indexOf(WEB_INFO) == -1) {
            appPath = classPath.substring(0, classPath.lastIndexOf("/"));
        } else {
            appPath = classPath.substring(0, classPath.lastIndexOf(WEB_INFO));
        }

        System.setProperty("APP_PATH", appPath);
        System.setProperty("CLASSES_PATH", classPath);

        _logger.info("PathUtils  App   Path  : " + appPath);
        _logger.info("PathUtils  Class Path  : " + classPath);
    }

    public String getAppPath() {
        return appPath + "/";
    }

    public String getClassPath() {
        return classPath + "/";
    }

    public String getWebInf() {
        return (classPath.lastIndexOf(WEB_INFO) > -1) ? (appPath + WEB_INFO) : "";
    }
}
