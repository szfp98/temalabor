package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

@Data
public class CouponDto {
    private long id;
    private String name;
    private String targetCategory;
    private int value;
}