package com.taltech.app.finance.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "financial_forecast")
public class FinancialForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1)
    private String name;

    private Double sellingInCreditRate;

    private Double buildingDeprecationRate;

    private Double equipmentDeprecationRate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "financial_forecast_id", referencedColumnName = "id")
    private List<Product> products = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "financial_forecast_id", referencedColumnName = "id")
    private List<Expense> expenses = new ArrayList<>();
}
