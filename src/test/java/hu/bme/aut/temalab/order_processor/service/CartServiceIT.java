package hu.bme.aut.temalab.order_processor.service;

import hu.bme.aut.temalab.order_processor.enums.CartStatus;
import hu.bme.aut.temalab.order_processor.model.CartItem;
import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import hu.bme.aut.temalab.order_processor.enums.Category;
import hu.bme.aut.temalab.order_processor.model.Component;
import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.repository.ProductRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartServiceIT {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    private Cart testCart;

    @BeforeEach
    void setUp(){
        testCart = new Cart();
        testCart.setId(0);
        testCart.setUser(null);
        testCart.setStatus(CartStatus.OPEN);
        testCart.setSubtotal(new BigDecimal(0));
        testCart.setCartItems(new HashSet<CartItem>());
        CartItem testCi = new CartItem();
        testCi.setProduct(new Product());
        testCi.getProduct().setName("Test Product");
        testCi.getProduct().setCategory(Category.ELECTRONICS);
        testCi.getProduct().setValue(new BigDecimal("10.00"));
        cartRepository.save(testCart);
    }

    @AfterEach
    @Transactional
    void cleanUp(){
        cartRepository.deleteAll();
    }

    @Test
    @Transactional
    void getCartContentTest(){
        Set<CartItem> cartContent = cartRepository.getById(0L).getCartItems();
        assertTrue(testCart.getCartItems().equals(cartContent));

    }

    @Test
    @Transactional
    void addItemTest(){

    }
}