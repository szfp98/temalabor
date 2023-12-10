package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

}