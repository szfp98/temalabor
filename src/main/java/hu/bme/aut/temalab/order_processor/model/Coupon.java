package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category targetCategory;

    @Column(name = "coupon_value")
    private int value;
}