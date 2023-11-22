package hu.bme.aut.temalab.order_processor.service;

import hu.bme.aut.temalab.order_processor.enums.CartStatus;
import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.Address;
import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.CartRepository;
import hu.bme.aut.temalab.order_processor.repository.OrderRepository;
import hu.bme.aut.temalab.order_processor.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User testUser;
    private Cart testCart;
    private Address testAddress;

    @BeforeEach
    void setUp() {
        testUser = new Customer();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        userRepository.save(testUser);

        testCart = new Cart();
        testCart.setStatus(CartStatus.OPEN);
        testCart.setUser(testUser);
        cartRepository.save(testCart);

        testAddress = new Address();
        testAddress.setCity("Test City");
        testAddress.setStreet("Test Street");
        testAddress.setZipCode("1234");
        testAddress.setHouseNumber("42");
        testAddress.setComment("Delivery instructions");
    }

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        cartRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getAllOrdersTest() {
        Order testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setCart(testCart);
        orderRepository.save(testOrder);

        List<Order> orders = orderService.getAllOrders();
        assertFalse(orders.isEmpty(), "A rendelések listája nem lehet üres");
        assertTrue(orders.contains(testOrder), "A létrehozott rendelésnek benne kell lennie a listában");
    }

    @Test
    @Transactional
    void createOrderTest() {
        Order newOrder = orderService.createOrder(testUser.getId(), testCart.getId(), testAddress, PaymentMethod.DEBIT_CARD, ShippingMethod.STANDARD);

        assertNotNull(newOrder, "Az új rendelés nem lehet null");
        assertEquals(testUser, newOrder.getUser(), "A rendelés felhasználója nem megfelelő");
        assertEquals(testCart, newOrder.getCart(), "A rendelés kosara nem megfelelő");
        assertEquals(testAddress, newOrder.getAddress(), "A rendelés címe nem megfelelő");
        assertEquals(PaymentMethod.DEBIT_CARD, newOrder.getPaymentMethod(), "A rendelés fizetési módja nem megfelelő");
        assertEquals(ShippingMethod.STANDARD, newOrder.getShippingMethod(), "A rendelés szállítási módja nem megfelelő");
        assertNotNull(newOrder.getTotal(), "A rendelés összértékének ki kell lennie számítva");
        assertEquals(OrderStatus.NEW, newOrder.getStatus(), "A rendelés állapotának újnak kell lennie");

        Order savedOrder = orderRepository.findById(newOrder.getId()).orElse(null);
        assertNotNull(savedOrder, "A mentett rendelés nem lehet null");
        assertEquals(newOrder.getId(), savedOrder.getId(), "A mentett rendelés ID-ja nem egyezik meg");
    }


    @Test
    @Transactional
    void getOrderByIdTest() {
        Order testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setCart(testCart);
        testOrder.setStatus(OrderStatus.NEW);
        testOrder.setAddress(testAddress);
        testOrder.setComment("Test comment");
        testOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        testOrder.setShippingMethod(ShippingMethod.EXPRESS);
        testOrder.setTotal(new BigDecimal("100.00"));
        Order savedOrder = orderRepository.save(testOrder);

        Order foundOrder = orderService.getOrderById(savedOrder.getId()).orElse(null);

        assertNotNull(foundOrder, "A lekérdezett rendelés nem lehet null");
        assertEquals(savedOrder.getId(), foundOrder.getId(), "A lekérdezett rendelés ID-ja nem megfelelő");
        assertEquals(testUser, foundOrder.getUser(), "A rendelés felhasználója nem megfelelő");
        assertEquals(testCart, foundOrder.getCart(), "A rendelés kosara nem megfelelő");
        assertEquals(OrderStatus.NEW, foundOrder.getStatus(), "A rendelés állapota nem megfelelő");
        assertEquals(testAddress, foundOrder.getAddress(), "A rendelés címe nem megfelelő");
        assertEquals("Test comment", foundOrder.getComment(), "A rendelés megjegyzése nem megfelelő");
        assertEquals(PaymentMethod.CREDIT_CARD, foundOrder.getPaymentMethod(), "A rendelés fizetési módja nem megfelelő");
        assertEquals(ShippingMethod.EXPRESS, foundOrder.getShippingMethod(), "A rendelés szállítási módja nem megfelelő");
        assertEquals(new BigDecimal("100.00"), foundOrder.getTotal(), "A rendelés összértéke nem megfelelő");
    }


    @Test
    @Transactional
    void updateOrderStatusTest() {
        Order testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setCart(testCart);
        testOrder.setStatus(OrderStatus.NEW);
        Order savedOrder = orderRepository.save(testOrder);

        Order updatedOrder = orderService.updateOrderStatus(savedOrder.getId(), OrderStatus.DELIVERED);

        assertNotNull(updatedOrder, "A frissített rendelés nem lehet null");
        assertEquals(OrderStatus.DELIVERED, updatedOrder.getStatus(), "A rendelés állapota nem megfelelő");

        Order reloadedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        assertNotNull(reloadedOrder, "A lekérdezett frissített rendelés nem lehet null");
        assertEquals(OrderStatus.DELIVERED, reloadedOrder.getStatus(), "Az adatbázisban tárolt rendelés állapota nem megfelelő");
    }

}