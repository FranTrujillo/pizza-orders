package com.example.pizzaorders.controller;

import com.example.pizzaorders.model.PizzaOrderRequest;
import com.example.pizzaorders.model.PizzaOrderResponse;
import com.example.pizzaorders.model.inputs.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.common.proto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Pizza Orders")
public class OrderController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LHConfig config;

    public OrderController(LHConfig config) {
        this.config = config;
    }

    @Operation(summary = "Create a new pizza order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza workflow was successful", content = @Content(schema = @Schema(implementation = PizzaOrderResponse.class))),
    })
    @PostMapping("/create-order")
    public PizzaOrderResponse createOrder(@RequestBody PizzaOrderRequest pizzaOrder) {
        try {
            String orderId = UUID.randomUUID().toString();
            pizzaOrder.setOrderId(orderId);
            pizzaOrder.setOrderStatus(OrderStatus.CREATED);
            String orderJson = objectMapper.writeValueAsString(pizzaOrder);

            VariableValue orderVariable = VariableValue.newBuilder()
                    .setJsonObj(orderJson)
                    .build();

            RunWfRequest request = RunWfRequest.newBuilder()
                    .setWfSpecName("pizza-orders")
                    .putVariables("order", orderVariable)
                    .build();

            config.getBlockingStub().runWf(request);

            return new PizzaOrderResponse("Pizza workflow started with OrderID: " + orderId, pizzaOrder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start pizza workflow: " + e.getMessage());
        }
    }
}
