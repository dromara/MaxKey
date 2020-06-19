package org.maxkey.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;


@Component
public class JsonDateTimeDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat dateFormat = 
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext dc)
            throws IOException, JsonProcessingException {
        Date parserDate = null;
        try {
            parserDate = dateFormat.parse(parser.getText());
        } catch (ParseException e) {
            throw new JsonParseException(parser,"Could not parse date", e);
        }
        return parserDate;
    }

}
