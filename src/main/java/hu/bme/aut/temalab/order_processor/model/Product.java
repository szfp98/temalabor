package hu.bme.aut.temalab.order_processor.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import hu.bme.aut.temalab.order_processor.enums.Category;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "product_value")
    private BigDecimal value;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Component> components;

    public BigDecimal getPrice() {
        return value;
    }

    public void addComponent(Component component) {
        if(component==null)
            throw new IllegalArgumentException("Component cannot be null.");
        if(this.components == null)
            this.components = new ArrayList<>();
        this.components.add(component);
        component.setProduct(this);
    }
}