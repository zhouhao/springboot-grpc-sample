package com.saltyee.protobuf.server.service;

import com.saltyee.grpc.HelloReply;
import com.saltyee.grpc.MyServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@GrpcService
@Service
public class MyServiceImpl extends MyServiceGrpc.MyServiceImplBase {

    @Override
    public void sayHello(com.saltyee.grpc.HelloRequest request,
                         StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
