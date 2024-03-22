package com.taltech.app.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taltech.app.finance.enums.ExpenseType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    private Double sum;

    @ManyToOne
    @JsonIgnore
    private FinancialForecast financialForecast;
}
