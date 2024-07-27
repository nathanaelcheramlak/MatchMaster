# Match Master (Using Java Swing)

## Overview

Match Master is a memory card game that challenges players to match pairs of cards while managing their moves and score. The game provides multiple levels, each with its own difficulty and number of cards. The goal is to match all the cards in the fewest moves possible while maximizing your score.

## Technology

- Java, Swing

## Features

- **Multiple Levels:** Play through different levels, each with increasing complexity.
- **Score Tracking:** Earn points for matching cards and lose points for mismatches.
- **Customizable Flip Timer:** Adjust the timer between card flips to match your preferred game pace.
- **High Scores:** View and track high scores for each level.
- **Dynamic Image Selection:** Choose your own images for the game to enhance the experience.

## Getting Started

To start playing Match Master, follow these steps:

1. **Run the Application:** Compile and run the GUI class to start the game.
2. **Select Difficulty:** Use the level dropdown to choose the game difficulty.
3. **Load Images:** Select the "Change Image Dir" option from the File menu to load your own images.
4. **Start Matching:** Flip cards to find and match pairs. Your score and remaining moves are displayed on the side.

## Controls

- **Restart Game:** Click the "Restart" option in the Game menu to start a new game.
- **Change Flip Timer:** Adjust the flip timer in the Game menu to speed up or slow down the card flipping.
- **View High Scores:** Access the high scores for each level through the Game menu.
- **About:** Learn more about the game and its creator by selecting the "About" option in the About menu.
- **Scoring Formula:** Review how scores are calculated by selecting the "Scoring" option in the About menu.

## Scoring Formula

### Matching Cards:

- Gain 10 points for each successful match.
- Receive an additional 5 points for each consecutive match.
- Gain points equal to the number of remaining moves.

### Non-Matching Cards:

- Lose 3 points for each mismatch, with the score adjusted to ensure it does not fall below 0.

### Consecutive Matches:

- The number of consecutive matches affects the bonus points for each match.

### Remaining Moves:

- Points are added based on the number of remaining moves at the time of a match.

## Requirements

- Java Development Kit (JDK) 8 or later
- Java Swing library for GUI components

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For more information or support, contact Nathanael Cheramlak at [nathanaelcheramlak7@gmail.com](mailto:nathanaelcheramlak7@gmail.com).
