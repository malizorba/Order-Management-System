package com.example.userservice.Entity;

import com.example.common.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "favorite_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteProductEntity extends BaseEntity {

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID productId;
}
