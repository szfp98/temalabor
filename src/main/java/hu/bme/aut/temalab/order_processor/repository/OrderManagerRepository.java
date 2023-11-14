package hu.bme.aut.temalab.order_processor.repository;
import hu.bme.aut.temalab.order_processor.model.users.OrderManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderManagerRepository extends JpaRepository<OrderManager, Long> {
}