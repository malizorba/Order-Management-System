package com.example.inventoryservice.Entity;

import com.example.common.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "inventory_items")
public class InventoryItemEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private UUID productId;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private int reservedQuantity = 0;
}
