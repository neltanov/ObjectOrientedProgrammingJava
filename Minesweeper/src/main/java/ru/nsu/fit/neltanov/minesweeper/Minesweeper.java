package ru.nsu.fit.neltanov.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import ru.nsu.fit.neltanov.minesweeper.sweeper.Box;
import ru.nsu.fit.neltanov.minesweeper.sweeper.Coord;
import ru.nsu.fit.neltanov.minesweeper.sweeper.Ranges;

public class Minesweeper extends JFrame {

    private JPanel panel;

    private final int COLS = 9;
    private final int ROWS = 9;
    private final int IMG_SIZE = 50;

    public static void main(String[] args) {
        new Minesweeper();
    }

    private Minesweeper() {
        Ranges.setSize(new Coord(COLS, ROWS));
        setImages();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) Box.BOMB.image, coord.x * IMG_SIZE, coord.y * IMG_SIZE, this);
                }
            }
        };
        setPreferredSize(new Dimension(Ranges.getSize().x * IMG_SIZE, Ranges.getSize().y * IMG_SIZE));
        add(panel);
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)));
        return icon.getImage();
    }
}