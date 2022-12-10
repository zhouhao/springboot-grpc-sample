package com.saltyee.protobuf.client.grpc;

import com.saltyee.grpc.HelloRequest;
import com.saltyee.grpc.MyServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcService {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    public String receiveGreeting(String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        return myServiceBlockingStub.sayHello(request).getMessage();
    }
}
