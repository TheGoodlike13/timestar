package com.superum.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
 
public class TestUtils {
 
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
 
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }
    
    public static String convertObjectToString(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T convertStringToObject(String object, Class<T> clazz) throws IOException {
        return mapper.reader(clazz).readValue(object);
    }

    public static <T> T convertStringToObject(String object, TypeReference<T> type) throws IOException {
        return mapper.reader(type).readValue(object);
    }

    public static <T> T convertBytesToObject(byte[] object, Class<T> clazz) throws IOException {
        return mapper.reader(clazz).readValue(object);
    }

    public static <T> T convertBytesToObject(byte[] object, TypeReference<T> type) throws IOException {
        return mapper.reader(type).readValue(object);
    }
 
    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
 
        for (int index = 0; index < length; index++)
            builder.append("a");

        return builder.toString();
    }
    
    // PRIVATE
    
    private static final ObjectMapper mapper;
    static {
    	mapper = new ObjectMapper();
    	mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}