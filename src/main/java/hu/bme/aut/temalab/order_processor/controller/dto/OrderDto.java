package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private long id;
    private UserDto user;
    private String status;
    private AddressDto address;
    private String comment;
    private List<CouponDto> coupons;
    private String paymentMethod;
    private String shippingMethod;
    private BigDecimal total;
    private CartDto cart;
}