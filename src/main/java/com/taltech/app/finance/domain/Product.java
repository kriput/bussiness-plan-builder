package com.taltech.app.finance.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {

    @Id
    private Long id;

    private String name;

    private Double tax;

    private Double stockReserveRate;
}
