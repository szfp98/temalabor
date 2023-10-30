package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.enums.CartStatus;
import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.users.User;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);

    Optional<Cart> findById(long id);

    List<Cart> findByUser(User user);

    List<Cart> findByStatus(CartStatus status);

    Cart update(Cart cart);

    void delete(Cart cart);
}