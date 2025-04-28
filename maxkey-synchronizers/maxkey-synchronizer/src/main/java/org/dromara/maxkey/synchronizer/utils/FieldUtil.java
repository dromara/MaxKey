/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.synchronizer.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class FieldUtil {

    public static void setFieldValue(Object obj, String fieldName, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        String setterMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String getterMethodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method getterMethod = clazz.getMethod(getterMethodName);
        Class<?> fieldType = getterMethod.getReturnType();

        Object convertedValue = convertValueToFieldType(value, fieldType);

        Method setterMethod = clazz.getMethod(setterMethodName, fieldType);
        setterMethod.invoke(obj, convertedValue);
    }

    public static Object getFieldValue(Object obj, String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        String getterMethodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method method = clazz.getMethod(getterMethodName);

        return method.invoke(obj);
    }

    public static boolean hasField(Class<?> clazz, String fieldName) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public static Object convertValueToFieldType(Object value, Class<?> fieldType) {
        if (fieldType.isInstance(value)) {
            return value;
        }

        if (fieldType == Integer.class || fieldType == int.class) {
            if (value instanceof Boolean iValue) {
                return Boolean.TRUE.equals(iValue) ? 1 : 0;
            }
            return Integer.parseInt(value.toString());
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(value.toString());
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(value.toString());
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return "1".equals(value.toString()) || Boolean.parseBoolean(value.toString());
        } else if (fieldType == String.class) {
            return value.toString();
        } else if (fieldType == DateTime.class) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dateTime;
            if (value instanceof Long iValue) {
                dateTime = new DateTime(iValue);
            } else if (value instanceof String iValue) {
                dateTime = DateTime.parse(iValue);
            }else {
                dateTime = new DateTime(value);
            }
            return dateTime.toString(formatter);
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }

    public static boolean areFieldsEqual(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return false;
        }
        if (!obj1.getClass().equals(obj2.getClass())) {
            return false;
        }

        Field[] fields = obj1.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value1 = field.get(obj1);
                Object value2 = field.get(obj2);
                if (value1 == null) {
                    if (value2 != null) {
                        return false;
                    }
                } else if (!value1.equals(value2)) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
