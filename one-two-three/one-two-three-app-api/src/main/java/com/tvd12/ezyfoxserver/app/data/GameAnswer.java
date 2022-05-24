package com.tvd12.ezyfoxserver.app.data;

import java.util.Comparator;

public enum GameAnswer {
    ONE, // scissors
    TWO, // paper
    THREE; // hammer

    public static class ValueComparator implements Comparator<GameAnswer> {
        @Override
        public int compare(GameAnswer a, GameAnswer b) {
            if (a == ONE) {
                if (b == TWO) {
                    return 1;
                }
                if (b == THREE) {
                    return -1;
                }
            }
            if (a == TWO) {
                if (b == ONE) {
                    return -1;
                }
                if (b == THREE) {
                    return 1;
                }
            }
            if (a == THREE) {
                if (b == ONE) {
                    return 1;
                }
                if (b == TWO) {
                    return -1;
                }
            }
            return 0;
        }
    }
}
