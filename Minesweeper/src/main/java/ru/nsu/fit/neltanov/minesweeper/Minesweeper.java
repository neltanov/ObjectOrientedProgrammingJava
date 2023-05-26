package ru.nsu.fit.neltanov.minesweeper;

import ru.nsu.fit.neltanov.minesweeper.gui.MinesweeperGUI;
import ru.nsu.fit.neltanov.minesweeper.model.Game;

public class Minesweeper {
    public static void main(String[] args) {
        runMinesweeperGUI(9, 9, 10);
    }

    public static void runMinesweeperGUI(int cols, int rows, int bombs) {
        Game game = new Game(cols, rows, bombs);
        game.start();
        new MinesweeperGUI(game, cols, rows, bombs);
    }
}
