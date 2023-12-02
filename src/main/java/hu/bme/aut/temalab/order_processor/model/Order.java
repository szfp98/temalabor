package hu.bme.aut.temalab.order_processor.model;
import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.users.User;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private Address address;

    private String comment;

    @OneToMany
    private List<Coupon> coupons;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    private BigDecimal total;

    @OneToOne
    private Cart cart;
}