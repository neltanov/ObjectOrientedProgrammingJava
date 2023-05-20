package ru.nsu.fit.neltanov.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import ru.nsu.fit.neltanov.minesweeper.sweeper.*;
import ru.nsu.fit.neltanov.minesweeper.sweeper.Box;

public class Minesweeper extends JFrame {
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 20;
    private final int ROWS = 20;
    private final int IMG_SIZE = 30;
    private final int BOMBS = 60;

    JButton[][] buttons = new JButton[COLS][ROWS];

    public static void main(String[] args) {
        new Minesweeper();
    }

    private Minesweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        GridLayout layout = new GridLayout(ROWS, COLS);
        panel = new JPanel(layout);
        Icon icon;
        for (int j = 0; j < ROWS; j++) {
            for (int i = 0; i < COLS; i++) {
                icon = new ImageIcon((Image) game.getBox(new Coords(i, j)).image);
                JButton button = new JButton(icon);
                button.addMouseListener(new MouseEventHandler());
                panel.add(button);
                buttons[i][j] = button;
            }
        }

        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMG_SIZE,
                Ranges.getSize().y * IMG_SIZE));
        add(panel);
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
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

    private String getMessage() {
        switch (game.getState()) {
            case IN_GAME -> {
                return "Think twice";
            }
            case LOST -> {
                return "You lose!";
            }
            case WON -> {
                return "You won! Congratulations!";
            }
            default -> {
                return "";
            }
        }
    }

    private class MouseEventHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getComponent().getLocation().x / IMG_SIZE;
            int y = e.getComponent().getLocation().y / IMG_SIZE;
            Coords mouseCoords = new Coords(x, y);
            if (e.getButton() == MouseEvent.BUTTON1) {
                game.pressLeftButton(mouseCoords);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                game.pressRightButton(mouseCoords);
            }
            if (e.getButton() == MouseEvent.BUTTON2) {
                game.start();
                for (Coords coords : Ranges.getAllCoords()) {
                    Icon icon = new ImageIcon((Image) game.getBox(coords).image);
                    buttons[coords.x][coords.y].setIcon(icon);
                }
            }

            for (Coords coords : Ranges.getAllCoords()) {
                Icon icon = new ImageIcon((Image) game.getBox(coords).image);
                buttons[coords.x][coords.y].setIcon(icon);
            }
            panel.repaint();

            label.setText(getMessage());
        }
    }
}