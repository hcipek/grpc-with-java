package com.cipek.service;

import com.cipek.game.proto.DiceRequest;
import com.cipek.game.proto.GameResponse;
import com.cipek.game.proto.GameServiceGrpc;
import com.cipek.model.DiceRequestStreamObserver;
import com.cipek.model.GameBoard;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

    @Override
    public StreamObserver<DiceRequest> rollTheDice(StreamObserver<GameResponse> responseObserver) {
        GameBoard gameBoard = new GameBoard();
        return new DiceRequestStreamObserver(responseObserver, gameBoard);
    }
}
