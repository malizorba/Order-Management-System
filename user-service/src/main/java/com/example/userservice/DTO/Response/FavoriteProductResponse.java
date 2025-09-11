package com.example.userservice.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FavoriteProductResponse {
    private UUID productId;
}
