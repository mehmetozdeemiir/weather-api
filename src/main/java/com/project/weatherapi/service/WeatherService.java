package com.project.weatherapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.weatherapi.constants.Constants;
import com.project.weatherapi.dto.WeatherDto;
import com.project.weatherapi.dto.WeatherResponse;
import com.project.weatherapi.model.WeatherEntity;
import com.project.weatherapi.repository.WeatherRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = {"weathers"})
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate; //resttemplate i direkt olarak autowired edemeyiz. Context de olmayan bir obje @Bean olarak congiguration kodunu yazman gerekli
    private final ObjectMapper objectMapper = new ObjectMapper(); //json ı nesneye çevirmek için kullanıyoruz
    private final Clock clock;

    @Cacheable(key = "#city")
    public WeatherDto getWeatherByCityName(String city) {
        Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestCityNameOrderByUpdatedTimeDesc(city);
        return weatherEntityOptional.map(weather -> {
            if (weather.getUpdatedTime().isBefore(getLocalDateTimeNow().minusMinutes(30))) {
                return WeatherDto.convert(getWeatherFromWeatherStack(city));
            }
            return WeatherDto.convert(weather);
        }).orElseGet(() -> WeatherDto.convert(getWeatherFromWeatherStack(city)));
        //ifpresentorelse kullanamıyoruz void donen metod için daha uygun burada sonuc donuyoruz map yeterli oluyor.
    }

    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackUrl(city), String.class); //apiden veriyi aldık
        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class); //nesneye donusturduk. try catch e almamızı istedi readValue
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //her 30 dakikada bir cache i temizleyecek
    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "1800000")
    public void clearCache(){
        log.info("cache cleared");
    }

    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity = new WeatherEntity(city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                getLocalDateTimeNow(),
                LocalDateTime.parse(weatherResponse.location().localtime(), dateTimeFormatter));
        return weatherRepository.save(weatherEntity);
    }

    private String getWeatherStackUrl(String city){
        return Constants.API_URL + Constants.ACCESS_KEY_PARAM + Constants.API_KEY + Constants.QUERY_KEY_PARAM + city;
    }

    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }

}
