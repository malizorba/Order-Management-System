package com.example.userservice.Service;

import com.example.userservice.DTO.Response.FavoriteProductResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface IFavoriteProductService {

    FavoriteProductResponse addFavorite(UUID userId, UUID productId);
}
