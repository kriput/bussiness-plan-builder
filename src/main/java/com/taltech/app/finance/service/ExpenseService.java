package com.taltech.app.finance.service;

import com.taltech.app.finance.domain.Expense;
import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final FinancialForecastService financialForecastService;

    public Expense addExpenseByForecastId(Long forecastId, Expense expense) {
        FinancialForecast forecast = financialForecastService.findForecastById(forecastId);
        expense.setFinancialForecast(forecast);
        return expenseRepository.save(expense);
    }

}
