package com.taltech.app.finance.repository;

import com.taltech.app.finance.domain.FinancialOperation;
import com.taltech.app.finance.enums.FinancialOperationType;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialOperationRepository extends ListCrudRepository<FinancialOperation, Long> {

    List<FinancialOperation> findAllByFinancialForecast_IdAndType(Long id, FinancialOperationType type);
}
