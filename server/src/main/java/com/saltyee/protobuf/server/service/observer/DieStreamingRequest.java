package com.saltyee.protobuf.server.service.observer;

import com.saltyee.grpc.game.Die;
import com.saltyee.grpc.game.GameState;
import com.saltyee.grpc.game.Player;
import com.saltyee.protobuf.server.service.rule.SnakeAndLadderRuleMap;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DieStreamingRequest implements StreamObserver<Die> {

    private Player client;
    private Player server;
    private StreamObserver<GameState> gameStateStreamObserver;

    public DieStreamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
        this.client = client;
        this.server = server;
        this.gameStateStreamObserver = gameStateStreamObserver;
    }

    @Override
    public void onNext(Die die) {
        this.client = this.getNewPlayerPosition(this.client, die.getValue());
        if (this.client.getPosition() != 100) {
            this.server = this.getNewPlayerPosition(this.server, ThreadLocalRandom.current().nextInt(1, 7));
        }

        this.gameStateStreamObserver.onNext(this.getGameState());
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        this.gameStateStreamObserver.onCompleted();
    }

    private GameState getGameState() {
        return GameState.newBuilder()
                .addPlayer(this.client)
                .addPlayer(this.server)
                .build();
    }

    private Player getNewPlayerPosition(Player player, int dieVal) {
        int position = player.getPosition() + dieVal;
        position = SnakeAndLadderRuleMap.getPosition(position);
        if (position <= 100) {
            player = player.toBuilder().setPosition(position).build();
        }
        return player;
    }
}
