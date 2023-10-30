package hu.bme.aut.temalab.order_processor.model;

import java.math.BigDecimal;
import java.util.List;

import hu.bme.aut.temalab.order_processor.enums.Category;

public class Product {
	private String name;
	private long id;
	private Category category;
	private BigDecimal value;
	private List<Component> components;
}
