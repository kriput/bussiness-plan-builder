package com.taltech.app.finance.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Embeddable
@Table(name = "expense_per_period")
public class ExpensePerPeriod {
    
    private Double sum;
    private Integer year;
}
