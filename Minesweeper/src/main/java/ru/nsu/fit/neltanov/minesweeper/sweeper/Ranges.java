package ru.nsu.fit.neltanov.minesweeper.sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges {
    private static Coords size;
    private static final ArrayList<Coords> allCoords = new ArrayList<>();
    private static final Random random = new Random();

    public static void setSize(Coords _size) {
        size = _size;
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                allCoords.add(new Coords(i, j));
            }
        }
    }

    public static Coords getSize() {
        return size;
    }

    public static ArrayList<Coords> getAllCoords() {
        return allCoords;
    }

    public static boolean inRange(Coords coords) {
        return coords.x >= 0 && coords.x < size.x && coords.y >= 0 && coords.y < size.y;
    }

    static Coords getRandomCoords() {
        return (new Coords(random.nextInt(size.x), random.nextInt(size.y)));
    }

    static ArrayList<Coords> getCoordsAround(Coords coords) {
        Coords around;
        ArrayList<Coords> list = new ArrayList<>();
        for (int x = coords.x - 1; x <= coords.x + 1; x++) {
            for (int y = coords.y - 1; y <= coords.y + 1; y++) {
                around = new Coords(x, y);
                if (inRange(around) && !around.equals(coords)) {
                    list.add(around);
                }
            }
        }
        return list;
    }
}
