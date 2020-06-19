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

/**
 * JSON deserializer for Jackson to handle regular date instances as timestamps
 * in ISO format.
 *
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    private static final SimpleDateFormat dateFormat = 
            new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        try {
            return dateFormat.parse(parser.getText());
        } catch (ParseException e) {
            throw new JsonParseException(parser,"Could not parse date", e);
        }
    }

}