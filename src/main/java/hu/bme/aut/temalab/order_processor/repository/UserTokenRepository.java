package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.model.users.UserToken;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository {
    UserToken save(UserToken userToken);

    Optional<UserToken> findByToken(String token);

    List<UserToken> findByUser(User user);

    UserToken update(UserToken userToken);

    void delete(UserToken userToken);
}