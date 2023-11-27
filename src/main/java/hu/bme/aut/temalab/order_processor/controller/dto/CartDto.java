package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private long id;
    private String status;
    private BigDecimal subtotal;
    private List<CartItemDto> cartItems;
}