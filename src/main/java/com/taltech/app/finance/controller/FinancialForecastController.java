package com.taltech.app.finance.controller;

import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.service.FinancialForecastService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forecasts")
public class FinancialForecastController {

    private final FinancialForecastService financialForecastService;

    @PostMapping("/create")
    public FinancialForecast createFinancialForecast(@RequestBody FinancialForecast forecast) {
        return financialForecastService.createForecast(forecast);
    }

    @GetMapping("/{id}")
    public FinancialForecast getFinancialForecast(@PathVariable long id) {
        return financialForecastService.findForecastById(id);
    }

    @GetMapping("")
    public List<FinancialForecast> getAllFinancialForecasts() {
        return financialForecastService.findAll();
    }
}
