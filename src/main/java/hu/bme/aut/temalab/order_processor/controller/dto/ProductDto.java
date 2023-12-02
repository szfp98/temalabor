package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import hu.bme.aut.temalab.order_processor.enums.Category;
import hu.bme.aut.temalab.order_processor.model.Component;

@Data
public class ProductDto {
    private long id;
    private String name;
    private Category category;
    private BigDecimal value;
    private List<Component> components;
}