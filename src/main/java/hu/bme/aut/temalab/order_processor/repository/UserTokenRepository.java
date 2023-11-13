package hu.bme.aut.temalab.order_processor.repository;
import hu.bme.aut.temalab.order_processor.model.users.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}