package com.saltyee.protobuf.client.grpc.observer;

import com.saltyee.grpc.game.Die;
import com.saltyee.grpc.game.GameState;
import com.saltyee.grpc.game.Player;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class GameStateStreamingResponse implements StreamObserver<GameState> {


    private StreamObserver<Die> dieStreamObserver;


    @Override
    public void onNext(GameState gameState) {
        List<Player> list = gameState.getPlayerList();
        list.forEach(player -> log.info(player.getName() + " : " + player.getPosition()));

        boolean isGameOver = list.stream().anyMatch(p -> p.getPosition() == 100);

        if (isGameOver) {
            log.info("Game Over");
            this.dieStreamObserver.onCompleted();
        } else {
            this.roll();
        }
        log.info("----------------------------------------------");
    }


    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
    }

    public void setDieStreamObserver(StreamObserver<Die> dieStreamObserver) {
        this.dieStreamObserver = dieStreamObserver;
    }

    public void roll() {
        int dieVal = ThreadLocalRandom.current().nextInt(1, 7);
        Die die = Die.newBuilder().setValue(dieVal).build();
        this.dieStreamObserver.onNext(die);
    }
}
