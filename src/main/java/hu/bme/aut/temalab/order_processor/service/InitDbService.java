package hu.bme.aut.temalab.order_processor.service;

import hu.bme.aut.temalab.order_processor.enums.*;
import hu.bme.aut.temalab.order_processor.model.*;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
import hu.bme.aut.temalab.order_processor.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitDbService {

    private final UserRepository userRepository;
    private final ComponentRepository componentRepository;
    private final AddressRepository addressRepository;
    private final CouponRepository couponRepository;

    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    @Transactional
    public void initDb() {
        try {
            List<Customer> users = createUsers();
            List<Product> products = createProductsAndComponents();

            for (int i = 0; i < users.size(); i++) {
                Cart cart = cartService.createCart(users.get(i).getId());
                cart = cartService.addItemToCart(cart.getId(), products.get(i).getId(), i + 1);

                String zipCode = "1234" + i;
                String city = "Város " + i;
                String street = "Utca " + i;
                String houseNumber = String.valueOf(i);
                String comment = "Megjegyzés " + i;

                String couponName = "Kupon " + i;
                Category couponCategory = Category.values()[i % Category.values().length];
                int couponValue = 10 * (i + 1);

                Address address = createAddress(users.get(i), zipCode, city, street, houseNumber, comment);
                Coupon coupon = createCoupon(couponName, couponCategory, couponValue);

                Order order = orderService.createOrder(users.get(i).getId(), cart.getId(), address, PaymentMethod.values()[i % PaymentMethod.values().length], ShippingMethod.values()[i % ShippingMethod.values().length]);
                orderService.addCouponToOrder(order.getId(), coupon.getId());
            }

        } catch (Exception e) {
            log.error("Error initializing database: {}", e.getMessage());
        }
    }

    private List<Customer> createUsers() {
        return Arrays.asList(
                userRepository.save(Customer.builder().name("John Doe").email("john.doe@example.com").build()),
                userRepository.save(Customer.builder().name("Jane Doe").email("jane.doe@example.com").build()),
                userRepository.save(Customer.builder().name("Jim Beam").email("jim.beam@example.com").build())
        );
    }

    private List<Product> createProductsAndComponents() {
        Component component1 = componentRepository.save(Component.builder().name("Komponens 1.1").category(Category.ELECTRONICS).value(new BigDecimal(1)).unit(Unit.KG).build());
        Component component2 = componentRepository.save(Component.builder().name("Komponens 1.2").category(Category.ELECTRONICS).value(new BigDecimal(1)).unit(Unit.KG).build());
        Component component3 = componentRepository.save(Component.builder().name("Komponens 2.1").category(Category.BOOKS).value(new BigDecimal(1)).unit(Unit.X).build());
        Component component4 = componentRepository.save(Component.builder().name("Komponens 2.2").category(Category.BOOKS).value(new BigDecimal(1)).unit(Unit.X).build());
        Component component5 = componentRepository.save(Component.builder().name("Komponens 3.1").category(Category.CLOTHING).value(new BigDecimal(1)).unit(Unit.G).build());
        Component component6 = componentRepository.save(Component.builder().name("Komponens 3.2").category(Category.CLOTHING).value(new BigDecimal(1)).unit(Unit.G).build());

        Product product1 = productService.createProduct("Termék 1", Category.ELECTRONICS, new BigDecimal(1000), Arrays.asList(component1, component2));
        Product product2 = productService.createProduct("Termék 2", Category.BOOKS, new BigDecimal(2000), Arrays.asList(component3, component4));
        Product product3 = productService.createProduct("Termék 3", Category.CLOTHING, new BigDecimal(3000), Arrays.asList(component5, component6));

        return Arrays.asList(product1, product2, product3);
    }

    private Address createAddress(Customer user, String zipCode, String city, String street, String houseNumber, String comment) {
        return addressRepository.save(Address.builder()
                .user(user)
                .zipCode(zipCode)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .comment(comment)
                .build());
    }

    private Coupon createCoupon(String name, Category targetCategory, int value) {
        return couponRepository.save(Coupon.builder()
                .name(name)
                .targetCategory(targetCategory)
                .value(value)
                .build());
    }

}