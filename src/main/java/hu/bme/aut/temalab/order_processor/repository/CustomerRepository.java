package hu.bme.aut.temalab.order_processor.repository;
import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.orderHistory FROM Customer c WHERE c.id = :customerId")
    List<Order> findOrderHistoryByCustomerId(@Param("customerId") Long customerId);
}