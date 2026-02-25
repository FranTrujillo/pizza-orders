package com.example.pizzaorders;

import com.example.pizzaorders.Tasks.PizzaOrderTask;
import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.wfsdk.WfRunVariable;
import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.internal.WorkflowImpl;
import io.littlehorse.sdk.worker.LHTaskWorker;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import static com.example.pizzaorders.config.config.getWorkflow;

@SpringBootApplication
@Component
public class PizzaOrdersApplication {

    static PizzaOrderTask tasks = new PizzaOrderTask();
    static LHConfig config;

    @PostConstruct
    public void registerWorkflowAndTasks() {
        try {
            config = new LHConfig();

            LHTaskWorker createOrder = new LHTaskWorker(tasks, "create-order", config);
            createOrder.registerTaskDef();

            LHTaskWorker confirmPayment = new LHTaskWorker(tasks, "confirm-payment", config);
            confirmPayment.registerTaskDef();

            LHTaskWorker preparePizza = new LHTaskWorker(tasks, "prepare-pizza", config);
            preparePizza.registerTaskDef();

            LHTaskWorker bakePizza = new LHTaskWorker(tasks, "bake-pizza", config);
            bakePizza.registerTaskDef();

            LHTaskWorker deliverPizza = new LHTaskWorker(tasks, "deliver-pizza", config);
            deliverPizza.registerTaskDef();

            Runtime.getRuntime().addShutdownHook(new Thread(createOrder::close));
            Runtime.getRuntime().addShutdownHook(new Thread(confirmPayment::close));
            Runtime.getRuntime().addShutdownHook(new Thread(preparePizza::close));
            Runtime.getRuntime().addShutdownHook(new Thread(bakePizza::close));
            Runtime.getRuntime().addShutdownHook(new Thread(deliverPizza::close));
            createOrder.start();
            confirmPayment.start();
            preparePizza.start();
            bakePizza.start();
            deliverPizza.start();

            Workflow workflow = getWorkflow();
            workflow.registerWfSpec(config.getBlockingStub());

            System.out.println("Workflow registered");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PizzaOrdersApplication.class, args);
    }


}
