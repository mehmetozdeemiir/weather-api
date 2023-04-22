package com.project.weatherapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String requestCityName;
    private String cityName;
    private String country;
    private Integer temperature;
    private LocalDateTime updatedTime;
    private LocalDateTime responseLocalTime;

    public WeatherEntity(String requestCityName, String cityName, String country, Integer temperature, LocalDateTime updatedTime, LocalDateTime responseLocalTime) {
        this.requestCityName = requestCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updatedTime = updatedTime;
        this.responseLocalTime = responseLocalTime;
    }
}