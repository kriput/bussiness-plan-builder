package com.taltech.app.finance.service;

import com.taltech.app.finance.domain.FinancialOperation;
import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.domain.TotalPerPeriod;
import com.taltech.app.finance.enums.FinancialOperationType;
import com.taltech.app.finance.repository.FinancialOperationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final FinancialForecastService financialForecastService;

    public FinancialOperation addFinancialOperationByForecastId(Long forecastId, FinancialOperation financialOperation) {
        FinancialForecast forecast = financialForecastService.findForecastById(forecastId);
        Optional<FinancialOperation> existingOperation = forecast.getFinancialOperations().stream()
            .filter(savedOperation ->
                savedOperation.getSubtype().equals(financialOperation.getSubtype())).findAny();
        if (existingOperation.isEmpty()) {
            financialOperation.setFinancialForecast(forecast);
            return financialOperationRepository.save(financialOperation);
        } else {
            for (TotalPerPeriod totalPerPeriod : financialOperation.getTotalsPerPeriod()) {
                Optional<TotalPerPeriod> existingTotalPerPeriod = existingOperation.get().getTotalsPerPeriod().stream()
                    .filter(t -> t.getYear().equals(totalPerPeriod.getYear())).findAny();
                if (existingTotalPerPeriod.isPresent()) {
                    existingTotalPerPeriod.get().setSum(existingTotalPerPeriod.get().getSum() + totalPerPeriod.getSum());
                } else {
                    existingOperation.get().getTotalsPerPeriod().add(totalPerPeriod);
                }
            }
        }
        return financialOperationRepository.save(existingOperation.get());
    }

    public List<FinancialOperation> getExpensesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId, FinancialOperationType.EXPENSE);
    }

    public List<FinancialOperation> getIncomesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId, FinancialOperationType.INCOME);
    }

}
