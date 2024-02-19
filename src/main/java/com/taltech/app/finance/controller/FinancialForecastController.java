package com.taltech.app.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forecast")
public class FinancialForecastController {

    @GetMapping("/{id}")
    public FinancialForecast getFinancialForecast(@PathVariable long id) {
    }
}
