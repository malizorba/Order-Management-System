package com.example.common.OutboxEvent.Repository;

import com.example.common.Entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findBySentFalse();

    @Transactional
    @Modifying
    @Query("DELETE FROM OutboxEventEntity o WHERE o.sent = true AND o.createdAt < :threshold")
    int deleteBySentTrueAndCreatedAtBefore(Instant threshold);
}
