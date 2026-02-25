package com.example.lh.example;


import io.littlehorse.sdk.common.proto.VariableType;
import io.littlehorse.sdk.wfsdk.WfRunVariable;
import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

//define a wfspec that use the definition
public class Quickstart {
    public static final String WF_Name = "quickstart";
    public static final String GREET = "greet";

    public void quickStartWf(WorkflowThread wf)
    {
        WfRunVariable name = wf.addVariable("input-name", VariableType.STR);
        wf.execute(GREET, name);
    }

    public Workflow getWorkflow()
    {
        return Workflow.newWorkflow(WF_Name, this::quickStartWf);
    }



}
