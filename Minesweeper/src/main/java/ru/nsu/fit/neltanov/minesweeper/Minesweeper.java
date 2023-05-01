package ru.nsu.fit.neltanov.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import ru.nsu.fit.neltanov.minesweeper.sweeper.*;
import ru.nsu.fit.neltanov.minesweeper.sweeper.Box;

public class Minesweeper extends JFrame {
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 40;
    private final int ROWS = 40;
    private final int IMG_SIZE = 30;
    private final int BOMBS = 40;

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
        panel = new JPanel(new GridLayout(COLS, ROWS));
        paintGameField();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMG_SIZE;
                int y = e.getY() / IMG_SIZE - 1;
                System.out.println("(" + e.getX() + "," + e.getY() + ")");
                System.out.println("("+ x +"," + y +")");
                Coord mouseCoord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(mouseCoord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(mouseCoord);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMessage());
                panel.removeAll();
                paintGameField();
                panel.revalidate();
            }
        });
        System.out.println(Ranges.getSize().x + " " + Ranges.getSize().y);
        setPreferredSize(new Dimension(Ranges.getSize().x * IMG_SIZE + 15,
                Ranges.getSize().y * IMG_SIZE + 50));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void paintGameField() {
        ImageIcon icon;
        JLabel imageLabel;
        for (Coord coord : Ranges.getAllCoords()) {
            icon = new ImageIcon((Image) game.getBox(coord).image);
            imageLabel = new JLabel(icon);
            panel.add(imageLabel);
        }
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
            case PLAYED -> {
                return "Think twice";
            }
            case BOMBED -> {
                return "You lose!";
            }
            case WINNER -> {
                return "You won! Congratulations!";
            }
            default -> {
                return "";
            }
        }
    }
}