package com.example.userservice.Service.Implementation;

import com.example.userservice.DTO.Response.FavoriteProductResponse;
import com.example.userservice.Entity.FavoriteProductEntity;
import com.example.userservice.Repository.FavoritePRoductRepository;
import com.example.userservice.Service.IFavoriteProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FavoriteProductImpl implements IFavoriteProductService {

   private final  FavoritePRoductRepository favoritePRoductRepository;
    @Override
    public FavoriteProductResponse addFavorite(UUID userId, UUID productId) {
        boolean exist=favoritePRoductRepository.existsByUserIdAndProductId(userId, productId);
        if(!exist){
            FavoriteProductEntity entity=new FavoriteProductEntity();
            entity.setUserId(userId);
            entity.setProductId(productId);
            favoritePRoductRepository.save(entity);
        }
        return new FavoriteProductResponse(productId);
    }
    public List<UUID> getUserIdsByProductId(UUID productId) {
        return favoritePRoductRepository.findAllByProductId(productId).stream()
                .map(FavoriteProductEntity::getUserId)
                .distinct()
                .toList();
    }
}
