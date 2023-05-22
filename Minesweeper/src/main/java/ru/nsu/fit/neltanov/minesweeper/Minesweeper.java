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
    private JLabel statusMessageLabel;
    private JButton[][] buttons;

    private int COLS = 9;
    private int ROWS = 9;
    private final int IMG_SIZE = 30;
    private int BOMBS = 10;

    public static void main(String[] args) {
        new Minesweeper();
    }

    private Minesweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initMenu();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        statusMessageLabel = new JLabel("Welcome!");
        statusMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusMessageLabel, BorderLayout.SOUTH);
    }

    private void initPanel() {
        GridLayout layout = new GridLayout(ROWS, COLS);
        panel = new JPanel(layout);
        Icon icon;
        buttons = new JButton[COLS][ROWS];
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

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem aboutGameItem = new JMenuItem("About");

        JMenu highScore = new JMenu("High Scores");

        JMenu settings = new JMenu("Settings");
        JRadioButtonMenuItem easyLevel = new JRadioButtonMenuItem("Easy: field 9x9, 10 mines");
        JRadioButtonMenuItem mediumLevel = new JRadioButtonMenuItem("Medium: field 16x16, 40 mines");
        JRadioButtonMenuItem hardLevel = new JRadioButtonMenuItem("Hard: field 16x30, 99 mines");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(easyLevel);
        buttonGroup.add(mediumLevel);
        buttonGroup.add(hardLevel);

        easyLevel.setSelected(true);
        settings.add(easyLevel);
        settings.add(mediumLevel);
        settings.add(hardLevel);

        easyLevel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setLevelParameters(9, 9, 10);
            }
        });

        mediumLevel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setLevelParameters(16, 16, 40);
            }
        });

        hardLevel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setLevelParameters(30, 16, 99);
            }
        });

        highScore.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JOptionPane.showMessageDialog(getContentPane(), "Coming soon...");
            }
        });

        newGameItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
//                new Minesweeper();
                game = new Game(COLS, ROWS, BOMBS);
                game.start();
                panel.repaint();
                initPanel();
                panel.repaint();
                pack();
            }
        });

        aboutGameItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JOptionPane.showMessageDialog(getContentPane(),
                        "Minesweeper is a game where mines are hidden in a grid of squares. " +
                                "Safe squares have numbers telling you how many mines touch the square. " +
                                "\nYou can use the number clues to solve the game by opening all of the safe squares. " +
                                "If you click on a mine you lose the game!");
            }
        });

        menu.add(newGameItem);
        menu.add(highScore);
        menu.add(aboutGameItem);
        menu.add(settings);
        menuBar.add(menu);
        add(menuBar, "North");
    }

    private void setLevelParameters(int cols, int rows, int bombs) {
        COLS = cols;
        ROWS = rows;
        BOMBS = bombs;
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

    private String getStatusMessage() {
        switch (game.getState()) {
            case IN_GAME -> {
                return "Good game!";
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
            super.mousePressed(e);
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
            }
            updatePanel();
            panel.repaint();

            statusMessageLabel.setText(getStatusMessage());
        }

    }
    private void updatePanel() {
        for (Coords coords : Ranges.getAllCoords()) {
            Icon icon = new ImageIcon((Image) game.getBox(coords).image);
            buttons[coords.x][coords.y].setIcon(icon);
        }
    }
}