package com.project.weatherapi.dto;
//resttemplate te olan json ı objeye donusturmek icin boyle bi objeye ihtiyac duyuldu.
public record WeatherResponse(
        Request request,
        Location location,
        Current current
) { }
