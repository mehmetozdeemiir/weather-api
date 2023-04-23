package com.project.weatherapi.controller;

import com.project.weatherapi.dto.WeatherDto;
import com.project.weatherapi.exception.GeneralExceptionAdvice;
import com.project.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import static com.project.weatherapi.TestSupport.formatter;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
@DirtiesContext
public class WeatherControllerTest {

    @MockBean //service mocklanması
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetWeather_whenCityParameterValid_shouldReturnWeatherDto() throws Exception {
        //given
        String request ="Amsterdam";
        LocalDateTime localDateTime = LocalDateTime.parse("2023-03-08 23:58", formatter);
        WeatherDto expected = WeatherDto.builder()
                .cityName("Amsterdam")
                .country("Netherlands")
                .temperature(2)
                .updatedTime(localDateTime)
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(new WeatherController(weatherService))
                .setControllerAdvice(GeneralExceptionAdvice.class)
                .build();

        //when
        when(weatherService.getWeatherByCityName(request)).thenReturn(expected);

        mockMvc.perform(get("/v1/api/weather/Amsterdam").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cityName", is("Amsterdam")))
                .andExpect(jsonPath("$.country", is("Netherlands")))
                .andExpect(jsonPath("$.temperature", is(2)))
                .andExpect(jsonPath("$.updatedTime", is("2023-03-08 23:58")));

    }

    @Test
    public void testGetWeather_whenCityParameterIsNotValid_shouldReturnHTTP400BadRequest() throws Exception {
        mockMvc.perform(get("/v1/api/weather/"+123).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
