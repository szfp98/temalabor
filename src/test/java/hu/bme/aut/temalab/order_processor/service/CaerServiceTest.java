package hu.bme.aut.temalab.order_processor.service;


import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.CartItem;
import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.CartRepository;
import hu.bme.aut.temalab.order_processor.repository.OrderRepository;
import hu.bme.aut.temalab.order_processor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CaerServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Mock
    private Cart cart;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getCartContentTest(){
        HashSet<CartItem> set = new HashSet<CartItem>();
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        assertTrue(cartService.getCartbyId(1L).isPresent());
        when(cartService.getCartContent(1L)).thenReturn(set);
        assertEquals(set.size(), cartService.getCartContent(1L).size());
    }

    @Test
    void addItemTest(){
        


    }



}
