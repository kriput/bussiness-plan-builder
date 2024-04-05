package com.taltech.app.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taltech.app.finance.enums.FinancialOperationSubtype;
import com.taltech.app.finance.enums.FinancialOperationType;
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
@Table(name = "financial_operation")
public class FinancialOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private FinancialOperationType type;

    @Enumerated(value = EnumType.STRING)
    private FinancialOperationSubtype subtype;

    private Double tax;

    @ManyToOne
    @JsonIgnore
    private FinancialForecast financialForecast;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "total_per_period",
        joinColumns =@JoinColumn(name = "financial_operation_id",
            referencedColumnName = "id")
    )
    private List<TotalPerPeriod> totalsPerPeriod;
}
