package com.example.pizzaorders.controller;

import com.example.pizzaorders.model.PizzaOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.common.proto.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LHConfig config;

    public OrderController(LHConfig config) {
        this.config = config;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestBody PizzaOrder pizzaOrder) {
        try {
            String orderId = UUID.randomUUID().toString();
            pizzaOrder.setOrderId(orderId);
            String orderJson = objectMapper.writeValueAsString(pizzaOrder);

            VariableValue orderVariable = VariableValue.newBuilder()
                    .setJsonObj(orderJson)
                    .build();

            RunWfRequest request = RunWfRequest.newBuilder()
                    .setWfSpecName("pizza-orders")
                    .putVariables("order", orderVariable)
                    .build();

            config.getBlockingStub().runWf(request);

            return "Pizza workflow started with OrderID: " + orderId;
        } catch (Exception e) {
            return "Error starting workflow: " + e.getMessage();
        }
    }
}
