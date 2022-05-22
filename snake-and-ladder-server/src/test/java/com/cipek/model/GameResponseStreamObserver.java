package com.cipek.model;

import com.cipek.game.proto.DiceRequest;
import com.cipek.game.proto.GameResponse;
import com.cipek.game.proto.Player;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class GameResponseStreamObserver implements StreamObserver<GameResponse> {

    private final CountDownLatch latch;
    private boolean showBoard = true;
    private StreamObserver<DiceRequest> requestStreamObserver;

    public GameResponseStreamObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void setRequestStreamObserver(StreamObserver streamObserver) {
        requestStreamObserver = streamObserver;
    }

    @Override
    public void onNext(GameResponse gameResponse) {
        if (showBoard) {
            System.out.println(gameResponse.getGameBoard());
            showBoard = false;
        }

        System.out.println("\nRolled dice : " + gameResponse.getRolledDice());
        System.out.println("Clients position : " + gameResponse.getClientPosition());
        System.out.println("Servers position : " + gameResponse.getServerPosition());
        System.out.println(gameResponse.getLastMove());

        if (gameResponse.getClientPosition() == 100 || gameResponse.getServerPosition() == 100) {
            requestStreamObserver.onCompleted();
            latch.countDown();
        } else {
            System.out.println("Current Move : " + gameResponse.getCurrentTurn());
            System.out.println("\n!--------------!");
        }

        if (gameResponse.getCurrentTurn().equals(Player.CLIENT))
            nextMove();
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("Game is Finished");
        latch.countDown();
    }

    public void startTheGame() {
        nextMove();
    }

    private void nextMove () {
        DiceRequest request = DiceRequest.newBuilder()
                .build();
        requestStreamObserver.onNext(request);
    }
}
