package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.Address;
import hu.bme.aut.temalab.order_processor.model.users.User;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    Address save(Address address);

    Optional<Address> findById(long id);

    List<Address> findByUser(User user);

    List<Address> findByZipCode(String zipCode);

    Address update(Address address);

    void delete(Address address);
}