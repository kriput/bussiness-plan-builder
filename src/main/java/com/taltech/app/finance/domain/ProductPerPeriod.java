package com.taltech.app.finance.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Embeddable
@Table(name = "product_per_period")
public class ProductPerPeriod {

    private Integer quantity;

    private Double forExport;

    private Double price;

    private Double costPerItem;

    private Integer periodYear;

    private Integer periodMonth;

}
