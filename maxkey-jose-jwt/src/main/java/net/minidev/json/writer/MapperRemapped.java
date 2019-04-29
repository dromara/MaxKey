package net.minidev.json.writer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.parser.ParseException;

/**
 * Simple solution to supporr on read filed renaming
 * 
 * @author uriel
 *
 * @param <T>
 */
public class MapperRemapped<T> extends JsonReaderI<T> {
	private Map<String, String> rename;
	private JsonReaderI<T> parent;

	public MapperRemapped(JsonReaderI<T> parent) {
		super(parent.base);
		this.parent = parent;
		this.rename = new HashMap<String, String>();
	}

	public void renameField(String source, String dest) {
		rename.put(source, dest);
	}

	private String rename(String key) {
		String k2 = rename.get(key);
		if (k2 != null)
			return k2;
		return key;
	}

	@Override
	public void setValue(Object current, String key, Object value) throws ParseException, IOException {
		key = rename(key);
		parent.setValue(current, key, value);
	}

	public Object getValue(Object current, String key) {
		key = rename(key);
		return parent.getValue(current, key);
	}

	@Override
	public Type getType(String key) {
		key = rename(key);
		return parent.getType(key);
	}

	@Override
	public JsonReaderI<?> startArray(String key) throws ParseException, IOException {
		key = rename(key);
		return parent.startArray(key);
	}

	@Override
	public JsonReaderI<?> startObject(String key) throws ParseException, IOException {
		key = rename(key);
		return parent.startObject(key);
	}

	@Override
	public Object createObject() {
		return parent.createObject();
	}
}
