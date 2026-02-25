package com.example.lh.example;

import io.littlehorse.sdk.worker.LHTaskMethod;

public class Greeter
{
    @LHTaskMethod("greet") //name of the task
    public String greet(String name)
    {
        System.out.println("Executing Task ");
        return "Hello, " + name + "!";
    }
}
