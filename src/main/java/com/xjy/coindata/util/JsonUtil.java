package com.xjy.coindata.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String obj2Json(Object object) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	public static <T> T json2Obj(String content, Class<T> valueType) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(content, valueType);
		} catch (IOException e) {
			logger.error("JSON READ ERROR:{}",e);
		}
		return null;
	}
}
