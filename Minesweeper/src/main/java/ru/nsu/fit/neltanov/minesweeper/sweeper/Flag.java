package ru.nsu.fit.neltanov.minesweeper.sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    public void setFlaggedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGGED);
    }

    public void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    int countOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    public int countOfFlaggedBoxesAroundNumber(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flagMap.get(around) == Box.FLAGGED) {
                count++;
            }
        }
        return count;
    }

    private void openBoxesAround(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord)) {
            setOpenedToBox(around);
        }
    }

    public void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGGED -> setClosedToBox(coord);
            case CLOSED -> setFlaggedToBox(coord);
        }
    }

    void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
        }
    }

    void setNoBombToFlaggedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGGED) {
            flagMap.set(coord, Box.NOBOMB);
        }
    }
}
