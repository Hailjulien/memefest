package com.memefest.DataAccess.JSON.Serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:hh:mm:ss");
  
  public CustomLocalDateTimeSerializer() {
    this(null);
  }
  
  public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
    super(t);
  }
  
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
    String formattedDateTime = value.format(formatter);
    gen.writeString(formattedDateTime);
  }
}
