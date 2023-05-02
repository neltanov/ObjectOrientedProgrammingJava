package ru.nsu.fit.neltanov.minesweeper.sweeper;

public class Game {
    private final Bomb bomb;
    private final Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        } else {
            return flag.get(coord);
        }
    }

    public void pressLeftButton(Coord coord) {
        if (state == GameState.PLAYED) {
            openBox(coord);
            checkWin();
        }
    }

    private void checkWin() {
        if (state == GameState.PLAYED) {
            if (flag.countOfClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
            }
        }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED -> setOpenedToClosedBoxesAroundNumber(coord);
            case CLOSED -> {
                switch (bomb.get(coord)) {
                    case ZERO -> openBoxesAround(coord);
                    case BOMB -> {
                        openBombs(coord);
                    }
                    default -> flag.setOpenedToBox(coord);
                }
            }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != Box.BOMB) {
            if (flag.countOfFlaggedBoxesAroundNumber(coord) == bomb.get(coord).ordinal()) {
                for (Coord around : Ranges.getCoordsAround(coord)) {
                    if (flag.get(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    private void openBombs(Coord bombedCoord) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombedCoord);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedToClosedBombBox(coord);
            }
            else {
                flag.setNoBombToFlaggedSafeBox(coord);
            }
        }
    }

    public void pressRightButton(Coord coord) {
        if (state == GameState.PLAYED) {
            flag.toggleFlagedToBox(coord);
        }
    }
}
