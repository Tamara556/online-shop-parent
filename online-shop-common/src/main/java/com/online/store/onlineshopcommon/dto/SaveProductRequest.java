package com.online.store.onlineshopcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductRequest {

    //@NotEmpty(message = "Title can't be empty")
//    @Size(min = 2, message = "Title's length should be > 2")
    private String name;

    //@Min(value = 1, message = "price should be >= 1")
    private int price;
    private String description;
    private int qty;
}
