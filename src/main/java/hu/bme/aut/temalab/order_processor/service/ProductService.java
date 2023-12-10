package hu.bme.aut.temalab.order_processor.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hu.bme.aut.temalab.order_processor.repository.CartItemRepository;
import hu.bme.aut.temalab.order_processor.repository.ComponentRepository;
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
    private final ComponentRepository componentRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(String name, Category category, BigDecimal value, List<Component> components) {
        Product product = Product.builder()
                .name(name)
                .category(category)
                .value(value)
                .components(new ArrayList<>())
                .build();
        components.forEach(component -> {
            product.addComponent(component);
            componentRepository.save(component);
        });
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
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            cartItemRepository.deleteByProduct(product);

            componentRepository.deleteByProduct(product);

            productRepository.delete(product);
        } else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }

    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}