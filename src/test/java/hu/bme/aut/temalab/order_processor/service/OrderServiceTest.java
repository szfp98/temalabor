package hu.bme.aut.temalab.order_processor.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.*;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Cart cart;
    private Order order;

    @BeforeEach
    void setUp() {
        user = new Customer(/* inicializálás */);
        cart = new Cart(/* inicializálás */);
        order = new Order(/* inicializálás */);

        Product product = new Product(/* inicializálás */);
        CartItem cartItem = new CartItem(/* inicializálás */);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItem.setCart(cart);

        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        assertEquals(1, orderService.getAllOrders().size());
        verify(orderRepository).findAll();
    }

    @Test
    void createOrderTest() {
        Address shippingAddress = new Address(/* inicializálás */);
        PaymentMethod paymentMethod = PaymentMethod.DEBIT_CARD;
        ShippingMethod shippingMethod = ShippingMethod.STANDARD;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(cart.getCartItems()).thenReturn(cart.getCartItems());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(1L, 1L, shippingAddress, paymentMethod, shippingMethod);

        assertNotNull(createdOrder);
        verify(userRepository).findById(anyLong());
        verify(cartRepository).findById(anyLong());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void getOrderByIdTest() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        assertTrue(orderService.getOrderById(1L).isPresent());
        verify(orderRepository).findById(anyLong());
    }

    @Test
    void updateOrderStatusTest() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.updateOrderStatus(1L, OrderStatus.NEW);
        assertEquals(OrderStatus.NEW, updatedOrder.getStatus());
        verify(orderRepository).findById(anyLong());
        verify(orderRepository).save(any(Order.class));
    }
}
