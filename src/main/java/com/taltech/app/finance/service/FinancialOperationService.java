package com.taltech.app.finance.service;

import static com.taltech.app.finance.enums.FinancialOperationSubtype.SALARY;
import static com.taltech.app.finance.enums.FinancialOperationSubtype.SOCIAL_TAX;
import static com.taltech.app.finance.enums.FinancialOperationSubtype.UNEMPLOYMENT_INSURANCE_TAX;

import com.taltech.app.finance.domain.FinancialOperation;
import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.domain.TotalPerPeriod;
import com.taltech.app.finance.enums.FinancialOperationSubtype;
import com.taltech.app.finance.enums.FinancialOperationType;
import com.taltech.app.finance.repository.FinancialOperationRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final FinancialForecastService financialForecastService;

    @Transactional
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

    @Transactional
    public void subtractFinancialOperationByForecastId(Long forecastId, FinancialOperation operation) {
        FinancialForecast forecast = financialForecastService.findForecastById(forecastId);

        Optional<FinancialOperation> existingOperation = findExistingOperation(operation, forecast);
        if (existingOperation.isPresent()) {
            for (TotalPerPeriod totalPerPeriod : operation.getTotalsPerPeriod()) {
                subtractTotalFromExistingOperation(existingOperation.get(), totalPerPeriod.getSum(), totalPerPeriod.getYear());
            }
            financialOperationRepository.save(existingOperation.get());
        }
    }

    private void subtractTotalFromExistingOperation(FinancialOperation existingOperation, Double sum, int year) {

        Optional<TotalPerPeriod> existingTotalPerPeriod = existingOperation.getTotalsPerPeriod()
            .stream()
            .filter(t -> t.getYear().equals(year)).findAny();

        existingTotalPerPeriod.ifPresent(perPeriod -> perPeriod
            .setSum(perPeriod.getSum() - sum));
    }

    public List<FinancialOperation> getExpensesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId,
            FinancialOperationType.EXPENSE);
    }

    public List<FinancialOperation> getIncomesForForecast(Long forecastId) {
        return financialOperationRepository.findAllByFinancialForecast_IdAndType(forecastId,
            FinancialOperationType.INCOME);
    }

    @Transactional
    public void deleteOperationById(Long operationId) {
        FinancialOperation operation = financialOperationRepository.findById(operationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    "Operation with ID " + operationId + " not found")
            );
        if (FinancialOperationSubtype.SALARY.equals(operation.getSubtype())) {
            deleteRelatedOperations(List.of(SALARY, SOCIAL_TAX, UNEMPLOYMENT_INSURANCE_TAX),
                operation.getFinancialForecast());
        } else {
            financialOperationRepository.deleteById(operationId);
        }
    }

    private void deleteRelatedOperations(List<FinancialOperationSubtype> subtypes,
        FinancialForecast forecast) {
        List<FinancialOperation> operations = subtypes.stream()
            .map(subtype -> findOperationBySubtypeAndForecast(subtype, forecast)).filter(
                Objects::nonNull).toList();
        operations.forEach(op -> forecast.getFinancialOperations().remove(op));
        financialOperationRepository.deleteAll(operations);
    }

    public FinancialOperation findOperationBySubtypeAndForecast(
        FinancialOperationSubtype subtype, FinancialForecast forecast) {
        return forecast
            .getFinancialOperations().stream()
            .filter(op -> op.getSubtype().equals(subtype))
            .findAny().orElse(null);
    }

}
