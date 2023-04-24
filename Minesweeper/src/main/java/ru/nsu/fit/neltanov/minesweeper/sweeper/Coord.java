package ru.nsu.fit.neltanov.minesweeper.sweeper;

public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            return ((Coord) obj).x == x && ((Coord) obj).y == y;
        }
        return super.equals(obj);
    }
}
