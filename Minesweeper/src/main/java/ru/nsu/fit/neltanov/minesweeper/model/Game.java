package ru.nsu.fit.neltanov.minesweeper.model;

import org.apache.commons.lang3.time.StopWatch;
import ru.nsu.fit.neltanov.minesweeper.HighScores;

public class Game {
    private final Bomb bomb;
    private final Flag flag;
    private GameState state;

    private String playerName;
    private final String level;
    private final HighScores highScores;

    StopWatch stopWatch = new StopWatch();
    private double gameTime;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs) {
        level = defineLevel(cols, rows, bombs);
        highScores = new HighScores("./src/main/resources/highScores.xml");
        Ranges.setSize(new Coords(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();

        enterPlayerName("bayarto");
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.IN_PAUSE;
    }

    private String defineLevel(int cols, int rows, int bombs) {
        if (cols == 9 && rows == 9 && bombs == 10) {
            return "easy";
        }
        if (cols == 16 && rows == 16 && bombs == 40) {
            return "medium";
        }
        if (cols == 30 && rows == 16 && bombs == 99) {
            return "hard";
        }
        return "custom";
    }

    public void resetHighscores() {
        highScores.resetHighscores();
    }

    public void enterPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Box getBox(Coords coords) {
        if (flag.get(coords) == Box.OPENED) {
            return bomb.get(coords);
        } else {
            return flag.get(coords);
        }
    }

    public void pressLeftButton(Coords coords) {
        if (state == GameState.IN_PAUSE) {
            stopWatch.start();
            if (flag.get(coords) == Box.CLOSED && bomb.get(coords) == Box.BOMB) {
                setGameTime();
                System.out.println("First bomb!1!!!");
                start();
                pressLeftButton(coords);
            }
            state = GameState.IN_GAME;
        }
        if (state == GameState.IN_GAME) {
            openBox(coords);
            checkWin();
        }
    }

    private void checkWin() {
        if (state == GameState.IN_GAME) {
            if (flag.countOfClosedBoxes() == bomb.getTotalBombs()) {
                setGameTime();
                state = GameState.WON;
                highScores.updateHighscore(level, playerName, gameTime);
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
        stopWatch.reset();
    }

    public double getGameTime() {
        return gameTime;
    }
}
