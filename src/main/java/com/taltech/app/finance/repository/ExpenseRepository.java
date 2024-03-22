package com.taltech.app.finance.repository;

import com.taltech.app.finance.domain.Expense;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {

}
