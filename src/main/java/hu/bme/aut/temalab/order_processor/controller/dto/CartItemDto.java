package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private long id;
    private ProductDto product;
    private Integer quantity;
}