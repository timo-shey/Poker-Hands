import card.Card;
import hand.HandService;
import hand.HandServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandServiceImplTest {

    private final HandService handService = new HandServiceImpl();

    @Test
    public void testHighCardBeatsNothing() {
        String[] highCardHand = {"2S", "4D", "7H", "9C", "KD"};
        String[] emptyHand = {"3C", "5H", "6D", "8S", "TS"};

        assertEquals("Player1", determineWinner(highCardHand, emptyHand));
    }

    @Test
    public void testOnePairBeatsHighCard() {
        String[] highCardHand = {"3C", "5H", "6D", "8S", "TS"};
        String[] onePairHand = {"2S", "2D", "7H", "9C", "KD"};

        assertEquals("Player2", determineWinner(highCardHand, onePairHand));
    }

    @Test
    public void testTwoPairsBeatsOnePair() {
        String[] twoPairsHand = {"2S", "2D", "7H", "7C", "KD"};
        String[] onePairHand = {"3C", "3H", "6D", "8S", "TS"};

        assertEquals("Player1", determineWinner(twoPairsHand, onePairHand));
    }

    @Test
    public void testThreeOfAKindBeatsTwoPairs() {
        String[] twoPairsHand = {"3C", "3H", "7D", "7S", "TS"};
        String[] threeOfAKindHand = {"2S", "2D", "2H", "7C", "KD"};

        assertEquals("Player2", determineWinner(twoPairsHand, threeOfAKindHand));
    }

    @Test
    public void testStraightBeatsThreeOfAKind() {
        String[] straightHand = {"2S", "3D", "4H", "5C", "6D"};
        String[] threeOfAKindHand = {"2C", "2H", "2D", "7S", "TS"};

        assertEquals("Player1", determineWinner(straightHand, threeOfAKindHand));
    }

    @Test
    public void testFlushBeatsStraight() {
        String[] straightHand = {"2C", "3H", "4D", "5S", "6D"};
        String[] flushHand = {"2S", "4S", "7S", "JS", "AS"};

        assertEquals("Player2", determineWinner(straightHand, flushHand));
    }

    @Test
    public void testFullHouseBeatsFlush() {
        String[] fullHouseHand = {"2S", "2D", "2H", "7C", "7D"};
        String[] flushHand = {"2C", "4C", "7C", "JC", "AC"};

        assertEquals("Player1", determineWinner(fullHouseHand, flushHand));
    }

    @Test
    public void testFourOfAKindBeatsFullHouse() {
        String[] fourOfAKindHand = {"2S", "2D", "2H", "2C", "7D"};
        String[] fullHouseHand = {"3C", "3H", "3D", "7S", "7C"};

        assertEquals("Player1", determineWinner(fourOfAKindHand, fullHouseHand));
    }

    @Test
    public void testStraightFlushBeatsFourOfAKind() {
        String[] fourOfAKindHand = {"2C", "2H", "2D", "2S", "7C"};
        String[] straightFlushHand = {"2S", "3S", "4S", "5S", "6S"};

        assertEquals("Player2", determineWinner(fourOfAKindHand, straightFlushHand));
    }

    @Test
    public void testRoyalFlushBeatsStraightFlush() {
        String[] royalFlushHand = {"TS", "JS", "QS", "KS", "AS"};
        String[] straightFlushHand = {"2S", "3S", "4S", "5S", "6S"};

        assertEquals("Player1", determineWinner(royalFlushHand, straightFlushHand));
    }

    private String determineWinner(String[] hand1, String[] hand2) {
        Card[] cards1 = Arrays.stream(hand1).map(Card::new).toArray(Card[]::new);
        Card[] cards2 = Arrays.stream(hand2).map(Card::new).toArray(Card[]::new);

        int score1 = handService.getScore(cards1);
        int score2 = handService.getScore(cards2);

        if (score1 > score2) {
            return "Player1";
        } else if (score1 < score2) {
            return "Player2";
        } else {
            return "Draw";
        }
    }

}