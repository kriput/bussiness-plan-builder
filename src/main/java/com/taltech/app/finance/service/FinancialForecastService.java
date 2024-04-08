package com.taltech.app.finance.service;

import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.repository.FinancialForecastRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialForecastService {

    private final FinancialForecastRepository repository;

    @Transactional
    public FinancialForecast createForecast(FinancialForecast forecast) {
        return repository.save(forecast);
    }

    public FinancialForecast findForecastById(long id) {
        return repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("No financial forecast found with provided id")
        );
    }

    public List<FinancialForecast> findAll() {
        return repository.findAll();
    }

    public void deleteForecastById(Long forecastId) {
        repository.deleteById(forecastId);
    }
}
