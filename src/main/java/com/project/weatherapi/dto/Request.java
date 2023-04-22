package com.project.weatherapi.dto;
//resttemplatedeki request objesi. ismini kendimiz belirleyemiyoruz hangi apiden veri cekiceksek ordaki objenin ismiyle aynı olmak zorunda.
public record Request(
        String type,
        String query,
        String language,
        String unit
) {}