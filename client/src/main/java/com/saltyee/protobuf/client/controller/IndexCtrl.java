package com.saltyee.protobuf.client.controller;

import com.saltyee.protobuf.client.grpc.GrpcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexCtrl {

    private final GrpcService grpcService;

    public IndexCtrl(GrpcService grpcService) {
        this.grpcService = grpcService;
    }

    @GetMapping("/")
    public String index(@RequestParam("name") String name) throws InterruptedException {
        return grpcService.receiveGreeting(name);
    }
}
