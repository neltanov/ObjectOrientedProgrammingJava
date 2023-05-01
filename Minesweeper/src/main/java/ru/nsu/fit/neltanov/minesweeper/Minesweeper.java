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
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int IMG_SIZE = 28;
    private final int BOMBS = 10;

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
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMG_SIZE, coord.y * IMG_SIZE, this);
                }
            }
        };
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMG_SIZE;
                int y = e.getY() / IMG_SIZE - 1;
                System.out.println("(" + e.getX() + "," + e.getY() + ")");
                System.out.println("("+ x +"," + y +")");
                Coord mouse_coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(mouse_coord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(mouse_coord);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });
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