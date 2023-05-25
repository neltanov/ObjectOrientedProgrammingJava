package ru.nsu.fit.neltanov.minesweeper.model;

public class Coords {
    public int x;
    public int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coords) {
            return ((Coords) obj).x == x && ((Coords) obj).y == y;
        }
        return super.equals(obj);
    }
}
