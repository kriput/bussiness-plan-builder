package com.taltech.app.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taltech.app.finance.enums.Unit;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
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
import jakarta.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double tax;

    private Double stockReserveRate;

    @JsonIgnore
    @ManyToOne
    private FinancialForecast financialForecast;

    @Column(name="financial_forecast_id", updatable=false, insertable=false)
    public Long financialForecastId;

    @Enumerated(value = EnumType.STRING)
    private Unit unit;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "product_per_period",
        joinColumns =@JoinColumn(name = "product_id",
        referencedColumnName = "id")
    )
    private List<ProductPerPeriod> productsPerPeriod = new ArrayList<>();

}
