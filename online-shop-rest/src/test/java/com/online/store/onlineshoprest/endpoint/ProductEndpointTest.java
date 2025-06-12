package com.online.store.onlineshoprest.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.onlineshopcommon.dto.ProductDto;
import com.online.store.onlineshopcommon.dto.SaveProductRequest;
import com.online.store.onlineshopcommon.entity.Product;
import com.online.store.onlineshopcommon.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})

    void testGetAllProducts() throws Exception {
        Product product = Product.builder()
                .name("tshirt")
                .description("asdygasd")
                .price(300)
                .qty(12)
                .build();

        productRepository.save(product);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("tshirt"))
                .andExpect(jsonPath("$[0].price").value(300));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateProduct() throws Exception {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("tshirt");
        request.setPrice(300);
        request.setDescription("asdygasd");
        request.setQty(12);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("tshirt"))
                .andExpect(jsonPath("$.price").value(300));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateProduct() throws Exception {
        Product product = Product.builder()
                .name("shirt")
                .description("old")
                .price(200)
                .qty(5)
                .build();

        product = productRepository.save(product);

        ProductDto updateDto = new ProductDto();
        updateDto.setName("tshirt");
        updateDto.setPrice(300);
        updateDto.setDescription("asdygasd");
        updateDto.setQty(12);

        mockMvc.perform(put("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("tshirt"))
                .andExpect(jsonPath("$.price").value(300));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDeleteProduct() throws Exception {
        Product product = Product.builder()
                .name("tshirt")
                .description("asdygasd")
                .price(300)
                .qty(12)
                .build();

        product = productRepository.save(product);

        mockMvc.perform(delete("/products/" + product.getId()))
                .andExpect(status().isNoContent());
    }
}