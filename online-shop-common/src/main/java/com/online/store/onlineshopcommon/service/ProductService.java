package com.online.store.onlineshopcommon.service;

import com.online.store.onlineshopcommon.dto.ProductDto;
import com.online.store.onlineshopcommon.entity.Product;
import com.online.store.onlineshopcommon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Path.of;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Value("${images.upload.path}")
    public String imageUploadPath;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void update(ProductDto request, Product product) {
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getQty() != null) {
            product.setQty(request.getQty());
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public byte[] getImage(String fileName) {
        var imagePath = of(imageUploadPath).resolve(fileName);
        try {
            return readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file: " + fileName);
        }
    }
}
