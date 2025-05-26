package com.online.store.onlineshopcommon.service;


import com.online.store.onlineshopcommon.dto.ProductDto;
import com.online.store.onlineshopcommon.entity.Product;
import com.online.store.onlineshopcommon.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void update(ProductDto request, Product product){
        if (request.getName() != null){
            product.setName(request.getName());
        }
        if (request.getPrice() != null){
            product.setPrice(request.getPrice());
        }
        if (request.getDescription() != null){
            product.setDescription(request.getDescription());
        }
        if (request.getQty() != null){
            product.setQty(request.getQty());
        }
    }
}
