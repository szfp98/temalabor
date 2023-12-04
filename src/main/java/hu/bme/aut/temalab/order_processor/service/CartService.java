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
import hu.bme.aut.temalab.order_processor.model.users.User;
import hu.bme.aut.temalab.order_processor.repository.CartRepository;
import hu.bme.aut.temalab.order_processor.repository.OrderRepository;
import hu.bme.aut.temalab.order_processor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
	
	private final CartRepository cartRepository;
	private final UserRepository userRepository;

  @Transactional(readOnly = true)
	public Set<CartItem> getCartContent(Long userID){
	  return cartRepository.findByUserId(userID).get().getContent();
  }


  @Transactional
  public Optional<Cart> getCartbyId(Long id){
	  return cartRepository.findById(id);
  }

  @Transactional
  public void addItem(CartItem ci, Long userID) {
	  cartRepository.findByUserId(userID).get().addItem(ci);
  }

  @Transactional
  public void removeItem(CartItem ci, Long userID) {
	  cartRepository.findByUserId(userID).get().removeItem(ci);
  }

  @Transactional
  public Cart createCart(Long id, User user) {
	  
	  User userExists = userRepository.findById(user.getId())
              .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	  
	  Cart cart = new Cart();
	  cart.setId(userExists.getId());
	  cart.setStatus(CartStatus.OPEN);
	  cart.setSubtotal(new BigDecimal(0));
	  cart.setUser(user);
	  
	  return cartRepository.save(cart);
	  
  }
  
}
