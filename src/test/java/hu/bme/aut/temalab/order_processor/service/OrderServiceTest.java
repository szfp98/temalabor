package hu.bme.aut.temalab.order_processor.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hu.bme.aut.temalab.order_processor.enums.*;
import hu.bme.aut.temalab.order_processor.model.*;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.CartRepository;
import hu.bme.aut.temalab.order_processor.repository.OrderRepository;
import hu.bme.aut.temalab.order_processor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Mock
    private Cart cart;

    @Mock
    private Order order;

    @Mock
    private Address shippingAddress;

    @Mock
    private Product product;

    @Mock
    private CartItem cartItem;

    @Mock
    private Coupon coupon;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem);

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon);
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        assertEquals(1, orderService.getAllOrders().size());
        verify(orderRepository).findAll();
    }

    @Test
    void createOrderTest() {
        PaymentMethod paymentMethod = PaymentMethod.DEBIT_CARD;
        ShippingMethod shippingMethod = ShippingMethod.STANDARD;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

        Cart mockCart = Mockito.mock(Cart.class);

        CartItem mockCartItem = Mockito.mock(CartItem.class);
        Product mockProduct = Mockito.mock(Product.class);
        when(mockProduct.getPrice()).thenReturn(new BigDecimal("50.00"));

        when(mockCartItem.getProduct()).thenReturn(mockProduct);
        when(mockCartItem.getQuantity()).thenReturn(2);

        Set<CartItem> mockCartItems = new HashSet<>();
        mockCartItems.add(mockCartItem);

        when(mockCart.getCartItems()).thenReturn(mockCartItems);
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(mockCart));

        Order mockOrder = Mockito.mock(Order.class);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

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
        Order testOrder = new Order();
        testOrder.setStatus(OrderStatus.PROCESSING);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(testOrder));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order argOrder = invocation.getArgument(0, Order.class);
            argOrder.setStatus(OrderStatus.NEW);
            return argOrder;
        });

        Order updatedOrder = orderService.updateOrderStatus(1L, OrderStatus.NEW);
        assertNotNull(updatedOrder, "Az Order objektum nem lehet null");
        assertEquals(OrderStatus.NEW, updatedOrder.getStatus());
        verify(orderRepository).findById(anyLong());
        verify(orderRepository).save(any(Order.class));
    }



}