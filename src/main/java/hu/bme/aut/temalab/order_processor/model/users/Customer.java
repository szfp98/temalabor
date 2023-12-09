package hu.bme.aut.temalab.order_processor.model.users;

import hu.bme.aut.temalab.order_processor.model.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Customer extends User{
    @OneToMany(mappedBy = "user")
    private List<Order> orderHistory;

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (this.orderHistory == null) {
            this.orderHistory = new ArrayList<>();
        }
        this.orderHistory.add(order);
    }
}