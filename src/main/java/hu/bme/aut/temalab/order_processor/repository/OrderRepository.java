package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.Address;
import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.model.users.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(long id);

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByAddress(Address address);

    List<Order> findByPaymentMethod(PaymentMethod paymentMethod);

    List<Order> findByShippingMethod(ShippingMethod shippingMethod);

    Order update(Order order);

    void delete(Order order);
}