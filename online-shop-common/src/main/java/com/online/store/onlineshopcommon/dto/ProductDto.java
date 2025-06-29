package com.online.store.onlineshopcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private Integer price;
    private String description;
    private Integer qty;
    private String image;
}
