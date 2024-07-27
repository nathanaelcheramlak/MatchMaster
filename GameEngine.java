import java.awt.event.*;

import javax.swing.*;

public class GameEngine {
    private GUI gameGui;

    private int firstCardIndex, secondCardIndex, correctImages = 0; // Used for Comparision
    private int timerDuration = 750; // Duration of the card being open.
    private int consecutiveMatches = 0;
    private boolean isFirstFilp = true;

    private Timer timer;

    public GameEngine(GUI gameGui) {
        this.gameGui = gameGui;
    }

    public void flipFirstCard(JButton card) {
        firstCardIndex = gameGui.getCards().indexOf(card);
        String cardImageName = gameGui.getImageName().get(firstCardIndex);
        ImageIcon secretImage = new ImageIcon(cardImageName);

        card.setIcon(secretImage);
        gameGui.removeCardActionListener(card);

        this.isFirstFilp = false;
    }

    public void flipSecondCard(JButton card) {
        secondCardIndex = gameGui.getCards().indexOf(card);
        String cardImageName = gameGui.getImageName().get(secondCardIndex);
        ImageIcon secretImage = new ImageIcon(cardImageName);

        card.setIcon(secretImage);
        checkIfMatched();
    }

    private void checkIfMatched() {
        String lScore = gameGui.getScorePointLabel().getText();
        int lastScore = Integer.parseInt(lScore);
        int maxFlips = gameGui.getMaxFlip();
        int movesPlayed = Integer.parseInt(gameGui.getMovesPlayedLabel().getText().split(": ")[1]);
        int remaingMoves = maxFlips - movesPlayed;
        gameGui.getMovesPlayedLabel().setText("Moves: " + (movesPlayed + 1));

        if (maxFlips > 2000) { // Refers Unlimited
            gameGui.getReamaingMovesLabel().setText("Remaining Moves: Unlimited");
        } else { // Decrements the remaining moves
            gameGui.getReamaingMovesLabel().setText("Remaining Moves: " + Integer.toString(remaingMoves));
        }

        if (compareCards()) { // If the Cards Match
            int updatedScore = incrementScore(lastScore, true, remaingMoves);
            gameGui.getScorePointLabel().setText(Integer.toString(updatedScore));

            correctImages += 2;

            gameGui.removeCardActionListener(gameGui.getCards().get(firstCardIndex));
            gameGui.removeCardActionListener(gameGui.getCards().get(secondCardIndex));
        } else { // If the Cards don't match
            int updatedScore = incrementScore(lastScore, false, remaingMoves);
            gameGui.getScorePointLabel().setText(Integer.toString(updatedScore));
            removeAllActionListener();
            timer = new Timer(timerDuration, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ImageIcon coverImg = new ImageIcon("assets\\LuckyLeaf.jpg");
                    gameGui.getCards().get(firstCardIndex).setIcon(coverImg);
                    gameGui.getCards().get(secondCardIndex).setIcon(coverImg);

                    // Stop the Timer after resetting the buttons
                    timer.stop();

                    addAllActionListener();
                }
            });
            timer.start(); // Start the Timer
        }
        isFirstFilp = true;
        isGameEnd(remaingMoves); // Checks if the game ended
    }

    private boolean compareCards() {
        return gameGui.getImageName().get(firstCardIndex).equals(gameGui.getImageName().get(secondCardIndex));
    }


    private int incrementScore(int lastScore, boolean isMatch, int remainingMoves) {
        if (isMatch) {
            consecutiveMatches++;
            return lastScore + (10) + (consecutiveMatches * 5) + remainingMoves;
        } else {
            consecutiveMatches = 0;
            return Math.max(0, lastScore - 3);
        }
    }


    private void isGameEnd(int remainingMoves) {
        int totalCards = gameGui.getRowCount() * gameGui.getColCount();
        if (correctImages >= totalCards) {
            int HighScore = Integer.parseInt(gameGui.getScorePointLabel().getText());
            ManageFiles file = new ManageFiles();
            file.highScoreWriter(HighScore, gameGui.getLevel());

            String scorePt = gameGui.getScorePointLabel().getText();
            String totalMoves = gameGui.getMovesPlayedLabel().getText().split(": ")[1];
            JOptionPane.showMessageDialog(gameGui,
                    "Congratulations\n You Won!\n Your Score was " + scorePt + "\nIt Took you "
                            + totalMoves + " Moves!",
                    "Memory Game",
                    JOptionPane.INFORMATION_MESSAGE);
        } 
        if (remainingMoves <= 0) {
            String message = "You lost! Would you like to try again?";
            int response = JOptionPane.showConfirmDialog(null, message, "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                gameGui.resetGamePanel();
            } else {
                System.exit(0);
            }
        }
        
    }

    private void removeAllActionListener() {
        for (JButton card : gameGui.getCards()) {
            gameGui.removeCardActionListener(card);
        }
    }

    private void addAllActionListener() {
        for (JButton card : gameGui.getCards()) {
            gameGui.addCardActionListener(card);
        }
    }

    public int getFlipTimer() {
        return timerDuration;
    }

    public void setFlipTimer(int timer) {
        this.timerDuration = timer;
    }

    public boolean getIsFirstFlip() {
        return isFirstFilp;
    }

    public void setCorrectImages(int correctImg) {
        this.correctImages = correctImg;
    }

    public void setIsFirstFlip(boolean isFirstFlip) {
        this.isFirstFilp = isFirstFlip;
    }
}
