package hu.bme.aut.temalab.order_processor.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import hu.bme.aut.temalab.order_processor.model.Product;
import hu.bme.aut.temalab.order_processor.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
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
