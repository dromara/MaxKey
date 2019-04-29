package net.minidev.json.writer;

/*
 *    Copyright 2011-2014 JSON-SMART authors
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
import net.minidev.json.JSONArray;
import net.minidev.json.JSONAwareEx;
import net.minidev.json.JSONObject;
/**
 * Simple Reader Class for generic Map
 * 
 * @author uriel
 *
 * @param <T>
 */
public class DefaultMapper<T> extends JsonReaderI<T> {
	protected DefaultMapper(JsonReader base) {
		super(base);
	}

	@Override
	public JsonReaderI<JSONAwareEx> startObject(String key) {
		return base.DEFAULT;
	}

	@Override
	public JsonReaderI<JSONAwareEx> startArray(String key) {
		return base.DEFAULT;
	}

	@Override
	public Object createObject() {
		return new JSONObject();
	}

	@Override
	public Object createArray() {
		return new JSONArray();
	}

	@Override
	public void setValue(Object current, String key, Object value) {
		((JSONObject) current).put(key, value);
	}

	@Override
	public void addValue(Object current, Object value) {
		((JSONArray) current).add(value);
	}

}
