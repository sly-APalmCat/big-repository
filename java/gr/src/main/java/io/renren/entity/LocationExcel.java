package io.renren.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationExcel {
    private BigDecimal lng;
    private BigDecimal lat;

}