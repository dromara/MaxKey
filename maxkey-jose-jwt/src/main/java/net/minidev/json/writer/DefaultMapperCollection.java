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
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

public class DefaultMapperCollection<T> extends JsonReaderI<T> {
	Class<T> clz;
	//? extends Collection
	public DefaultMapperCollection(JsonReader base, Class<T> clz) {
		super(base);
		this.clz = clz;
	}

	// public static AMapper<JSONAwareEx> DEFAULT = new
	// DefaultMapperCollection<JSONAwareEx>();
	@Override
	public JsonReaderI<T> startObject(String key) {
		return this;
	}

	@Override
	public JsonReaderI<T> startArray(String key) {
		return this;
	}

	@Override
	public Object createObject() {
		try {
			Constructor<T> c = clz.getConstructor();
			return c.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Object createArray() {
		try {
			Constructor<T> c = clz.getConstructor();
			return c.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public void setValue(Object current, String key, Object value) {
		((Map<String, Object>) current).put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addValue(Object current, Object value) {
		((List<Object>) current).add(value);
	}

}
