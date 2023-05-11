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
    private final int IMG_SIZE = 30;
    private final int BOMBS = 10;

    JButton[][] buttons = new JButton[ROWS][COLS];

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
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                icon = new ImageIcon((Image) game.getBox(new Coord(i, j)).image);
                JButton button = new JButton(icon);
                int finalI = i;
                int finalJ = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int x = finalI;
                        int y = finalJ;
                        System.out.println("(" + e.getX() + "," + e.getY() + ")");
                        System.out.println("(" + x + "," + y + ")");
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

//                        panel.revalidate();
                        for (Coord coord : Ranges.getAllCoords()) {
                            Icon icon = new ImageIcon((Image) game.getBox(coord).image);
                            buttons[coord.x][coord.y].setIcon(icon);
                            buttons[coord.x][coord.y].repaint();
                        }
//                        button.repaint();
                    }
                });
                panel.add(button);
                buttons[i][j] = button;
            }
        }

        getContentPane().add(panel, BorderLayout.CENTER);


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