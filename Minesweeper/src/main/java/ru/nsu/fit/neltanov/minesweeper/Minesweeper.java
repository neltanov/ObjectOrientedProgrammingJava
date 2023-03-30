package ru.nsu.fit.neltanov.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import ru.nsu.fit.neltanov.minesweeper.sweeper.Box;

public class Minesweeper extends JFrame {

    private JPanel panel;

    private final int COLS = 15;
    private final int ROWS = 2;
    private final int IMG_SIZE = 50;

    public static void main(String[] args) {
        new Minesweeper();
    }

    private Minesweeper() {
        setImages();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Box box : Box.values()) {
                    g.drawImage((Image) box.image, box.ordinal() * IMG_SIZE, 0, this);
                }
            }
        };
        setPreferredSize(new Dimension(COLS * IMG_SIZE, ROWS * IMG_SIZE));
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