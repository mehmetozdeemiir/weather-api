package com.project.weatherapi.controller;

import com.project.weatherapi.dto.WeatherDto;
import com.project.weatherapi.service.WeatherService;
import com.project.weatherapi.validation.CityNameConstraint;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather")
@AllArgsConstructor
@Validated//string değilde obje olsaydı valid calısırdı ama string oldugu için validated koymak zorunda kaldık
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/{city}")
    @RateLimiter(name = "basic")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") @Valid @CityNameConstraint @NotBlank String city){
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }
}
