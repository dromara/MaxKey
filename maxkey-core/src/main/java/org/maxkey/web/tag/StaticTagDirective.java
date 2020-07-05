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
 

package org.maxkey.web.tag;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
    *  静态变量读取
 *   <@static/>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("static")
public class StaticTagDirective implements TemplateDirectiveModel {
	

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		 
	        // 获取字符串变量
	        String[] c = params.get("name").toString().trim().split("@");
	 
	        StringBuffer sb = new StringBuffer();
	        try {
	            if(null == c || c.length < 2) {
	                throw new TemplateException("至少应该包含一个@符。", env);
	            }
	 
	            Class<?> clazz = null;
	            for(int i=0;i<c.length;i++) {
	                sb.append(c[i]).append("@");
	                if(i == 0) {
	                    clazz = Class.forName(c[i]);
	                } else if(i != c.length - 1) {
	                    Class<?>[] clazzs = clazz.getDeclaredClasses();
	                    boolean flag = false;
	                    for(Class<?> clz : clazzs) {
	                        if(clz.getSimpleName().equals(c[i])) {
	                            clazz = clz;
	                            flag = true;
	                            break;
	                        }
	                    }
	                    if(!flag) {
	                        throw new TemplateException("内部类 " + sb.substring(0, sb.length() - 1) + " 未找到。", env);
	                    }
	                } else {
	                    Field sp = clazz.getDeclaredField(c[i]);
	                    env.getOut().write(sp.get(clazz).toString());
	                }
	            }
	        } catch (ClassNotFoundException e) {
	            throw new TemplateException("类 " + sb.substring(0, sb.length() - 1) + " 未找到。", e.getCause(), env);
	        } catch (NoSuchFieldException e) {
	            throw new TemplateException("属性 " + sb.substring(0, sb.length() - 1) + " 未找到。", e.getCause(), env);
	        } catch (IllegalAccessException e) {
	            throw new TemplateException("没权限访问 " + sb.substring(0, sb.length() - 1) + " 属性。", e.getCause(), env);
	        }
	}

}
