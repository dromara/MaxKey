/*
 * Copyright [2026] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn.web.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;


@Aspect
@Component
public class InjectInstAspect {
	private static final  Logger _logger = LoggerFactory.getLogger(InjectInstAspect.class);
    
    static final String INSTID_FIELD	= "instId";
    
    @Pointcut("@annotation(org.dromara.maxkey.authn.annotation.InjectInst)")
    public void injectInstMethods() {
    }
    
    /**
     * 
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("injectInstMethods()")
    public Object injectInst(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 获取实际传入的方法参数
            Object[] args = joinPoint.getArgs(); 
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Object arg = args[i]; // 获取对应的参数值
                Class<?> parameterType = parameter.getType();
                // 检查参数类型是否是基本类型、集合类型、映射类型或数组类型,如果不是，则尝试注入
                if (ClassUtils.isPrimitiveOrWrapper(parameterType)
                        || Collection.class.isAssignableFrom(parameterType) 
                        || Map.class.isAssignableFrom(parameterType) 
                        || parameterType.isArray()) {
                    continue; // 跳过基本类型、集合类型、映射类型和数组类型
                }
                try {
                    if (arg != null) { // 确保参数不是null
                        // 获取参数类型中的INSTID_FIELD字段
                        Field field = parameterType.getDeclaredField(INSTID_FIELD); 
                        if(field != null) {
                            field.setAccessible(true);
                            SignPrincipal principal = AuthorizationUtils.getPrincipal();
                            // 使用set方法设置值
                            field.set(arg, principal.getInstId()); 
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                	_logger.trace("Field '{}' not found or inaccessible in parameter type: {}" , INSTID_FIELD,parameterType.getName());
                }
            }
            return joinPoint.proceed();
        } finally {

        }
    }

}
