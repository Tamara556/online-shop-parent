
import com.online.store.onlineshopcommon.dto.ProductDto;
import com.online.store.onlineshopcommon.entity.Product;
import com.online.store.onlineshopcommon.repository.ProductRepository;
import com.online.store.onlineshopcommon.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void update_shouldUpdateNonNullFields() {
        ProductDto dto = new ProductDto();
        dto.setName("Updated Name");
        dto.setPrice(99);
        dto.setDescription(null);
        dto.setQty(50);

        Product product = new Product();
        product.setName("Old Name");
        product.setPrice(10);
        product.setDescription("Old Description");
        product.setQty(10);

        productService.update(dto, product);

        assertEquals("Updated Name", product.getName());
        assertEquals(99, product.getPrice());
        assertEquals("Old Description", product.getDescription());
        assertEquals(50, product.getQty());
    }

    @Test
    void getAllProducts_shouldReturnProductList() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }
}
