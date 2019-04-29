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

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

import net.minidev.asm.Accessor;
import net.minidev.asm.BeansAccess;
import net.minidev.asm.ConvertDate;
import net.minidev.json.JSONUtil;

@SuppressWarnings("unchecked")
public abstract class BeansMapper<T> extends JsonReaderI<T> {

	public BeansMapper(JsonReader base) {
		super(base);
	}

	public abstract Object getValue(Object current, String key);

	public static class Bean<T> extends JsonReaderI<T> {
		final Class<T> clz;
		final BeansAccess<T> ba;
		final HashMap<String, Accessor> index;

		public Bean(JsonReader base, Class<T> clz) {
			super(base);
			this.clz = clz;
			this.ba = BeansAccess.get(clz, JSONUtil.JSON_SMART_FIELD_FILTER);
			this.index = ba.getMap();
		}

		@Override
		public void setValue(Object current, String key, Object value) {
			ba.set((T) current, key, value);
			// Accessor nfo = index.get(key);
			// if (nfo == null)
			// throw new RuntimeException("Can not set " + key + " field in " +
			// clz);
			// value = JSONUtil.convertTo(value, nfo.getType());
			// ba.set((T) current, nfo.getIndex(), value);
		}

		public Object getValue(Object current, String key) {
			return ba.get((T) current, key);
			// Accessor nfo = index.get(key);
			// if (nfo == null)
			// throw new RuntimeException("Can not set " + key + " field in " +
			// clz);
			// return ba.get((T) current, nfo.getIndex());
		}

		@Override
		public Type getType(String key) {
			Accessor nfo = index.get(key);
			return nfo.getGenericType();
		}

		@Override
		public JsonReaderI<?> startArray(String key) {
			Accessor nfo = index.get(key);
			if (nfo == null)
				throw new RuntimeException("Can not find Array '" + key + "' field in " + clz);
			return base.getMapper(nfo.getGenericType());
		}

		@Override
		public JsonReaderI<?> startObject(String key) {
			Accessor f = index.get(key);
			if (f == null)
				throw new RuntimeException("Can not find Object '" + key + "' field in " + clz);
			return base.getMapper(f.getGenericType());
		}

		@Override
		public Object createObject() {
			return ba.newInstance();
		}
	}

	public static class BeanNoConv<T> extends JsonReaderI<T> {
		final Class<T> clz;
		final BeansAccess<T> ba;
		final HashMap<String, Accessor> index;

		public BeanNoConv(JsonReader base, Class<T> clz) {
			super(base);
			this.clz = clz;
			this.ba = BeansAccess.get(clz, JSONUtil.JSON_SMART_FIELD_FILTER);
			this.index = ba.getMap();
		}

		@Override
		public void setValue(Object current, String key, Object value) {
			ba.set((T) current, key, value);
		}

		public Object getValue(Object current, String key) {
			return ba.get((T) current, key);
		}

		@Override
		public Type getType(String key) {
			Accessor nfo = index.get(key);
			return nfo.getGenericType();
		}

		@Override
		public JsonReaderI<?> startArray(String key) {
			Accessor nfo = index.get(key);
			if (nfo == null)
				throw new RuntimeException("Can not set " + key + " field in " + clz);
			return base.getMapper(nfo.getGenericType());
		}

		@Override
		public JsonReaderI<?> startObject(String key) {
			Accessor f = index.get(key);
			if (f == null)
				throw new RuntimeException("Can not set " + key + " field in " + clz);
			return base.getMapper(f.getGenericType());
		}

		@Override
		public Object createObject() {
			return ba.newInstance();
		}
	}
	
	public static JsonReaderI<Date> MAPPER_DATE = new ArraysMapper<Date>(null) {
		@Override
		public Date convert(Object current) {
			return ConvertDate.convertToDate(current);
		}
	};

}
