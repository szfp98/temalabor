package hu.bme.aut.temalab.order_processor.repository;

import hu.bme.aut.temalab.order_processor.enums.Category;
import hu.bme.aut.temalab.order_processor.model.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    Optional<Coupon> findById(long id);

    List<Coupon> findByName(String name);

    List<Coupon> findByTargetCategory(Category targetCategory);

    List<Coupon> findByValue(int value);

    Coupon update(Coupon coupon);

    void delete(Coupon coupon);
}
