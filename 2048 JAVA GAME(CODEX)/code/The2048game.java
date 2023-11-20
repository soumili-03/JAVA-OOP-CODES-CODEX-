import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class The2048game {
    private static final int BOARD_SIZE = 4;
    private int[][] grid;
    private Random random;
    private int score;
    private int highScore;
    private JFrame frame;
    private JPanel gridPanel;
    private JLabel[][] gridLabels;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private boolean winConditionReached;

    // Constructor
    public The2048game() {
        grid = new int[BOARD_SIZE][BOARD_SIZE];
        random = new Random();
        score = 0;
        highScore = 0;
        frame = new JFrame("Soumili's 2048");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());
        gridPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        gridLabels = new JLabel[BOARD_SIZE][BOARD_SIZE];

        // Initialize grid labels
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gridLabels[i][j] = new JLabel("", JLabel.CENTER);
                gridLabels[i][j].setFont(new Font("", Font.BOLD, 24));
                gridLabels[i][j].setOpaque(true);
                gridLabels[i][j].setBackground(Color.YELLOW);
                gridPanel.add(gridLabels[i][j]);
            }
        }

        // Set up frame layout
        frame.add(gridPanel, BorderLayout.CENTER);
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        highScoreLabel = new JLabel("High Score: 0", JLabel.CENTER);
        infoPanel.add(scoreLabel);
        infoPanel.add(highScoreLabel);
        frame.add(infoPanel, BorderLayout.NORTH);

        // Add key listener for arrow keys
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    moveUp();
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    moveRight();
                }
                updateGridLabels();
                updateScore();
                if (isGameOver()) {
                    showGameOverMessage();
                }
            }
        });

        // Set focus on frame
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setVisible(true);
        initializeGrid();
        updateGridLabels();
    }

    // Initialize grid with two random numbers
    public void initializeGrid() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = 0;
            }
        }
        addNewNumber();
        addNewNumber();
    }

    // Add a new random number (2 or 4) to an empty cell
    public void addNewNumber() {
        int row, col;
        do {
            row = random.nextInt(BOARD_SIZE);
            col = random.nextInt(BOARD_SIZE);
        } while (grid[row][col] != 0);

        grid[row][col] = (random.nextInt(2) + 1) * 2;
    }

    // Update the display of the grid labels based on the grid values
    public void updateGridLabels() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (grid[i][j] == 0) {
                    gridLabels[i][j].setText("");
                    gridLabels[i][j].setBackground(Color.lightGray);
                } else if (grid[i][j] == 2048) {
                    winConditionReached = true;
                    gridLabels[i][j].setText(String.valueOf(grid[i][j]));
                    gridLabels[i][j].setBackground(getTileColor(grid[i][j]));
                } else {
                    gridLabels[i][j].setText(String.valueOf(grid[i][j]));
                    gridLabels[i][j].setBackground(getTileColor(grid[i][j]));
                }
                gridLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            }
        }
    }

    // Get color based on tile value
    public Color getTileColor(int value) {
        switch (value) {
            case 2:
                return new Color(173, 216, 230); // Light Blue
            case 4:
                return new Color(135, 206, 250); // Light Sky Blue
            case 8:
                return new Color(70, 130, 180); // Steel Blue
            case 16:
                return new Color(0, 191, 255); // Deep Sky Blue
            case 32:
                return new Color(0, 0, 205); // Medium Blue
            case 64:
                return new Color(0, 0, 128); // Navy
            case 128:
                return new Color(0, 139, 139); // Dark Cyan
            case 256:
                return new Color(0, 0, 255); // Blue
            case 512:
                return new Color(25, 25, 112); // Midnight Blue
            case 1024:
                return new Color(0, 0, 139); // Dark Blue
            case 2048:
                return new Color(0, 0, 128); // Navy
            default:
                return Color.WHITE;
        }
    }

    // Move tiles upwards
    public void moveUp() {
        boolean moved = false;
        int mergeValue;
        for (int j = 0; j < BOARD_SIZE; j++) {
            mergeValue = -1;
            for (int i = 1; i < BOARD_SIZE; i++) {
                if (grid[i][j] != 0) {
                    int row = i;
                    while (row > 0 && (grid[row - 1][j] == 0 || grid[row - 1][j] == grid[row][j])) {
                        if (grid[row - 1][j] == grid[row][j] && mergeValue != row - 1) {
                            grid[row - 1][j] *= 2;
                            score += grid[row - 1][j];
                            grid[row][j] = 0;
                            mergeValue = row - 1;
                            moved = true;
                        } else if (grid[row - 1][j] == 0) {
                            grid[row - 1][j] = grid[row][j];
                            grid[row][j] = 0;
                            moved = true;
                        }
                        row--;
                    }
                }
            }
        }
        if (moved) {
            addNewNumber();
            updateScore();
        }
    }

    // Move tiles downwards
    public void moveDown() {
        boolean moved = false;
        int mergeValue;
        for (int j = 0; j < BOARD_SIZE; j++) {
            mergeValue = -1;
            for (int i = BOARD_SIZE - 2; i >= 0; i--) {
                if (grid[i][j] != 0) {
                    int row = i;
                    while (row < BOARD_SIZE - 1 && (grid[row + 1][j] == 0 || grid[row + 1][j] == grid[row][j])) {
                        if (grid[row + 1][j] == grid[row][j] && mergeValue != row + 1) {
                            grid[row + 1][j] *= 2;
                            score += grid[row + 1][j];
                            grid[row][j] = 0;
                            mergeValue = row + 1;
                            moved = true;
                        } else if (grid[row + 1][j] == 0) {
                            grid[row + 1][j] = grid[row][j];
                            grid[row][j] = 0;
                            moved = true;
                        }
                        row++;
                    }
                }
            }
        }
        if (moved) {
            addNewNumber();
            updateScore();
        }
    }

    // Move tiles to the left
    public void moveLeft() {
        boolean moved = false;
        int mergeValue;
        for (int i = 0; i < BOARD_SIZE; i++) {
            mergeValue = -1;
            for (int j = 1; j < BOARD_SIZE; j++) {
                if (grid[i][j] != 0) {
                    int col = j;
                    while (col > 0 && (grid[i][col - 1] == 0 || grid[i][col - 1] == grid[i][col])) {
                        if (grid[i][col - 1] == grid[i][col] && mergeValue != col - 1) {
                            grid[i][col - 1] *= 2;
                            score += grid[i][col - 1];
                            grid[i][col] = 0;
                            mergeValue = col - 1;
                            moved = true;
                        } else if (grid[i][col - 1] == 0) {
                            grid[i][col - 1] = grid[i][col];
                            grid[i][col] = 0;
                            moved = true;
                        }
                        col--;
                    }
                }
            }
        }
        if (moved) {
            addNewNumber();
            updateScore();
        }
    }

    // Move tiles to the right
    public void moveRight() {
        boolean moved = false;
        int mergeValue;
        for (int i = 0; i < BOARD_SIZE; i++) {
            mergeValue = -1;
            for (int j = BOARD_SIZE - 2; j >= 0; j--) {
                if (grid[i][j] != 0) {
                    int col = j;
                    while (col < BOARD_SIZE - 1 && (grid[i][col + 1] == 0 || grid[i][col + 1] == grid[i][col])) {
                        if (grid[i][col + 1] == grid[i][col] && mergeValue != col + 1) {
                            grid[i][col + 1] *= 2;
                            score += grid[i][col + 1];
                            grid[i][col] = 0;
                            mergeValue = col + 1;
                            moved = true;
                        } else if (grid[i][col + 1] == 0) {
                            grid[i][col + 1] = grid[i][col];
                            grid[i][col] = 0;
                            moved = true;
                        }
                        col++;
                    }
                }
            }
        }
        if (moved) {
            addNewNumber();
            updateScore();
        }
    }

    // Check if the game is over
    public boolean isGameOver() {
        if (winConditionReached) {
            return true;
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (grid[i][j] == 0 ||
                        (i > 0 && grid[i][j] == grid[i - 1][j]) ||
                        (i < BOARD_SIZE - 1 && grid[i][j] == grid[i + 1][j]) ||
                        (j > 0 && grid[i][j] == grid[i][j - 1]) ||
                        (j < BOARD_SIZE - 1 && grid[i][j] == grid[i][j + 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    // Show game over message dialog
    public void showGameOverMessage() {
        String message;
        if (winConditionReached) {
            message = "Game Complete!\nContinue playing?";
        } else {
            message = "Game over! Restart?";
        }
        int choice = JOptionPane.showConfirmDialog(frame, message, "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    // Restart the game
    public void restartGame() {
        score = 0;
        winConditionReached = false;
        updateScore();
        initializeGrid();
        updateGridLabels();
    }

    // Update the score labels
    public void updateScore() {
        scoreLabel.setText("Score: " + score);
        if (score > highScore) {
            highScore = score;
            highScoreLabel.setText("High Score: " + highScore);
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new The2048game();
            }
        });
    }
}