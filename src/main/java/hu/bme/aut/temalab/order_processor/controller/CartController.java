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
    public ResponseEntity<Set<CartItemDto>> getCartContent(@RequestParam Long userId) {
        try{
            Set<CartItem> cartContent = cartService.getCartContent(userId);
            Set<CartItemDto> cid = cartContent.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toSet());
            return ResponseEntity.ok(cid);

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<CartItemDto> getCartById(Long id) {
        try{
            Optional<Cart> ci = cartService.getCartbyId(id);
            if(ci.isPresent()){
                CartItemDto cid = modelMapper.map(ci.get(), CartItemDto.class);
                return ResponseEntity.ok(cid);
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    private ResponseEntity<CartDto> addItemtToCart(Long Id, Long pId, Integer qty) {
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
    private ResponseEntity<CartDto> removeItemFromCart(Long cId, Long cartItemId){
        try{
            cartService.removeItemFromCart(cId, cartItemId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
