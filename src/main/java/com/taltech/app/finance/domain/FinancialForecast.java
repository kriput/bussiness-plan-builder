package com.taltech.app.finance.domain;

import lombok.Data;

@Data
public class FinancialForecast {

    private Long id;

    private String name;

    private Double percentageSellingInCredit;

    private Double buildingDeprecationRate;

    private Double equipmentDeprecationRate;
}
