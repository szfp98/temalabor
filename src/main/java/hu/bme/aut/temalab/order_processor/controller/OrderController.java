package hu.bme.aut.temalab.order_processor.controller;

import hu.bme.aut.temalab.order_processor.controller.dto.OrderDto;
import hu.bme.aut.temalab.order_processor.controller.dto.OrderStatusDto;
import hu.bme.aut.temalab.order_processor.enums.OrderStatus;
import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getOrders(@RequestParam(required = false) Long id,
                                       @RequestParam(required = false) Long userId) {
        if (id != null) {
            log.debug("Received request to get order by id: {}", id);
            return getOrderById(id);
        } else if (userId != null) {
            log.debug("Received request to get orders for user: {}", userId);
            return getOrdersByUserId(userId);
        } else {
            log.debug("Received request to get all orders");
            return getAllOrders();
        }
    }

    private ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            List<OrderDto> orderDtos = orders.stream()
                    .map(order -> modelMapper.map(order, OrderDto.class))
                    .collect(Collectors.toList());
            log.debug("Retrieved {} orders", orderDtos.size());
            return ResponseEntity.ok(orderDtos);
        } catch (Exception e) {
            log.error("Error fetching orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<OrderDto> getOrderById(Long id) {
        try {
            Optional<Order> orderOptional = orderService.getOrderById(id);
            if (orderOptional.isPresent()) {
                OrderDto orderDto = modelMapper.map(orderOptional.get(), OrderDto.class);
                log.debug("Order found with id: {}", id);
                return ResponseEntity.ok(orderDto);
            } else {
                log.debug("No order found with id: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error fetching order with id " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<List<OrderDto>> getOrdersByUserId(Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            List<OrderDto> orderDtos = orders.stream()
                    .map(order -> modelMapper.map(order, OrderDto.class))
                    .collect(Collectors.toList());
            log.debug("Retrieved {} orders for user {}", orderDtos.size(), userId);
            return ResponseEntity.ok(orderDtos);
        } catch (Exception e) {
            log.error("Error fetching orders for user " + userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrderStatus(@RequestParam Long id, @RequestBody OrderStatusDto statusDto) {
        log.debug("Received request to update order status for id: {}, status: {}", id, statusDto.getStatus());
        try {
            Order order = orderService.updateOrderStatus(id, OrderStatus.valueOf(statusDto.getStatus()));
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            log.debug("Order status updated for id: {}", id);
            return ResponseEntity.ok(orderDto);
        } catch (RuntimeException e) {
            log.error("Error updating order status", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating order status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}