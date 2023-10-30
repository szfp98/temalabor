package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.enums.CartStatus;
import hu.bme.aut.temalab.order_processor.model.users.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class Cart {
    private long id;
    private CartStatus status;
    private BigDecimal subtotal;
    private User user;
    private Map<Product, Integer> products;
}