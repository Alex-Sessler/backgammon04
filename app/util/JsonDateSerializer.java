package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<Date> {

	@Value("dd.MM.yyyy HH:mm")
	private String JSON_DATE_FORMAT;

	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

		String formatedDate = dateFormat.format(date);

		gen.writeString(formatedDate);
	}
}