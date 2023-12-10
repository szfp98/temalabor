package hu.bme.aut.temalab.order_processor.controller;

import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import hu.bme.aut.temalab.order_processor.service.CartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class CartTLController {

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String getCartContent(Model model, Long userId){
        Set<CartItem> cartItems = cartService.getCartContent(userId);
        model.addAttribute("cartitems", cartItems);
        return "cart_content";
    }

    @GetMapping("/")
    public String getSubTotal(Model model, Long userId){
        BigDecimal subTotal = cartService.getCartbyId(userId).get().getSubtotal();
        model.addAttribute("subtotal", subTotal);
        return "cart_content";
    }

    @GetMapping("/")
    public String getCart(Model model, Long Id){
        Optional<Cart> cart = cartService.getCartbyId(Id);
        model.addAttribute("cart", cart);
        return "cart_content";
    }




}
