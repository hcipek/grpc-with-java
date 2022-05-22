package com.cipek.model;

import com.cipek.game.proto.MovableType;

public class BoardRules {

    public static int MIN_START (MovableType type) {
        return switch (type) {
            case LADDER -> 1;
            case SNAKE -> 11;
            default -> 0;
        };
    }

    public static int MIN_END (MovableType type) {
        return switch (type) {
            case LADDER -> 11;
            case SNAKE -> 1;
            default -> 0;
        };
    }

    public static int MAX_START (MovableType type) {
        return switch (type) {
            case LADDER -> 90;
            case SNAKE -> 100;
            default -> 0;
        };
    }

    public static int MAX_END (MovableType type) {
        return switch (type) {
            case LADDER -> 100;
            case SNAKE -> 90;
            default -> 0;
        };
    }
}
