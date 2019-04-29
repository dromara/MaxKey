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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONAware;
import net.minidev.json.JSONAwareEx;
import net.minidev.json.JSONObject;

public class JsonReader {
	private final ConcurrentHashMap<Type, JsonReaderI<?>> cache;

	public JsonReaderI<JSONAwareEx> DEFAULT;
	public JsonReaderI<JSONAwareEx> DEFAULT_ORDERED;

	public JsonReader() {
		cache = new ConcurrentHashMap<Type, JsonReaderI<?>>(100);

		cache.put(Date.class, BeansMapper.MAPPER_DATE);

		cache.put(int[].class, ArraysMapper.MAPPER_PRIM_INT);
		cache.put(Integer[].class, ArraysMapper.MAPPER_INT);

		cache.put(short[].class, ArraysMapper.MAPPER_PRIM_INT);
		cache.put(Short[].class, ArraysMapper.MAPPER_INT);

		cache.put(long[].class, ArraysMapper.MAPPER_PRIM_LONG);
		cache.put(Long[].class, ArraysMapper.MAPPER_LONG);

		cache.put(byte[].class, ArraysMapper.MAPPER_PRIM_BYTE);
		cache.put(Byte[].class, ArraysMapper.MAPPER_BYTE);

		cache.put(char[].class, ArraysMapper.MAPPER_PRIM_CHAR);
		cache.put(Character[].class, ArraysMapper.MAPPER_CHAR);

		cache.put(float[].class, ArraysMapper.MAPPER_PRIM_FLOAT);
		cache.put(Float[].class, ArraysMapper.MAPPER_FLOAT);

		cache.put(double[].class, ArraysMapper.MAPPER_PRIM_DOUBLE);
		cache.put(Double[].class, ArraysMapper.MAPPER_DOUBLE);

		cache.put(boolean[].class, ArraysMapper.MAPPER_PRIM_BOOL);
		cache.put(Boolean[].class, ArraysMapper.MAPPER_BOOL);

		this.DEFAULT = new DefaultMapper<JSONAwareEx>(this);
		this.DEFAULT_ORDERED = new DefaultMapperOrdered(this);

		cache.put(JSONAwareEx.class, this.DEFAULT);
		cache.put(JSONAware.class, this.DEFAULT);
		cache.put(JSONArray.class, this.DEFAULT);
		cache.put(JSONObject.class, this.DEFAULT);
	}

	/**
	 * remap field name in custom classes
	 * 
	 * @param fromJson
	 *            field name in json
	 * @param toJava
	 *            field name in Java
	 * @since 2.1.1
	 */
	public <T> void remapField(Class<T> type, String fromJson, String toJava) {
		JsonReaderI<T> map = this.getMapper(type);
		if (!(map instanceof MapperRemapped)) {
			map = new MapperRemapped<T>(map);
			registerReader(type, map);
		}
		((MapperRemapped<T>) map).renameField(fromJson, toJava);
	}

	public <T> void registerReader(Class<T> type, JsonReaderI<T> mapper) {
		cache.put(type, mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> JsonReaderI<T> getMapper(Type type) {
		if (type instanceof ParameterizedType)
			return getMapper((ParameterizedType) type);
		return getMapper((Class<T>) type);
	}

	/**
	 * Get the corresponding mapper Class, or create it on first call
	 * 
	 * @param type
	 *            to be map
	 */
	public <T> JsonReaderI<T> getMapper(Class<T> type) {
		// look for cached Mapper
		@SuppressWarnings("unchecked")
		JsonReaderI<T> map = (JsonReaderI<T>) cache.get(type);
		if (map != null)
			return map;
		/*
		 * Special handle
		 */
		if (type instanceof Class) {
			if (Map.class.isAssignableFrom(type))
				map = new DefaultMapperCollection<T>(this, type);
			else if (List.class.isAssignableFrom(type))
				map = new DefaultMapperCollection<T>(this, type);
			if (map != null) {
				cache.put(type, map);
				return map;
			}
		}

		if (type.isArray())
			map = new ArraysMapper.GenericMapper<T>(this, type);
		else if (List.class.isAssignableFrom(type))
			map = new CollectionMapper.ListClass<T>(this, type);
		else if (Map.class.isAssignableFrom(type))
			map = new CollectionMapper.MapClass<T>(this, type);
		else
			// use bean class
			map = new BeansMapper.Bean<T>(this, type);
		cache.putIfAbsent(type, map);
		return map;
	}

	@SuppressWarnings("unchecked")
	public <T> JsonReaderI<T> getMapper(ParameterizedType type) {
		JsonReaderI<T> map = (JsonReaderI<T>) cache.get(type);
		if (map != null)
			return map;
		Class<T> clz = (Class<T>) type.getRawType();
		if (List.class.isAssignableFrom(clz))
			map = new CollectionMapper.ListType<T>(this, type);
		else if (Map.class.isAssignableFrom(clz))
			map = new CollectionMapper.MapType<T>(this, type);
		cache.putIfAbsent(type, map);
		return map;
	}
}
