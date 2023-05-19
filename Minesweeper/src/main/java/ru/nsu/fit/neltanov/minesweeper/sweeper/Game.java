package ru.nsu.fit.neltanov.minesweeper.sweeper;

public class Game {
    private final Bomb bomb;
    private final Flag flag;
    private GameState state;
    // TODO: добавить таймер в игру и привязать его к окошку.


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
        state = GameState.PLAYED;
    }

    public Box getBox(Coords coords) {
        if (flag.get(coords) == Box.OPENED) {
            return bomb.get(coords);
        } else {
            return flag.get(coords);
        }
    }

    public void pressLeftButton(Coords coords) {
        if (state == GameState.PLAYED) {
            openBox(coords);
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

    private void openBox(Coords coords) {
        try {
            switch (flag.get(coords)) {
                case OPENED -> setOpenedToClosedBoxesAroundNumber(coords);
                case CLOSED -> {
                    switch (bomb.get(coords)) {
                        case ZERO -> openBoxesAround(coords);
                        case BOMB -> {
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
        state = GameState.BOMBED;
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
        if (state == GameState.PLAYED) {
            flag.toggleFlaggedToBox(coords);
        }
    }

    public boolean isBoxOpened(Coords coords) {
        return flag.get(coords) != Box.CLOSED || flag.get(coords) == Box.FLAGGED; // TODO: добавить снятие флага
    }
}
