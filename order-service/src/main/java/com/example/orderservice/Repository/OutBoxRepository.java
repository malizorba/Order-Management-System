package com.example.orderservice.Repository;

import com.example.orderservice.Entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutBoxRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findBySentFalse();
}
