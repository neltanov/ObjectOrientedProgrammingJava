package ru.nsu.fit.neltanov.minesweeper.sweeper;

public class Game {
    public Game(int cols, int rows) {
        Ranges.setSize(new Coord(cols, rows));
    }
}
