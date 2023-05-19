package ru.nsu.fit.neltanov.minesweeper.sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get(Coords coords) {
        return flagMap.get(coords);
    }

    public void setOpenedToBox(Coords coords) {
        flagMap.set(coords, Box.OPENED);
        countOfClosedBoxes--;
    }

    public void setFlaggedToBox(Coords coords) {
        flagMap.set(coords, Box.FLAGGED);
    }

    public void setClosedToBox(Coords coords) {
        flagMap.set(coords, Box.CLOSED);
    }

    void setBombedToBox(Coords coords) {
        flagMap.set(coords, Box.BOMBED);
    }

    int countOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    public int countOfFlaggedBoxesAroundNumber(Coords coords) {
        int count = 0;
        for (Coords around : Ranges.getCoordsAround(coords)) {
            if (flagMap.get(around) == Box.FLAGGED) {
                count++;
            }
        }
        return count;
    }

    private void openBoxesAround(Coords coords) {
        for (Coords around : Ranges.getCoordsAround(coords)) {
            setOpenedToBox(around);
        }
    }

    public void toggleFlaggedToBox(Coords coords) {
        switch (flagMap.get(coords)) {
            case FLAGGED -> setClosedToBox(coords);
            case CLOSED -> setFlaggedToBox(coords);
        }
    }

    void setOpenedToClosedBombBox(Coords coords) {
        if (flagMap.get(coords) == Box.CLOSED) {
            flagMap.set(coords, Box.OPENED);
        }
    }

    void setNoBombToFlaggedSafeBox(Coords coords) {
        if (flagMap.get(coords) == Box.FLAGGED) {
            flagMap.set(coords, Box.NOBOMB);
        }
    }
}
