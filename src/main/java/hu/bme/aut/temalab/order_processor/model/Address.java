package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.model.users.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    private String zipCode;
    private String city;
    private String street;
    private String houseNumber;
    private String comment;
}