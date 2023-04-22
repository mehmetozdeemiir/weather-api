package com.project.weatherapi.exception;

public record ErrorResponse (
        String success,
        Error error
) { }
