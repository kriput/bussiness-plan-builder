package com.taltech.app.finance.service;

import com.taltech.app.finance.domain.FinancialOperation;
import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.enums.FinancialOperationType;
import com.taltech.app.finance.repository.FinancialOperationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final FinancialForecastService financialForecastService;

    public FinancialOperation addExpenseByForecastId(Long forecastId, FinancialOperation expense) {
        FinancialForecast forecast = financialForecastService.findForecastById(forecastId);
        expense.setFinancialForecast(forecast);
        return financialOperationRepository.save(expense);
    }

    public List<FinancialOperation> getExpensesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId, FinancialOperationType.EXPENSE);
    }

    public List<FinancialOperation> getIncomesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId, FinancialOperationType.INCOME);
    }

}
