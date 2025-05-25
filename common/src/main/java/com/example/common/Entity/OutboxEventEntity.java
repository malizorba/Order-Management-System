package com.example.common.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "outbox_event")
public class OutboxEventEntity extends BaseEntity {
    private String aggregateId;
    private String aggregateType;
    private String eventType;
    private String payload;

    private boolean sent;
}
