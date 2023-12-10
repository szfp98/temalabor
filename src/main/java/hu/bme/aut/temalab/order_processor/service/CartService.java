package hu.bme.aut.temalab.order_processor.service;
import hu.bme.aut.temalab.order_processor.enums.CartStatus;
import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.Address;
import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.CartItem;
import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.model.users.Customer;
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (!(user instanceof Customer)) {
            throw new RuntimeException("User with id: " + userId + " is not a customer");
        }

        Cart cart = Cart.builder()
                .status(CartStatus.OPEN)
                .user(user)
                .cartItems(new HashSet<>())
                .subtotal(BigDecimal.ZERO)
                .build();

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addItemToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        CartItem newItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        cart.addItem(newItem);
        cart.setSubtotal(calculateSubtotal(cartId));

        cartItemRepository.save(newItem);
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        CartItem itemToRemove = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));

        cart.removeItem(itemToRemove);
        cart.setSubtotal(calculateSubtotal(cartId));

        cartItemRepository.delete(itemToRemove);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> getCartbyId(Long id) {
        Optional<Cart> cartOpt = cartRepository.findById(id);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            Hibernate.initialize(cart.getCartItems());
            for (CartItem item : cart.getCartItems()) {
                Hibernate.initialize(item.getProduct());
                log.info("Product loaded: " + item.getProduct().getName());
            }
            log.info("Cart loaded with items: " + cart.getCartItems().size());
            return Optional.of(cart);
        }
        return cartOpt;
    }


    @Transactional
    public Cart updateCartStatus(Long cartId, CartStatus newStatus) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        cart.setStatus(newStatus);
        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public Set<CartItem> getCartContent(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        Hibernate.initialize(cart.getCartItems());
        return cart.getCartItems();
    }

    private BigDecimal calculateSubtotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            subtotal = subtotal.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return subtotal;
    }
  
}