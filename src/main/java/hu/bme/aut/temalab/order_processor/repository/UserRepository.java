package hu.bme.aut.temalab.order_processor.repository;
import hu.bme.aut.temalab.order_processor.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}