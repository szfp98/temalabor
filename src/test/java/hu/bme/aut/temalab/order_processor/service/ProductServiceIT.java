package hu.bme.aut.temalab.order_processor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.bme.aut.temalab.order_processor.enums.Category;
import hu.bme.aut.temalab.order_processor.model.Component;
import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.repository.ProductRepository;
import jakarta.transaction.Transactional;

@SpringBootTest(properties = { "spring.config.name=application-test" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductServiceIT {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setCategory(Category.ELECTRONICS);
        testProduct.setValue(new BigDecimal("10.00"));
        productRepository.save(testProduct);

    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    @Transactional
    void getAllOrdersTest() {
        List<Product> products = productService.getAllProducts();
        assertFalse(products.isEmpty(), "A rendelések listája nem lehet üres");
        assertTrue(products.contains(testProduct), "A létrehozott rendelésnek benne kell lennie a listában");
    }

    @Test
    @Transactional
    void createProductTest() {
        Category testCategory = Category.ELECTRONICS;
        BigDecimal testValue = new BigDecimal("20.00");
        List<Component> testComponents = new ArrayList<>();

        Product createdProduct = productService.createProduct("New Test Product", testCategory, testValue,
                testComponents);

        assertNotNull(createdProduct, "The created product should not be null");
        assertNotNull(createdProduct.getId(), "The created product should have an ID");

        Optional<Product> foundProduct = productRepository.findById(createdProduct.getId());
        assertTrue(foundProduct.isPresent(), "The product should be found in the database");
        assertEquals("New Test Product", foundProduct.get().getName(), "The product name should match");
        assertEquals(testValue, foundProduct.get().getValue(), "The product value should match");
    }

    @Test
    @Transactional
    void updateProductTest() {
        String updatedName = "Updated Product";
        Category updatedCategory = Category.CLOTHING;
        BigDecimal updatedValue = new BigDecimal("20.00");
        List<Component> updatedComponents = new ArrayList<>();

        Product updatedProduct = productService.updateProduct(testProduct.getId(), updatedName, updatedCategory,
                updatedValue, updatedComponents);

        assertNotNull(updatedProduct, "The updated product should not be null");
        assertEquals(updatedName, updatedProduct.getName(), "The product name should be updated");
        assertEquals(updatedCategory, updatedProduct.getCategory(), "The product category should be updated");
        assertEquals(updatedValue, updatedProduct.getValue(), "The product value should be updated");

        Optional<Product> fetchedProduct = productRepository.findById(testProduct.getId());
        assertTrue(fetchedProduct.isPresent(), "The updated product should exist in the database");
        assertEquals(updatedName, fetchedProduct.get().getName(),
                "The fetched product name should match the updated name");
        assertEquals(updatedCategory, fetchedProduct.get().getCategory(),
                "The fetched product category should match the updated category");
        assertEquals(updatedValue, fetchedProduct.get().getValue(),
                "The fetched product value should match the updated value");
    }

    @Test
    @Transactional
    void deleteProductTest() {
        productService.deleteProduct(testProduct.getId());
        Optional<Product> deletedProduct = productRepository.findById(testProduct.getId());
        assertFalse(deletedProduct.isPresent(), "The product should no longer exist in the database");
    }

    @Test
    @Transactional
    void searchProductsByNameTest() {
        // Ezt kellene megtalálnia a search-nek
        Product productForSearch1 = new Product();
        productForSearch1.setName("Search Test Product A");
        productForSearch1.setCategory(Category.ELECTRONICS);
        productForSearch1.setValue(new BigDecimal("30.00"));
        productRepository.save(productForSearch1);
        // Ezt nem kéne megtalálni
        Product nonMatchingProduct = new Product();
        nonMatchingProduct.setName("NonMatching Product");
        nonMatchingProduct.setCategory(Category.CLOTHING);
        nonMatchingProduct.setValue(new BigDecimal("50.00"));
        productRepository.save(nonMatchingProduct);

        String searchKeyword = "Search Test";
        List<Product> searchResults = productService.searchProductsByName(searchKeyword);

        assertFalse(searchResults.isEmpty(), "The search result should not be empty");
        assertTrue(searchResults.contains(productForSearch1), "The search result should contain productForSearch1");
        assertFalse(searchResults.contains(nonMatchingProduct),
                "The search result should not contain nonMatchingProduct");
    }

}
