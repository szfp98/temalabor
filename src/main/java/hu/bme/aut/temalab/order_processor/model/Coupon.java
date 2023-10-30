package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.enums.Category;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Coupon {
    private long id;
    private String name;
    private Category targetCategory;
    private int value;
}