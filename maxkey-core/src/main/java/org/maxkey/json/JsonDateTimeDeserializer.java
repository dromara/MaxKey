package org.maxkey.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

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
