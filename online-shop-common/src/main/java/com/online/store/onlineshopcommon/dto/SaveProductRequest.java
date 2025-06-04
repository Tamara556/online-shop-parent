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

    private String name;


    private int price;
    private String description;
    private int qty;
}
