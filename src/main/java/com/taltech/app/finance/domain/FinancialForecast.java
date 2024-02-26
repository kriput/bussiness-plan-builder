package com.taltech.app.finance.domain;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class FinancialForecast {

    @Id
    private Long id;

    private String name;

    private Double sellingInCreditRate;

    private Double buildingDeprecationRate;

    private Double equipmentDeprecationRate;

    private List<Product> products;
}
