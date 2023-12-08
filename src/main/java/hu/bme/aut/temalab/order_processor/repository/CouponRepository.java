package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> { }