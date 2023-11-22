package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.enums.CartStatus;
import hu.bme.aut.temalab.order_processor.model.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    private BigDecimal subtotal;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;
    
    public Set<CartItem> getContent(){
    	return cartItems;
    }
}