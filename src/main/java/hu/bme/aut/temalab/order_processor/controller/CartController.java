package hu.bme.aut.temalab.order_processor.controller;

import hu.bme.aut.temalab.order_processor.controller.dto.CartDto;
import hu.bme.aut.temalab.order_processor.controller.dto.CartItemDto;
import hu.bme.aut.temalab.order_processor.controller.dto.OrderDto;
import hu.bme.aut.temalab.order_processor.model.Cart;
import hu.bme.aut.temalab.order_processor.model.CartItem;
import hu.bme.aut.temalab.order_processor.service.CartService;
import hu.bme.aut.temalab.order_processor.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam(required = false) Long userId,
                                     @RequestParam(required = false) Long id) {
        try {
            if (userId != null) {
                return getCartContentForUser(userId);
            } else if (id != null) {
                return getCartById(id);
            } else {
                log.error("No valid parameters provided for cart retrieval");
                return ResponseEntity.badRequest().body("Missing or invalid parameters");
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    private ResponseEntity<CartDto> addItemtToCart(@RequestParam Long Id, @RequestParam Long pId, @RequestParam Integer qty) {
        try {
            if(qty > 0){
                Cart cart = cartService.addItemToCart(Id, pId, qty);
                CartDto cartDto = modelMapper.map(cart, CartDto.class);
                return ResponseEntity.ok(cartDto);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    private ResponseEntity<CartDto> removeItemFromCart(@RequestParam Long cId, @RequestParam Long cartItemId){
        try{
            cartService.removeItemFromCart(cId, cartItemId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<Set<CartItemDto>> getCartContentForUser(Long userId) {
        Set<CartItem> cartContent = cartService.getCartContent(userId);
        Set<CartItemDto> cartItemDtos = cartContent.stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(cartItemDtos);
    }

    private ResponseEntity<CartDto> getCartById(Long id) {
        Optional<Cart> cart = cartService.getCartbyId(id);
        if (cart.isPresent()) {
            CartDto cartDto = modelMapper.map(cart.get(), CartDto.class);
            return ResponseEntity.ok(cartDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}