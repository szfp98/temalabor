package hu.bme.aut.temalab.order_processor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.aut.temalab.order_processor.enums.Category;
import hu.bme.aut.temalab.order_processor.model.Component;
import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(String name, Category category, BigDecimal value, List<Component> components) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setValue(value);
        product.setComponents(components);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product updateProduct(long id, String name, Category category, BigDecimal value,
            List<Component> components) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(name);
            product.setCategory(category);
            product.setValue(value);
            product.setComponents(components);
            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }

    @Transactional
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}
