package ru.nsu.fit.neltanov.minesweeper.model;

class Bomb {
    private FieldLayer bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombCount();
    }

    void start() {
        bombMap = new FieldLayer(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    Box get(Coords coords) {
        return bombMap.get(coords);
    }

    private void fixBombCount() {
        int maxBombs = (int) (0.6 * Ranges.getSize().x * Ranges.getSize().y);
        if (totalBombs > maxBombs) {
            totalBombs = maxBombs;
        }
    }

    private void placeBomb() {
        while (true) {
            Coords coords = Ranges.getRandomCoords();
            if (Box.BOMB == bombMap.get(coords)) {
                continue;
            }
            bombMap.set(coords, Box.BOMB);
            incrementNumbersAroundTheBomb(coords);
            break;
        }
    }

    private void incrementNumbersAroundTheBomb(Coords coords) {
        for (Coords around : Ranges.getCoordsAround(coords)) {
            if (Box.BOMB != bombMap.get(around)) {
                bombMap.set(around, bombMap.get(around).nextNumberBox());
            }
        }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
