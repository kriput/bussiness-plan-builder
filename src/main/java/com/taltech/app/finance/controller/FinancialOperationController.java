package com.taltech.app.finance.controller;

import com.taltech.app.finance.domain.FinancialOperation;
import com.taltech.app.finance.service.FinancialOperationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operations")
public class FinancialOperationController {

    private final FinancialOperationService financialOperationService;

    @PostMapping("/{forecastId}/add")
    public FinancialOperation addExpenseByForecastIdGeneratedAutomatically(@PathVariable Long forecastId, @RequestBody FinancialOperation expense) {
        return financialOperationService.addFinancialOperationByForecastId(forecastId, expense, false);
    }

    @PutMapping("/{forecastId}/add")
    public FinancialOperation updateExpenseByForecastIdInsertedManually(@PathVariable Long forecastId, @RequestBody FinancialOperation expense) {
        return financialOperationService.addFinancialOperationByForecastId(forecastId, expense, true);
    }

    @GetMapping("/{forecastId}/expenses")
    public List<FinancialOperation> getExpensesForForecast(@PathVariable Long forecastId) {
        return financialOperationService.getExpensesForForecast(forecastId);
    }

    @GetMapping("{forecastId}/incomes")
    public List<FinancialOperation> getIncomesForForecast(@PathVariable Long forecastId) {
        return financialOperationService.getIncomesForForecast(forecastId);
    }

    @PostMapping("/{forecastId}/delete")
    public void deleteOperationById(@PathVariable Long forecastId, @RequestBody FinancialOperation operation) {
        financialOperationService.subtractFinancialOperationByForecastId(forecastId, operation);
    }

    @DeleteMapping("/{operationId}")
    public void deleteOperationById(@PathVariable Long operationId) {
        financialOperationService.deleteOperationById(operationId);
    }

}
