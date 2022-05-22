package com.cipek.model;

import com.cipek.game.proto.DiceRequest;
import com.cipek.game.proto.GameResponse;
import com.cipek.game.proto.Player;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRequestStreamObserver implements StreamObserver<DiceRequest> {

    private StreamObserver<GameResponse> responseStreamObserver;
    private GameBoard gameBoard;
    private Player currentTurn = Player.CLIENT;

    public DiceRequestStreamObserver(StreamObserver<GameResponse> responseStreamObserver, GameBoard gameBoard) {
        this.responseStreamObserver = responseStreamObserver;
        this.gameBoard = gameBoard;
    }

    @Override
    public void onNext(DiceRequest diceRequest) {
        while (!Player.CLIENT.equals(currentTurn)) {
            System.out.println("Its still servers turn");
            return;
        }

        if (isCompleted()) {
            responseStreamObserver.onCompleted();
            return;
        }

        this.currentTurn = Player.SERVER;
        int clientDice = rollTheDice();
        boolean isMoved = gameBoard.move(clientDice, Player.CLIENT);
        String lastMove = getLastMove(gameBoard, Player.CLIENT, clientDice, isMoved);

        GameResponse clientMovedResponse = GameResponse.newBuilder()
                .setClientPosition(gameBoard.getClientPosition())
                .setServerPosition(gameBoard.getServerPosition())
                .setCurrentTurn(currentTurn)
                .setGameBoard(gameBoard.getBoard())
                .setRolledDice(clientDice)
                .setLastMove(lastMove)
                .build();
        responseStreamObserver.onNext(clientMovedResponse);
        System.out.println(lastMove);

        if (isCompleted()) {
            responseStreamObserver.onCompleted();
            return;
        }

        int serverDice = rollTheDice();
        isMoved = gameBoard.move(serverDice, Player.SERVER);
        lastMove = getLastMove(gameBoard, Player.SERVER, serverDice, isMoved);
        GameResponse serverMovedResponse = GameResponse.newBuilder()
                .setClientPosition(gameBoard.getClientPosition())
                .setServerPosition(gameBoard.getServerPosition())
                .setCurrentTurn(Player.CLIENT)
                .setGameBoard(gameBoard.getBoard())
                .setRolledDice(serverDice)
                .setLastMove(lastMove)
                .build();
        responseStreamObserver.onNext(serverMovedResponse);
        System.out.println(lastMove);

        if (isCompleted()) {
            responseStreamObserver.onCompleted();
            return;
        }

        currentTurn = Player.CLIENT;


    }

    private String getLastMove(GameBoard gameBoard, Player player, int dice, boolean isMoved) {
        StringBuilder sb = new StringBuilder(player.name())
                .append(" has rolled dice and get ")
                .append(dice)
                .append(". ");

        if (!isMoved)
            sb.append(" but its out of reach! ");
        else
            switch (gameBoard.getLastMovementType()) {
                case FOUND_LADDER -> sb.append("It looks like that ")
                        .append(player.name())
                        .append(" found a LADDER! Flying must be feels like this...");
                case FOUND_SNAKE -> sb.append("So unlucky! ")
                        .append(player.name())
                        .append(" found a SNAKE! Careful, don't brake your hips...");
                default -> sb.append(player.name())
                        .append(" going step by step... ");
            }

        sb.append(player.name())
                .append("'s Current Position is : ");

        switch (player) {
            case SERVER -> sb.append(gameBoard.getServerPosition());
            case CLIENT -> sb.append(gameBoard.getClientPosition());
            default -> throw new IllegalStateException("Unexpected value: " + player);
        }

        if (isCompleted())
            sb.append("\n")
                    .append(player.name())
                    .append(" WINS the GAME!");

        return sb.toString();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }

    private int rollTheDice () {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }

    private boolean isCompleted () {
        return gameBoard.getClientPosition() == 100 || gameBoard.getServerPosition() == 100;
    }
}
