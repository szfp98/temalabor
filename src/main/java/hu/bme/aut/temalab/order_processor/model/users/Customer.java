package hu.bme.aut.temalab.order_processor.model.users;

import hu.bme.aut.temalab.order_processor.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Customer extends User{
    List<Order> orderHistory;
}
