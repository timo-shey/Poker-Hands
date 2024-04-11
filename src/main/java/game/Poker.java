package game;

import card.InvalidCardException;
import hand.Hand;
import hand.HandService;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class Poker {
    private final HandService handService;
    private static final int NUM_HANDS = 1000;
    private static final String FILE_PATH = "src/main/resources/poker.txt";
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

    private boolean p1Wins(String[] cards) {
        Hand player1 = new Hand(Arrays.copyOfRange(cards, 0, 5), handService);
        Hand player2 = new Hand(Arrays.copyOfRange(cards, 5, 10), handService);
        return player1.getScore() > player2.getScore();
    }
}
