package com.example.userservice.Controller;

import com.example.userservice.DTO.Request.FavoriteProductRequest;
import com.example.userservice.DTO.Response.FavoriteProductResponse;
import com.example.userservice.Entity.FavoriteProductEntity;
import com.example.userservice.Service.IFavoriteProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteProductController {

    private final IFavoriteProductService favoriteProductService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FavoriteProductResponse> addFavorite(Authentication authentication, @RequestBody FavoriteProductRequest request) {
        // Retrieve the logged-in user's id from authentication (assumes getName() returns a UUID string)
        UUID userId = UUID.fromString(authentication.getName());
        FavoriteProductResponse response=favoriteProductService.addFavorite(userId, request.getProductId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
