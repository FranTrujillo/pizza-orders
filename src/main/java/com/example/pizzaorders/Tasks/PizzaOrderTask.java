package com.example.pizzaorders.Tasks;

import com.example.pizzaorders.model.PizzaOrderRequest;
import com.example.pizzaorders.model.inputs.OrderStatus;
import io.littlehorse.sdk.worker.LHTaskMethod;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PizzaOrderTask {

    private final Map<String, PizzaOrderRequest> orderStore = new ConcurrentHashMap<>();

    @LHTaskMethod("create-order")
    public PizzaOrderRequest createOrder(PizzaOrderRequest pizzaOrder) {
        orderStore.put(pizzaOrder.getOrderId(), pizzaOrder);
        System.out.println("New order was successfully created, order: " + pizzaOrder.getOrderId());
        return pizzaOrder;
    }

    @LHTaskMethod("confirm-payment")
    public PizzaOrderRequest confirmPayment(String orderId) {
        updateOrderStatus(orderId, OrderStatus.PAYMENT_CONFIRMED);
        System.out.println("Payment confirmed, order: " + orderId);
        return getPizzaOrder(orderId);
    }

    @LHTaskMethod("prepare-pizza")
    public PizzaOrderRequest preparePizza(String orderId) {
        updateOrderStatus(orderId, OrderStatus.PREPARING);
        System.out.println("Your pizza is on preparation, order: " + orderId);
        return getPizzaOrder(orderId);
    }

    @LHTaskMethod("bake-pizza")
    public PizzaOrderRequest bakePizza(String orderId) {
        updateOrderStatus(orderId, OrderStatus.BAKED);
        System.out.println("Your pizza is on baked process, order: " + orderId);
        return getPizzaOrder(orderId);
    }

    @LHTaskMethod("deliver-pizza")
    public PizzaOrderRequest deliverPizza(String orderId) {
        updateOrderStatus(orderId, OrderStatus.DELIVERED);
        System.out.println("Your pizza was delivered, order: " + orderId);
        return getPizzaOrder(orderId);
    }

    public PizzaOrderRequest getPizzaOrder(String orderId) {
        return orderStore.get(orderId);
    }

    private void updateOrderStatus(String orderId, OrderStatus status) {
        orderStore.get(orderId).setOrderStatus(status);
    }
}
