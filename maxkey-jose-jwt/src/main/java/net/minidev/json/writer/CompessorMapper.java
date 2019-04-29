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
import java.io.IOException;

import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

public class CompessorMapper extends JsonReaderI<CompessorMapper> {
	private Appendable out;
	private JSONStyle compression;
	private Boolean _isObj;
	private boolean needSep = false;
	private boolean isOpen = false;
	private boolean isClosed = false;

	// private boolean isRoot = false;

	private boolean isArray() {
		return _isObj == Boolean.FALSE;
	}

	private boolean isObject() {
		return _isObj == Boolean.TRUE;
	}

	private boolean isCompressor(Object obj) {
		return obj instanceof CompessorMapper;
	}

	public CompessorMapper(JsonReader base, Appendable out, JSONStyle compression) {
		this(base, out, compression, null);
		// isRoot = true;
	}

	public CompessorMapper(JsonReader base, Appendable out, JSONStyle compression, Boolean isObj) {
		super(base);
		this.out = out;
		this.compression = compression;
		this._isObj = isObj;
		// System.out.println("new CompressorMapper isObj:" + isObj);
	}

	@Override
	public JsonReaderI<?> startObject(String key) throws IOException {
		open(this);
		startKey(key);
		// System.out.println("startObject " + key);
		CompessorMapper r = new CompessorMapper(base, out, compression, true);
		open(r);
		return r;
	}

	@Override
	public JsonReaderI<?> startArray(String key) throws IOException {
		open(this);
		startKey(key);
		// System.out.println("startArray " + key);
		CompessorMapper r = new CompessorMapper(base, out, compression, false);
		open(r);
		return r;
	}

	private void startKey(String key) throws IOException {
		addComma();
		// if (key == null)
		// return;
		if (isArray())
			return;
		if (!compression.mustProtectKey(key))
			out.append(key);
		else {
			out.append('"');
			JSONValue.escape(key, out, compression);
			out.append('"');
		}
		out.append(':');
	}

	@Override
	public void setValue(Object current, String key, Object value) throws IOException {
		// System.out.println("setValue(" + key + "," + value + ")");
		// if comprossor => data allready writed
		if (isCompressor(value)) {
			addComma();
			return;
		}
		startKey(key);
		writeValue(value);
	}

	@Override
	public void addValue(Object current, Object value) throws IOException {
		// System.out.println("add value" + value);
		// if (!isCompressor(value))
		addComma();
		writeValue(value);
	}

	private void addComma() throws IOException {
		if (needSep) {
			out.append(',');
			// needSep = false;
		} else {
			needSep = true;
		}
	}

	private void writeValue(Object value) throws IOException {
		if (value instanceof String) {
			compression.writeString(out, (String) value);
//
//			if (!compression.mustProtectValue((String) value))
//				out.append((String) value);
//			else {
//				out.append('"');
//				JSONValue.escape((String) value, out, compression);
//				out.append('"');
//			}
			// needSep = true;
		} else {
			if (isCompressor(value)) {
				close(value);
				// needSep = true;
			} else {
				JSONValue.writeJSONString(value, out, compression);
				// needSep = true;
			}
		}
	}

	@Override
	public Object createObject() {
		// System.out.println("createObject");
		this._isObj = true;
		try {
			open(this);
		} catch (Exception e) {
		}
		// if (this.isUnknow() && isRoot) { // && isRoot
		// this._isObj = true;
		// try {
		// out.append('{'); // 1
		// } catch (Exception e) {
		// }
		// }
		return this;
	}

	@Override
	public Object createArray() {
		// System.out.println("createArray");
		this._isObj = false;
		try {
			open(this);
		} catch (Exception e) {
		}
		return this;
	}

	public CompessorMapper convert(Object current) {
		try {
			close(current);
			return this;
		} catch (Exception e) {
			return this;
		}
	}

	private void close(Object obj) throws IOException {
		if (!isCompressor(obj))
			return;
		if (((CompessorMapper) obj).isClosed)
			return;
		((CompessorMapper) obj).isClosed = true;
		if (((CompessorMapper) obj).isObject()) {
			// System.out.println("convert }");
			out.append('}');
			needSep = true;
		} else if (((CompessorMapper) obj).isArray()) {
			// System.out.println("convert ]");
			out.append(']');
			needSep = true;
		}
	}

	private void open(Object obj) throws IOException {
		if (!isCompressor(obj))
			return;
		if (((CompessorMapper) obj).isOpen)
			return;
		((CompessorMapper) obj).isOpen = true;
		if (((CompessorMapper) obj).isObject()) {
			// System.out.println("open {");
			out.append('{');
			needSep = false;
		} else if (((CompessorMapper) obj).isArray()) {
			// System.out.println("open [");
			out.append('[');
			needSep = false;
		}
	}

}
