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

       log.info("Received payment for order {}", event.getOrderId());

       //Simulate payment success
        Order order = orderRepository.findById(event.getOrderId())
                        .orElseThrow();

        order.setStatus(OrderStatus.PAID);

        orderRepository.save(order);

        log.info("Order {} marked as PAID", event.getOrderId());

    }
}
