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

    public FinancialOperation addFinancialOperationByForecastId(Long forecastId,
        FinancialOperation financialOperation, boolean isManualInsert) {
        FinancialForecast forecast = financialForecastService.findForecastById(forecastId);

        Optional<FinancialOperation> existingOperation = findExistingOperation(
            financialOperation, forecast);

        if (existingOperation.isEmpty()) {
            financialOperation.setFinancialForecast(forecast);
            return financialOperationRepository.save(financialOperation);
        } else {
            for (TotalPerPeriod totalPerPeriod : financialOperation.getTotalsPerPeriod()) {
                addTotalToExistingOperation(isManualInsert, totalPerPeriod,
                    existingOperation.get());
            }
        }
        return financialOperationRepository.save(existingOperation.get());
    }

    private void addTotalToExistingOperation(boolean isManualInsert, TotalPerPeriod totalPerPeriod,
        FinancialOperation existingOperation) {

        Optional<TotalPerPeriod> existingTotalPerPeriod = existingOperation.getTotalsPerPeriod()
            .stream()
            .filter(t -> t.getYear().equals(totalPerPeriod.getYear())).findAny();

        if (existingTotalPerPeriod.isPresent()) {
            if (isManualInsert) {
                existingTotalPerPeriod.get().setSum(totalPerPeriod.getSum());
            } else {
                existingTotalPerPeriod.get()
                    .setSum(existingTotalPerPeriod.get().getSum() + totalPerPeriod.getSum());
            }
        } else {
            existingOperation.getTotalsPerPeriod().add(totalPerPeriod);
        }
    }

    private Optional<FinancialOperation> findExistingOperation(
        FinancialOperation financialOperation, FinancialForecast forecast) {
        return forecast.getFinancialOperations().stream()
            .filter(savedOperation ->
                savedOperation.getSubtype().equals(financialOperation.getSubtype())
                    && savedOperation.getType().equals(financialOperation.getType())
                    && isTaxValueSame(savedOperation.getTax(), financialOperation.getTax()))
            .findAny();
    }

    private boolean isTaxValueSame(Double savedTaxValue, Double newTaxValue) {
        if (newTaxValue == null && savedTaxValue == null) {
            return true;
        } else if (newTaxValue != null && savedTaxValue == null) {
            return false;
        } else {
            return savedTaxValue.equals(newTaxValue);
        }
    }

    public List<FinancialOperation> getExpensesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId,
            FinancialOperationType.EXPENSE);
    }

    public List<FinancialOperation> getIncomesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId,
            FinancialOperationType.INCOME);
    }

}
