package hu.bme.aut.temalab.order_processor.controller;

import hu.bme.aut.temalab.order_processor.enums.PaymentMethod;
import hu.bme.aut.temalab.order_processor.enums.ShippingMethod;
import hu.bme.aut.temalab.order_processor.model.Address;
import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import hu.bme.aut.temalab.order_processor.service.CartService;
import hu.bme.aut.temalab.order_processor.service.OrderService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CartTLController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/cart")
    public String getCartContent(Model model, Long userId) {
        Set<CartItem> cartItems = cartService.getCartContent(1L);
        model.addAttribute("cartitems", cartItems);
        return "cart_content";
    }

    @PostMapping("/createorder")
    public String createOrder() {
        orderService.createOrder(1L, 1L, new Address(), PaymentMethod.CASH, ShippingMethod.STANDARD);
        return "redirect:/cart";
    }
}
