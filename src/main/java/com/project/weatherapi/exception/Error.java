package com.project.weatherapi.exception;

public record Error (
        String code,
        String type,
        String info
) { }
