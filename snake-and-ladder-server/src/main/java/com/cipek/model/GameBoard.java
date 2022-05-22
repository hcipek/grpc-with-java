package com.cipek.model;

import com.cipek.game.proto.Board;
import com.cipek.game.proto.Movable;
import com.cipek.game.proto.MovableType;
import com.cipek.game.proto.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBoard {
    private final Board board;
    private int clientPosition;
    private int serverPosition;
    private final Map<Integer, Integer> movableMap;
    private PlayerMovementType lastMovementType;

    public GameBoard () {
        this.movableMap = new HashMap<>();
        this.clientPosition = 0;
        this.serverPosition = 0;
        this.board = Board.newBuilder()
                .addAllLadders(generateMovables(MovableType.LADDER))
                .addAllSnakes(generateMovables(MovableType.SNAKE))
                .build();
    }

    public Board getBoard () {
        return this.board;
    }

    public int getClientPosition () {
        return this.clientPosition;
    }

    public int getServerPosition () {
        return this.serverPosition;
    }

    public PlayerMovementType getLastMovementType () {
        return this.lastMovementType;
    }

    public GameBoard reset() {
        this.clientPosition = 0;
        this.serverPosition = 0;
        return this;
    }

    public boolean move (final int dice, Player player) {
        int destination = dice;
        destination += switch (player) {
            case CLIENT -> clientPosition;
            case SERVER -> serverPosition;
            default -> 0;
        };

        if (destination > 100) {
            return false;
        }

        PlayerMovementType movementType = PlayerMovementType.REGULAR;

        if (movableMap.containsKey(destination)) {
            int preDestination = destination;
            destination = movableMap.get(destination);

            if (preDestination < destination)
                movementType = PlayerMovementType.FOUND_LADDER;
            else
                movementType = PlayerMovementType.FOUND_SNAKE;
        }

        this.lastMovementType = movementType;

        switch (player) {
            case CLIENT -> clientPosition = destination;
            case SERVER -> serverPosition = destination;
        }

        return true;
    }

    //Adding 10 range of movables, either ladder or snake, based on param
    private List<Movable> generateMovables (MovableType movableType) {
        return switch (movableType) {
            case LADDER -> IntStream.rangeClosed(1, 10)
                    .boxed()
                    .map(v -> getRandomLadder())
                    .collect(Collectors.toList());
            case SNAKE -> IntStream.rangeClosed(1, 10)
                    .boxed()
                    .map(v -> getRandomSnake())
                    .collect(Collectors.toList());
            default -> new ArrayList<>();
        };

    }

    //Logic is based on start point of snake must be minimum one row higher than destination.
    private Movable getRandomSnake() {
        MovableType snake = MovableType.SNAKE;
        int startPoint;

        do {
            startPoint = ThreadLocalRandom.current().nextInt(BoardRules.MIN_START(snake), BoardRules.MAX_START(snake));
        } while (movableMap.containsKey(startPoint) || movableMap.containsValue(startPoint));

        int endPoint = 0;

        try {
            endPoint = ThreadLocalRandom.current().nextInt(BoardRules.MIN_END(snake), (((startPoint -1) / 10) * 10));
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        movableMap.put(startPoint, endPoint);

        return Movable.newBuilder()
                .setStart(startPoint)
                .setDestination(endPoint)
                .setType(snake)
                .build();
    }

    //Ladder creation logic based on end point must be at least one row higher than start point.
    private Movable getRandomLadder() {
        MovableType ladder = MovableType.LADDER;
        int startPoint;

        do {
            startPoint = ThreadLocalRandom.current().nextInt(BoardRules.MIN_START(ladder), BoardRules.MAX_START(ladder));
        } while (movableMap.containsKey(startPoint) || movableMap.containsValue(startPoint));

        int endPoint = 0;

        try {
            endPoint = ThreadLocalRandom.current().nextInt((((startPoint / 10) + 1) * 10) + 1, BoardRules.MAX_END(ladder));
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }

        movableMap.put(startPoint, endPoint);

        return Movable.newBuilder()
                .setStart(startPoint)
                .setDestination(endPoint)
                .setType(ladder)
                .build();
    }

}
