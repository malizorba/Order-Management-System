package com.example.userservice.Repository;

import com.example.userservice.Entity.FavoriteProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoritePRoductRepository extends JpaRepository<FavoriteProductEntity, UUID> {
    boolean existsByUserIdAndProductId(UUID userId, UUID productId);

    List<FavoriteProductEntity> findAllByProductId(UUID productId);
}
