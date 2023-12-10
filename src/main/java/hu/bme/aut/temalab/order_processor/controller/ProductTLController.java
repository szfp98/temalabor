package hu.bme.aut.temalab.order_processor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
public class ProductTLController {

    @Autowired
    private ProductService productService; 

    @GetMapping("/")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index"; 
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> searchResults = productService.searchProductsByName(query);
        model.addAttribute("products", searchResults);
        return "index"; 
    }
}

