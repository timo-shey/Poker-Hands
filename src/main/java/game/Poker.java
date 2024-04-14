package game;

import card.InvalidCardException;
import hand.Hand;
import hand.HandService;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Poker game with logic to count the number of wins for Player 1 over a series of hands.
 */
@AllArgsConstructor
public class Poker {
    private final HandService handService;
    private static final int NUM_HANDS = 1000;
    private static final String FILE_PATH = "src/main/resources/poker.txt";

    /**
     * Counts the number of wins for Player 1 over a series of hands stored in a file.
     *
     * @return The number of wins for Player 1.
     */
    public int countPlayer1Wins() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(Poker.FILE_PATH))) {
            count = (int) reader.lines()
                .limit(Poker.NUM_HANDS)
                .map(line -> line.split(" "))
                .peek(cards -> {
                    if (cards.length != 10) {
                        throw new InvalidCardException("Invalid number of cards");
                    }
                })
                .filter(this::p1Wins)
                .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Determines if Player 1 wins a hand against Player 2.
     *
     * @param cards The cards dealt in the hand.
     * @return True if Player 1 wins, false otherwise.
     */
    private boolean p1Wins(String[] cards) {
        List<String> player1CardStrings = Arrays.asList(Arrays.copyOfRange(cards, 0, 5));
        List<String> player2CardStrings = Arrays.asList(Arrays.copyOfRange(cards, 5, 10));
        Hand player1 = new Hand(player1CardStrings, handService);
        Hand player2 = new Hand(player2CardStrings, handService);
        return player1.getScore() > player2.getScore();
    }
}
