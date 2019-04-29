package net.minidev.json.writer;

import java.io.IOException;
import java.lang.reflect.Type;

import net.minidev.json.parser.ParseException;

public class UpdaterMapper<T> extends JsonReaderI<T> {
	final T obj;
	final JsonReaderI<?> mapper;

	public UpdaterMapper(JsonReader base, T obj) {
		super(base);
		if (obj == null)
			throw new NullPointerException("can not update null Object");
		this.obj = obj;
		this.mapper = (JsonReaderI<?>) base.getMapper(obj.getClass());
	}

	public UpdaterMapper(JsonReader base, T obj, Type type) {
		super(base);
		if (obj == null)
			throw new NullPointerException("can not update null Object");
		this.obj = obj;
		this.mapper = (JsonReaderI<?>) base.getMapper(type);
	}

	/**
	 * called when json-smart parser meet an object key
	 */
	public JsonReaderI<?> startObject(String key) throws ParseException, IOException {
		Object bean = mapper.getValue(obj, key);
		if (bean == null)
			return mapper.startObject(key);
		return new UpdaterMapper<Object>(base, bean, mapper.getType(key));
	}

	/**
	 * called when json-smart parser start an array.
	 * 
	 * @param key
	 *            the destination key name, or null.
	 */
	public JsonReaderI<?> startArray(String key) throws ParseException, IOException {
		// if (obj != null)
		return mapper.startArray(key);
	}

	/**
	 * called when json-smart done parsing a value
	 */
	public void setValue(Object current, String key, Object value) throws ParseException, IOException {
		// if (obj != null)
		mapper.setValue(current, key, value);
	}

	/**
	 * add a value in an array json object.
	 */
	public void addValue(Object current, Object value) throws ParseException, IOException {
		// if (obj != null)
		mapper.addValue(current, value);
	}

	/**
	 * use to instantiate a new object that will be used as an object
	 */
	public Object createObject() {
		if (obj != null)
			return obj;
		return mapper.createObject();
	}

	/**
	 * use to instantiate a new object that will be used as an array
	 */
	public Object createArray() {
		if (obj != null)
			return obj;
		return mapper.createArray();
	}

	/**
	 * Allow a mapper to converte a temprary structure to the final data format.
	 * 
	 * example: convert an List&lt;Integer&gt; to an int[]
	 */
	@SuppressWarnings("unchecked")
	public T convert(Object current) {
		if (obj != null)
			return obj;
		return (T) mapper.convert(current);
	}
}
