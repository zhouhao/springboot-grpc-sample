package com.saltyee.protobuf.client.grpc;

import com.saltyee.grpc.HelloRequest;
import com.saltyee.grpc.MyServiceGrpc;
import com.saltyee.grpc.game.Die;
import com.saltyee.grpc.game.GameServiceGrpc;
import com.saltyee.protobuf.client.grpc.observer.GameStateStreamingResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class GrpcService {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @GrpcClient("myService")
    private GameServiceGrpc.GameServiceStub gameServiceStub;

    public String receiveGreeting(String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        return myServiceBlockingStub.sayHello(request).getMessage();
    }

    public void roll() throws InterruptedException {
        log.info("Rolling the dice...");

        GameStateStreamingResponse gameStateStreamingResponse = new GameStateStreamingResponse();
        StreamObserver<Die> dieStreamObserver = gameServiceStub.roll(gameStateStreamingResponse);
        gameStateStreamingResponse.setDieStreamObserver(dieStreamObserver);
        gameStateStreamingResponse.roll();

    }
}
