package com.taltech.app.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taltech.app.finance.enums.ExpenseType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ExpenseType type;

    @ManyToOne
    @JsonIgnore
    private FinancialForecast financialForecast;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "expense_per_period",
        joinColumns =@JoinColumn(name = "expense_id",
            referencedColumnName = "id")
    )
    private List<ExpensePerPeriod> expensesPerPeriod;
}
