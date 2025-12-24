package com.studying.bankAnalyzer.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeRatesResponse(String base, Map<String, Double> rates) { }
