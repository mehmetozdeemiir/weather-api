package com.project.weatherapi.repository;

import com.project.weatherapi.model.WeatherEntity;
import org.springframework.boot.actuate.autoconfigure.metrics.export.otlp.OtlpProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WeatherRepository extends JpaRepository<WeatherEntity,String> {

    Optional<WeatherEntity> findFirstByRequestCityNameOrderByUpdatedTimeDesc(String city);

}
