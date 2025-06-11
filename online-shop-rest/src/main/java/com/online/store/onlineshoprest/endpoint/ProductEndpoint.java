package com.online.store.onlineshoprest.endpoint;


import com.online.store.onlineshopcommon.dto.ProductDto;
import com.online.store.onlineshopcommon.dto.SaveProductRequest;
import com.online.store.onlineshopcommon.entity.Product;
import com.online.store.onlineshopcommon.mapper.ProductMapper;
import com.online.store.onlineshopcommon.repository.ProductRepository;
import com.online.store.onlineshopcommon.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Slf4j
public class ProductEndpoint {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductService productService;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(@RequestParam(required = false, defaultValue = "", name = "sort")String sortBy){
        if (!Objects.equals("name", sortBy)){
            sortBy = "name";
        }
        return productRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody SaveProductRequest saveProductRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var product = Product.builder()
                .name(saveProductRequest.getName())
                .price(saveProductRequest.getPrice())
                .description(saveProductRequest.getDescription())
                .qty(saveProductRequest.getQty())
                .build();

        productRepository.save(product);

        var productDto = productMapper.toDto(product);

        var uri = uriBuilder.path("/products/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto request
    ){
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productService.update(request, product);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }


}
