package com.taltech.app.finance.repository;

import com.taltech.app.finance.domain.FinancialForecast;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialForecastRepository extends ListCrudRepository<FinancialForecast, Long> {
}
