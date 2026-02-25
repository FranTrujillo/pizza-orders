package com.example.pizzaorders.config;

import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.wfsdk.WfRunVariable;
import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.internal.WorkflowImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    @Bean
    public LHConfig lhConfig() {
        return new LHConfig();
    }

    public static Workflow getWorkflow() {
        return new WorkflowImpl("pizza-orders", wf -> {

            WfRunVariable pizzaOrder = wf.declareJsonObj("order");
            WfRunVariable orderId = wf.declareStr("orderId");

            var createOrderOutput = wf.execute("create-order", pizzaOrder);
            orderId.assign(createOrderOutput.jsonPath("$.orderId"));

            wf.execute("confirm-payment", orderId);
            wf.execute("prepare-pizza", orderId);
            wf.execute("bake-pizza", orderId);
            wf.execute("deliver-pizza", orderId);

        });
    }
}

