package hu.bme.aut.temalab.order_processor.repository;
import hu.bme.aut.temalab.order_processor.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}