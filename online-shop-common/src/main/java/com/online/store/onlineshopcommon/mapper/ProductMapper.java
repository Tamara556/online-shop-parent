package com.online.store.onlineshopcommon.mapper;


import com.online.store.onlineshopcommon.dto.ProductDto;
import com.online.store.onlineshopcommon.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}
