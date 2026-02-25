package com.example.lh.example;

import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.worker.LHTaskWorker;

import java.io.IOException;

public class App {

    private static void registerWorkflow() throws IOException{
        LHConfig config = new LHConfig();
        LHTaskWorker worker = new LHTaskWorker(new Greeter(), "greet", config);
        worker.registerTaskDef();

        Quickstart quickstart = new Quickstart();
        quickstart.getWorkflow().registerWfSpec(config.getBlockingStub());
    }

    private static void registerAndRunTaskWorker(LHConfig config) {
        LHTaskWorker worker = new LHTaskWorker(new Greeter(), "greet", config);
        worker.registerTaskDef(); //puede mostrar si ya esta registrado

        System.out.println("Done register task definition, now start worker");
        Runtime.getRuntime().addShutdownHook(new Thread(worker::close)); //cuando el worker muere, cierra las conexiones
        worker.start();
    }

    public static void main(String[] args) throws IOException {
        LHConfig config = new LHConfig(); //Si no se añade props las configuracion van por defecto y se conecta al docker
        registerAndRunTaskWorker(config);
        System.out.println("Done starting worker!");
    }
}
