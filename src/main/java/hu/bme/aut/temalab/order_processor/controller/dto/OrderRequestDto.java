package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long userId;
    private Long cartId;
    private AddressDto shippingAddress;
    private String paymentMethod;
    private String shippingMethod;
}