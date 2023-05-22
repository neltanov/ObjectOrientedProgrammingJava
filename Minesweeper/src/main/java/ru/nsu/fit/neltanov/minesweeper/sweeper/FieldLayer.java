package ru.nsu.fit.neltanov.minesweeper.sweeper;

class FieldLayer {
    private final Box[][] matrix;

    FieldLayer(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coords coords : Ranges.getAllCoords()) {
            matrix[coords.x][coords.y] = defaultBox;
        }
    }

    Box get(Coords coords) {
        if (Ranges.inRange(coords)) {
            return matrix[coords.x][coords.y];
        } else {
            throw new NullPointerException("You didn't click on the button from the playing field\n");
        }
    }

    void set(Coords coords, Box box) {
        if (Ranges.inRange(coords)) {
            matrix[coords.x][coords.y] = box;
        }
    }
}
