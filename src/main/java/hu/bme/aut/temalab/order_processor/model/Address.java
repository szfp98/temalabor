package hu.bme.aut.temalab.order_processor.model;

import hu.bme.aut.temalab.order_processor.model.users.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private User user;
    private String zipCode;
    private String city;
    private String street;
    private String houseNumber;
    private String comment;

}
