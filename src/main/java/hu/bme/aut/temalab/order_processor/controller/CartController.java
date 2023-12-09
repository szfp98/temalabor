package hu.bme.aut.temalab.order_processor.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Set<CartItemDto>> getCartContent(Long userId) {
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
                return ResponseEntity.notFound().build()
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    

}
