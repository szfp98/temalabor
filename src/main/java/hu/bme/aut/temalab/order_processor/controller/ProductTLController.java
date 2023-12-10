package hu.bme.aut.temalab.order_processor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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

    @PostMapping("/create")
    public String createProduct(@RequestParam String name, 
                                @RequestParam BigDecimal value, 
                                @RequestParam String category) {
        productService.createProduct(name, null, value, null);
        return "redirect:/";
    }

    @PostMapping("/delete")
public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
        productService.deleteProduct(id);
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the product: " + e.getMessage());
        return "redirect:/"; 
    }
    return "redirect:/";
}

}

