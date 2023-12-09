package hu.bme.aut.temalab.order_processor.model.users;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderManager extends User{
    @Builder
    public OrderManager(long id, String name, String email) {
        super(id, name, email);
    }
}