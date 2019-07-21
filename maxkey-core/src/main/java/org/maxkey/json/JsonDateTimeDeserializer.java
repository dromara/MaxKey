package org.maxkey.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class JsonDateTimeDeserializer   extends JsonDeserializer<Date>{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext dc)
			throws IOException, JsonProcessingException {
		Date parserDate=null;
		try {
			parserDate = dateFormat.parse(jsonParser.getText());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return parserDate;
	}

}
