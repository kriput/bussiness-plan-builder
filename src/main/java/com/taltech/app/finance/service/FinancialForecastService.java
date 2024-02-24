package com.taltech.app.finance.service;

import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.repository.FinancialForecastRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialForecastService {

    private final FinancialForecastRepository repository;

    public FinancialForecast createForecast(FinancialForecast forecast) {
        return repository.save(forecast);
    }

    public FinancialForecast findForecastById(long id) {
        return repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("No financial forecast found with provided id!")
        );
    }

    public List<FinancialForecast> getAll() {
        return repository.findAll();
    }
}
