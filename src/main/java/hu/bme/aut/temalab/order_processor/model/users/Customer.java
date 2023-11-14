package hu.bme.aut.temalab.order_processor.model.users;

import hu.bme.aut.temalab.order_processor.model.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends User{
    @OneToMany(mappedBy = "user")
    private List<Order> orderHistory;
}