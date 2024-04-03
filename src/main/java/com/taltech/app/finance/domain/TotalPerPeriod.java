package com.taltech.app.finance.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Embeddable
@Table(name = "total_per_period")
public class TotalPerPeriod {
    
    private Double sum;
    private Integer year;
}
