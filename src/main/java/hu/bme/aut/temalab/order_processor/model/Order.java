package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.users.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Order {
    private long id;
    private User user;
    private OrderStatus status;
    private Address address;
    private String comment;
    private List<Coupon> coupons;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    private BigDecimal total;
    private Cart cart;
}