package com.shengsiyuan.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author yangsibao
 * @date 2019/6/20-17:31
 */
public class GrpcServer {

    private Server server;

    public static void main(String[] args) throws InterruptedException, IOException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination();
    }

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();

        System.out.println("server started!");
    }

    private void stop() {
        if (this.server != null) {
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }
}
