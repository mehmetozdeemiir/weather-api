package com.project.weatherapi.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String API_URL;
    public static String API_KEY;
    public static final String ACCESS_KEY_PARAM = "?access_key=";
    public static final String QUERY_KEY_PARAM = "&query=";
    public static final String RATE_LIMIT_ERROR_MESSAGE = "Rate limit exceeded. Please try your request again later!";
    public static Integer API_CALL_LIMIT;

    @Value("${weather-stack.api-url}")
    public void setApiUrl(String apiUrl) {
        Constants.API_URL = apiUrl;
    }

    @Value("${weather-stack.api-key}")
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    @Value("${weather-stack.api-call-limit}")
    public void setApiCallLimit(Integer apiCallLimit) {
        API_CALL_LIMIT = apiCallLimit;
    }
}
