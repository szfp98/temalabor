package hu.bme.aut.temalab.order_processor.service;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {


	@Transactional(readOnly = true)
	public List<Product> getCartContent()
	
}
