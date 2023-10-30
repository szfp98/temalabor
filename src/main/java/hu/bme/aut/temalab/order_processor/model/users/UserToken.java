package hu.bme.aut.temalab.order_processor.model.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToken {
    private String token;
    private User user;
}
