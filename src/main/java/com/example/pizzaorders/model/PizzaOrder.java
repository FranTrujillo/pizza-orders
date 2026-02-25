package com.example.pizzaorders.model;

import com.example.pizzaorders.model.inputs.OrderStatus;
import com.example.pizzaorders.model.inputs.PizzaSizes;
import com.example.pizzaorders.model.inputs.PizzaTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaOrder {
    private String orderId;
    private PizzaTypes pizzaType;
    private PizzaSizes pizzaSize;
    private OrderStatus orderStatus;
}
