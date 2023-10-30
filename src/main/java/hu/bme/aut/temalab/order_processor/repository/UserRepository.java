package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository<T extends User> {
    T save(T user);

    Optional<T> findById(long id);

    List<T> findAll();

    List<T> findByName(String name);

    List<T> findByEmail(String email);

    T update(T user);

    void delete(T user);
}