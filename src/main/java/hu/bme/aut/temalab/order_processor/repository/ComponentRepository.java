package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}