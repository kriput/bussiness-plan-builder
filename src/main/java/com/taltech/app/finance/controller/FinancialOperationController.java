package com.taltech.app.finance.controller;

import com.taltech.app.finance.domain.FinancialOperation;
import com.taltech.app.finance.service.FinancialOperationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operations")
public class FinancialOperationController {

    private final FinancialOperationService financialOperationService;

    @RequestMapping("/{forecastId}/add")
    public FinancialOperation addExpenseByForecastId(@PathVariable Long forecastId, @RequestBody FinancialOperation expense) {
        return financialOperationService.addFinancialOperationByForecastId(forecastId, expense);
    }

    @RequestMapping("/{forecastId}/expenses")
    public List<FinancialOperation> getExpensesForForecast(@PathVariable Long forecastId) {
        return financialOperationService.getExpensesForForecast(forecastId);
    }

    @RequestMapping("{forecastId}/incomes")
    public List<FinancialOperation> getIncomesForForecast(@PathVariable Long forecastId) {
        return financialOperationService.getIncomesForForecast(forecastId);
    }

}
