package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}