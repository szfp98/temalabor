package hu.bme.aut.temalab.order_processor.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import hu.bme.aut.temalab.order_processor.enums.Category;
import hu.bme.aut.temalab.order_processor.model.Component;
import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.Arrays;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> mockProducts = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository).findAll();
    }

    @Test
    public void testCreateProduct() {
        String name = "Test Product";
        Category category = Category.CLOTHING;
        BigDecimal value = BigDecimal.valueOf(100.0);
        List<Component> components = Arrays.asList(new Component(), new Component()); // Initialize as needed

        Product productToCreate = new Product();
        productToCreate.setName(name);
        productToCreate.setCategory(category);
        productToCreate.setValue(value);
        productToCreate.setComponents(components);

        when(productRepository.save(any(Product.class))).thenReturn(productToCreate);

        Product createdProduct = productService.createProduct(name, category, value, components);

        assertNotNull(createdProduct);
        assertEquals(name, createdProduct.getName());
        assertEquals(category, createdProduct.getCategory());
        assertEquals(value, createdProduct.getValue());
        assertEquals(components, createdProduct.getComponents());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void testGetProductByIdFound() {
        Product expectedProduct = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(expectedProduct, result.get());
        verify(productRepository).findById(1L);
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(1L);

        assertFalse(result.isPresent());
        verify(productRepository).findById(1L);
    }

    @Test
    public void testDeleteProduct() {
        long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

}
