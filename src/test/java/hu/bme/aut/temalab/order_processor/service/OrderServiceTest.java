package hu.bme.aut.temalab.order_processor.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hu.bme.aut.temalab.order_processor.enums.*;
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

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Cart cart;
    private Order order;

    private Address shippingAddress;

    @BeforeEach
    void setUp() {
        user = new Customer();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        cart = new Cart();
        cart.setId(1);
        cart.setStatus(CartStatus.OPEN);
        cart.setSubtotal(new BigDecimal("100.00"));
        cart.setUser(user);

        shippingAddress = new Address();
        shippingAddress.setZipCode("1234");
        shippingAddress.setCity("Test City");
        shippingAddress.setStreet("Main Street");
        shippingAddress.setHouseNumber("1A");
        shippingAddress.setComment("Doorbell broken");


        order = new Order();
        order.setId(1);
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        order.setAddress(shippingAddress);
        order.setComment("Please deliver after 6 PM");
        order.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        order.setShippingMethod(ShippingMethod.STANDARD);
        order.setTotal(new BigDecimal("100.00"));
        order.setCart(cart);

        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setCategory(Category.ELECTRONICS);
        product.setValue(new BigDecimal("50.00"));

        CartItem cartItem = new CartItem();
        cartItem.setId(1);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        Coupon coupon = new Coupon();
        coupon.setId(1);
        coupon.setName("Discount10");
        coupon.setTargetCategory(Category.ELECTRONICS);
        coupon.setValue(10);

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon);
        order.setCoupons(coupons);
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
        when(mockCart.getCartItems()).thenReturn(cart.getCartItems());
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(mockCart));

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