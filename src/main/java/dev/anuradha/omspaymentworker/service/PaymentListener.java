package dev.anuradha.omspaymentworker.service;

import dev.anuradha.omspaymentworker.dto.OrderCreatedEvent;
import dev.anuradha.omspaymentworker.model.OrderStatus;
import dev.anuradha.omspaymentworker.repository.OrderRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import dev.anuradha.omspaymentworker.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentListener {

    private final OrderRepository orderRepository;

    @SqsListener("${sqs.order-created-queue}")
    public void listen(OrderCreatedEvent event){

        log.info("Processing payment for order {}", event.getOrderId());

        boolean paymentSuccess = Math.random() > 0.5;

        Order order = orderRepository.findById(event.getOrderId())
                .orElse(null);

        if(order == null){
            log.warn("Order {} not found yet. Will retry.", event.getOrderId());
            throw new RuntimeException("Order not found yet");
        }

        if (paymentSuccess) {
            order.setStatus(OrderStatus.PAID);
            log.info("Payment SUCCESS for order {}", order.getId());
        } else {
            order.setStatus(OrderStatus.FAILED);
            log.warn("Payment FAILED for order {}", order.getId());
        }

        orderRepository.save(order);

    }
}
