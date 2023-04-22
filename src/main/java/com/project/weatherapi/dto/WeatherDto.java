package com.project.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.weatherapi.model.WeatherEntity;

import java.time.LocalDateTime;

//spring datanın constructor a ihtiyacı var bu ihtiyactan dolayı record u entity olarak kullanmamız sacma oluyor dtolarda işe yarıyor.record boş constructor kullanmıyor buda spring data için sorun entity uygun değil.
public record WeatherDto(
        String cityName,
        String country,
        Integer temperature,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime updatedTime
) {
    public static WeatherDto convert(WeatherEntity from) {
        return new WeatherDto(
                from.getCityName(),
                from.getCountry(),
                from.getTemperature(),
                from.getUpdatedTime());
    }
}