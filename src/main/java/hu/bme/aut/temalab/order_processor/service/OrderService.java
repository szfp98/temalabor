package hu.bme.aut.temalab.order_processor.service;
import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.*;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> {
            Hibernate.initialize(order.getCart().getCartItems());
            Hibernate.initialize(order.getUser());
            Hibernate.initialize(order.getAddress());
        });
        return orders;
    }

    @Transactional
    public Order createOrder(Long userId, Long cartId, Address shippingAddress, PaymentMethod paymentMethod, ShippingMethod shippingMethod) {
        log.info("Creating new order for user {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if (!(user instanceof Customer customer)) {
            throw new RuntimeException("User with id: " + userId + " is not a customer");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        shippingAddress = addressRepository.save(shippingAddress);

        Order order = Order.builder()
                .user(customer)
                .address(shippingAddress)
                .paymentMethod(paymentMethod)
                .shippingMethod(shippingMethod)
                .status(OrderStatus.NEW)
                .total(calculateTotal(cart, new HashSet<>()))
                .cart(cart)
                .build();

        customer.addOrder(order);
        userRepository.save(customer);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long id) {
        log.info("Fetching order with id {}", id);
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            Hibernate.initialize(order.getCart().getCartItems());
            Hibernate.initialize(order.getUser());
            Hibernate.initialize(order.getAddress());
            return Optional.of(order);
        }
        return orderOpt;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        log.info("Fetching orders for user {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<Order> orders = orderRepository.findByUser(user);
        orders.forEach(order -> {
            Hibernate.initialize(order.getCart().getCartItems());
            Hibernate.initialize(order.getUser());
            Hibernate.initialize(order.getAddress());
        });
        return orders;
    }


    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        log.info("Updating order {} status to {}", id, status);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        Hibernate.initialize(order.getCart().getCartItems());
        Hibernate.initialize(order.getUser());
        Hibernate.initialize(order.getAddress());
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public Order addCouponToOrder(Long orderId, Long couponId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + couponId));
        order.addCoupon(coupon);
        order.setTotal(calculateTotal(order.getCart(), order.getCouponIds()));
        return orderRepository.save(order);
    }

    private BigDecimal calculateTotal(Cart cart, Set<Long> couponIds) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            total = total.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        for (Long couponId : couponIds) {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + couponId));
            total = total.subtract(total.multiply(BigDecimal.valueOf(coupon.getValue()).divide(new BigDecimal(100))));
        }
        return total;
    }
}