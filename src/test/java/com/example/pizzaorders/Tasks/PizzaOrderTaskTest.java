package com.example.pizzaorders.Tasks;

import com.example.pizzaorders.model.PizzaOrder;
import com.example.pizzaorders.model.inputs.OrderStatus;
import com.example.pizzaorders.model.inputs.PizzaSizes;
import com.example.pizzaorders.model.inputs.PizzaTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaOrderTaskTest {

    private PizzaOrderTask pizzaOrderTask;

    @BeforeEach
    void setUp() {
        pizzaOrderTask = new PizzaOrderTask();
    }

    PizzaOrder order = PizzaOrder.builder()
            .orderId(UUID.randomUUID().toString())
            .pizzaType(PizzaTypes.PEPPERONI)
            .pizzaSize(PizzaSizes.MEDIUM)
            .build();

    @Nested
    @DisplayName("create-order")
    class CreateOrder {
        @Test
        void shouldCreateNewOrder() {
            PizzaOrder result = pizzaOrderTask.createOrder(order);
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.CREATED);
        }
    }

    @Nested
    @DisplayName("confirm-payment")
    class ConfirmPayment {
        @Test
        void shouldConfirmPayment() {
            pizzaOrderTask.createOrder(order);
            PizzaOrder result = pizzaOrderTask.confirmPayment(order.getOrderId());
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PAYMENT_CONFIRMED);
        }
    }

    @Nested
    @DisplayName("prepare-pizza")
    class PreparePizza {
        @Test
        void shouldPreparePizza() {
            pizzaOrderTask.createOrder(order);
            PizzaOrder result = pizzaOrderTask.preparePizza(order.getOrderId());
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PREPARING);
        }
    }

    @Nested
    @DisplayName("bake-pizza")
    class BakePizza {
        @Test
        void shouldBakedPizza() {
            pizzaOrderTask.createOrder(order);
            PizzaOrder result = pizzaOrderTask.bakePizza(order.getOrderId());
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.BAKED);
        }
    }

    @Nested
    @DisplayName("deliver-pizza")
    class DeliverPizza {
        @Test
        void shouldDeliverPizza() {
            pizzaOrderTask.createOrder(order);
            PizzaOrder result = pizzaOrderTask.deliverPizza(order.getOrderId());
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
        }
    }

    @Nested
    @DisplayName("workflow")
    class OrderWorkflow {
        @Test
        void shouldFollowAllWorkflow() {
            assertThat(pizzaOrderTask.createOrder(order).getOrderStatus())
                    .isEqualTo(OrderStatus.CREATED);

            assertThat(pizzaOrderTask.confirmPayment(order.getOrderId()).getOrderStatus())
                    .isEqualTo(OrderStatus.PAYMENT_CONFIRMED);

            assertThat(pizzaOrderTask.preparePizza(order.getOrderId()).getOrderStatus())
                    .isEqualTo(OrderStatus.PREPARING);

            assertThat(pizzaOrderTask.bakePizza(order.getOrderId()).getOrderStatus())
                    .isEqualTo(OrderStatus.BAKED);

            assertThat(pizzaOrderTask.deliverPizza(order.getOrderId()).getOrderStatus())
                    .isEqualTo(OrderStatus.DELIVERED);

        }
    }
}
