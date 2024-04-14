import card.Card;
import hand.Hand;
import hand.HandService;
import hand.HandServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandServiceImplTest {

    private final HandService handService = new HandServiceImpl();

    @Test
    public void testHighCardBeatsNothing() {
        List<String> highCardHand = Arrays.asList("2S", "4D", "7H", "9C", "KD");
        List<String> emptyHand = Arrays.asList("3C", "5H", "6D", "8S", "TS");

        assertEquals("Player1", determineWinner(highCardHand, emptyHand));
    }

    @Test
    public void testOnePairBeatsHighCard() {
        List<String> highCardHand = Arrays.asList("3C", "5H", "6D", "8S", "TS");
        List<String> onePairHand = Arrays.asList("2S", "2D", "7H", "9C", "KD");

        assertEquals("Player2", determineWinner(highCardHand, onePairHand));
    }

    @Test
    public void testTwoPairsBeatsOnePair() {
        List<String> twoPairsHand = Arrays.asList("2S", "2D", "7H", "7C", "KD");
        List<String> onePairHand = Arrays.asList("3C", "3H", "6D", "8S", "TS");

        assertEquals("Player1", determineWinner(twoPairsHand, onePairHand));
    }

    @Test
    public void testThreeOfAKindBeatsTwoPairs() {
        List<String> twoPairsHand = Arrays.asList("3C", "3H", "7D", "7S", "TS");
        List<String> threeOfAKindHand = Arrays.asList("2S", "2D", "2H", "7C", "KD");

        assertEquals("Player2", determineWinner(twoPairsHand, threeOfAKindHand));
    }

    @Test
    public void testStraightBeatsThreeOfAKind() {
        List<String> straightHand = Arrays.asList("2S", "3D", "4H", "5C", "6D");
        List<String> threeOfAKindHand = Arrays.asList("2C", "2H", "2D", "7S", "TS");

        assertEquals("Player1", determineWinner(straightHand, threeOfAKindHand));
    }

    @Test
    public void testFlushBeatsStraight() {
        List<String> straightHand = Arrays.asList("2C", "3H", "4D", "5S", "6D");
        List<String> flushHand = Arrays.asList("2S", "4S", "7S", "JS", "AS");

        assertEquals("Player2", determineWinner(straightHand, flushHand));
    }

    @Test
    public void testFullHouseBeatsFlush() {
        List<String> fullHouseHand = Arrays.asList("2S", "2D", "2H", "7C", "7D");
        List<String> flushHand = Arrays.asList("2C", "4C", "7C", "JC", "AC");

        assertEquals("Player1", determineWinner(fullHouseHand, flushHand));
    }

    @Test
    public void testFourOfAKindBeatsFullHouse() {
        List<String> fourOfAKindHand = Arrays.asList("2S", "2D", "2H", "2C", "7D");
        List<String> fullHouseHand = Arrays.asList("3C", "3H", "3D", "7S", "7C");

        assertEquals("Player1", determineWinner(fourOfAKindHand, fullHouseHand));
    }

    @Test
    public void testStraightFlushBeatsFourOfAKind() {
        List<String> fourOfAKindHand = Arrays.asList("2C", "2H", "2D", "2S", "7C");
        List<String> straightFlushHand = Arrays.asList("2S", "3S", "4S", "5S", "6S");

        assertEquals("Player2", determineWinner(fourOfAKindHand, straightFlushHand));
    }

    @Test
    public void testRoyalFlushBeatsStraightFlush() {
        List<String> royalFlushHand = Arrays.asList("TS", "JS", "QS", "KS", "AS");
        List<String> straightFlushHand = Arrays.asList("2S", "3S", "4S", "5S", "6S");

        assertEquals("Player1", determineWinner(royalFlushHand, straightFlushHand));
    }

    private String determineWinner(List<String> hand1, List<String> hand2) {
        Hand player1Hand = new Hand(hand1, handService);
        Hand player2Hand = new Hand(hand2, handService);

        int score1 = player1Hand.getScore();
        int score2 = player2Hand.getScore();

        if (score1 > score2) {
            return "Player1";
        } else if (score1 < score2) {
            return "Player2";
        } else {
            return "Draw";
        }
    }
}
