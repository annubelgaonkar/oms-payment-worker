package dev.anuradha.omspaymentworker.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {

    private UUID orderId;
    private UUID productId;
    private Integer quantity;
}
