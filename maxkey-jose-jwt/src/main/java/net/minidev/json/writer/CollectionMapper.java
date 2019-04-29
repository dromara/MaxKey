package net.minidev.json.writer;

/*
 *    Copyright 2011 JSON-SMART authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import net.minidev.asm.BeansAccess;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;

public class CollectionMapper {

	public static class MapType<T> extends JsonReaderI<T> {
		final ParameterizedType type;
		final Class<?> rawClass;
		final Class<?> instance;
		final BeansAccess<?> ba;

		final Type keyType;
		final Type valueType;

		final Class<?> keyClass;
		final Class<?> valueClass;

		JsonReaderI<?> subMapper;

		public MapType(JsonReader base, ParameterizedType type) {
			super(base);
			this.type = type;
			this.rawClass = (Class<?>) type.getRawType();
			if (rawClass.isInterface())
				instance = JSONObject.class;
			else
				instance = rawClass;
			ba = BeansAccess.get(instance, JSONUtil.JSON_SMART_FIELD_FILTER);

			keyType = type.getActualTypeArguments()[0];
			valueType = type.getActualTypeArguments()[1];
			if (keyType instanceof Class)
				keyClass = (Class<?>) keyType;
			else
				keyClass = (Class<?>) ((ParameterizedType) keyType).getRawType();
			if (valueType instanceof Class)
				valueClass = (Class<?>) valueType;
			else
				valueClass = (Class<?>) ((ParameterizedType) valueType).getRawType();
		}

		@Override
		public Object createObject() {
			try {
				return instance.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public JsonReaderI<?> startArray(String key) {
			if (subMapper == null)
				subMapper = base.getMapper(valueType);
			return subMapper;
		}

		@Override
		public JsonReaderI<?> startObject(String key) {
			if (subMapper == null)
				subMapper = base.getMapper(valueType);
			return subMapper;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Object current, String key, Object value) {
			((Map<Object, Object>) current).put(JSONUtil.convertToX(key, keyClass),
					JSONUtil.convertToX(value, valueClass));
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getValue(Object current, String key) {
			return ((Map<String, Object>) current).get(JSONUtil.convertToX(key, keyClass));
		}

		@Override
		public Type getType(String key) {
			return type;
		}
	};

	public static class MapClass<T> extends JsonReaderI<T> {
		final Class<?> type;
		final Class<?> instance;
		final BeansAccess<?> ba;

		JsonReaderI<?> subMapper;

		public MapClass(JsonReader base, Class<?> type) {
			super(base);
			this.type = type;
			if (type.isInterface())
				this.instance = JSONObject.class;
			else
				this.instance = type;
			this.ba = BeansAccess.get(instance, JSONUtil.JSON_SMART_FIELD_FILTER);
		}

		@Override
		public Object createObject() {
			return ba.newInstance();
		}

		@Override
		public JsonReaderI<?> startArray(String key) {
			return base.DEFAULT ; // _ARRAY
		}

		@Override
		public JsonReaderI<?> startObject(String key) {
			return base.DEFAULT; // _MAP
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Object current, String key, Object value) {
			((Map<String, Object>) current).put(key, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getValue(Object current, String key) {
			return ((Map<String, Object>) current).get(key);
		}

		@Override
		public Type getType(String key) {
			return type;
		}
	};

	public static class ListType<T> extends JsonReaderI<T> {
		final ParameterizedType type;
		final Class<?> rawClass;
		final Class<?> instance;
		final BeansAccess<?> ba;

		final Type valueType;
		final Class<?> valueClass;

		JsonReaderI<?> subMapper;

		public ListType(JsonReader base, ParameterizedType type) {
			super(base);
			this.type = type;
			this.rawClass = (Class<?>) type.getRawType();
			if (rawClass.isInterface())
				instance = JSONArray.class;
			else
				instance = rawClass;
			ba = BeansAccess.get(instance, JSONUtil.JSON_SMART_FIELD_FILTER); // NEW
			valueType = type.getActualTypeArguments()[0];
			if (valueType instanceof Class)
				valueClass = (Class<?>) valueType;
			else
				valueClass = (Class<?>) ((ParameterizedType) valueType).getRawType();
		}

		@Override
		public Object createArray() {
			return ba.newInstance();
		}

		@Override
		public JsonReaderI<?> startArray(String key) {
			if (subMapper == null)
				subMapper = base.getMapper(type.getActualTypeArguments()[0]);
			return subMapper;
		}

		@Override
		public JsonReaderI<?> startObject(String key) {
			if (subMapper == null)
				subMapper = base.getMapper(type.getActualTypeArguments()[0]);
			return subMapper;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void addValue(Object current, Object value) {
			((List<Object>) current).add(JSONUtil.convertToX(value, valueClass));
		}
	};

	public static class ListClass<T> extends JsonReaderI<T> {
		final Class<?> type;
		final Class<?> instance;
		final BeansAccess<?> ba;

		JsonReaderI<?> subMapper;

		public ListClass(JsonReader base, Class<?> clazz) {
			super(base);
			this.type = clazz;
			if (clazz.isInterface())
				instance = JSONArray.class;
			else
				instance = clazz;
			ba = BeansAccess.get(instance, JSONUtil.JSON_SMART_FIELD_FILTER);
		}

		@Override
		public Object createArray() {
			return ba.newInstance();
		}

		@Override
		public JsonReaderI<?> startArray(String key) {
			return base.DEFAULT;// _ARRAY;
		}

		@Override
		public JsonReaderI<?> startObject(String key) {
			return base.DEFAULT;// _MAP;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void addValue(Object current, Object value) {
			((List<Object>) current).add(value);
		}
	};
}
