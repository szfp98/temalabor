package hu.bme.aut.temalab.order_processor.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import hu.bme.aut.temalab.order_processor.enums.Category;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private BigDecimal value;

    @OneToMany
    private List<Component> components;

    public BigDecimal getPrice() {
        // TODO
        return null;
    }
}