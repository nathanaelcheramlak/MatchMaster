import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    private JFrame mainFrame;

    private JPanel infoPanel;
    private JPanel gamePanel;

    private JMenuBar menuBar;

    private JMenu gameMenu;
    private JMenu fileMenu;
    private JMenu aboutMenu;

    private JMenuItem aboutMenuItem;
    private JMenuItem scoringMenuItem;
    private JMenuItem restartMenuItem;
    private JMenuItem highScoreMenuItem;
    private JMenuItem changeImageMenuItem;
    private JMenuItem changeFlipTimerMenuItem;

    private JComboBox<String> levelsComboBox;

    private JLabel scoreLabel;
    private JLabel scorePointLabel;
    private JLabel highScoreLabel;
    private JLabel movesPlayesLabel;
    private JLabel remainingMovesLabel;

    private int level = 1, rowCount = 2, colCount = 2, maxFlips = Integer.MAX_VALUE;
    private ArrayList<JButton> Cards = new ArrayList<>();
    private ArrayList<String> imageName = new ArrayList<>();

    // Default Image Path
    private String imgPath = "assets\\Gumball";

    // Reference Classes
    GameEngine engine;

    public GUI() {
        createFrame();
        createMenuBar();
        createInfoPanel();
        createGamePanel();
    }

    private void createFrame() {
        mainFrame = new JFrame();

        // Setting the App Logo
        ImageIcon icon = new ImageIcon("assets\\logo.png");
        mainFrame.setIconImage(icon.getImage());

        mainFrame.setTitle("Match Master");
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(false);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        createGameMenu();
        createFileMenu();
        createAboutMenu();

        mainFrame.setJMenuBar(menuBar);
    }

    private void createGameMenu() {
        gameMenu = new JMenu("Game");

        restartMenuItem = new JMenuItem("Restart");
        highScoreMenuItem = new JMenuItem("High Score");
        changeFlipTimerMenuItem = new JMenuItem("Flip Timer");

        restartMenuItem.addActionListener(this);
        highScoreMenuItem.addActionListener(this);
        changeFlipTimerMenuItem.addActionListener(this);

        gameMenu.add(restartMenuItem);
        gameMenu.add(highScoreMenuItem);
        gameMenu.add(changeFlipTimerMenuItem);

        menuBar.add(gameMenu);
    }

    private void createFileMenu() {
        fileMenu = new JMenu("File");

        changeImageMenuItem = new JMenuItem("Change Image Dir");
        changeImageMenuItem.addActionListener(this);

        fileMenu.add(changeImageMenuItem);

        menuBar.add(fileMenu);
    }

    private void createAboutMenu() {
        aboutMenu = new JMenu("About");

        scoringMenuItem = new JMenuItem("Scoring");
        aboutMenuItem = new JMenuItem("About");

        scoringMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);

        aboutMenu.add(aboutMenuItem);
        aboutMenu.add(scoringMenuItem);

        menuBar.add(aboutMenu);
    }

    private void createInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setBackground(new Color(167, 84, 84));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("Score");
        scorePointLabel = new JLabel("0");
        highScoreLabel = new JLabel("HighScore: 0");
        movesPlayesLabel = new JLabel("Moves: 0");
        remainingMovesLabel = new JLabel("Remaining Moves: Unlimited");

        // Set Font
        scoreLabel.setFont(new Font("Istok Web", Font.PLAIN, 28));
        scorePointLabel.setFont(new Font("Istok Web", Font.PLAIN, 32));
        highScoreLabel.setFont(new Font("Istok Web", Font.PLAIN, 24));
        movesPlayesLabel.setFont(new Font("Istok Web", Font.PLAIN, 28));
        remainingMovesLabel.setFont(new Font("Istok Web", Font.PLAIN, 20));

        // Change Color
        scoreLabel.setForeground(Color.white);
        scorePointLabel.setForeground(Color.white);
        highScoreLabel.setForeground(Color.white);
        movesPlayesLabel.setForeground(Color.white);
        remainingMovesLabel.setForeground(Color.white);

        // Center the labels
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePointLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        movesPlayesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        remainingMovesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding Components
        createComboBox();
        infoPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        infoPanel.add(scoreLabel);
        infoPanel.add(scorePointLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(highScoreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(movesPlayesLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(remainingMovesLabel);

        mainFrame.add(infoPanel, BorderLayout.EAST);
    }

    private void createComboBox() {
        levelsComboBox = new JComboBox<>(new String[] {
                "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6"
        });

        levelsComboBox.setPreferredSize(new Dimension(100, 40));
        levelsComboBox.setMaximumSize(new Dimension(100, 40));
        levelsComboBox.addActionListener(this);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(levelsComboBox);
    }

    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rowCount, colCount));

        createFilpCards(); // Creates the Flip Cards

        engine = new GameEngine(this);

        mainFrame.add(gamePanel, BorderLayout.CENTER);
    }

    private void createFilpCards() {
        int totalCards = rowCount * colCount;

        for (int i = 0; i < totalCards; i++) {
            JButton card = new JButton();
            ImageIcon img = new ImageIcon("assets\\LuckyLeaf.jpg");
            card.setIcon(img);
            card.setFocusable(false);
            card.addActionListener(this);

            Cards.add(card);
        }

        for (JButton card : Cards) {
            gamePanel.add(card);
        }

        // Calling the function that initalize imageName ArrayList
        initializeImagePath(totalCards, null);
    }

    private void initializeImagePath(int totalCards, ArrayList<String> img) {
        ManageFiles file = new ManageFiles(totalCards);
        if (imageName.isEmpty() || img == null)
            imageName = file.getImages(imgPath);
        else {
            imageName = file.getImages(img);
        }

        // Sets the highscore to the label
        int highscore = file.getHighScore(level);
        highScoreLabel.setText("HighScore: " + Integer.toString(highscore));
    }


    public void resetGamePanel() {
        engine.setCorrectImages(0);
        engine.setIsFirstFlip(true);
        scorePointLabel.setText("0");
        movesPlayesLabel.setText("Moves: 0");

        Cards.clear();
        imageName.clear();

        gamePanel.removeAll();
        gamePanel.setLayout(new GridLayout(rowCount, colCount));
        createFilpCards();
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void highScoreDialog(int highscore) {
        String message = "High Score (Level " + level + "): " + highscore;
        JOptionPane.showMessageDialog(null, message, "High Score", JOptionPane.INFORMATION_MESSAGE);
    }

    private int changeFlipTimerDialog() {
        // Show input dialog to get the flip timer value from the user
        String input = JOptionPane.showInputDialog(null, "Enter the flip timer value (in milli-seconds):", "Change Flip Timer", JOptionPane.QUESTION_MESSAGE);

        if (input != null) {
            try {
                int flipTimerValue = Integer.parseInt(input);
                if (flipTimerValue > 0) {
                    JOptionPane.showMessageDialog(null, "Flip Timer set to: " + flipTimerValue + " milli-seconds", "Flip Timer Changed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                return flipTimerValue;
            } catch (NumberFormatException e) {
                // Show an error message if the input is not a valid integer
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return 0;
    }

    private ArrayList<String> changeImageDirDialog(int imagesNeeded) {
        // Warning Message
        String warningMessage = "Please note:\n" +
                                "1. The images you select may be zoomed in or deformed depending on their resolution and aspect ratio.\n" +
                                "2. Ensure the images have similar dimensions for the best display results.\n" +
                                "3. You need to select at least " + imagesNeeded + " image files.";
        JOptionPane.showMessageDialog(null, warningMessage, "Image Selection Warning", JOptionPane.WARNING_MESSAGE);
        // Show input dialog to get the image directory from the user
        FileDialog fileDialog = new FileDialog(mainFrame, "Select " + imagesNeeded + "Images", FileDialog.LOAD);
        fileDialog.setMultipleMode(true);

        fileDialog.setVisible(true);

        // Get the directory and file selected by the user
        String directory = fileDialog.getDirectory();
        File file[] = fileDialog.getFiles();
        ArrayList<String> imagePath = new ArrayList<>();

        // If the user selects a file, extract the directory path
        if (directory != null && file != null) {

            // Count the number of image files
            int imageCount = 0;
            for (File f : file) {
                String fileName = f.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                    imageCount++;
                }
                String image = directory + "\\" + f.getName();
                imagePath.add(image);
            }

            if (imageCount >= imagesNeeded) {
                return imagePath;
            } else {
                JOptionPane.showMessageDialog(null, "The selected directory does not contain exactly " + imagesNeeded + " image files.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            // User cancelled the operation
            JOptionPane.showMessageDialog(null, "Operation cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    private void aboutDialog() {
        String aboutMessage = "Application Name: Match Master\n" +
                              "Version: 1.0\n" +
                              "Author: Nathanael Cheramlak\n" +
                              "Description: This is a simple memory game made for fun.\n\n" +
                              "For more information, Contact: nathanaelcheramlak7@gmail.com";

        JOptionPane.showMessageDialog(null, aboutMessage, "About Match Master", JOptionPane.INFORMATION_MESSAGE);
    }

    private void scoringDialog() {
        // Description of the scoring formula
        String description = "Scoring Formula:\n\n" +
                "1. Matching Cards:\n" +
                "   - If you successfully match two cards:\n" +
                "     - You gain 10 points.\n" +
                "     - An additional bonus of 5 points is added for each consecutive match.\n" +
                "     - You also gain points equal to the number of remaining moves.\n" +
                "   - Example: If you have 5 remaining moves and this is your third consecutive match, you gain:\n" +
                "     - 10 points + (3 * 5) bonus points + 5 remaining moves = 10 + 15 + 5 = 30 points.\n\n" +
                "2. Non-Matching Cards:\n" +
                "   - If the cards do not match:\n" +
                "     - You lose 3 points.\n" +
                "     - The score is adjusted to ensure it does not fall below 0.\n" +
                "   - Example: If your current score is 5 and the cards do not match, you lose 3 points,\n" +
                "     - resulting in a new score of 2.\n\n" +
                "3. Consecutive Matches:\n" +
                "   - The consecutiveMatches variable tracks the number of consecutive matches.\n" +
                "   - Each successful match increases this count, affecting the bonus calculation.\n\n" +
                "4. Remaining Moves:\n" +
                "   - Points are added based on the number of remaining moves at the time of a match.\n" +
                "   - This encourages efficient play with fewer moves.\n";

        JOptionPane.showMessageDialog(null, description, "Scoring Formula", JOptionPane.INFORMATION_MESSAGE);
    }

    public void actionPerformed(ActionEvent e) {
        for (JButton card : Cards) {
            if (e.getSource() == card) {
                if (engine.getIsFirstFlip()) {
                    engine.flipFirstCard(card);
                    break;
                } else {
                    engine.flipSecondCard(card);
                    break;
                }
            }
        }
        if (e.getSource() == levelsComboBox) {
            level = levelsComboBox.getSelectedIndex() + 1;
            MemoryGameLevel lev = new MemoryGameLevel(level);

            this.rowCount = lev.getRowCount();
            this.colCount = lev.getColCount();
            this.maxFlips = lev.getMaxFlip();

            resetGamePanel();
        }
        if (e.getSource() == restartMenuItem) {
            resetGamePanel();
        }
        if (e.getSource() == highScoreMenuItem) {
            ManageFiles file = new ManageFiles();
            int highscore = file.getHighScore(level);
            highScoreDialog(highscore);
            System.out.println("HighScore: " + highscore);
        }
        if (e.getSource() == changeFlipTimerMenuItem) {
            int currentFlipTimer = engine.getFlipTimer();
            int newFlipTimer = changeFlipTimerDialog();
            if (newFlipTimer > 0)
                engine.setFlipTimer(newFlipTimer);
            else
                engine.setFlipTimer(currentFlipTimer);
        }
        if (e.getSource() == changeImageMenuItem) {
            int imagesNeeded = rowCount * colCount / 2;
            initializeImagePath(imagesNeeded * 2, changeImageDirDialog(imagesNeeded));
        } 
        if (e.getSource() == aboutMenuItem) {
            aboutDialog();
        }
        if (e.getSource() == scoringMenuItem) {
            scoringDialog();
        }
    }

    // Adds the ActionListener to a card
    public void addCardActionListener(JButton card) {
        card.addActionListener(this);
    }

    // Removes the ActionListener from a card
    public void removeCardActionListener(JButton card) {
        card.removeActionListener(this);
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public JLabel getScorePointLabel() {
        return scorePointLabel;
    }

    public JLabel getHighScoreLabel() {
        return highScoreLabel;
    }

    public JLabel getMovesPlayedLabel() {
        return movesPlayesLabel;
    }

    public JLabel getReamaingMovesLabel() {
        return remainingMovesLabel;
    }

    public ArrayList<JButton> getCards() {
        return Cards;
    }

    public ArrayList<String> getImageName() {
        return imageName;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public int getTotalGrid() {
        return rowCount * colCount;
    }

    public int getMaxFlip() {
        return maxFlips;
    }

    public int getLevel() {
        return level;
    }

}
