package org.maxkey.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JSON serializer for Jackson to handle regular date instances as timestamps in
 * ISO format.
 */

public class JsonISODateSerializer extends JsonSerializer<Date> {

    private static final SimpleDateFormat dateFormat = 
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String formatted = dateFormat.format(date);
        generator.writeString(formatted);
    }

}