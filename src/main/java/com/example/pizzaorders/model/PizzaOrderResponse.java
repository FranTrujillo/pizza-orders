
package com.example.pizzaorders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaOrderResponse {

    private String message;
    private PizzaOrderRequest pizzaOrder;
}