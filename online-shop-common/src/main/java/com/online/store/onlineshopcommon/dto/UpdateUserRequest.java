package com.online.store.onlineshopcommon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private Integer id;
    private String username;
    private String email;
}
