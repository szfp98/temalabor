package hu.bme.aut.temalab.order_processor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hu.bme.aut.temalab.order_processor.model.Order;
import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.service.OrderService;


@Controller
public class OrderTLController {
    @Autowired
    private OrderService orderService;

     @GetMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getOrdersByUserId(1L);
        model.addAttribute("oders", orders);
        return "orders"; 
    }
}
