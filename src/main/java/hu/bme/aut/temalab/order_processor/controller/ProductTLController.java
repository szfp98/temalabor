package hu.bme.aut.temalab.order_processor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
public class ProductTLController {

    @Autowired
    private ProductService productService; 

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index"; 
    }
}

