package com.example.pizzaorders.model;

import com.example.pizzaorders.model.inputs.OrderStatus;
import com.example.pizzaorders.model.inputs.PizzaSizes;
import com.example.pizzaorders.model.inputs.PizzaTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pizza Order Model")
public class PizzaOrder {

    @Schema(description = "Identifier of the order (UUID)", example = "a6b611e7-c230-4300-b35d-a45c21392433", accessMode = Schema.AccessMode.READ_ONLY)
    private String orderId;

    @Schema(description = "Type of pizza", example = "PEPPERONI", requiredMode = Schema.RequiredMode.REQUIRED)
    private PizzaTypes pizzaType;

    @Schema(description = "Size of the pizza", example = "LARGE", requiredMode = Schema.RequiredMode.REQUIRED)
    private PizzaSizes pizzaSize;

    @Schema(description = "Status of the order", example = "CREATED", accessMode = Schema.AccessMode.READ_ONLY)
    private OrderStatus orderStatus;
}
