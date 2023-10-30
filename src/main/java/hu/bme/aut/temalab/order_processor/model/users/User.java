package hu.bme.aut.temalab.order_processor.model.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {
    private long id;
    private String name;
    private String email;
}