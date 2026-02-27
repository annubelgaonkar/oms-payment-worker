package dev.anuradha.omspaymentworker.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class OrderCreatedEvent {

    private UUID userId;
    private UUID orderId;
    private BigDecimal amount;
}
