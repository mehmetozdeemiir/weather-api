package com.project.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
//apideki obje
public record Location(
        String name,
        String country,
        String region,
        Double lat,
        Double lon,
        @JsonProperty("timezone_id")
        String timezoneId,
        String localtime,
        @JsonProperty("localtime_epoch")//bu çekeceğimiz apideki ismi biz camelcase yazdığımız için ona map ediyor.
        String localtimeEpoch,
        @JsonProperty("utc_offset")
        String utcOffset
) { }