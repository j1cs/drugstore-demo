package me.jics.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Deserializer for normalize some coordinates. The source sent coordinates like -70-000 or -30,000. super bad.
 */
public class CoorsDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getText();
        if (value.length() <= 0)
            return 0.0;
        return Double.parseDouble(value.replaceAll("[0-9]([-,])[0-9]", "."));
    }
}
