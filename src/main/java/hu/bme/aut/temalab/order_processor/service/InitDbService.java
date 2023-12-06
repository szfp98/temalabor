package hu.bme.aut.temalab.order_processor.service;

import hu.bme.aut.temalab.order_processor.enums.*;
import hu.bme.aut.temalab.order_processor.model.*;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
import hu.bme.aut.temalab.order_processor.model.users.OrderManager;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InitDbService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OrderManagerRepository orderManagerRepository;

    @Transactional
    public void initDb() {
        Customer customer1 = new Customer();
        customer1.setName("Mikorka Kálmán");
        customer1.setEmail("mikorka@example.com");

        OrderManager manager1 = new OrderManager();
        manager1.setName("Golyvás Irén");
        manager1.setEmail("golyvas@example.com");

        Product product1 = new Product();
        product1.setName("Product1");
        product1.setCategory(Category.ELECTRONICS);
        product1.setValue(new BigDecimal(100));

        Component component1 = new Component();
        component1.setName("Component1");
        component1.setCategory(Category.ELECTRONICS);
        component1.setValue(new BigDecimal(100));
        component1.setUnit(Unit.x);
        component1.setProduct(product1);

        Cart cart1 = new Cart();
        cart1.setUser(customer1);
        cart1.setStatus(CartStatus.OPEN);
        cart1.setSubtotal(new BigDecimal(100));

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(cart1);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cart1.addItem(cartItem1);

        Order order1 = new Order();
        order1.setUser(customer1);
        order1.setStatus(OrderStatus.NEW);
        order1.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order1.setShippingMethod(ShippingMethod.STANDARD);

        Address address1 = new Address();
        address1.setUser(customer1);
        address1.setZipCode("12345");
        address1.setCity("City1");
        address1.setStreet("Street1");
        address1.setHouseNumber("1A");

        Coupon coupon1 = new Coupon();
        coupon1.setName("Coupon1");
        coupon1.setTargetCategory(Category.ELECTRONICS);
        coupon1.setValue(10);

        customerRepository.save(customer1);
        orderManagerRepository.save(manager1);
        productRepository.save(product1);
        componentRepository.save(component1);
        cartRepository.save(cart1);
        cartItemRepository.save(cartItem1);
        orderRepository.save(order1);
        addressRepository.save(address1);
        couponRepository.save(coupon1);
    }
}
