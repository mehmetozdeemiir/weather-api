package com.project.weatherapi.repository;

import com.project.weatherapi.model.WeatherEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class WeatherRepositoryTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Test
    void testFindFirstByRequestCityNameOrderByUpdatedTimeDesc() {
        // given
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId("4e4f9ee4-d628-4e67-8f78-65c88a54d7a1");
        weatherEntity.setRequestCityName("Istanbul");
        weatherEntity.setCityName("Istanbul");
        weatherEntity.setCountry("TR");
        weatherEntity.setTemperature(20);
        weatherEntity.setUpdatedTime(LocalDateTime.of(2023, 4, 24, 10, 0, 0));
        weatherEntity.setResponseLocalTime(LocalDateTime.of(2023, 4, 24, 11, 0, 0));

        //when
        when(weatherRepository.findFirstByRequestCityNameOrderByUpdatedTimeDesc("Istanbul"))
                .thenReturn(Optional.of(weatherEntity));


        //then
        Optional<WeatherEntity> result = weatherRepository.findFirstByRequestCityNameOrderByUpdatedTimeDesc("Istanbul");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("4e4f9ee4-d628-4e67-8f78-65c88a54d7a1");
        assertThat(result.get().getRequestCityName()).isEqualTo("Istanbul");
        assertThat(result.get().getCityName()).isEqualTo("Istanbul");
        assertThat(result.get().getCountry()).isEqualTo("TR");
        assertThat(result.get().getTemperature()).isEqualTo(20);
        assertThat(result.get().getUpdatedTime()).isEqualTo(LocalDateTime.of(2023, 4, 24, 10, 0, 0));
        assertThat(result.get().getResponseLocalTime()).isEqualTo(LocalDateTime.of(2023, 4, 24, 11, 0, 0));
    }
}

