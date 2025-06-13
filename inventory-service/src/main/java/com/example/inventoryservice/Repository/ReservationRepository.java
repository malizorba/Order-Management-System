package com.example.inventoryservice.Repository;

import com.example.inventoryservice.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {


    List<ReservationEntity> findByExpiresAtBefore(Instant time);


}
