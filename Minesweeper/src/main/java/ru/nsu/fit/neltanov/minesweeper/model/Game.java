package ru.nsu.fit.neltanov.minesweeper.model;

import org.apache.commons.lang3.time.StopWatch;

public class Game {
    private final Bomb bomb;
    private final Flag flag;
    private GameState state;

    StopWatch stopWatch = new StopWatch();
    private double gameTime;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coords(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.IN_GAME;
        stopWatch.start();
    }

    public Box getBox(Coords coords) {
        if (flag.get(coords) == Box.OPENED) {
            return bomb.get(coords);
        } else {
            return flag.get(coords);
        }
    }

    public void pressLeftButton(Coords coords) {
        if (state == GameState.IN_GAME) {
            openBox(coords);
            checkWin();
        }
    }

    private void checkWin() {
        if (state == GameState.IN_GAME) {
            if (flag.countOfClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.WON;
            }
        }
    }

    private void openBox(Coords coords) {
        try {
            switch (flag.get(coords)) {
                case OPENED -> setOpenedToClosedBoxesAroundNumber(coords);
                case CLOSED -> {
                    switch (bomb.get(coords)) {
                        case ZERO -> openBoxesAround(coords);
                        case BOMB -> {
                            setGameTime();
                            openBombs(coords);
                        }
                        default -> flag.setOpenedToBox(coords);
                    }
                }
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coords coords) {
        if (bomb.get(coords) != Box.BOMB) {
            if (flag.countOfFlaggedBoxesAroundNumber(coords) == bomb.get(coords).ordinal()) {
                for (Coords around : Ranges.getCoordsAround(coords)) {
                    if (flag.get(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }
    }

    private void openBoxesAround(Coords coords) {
        flag.setOpenedToBox(coords);
        for (Coords around : Ranges.getCoordsAround(coords)) {
            openBox(around);
        }
    }

    private void openBombs(Coords bombedCoords) {
        state = GameState.LOST;

        flag.setBombedToBox(bombedCoords);
        for (Coords coords : Ranges.getAllCoords()) {
            if (bomb.get(coords) == Box.BOMB) {
                flag.setOpenedToClosedBombBox(coords);
            }
            else {
                flag.setNoBombToFlaggedSafeBox(coords);
            }
        }
    }

    public void pressRightButton(Coords coords) {
        if (state == GameState.IN_GAME) {
            flag.toggleFlaggedToBox(coords);
        }
    }

    private void setGameTime() {
        stopWatch.stop();
        gameTime = (double) stopWatch.getTime() / 1000;
        System.out.println("Time taken: " + gameTime + " seconds");
        stopWatch.reset();
    }

    public double getGameTime() {
        return gameTime;
    }
}
