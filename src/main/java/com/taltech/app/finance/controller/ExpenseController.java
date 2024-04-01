package com.taltech.app.finance.controller;

import com.taltech.app.finance.domain.Expense;
import com.taltech.app.finance.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @RequestMapping("/add/{forecastId}")
    public Expense addExpenseByForecastId(@PathVariable Long forecastId, @RequestBody Expense expense) {
        return expenseService.addExpenseByForecastId(forecastId, expense);
    }

}
