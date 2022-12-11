package com.saltyee.protobuf.server.service;

import com.saltyee.grpc.game.Die;
import com.saltyee.grpc.game.GameServiceGrpc;
import com.saltyee.grpc.game.GameState;
import com.saltyee.grpc.game.Player;
import com.saltyee.protobuf.server.service.observer.DieStreamingRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@GrpcService
@Service
public class GameService extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {

        Player client = Player.newBuilder().setName("Client").setPosition(0).build();
        Player server = Player.newBuilder().setName("Server").setPosition(0).build();
        return new DieStreamingRequest(client, server, responseObserver);
    }
}
