package hu.bme.aut.temalab.order_processor.model.users;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderManager extends User{

}