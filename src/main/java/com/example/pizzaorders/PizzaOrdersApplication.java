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

            LHTaskWorker[] workers = {
                    new LHTaskWorker(tasks, "create-order", config),
                    new LHTaskWorker(tasks, "confirm-payment", config),
                    new LHTaskWorker(tasks, "prepare-pizza", config),
                    new LHTaskWorker(tasks, "bake-pizza", config),
                    new LHTaskWorker(tasks, "deliver-pizza", config)
            };

            for (LHTaskWorker worker : workers) {
                worker.registerTaskDef();
                Runtime.getRuntime().addShutdownHook(new Thread(worker::close));
                worker.start();
            }

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
