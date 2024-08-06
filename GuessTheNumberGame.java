import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheNumberGame extends JFrame implements ActionListener {
    private JTextField guessField;
    private JButton guessButton, newGameButton;
    private JLabel messageLabel, chancesLabel;
    private int randomNumber, chances;

    public GuessTheNumberGame() {
        setTitle("Guess the Number Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        messageLabel = new JLabel("Guess a number between 1 and 100:");
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        newGameButton = new JButton("New Game");
        chancesLabel = new JLabel("Chances left: 5");

        add(messageLabel);
        add(guessField);
        add(guessButton);
        add(chancesLabel);

        guessButton.addActionListener(this);
        newGameButton.addActionListener(this);

        startNewGame();
        setVisible(true);
    }

    private void startNewGame() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        chances = 5;
        chancesLabel.setText("Chances left: " + chances);
        messageLabel.setText("Guess a number between 1 and 100:");
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        remove(newGameButton);
        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                chances--;
                if (guess == randomNumber) {
                    messageLabel.setText("Correct! You guessed the number.");
                    guessField.setEnabled(false);
                    guessButton.setEnabled(false);
                    add(newGameButton);
                } else if (chances == 0) {
                    messageLabel.setText("Game Over! The number was " + randomNumber);
                    guessField.setEnabled(false);
                    guessButton.setEnabled(false);
                    add(newGameButton);
                } else {
                    if (guess < randomNumber) {
                        messageLabel.setText("Too low! Try again.");
                    } else {
                        messageLabel.setText("Too high! Try again.");
                    }
                    chancesLabel.setText("Chances left: " + chances);
                }
                revalidate();
                repaint();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid number.");
            }
        } else if (e.getSource() == newGameButton) {
            startNewGame();
        }
    }

    public static void main(String[] args) {
        new GuessTheNumberGame();
    }
}
