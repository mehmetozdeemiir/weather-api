package com.project.weatherapi.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String API_URL;
    public static String API_KEY;
    public static final String ACCESS_KEY_PARAM = "?access_key=";
    public static final String QUERY_KEY_PARAM = "&query=";
    public static final String RATE_LIMIT_ERROR_MESSAGE="Rate limit exceeded. Please try your request again later!";

    @Value("${weather-stack.api-url}")
    public void setApiUrl(String apiUrl) {
        Constants.API_URL = apiUrl;
    }

    @Value("${weather-stack.api-key}")
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}
