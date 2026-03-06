package dev.anuradha.omspaymentworker.service;

import dev.anuradha.omspaymentworker.dto.PaymentFailedEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {

    private final SqsTemplate sqsTemplate;

    @Value("${sqs.payment-failed-queue}")
    private String queue;

    public void publishPaymentFailed(PaymentFailedEvent event){
        log.warn("Publishing PAYMENT_FAILED event for order {}", event.getOrderId());

        sqsTemplate.send(queue, event);
    }

}
