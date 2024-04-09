import hand.Hand;
import hand.HandComparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandComparatorTest {

    @Test
    void testCompare_HighCard() {
//        hand.Hand hand1 = hand.Hand.createHandFromString("2H 4D 6S 8C TH"); // Ten High card.Card
        Hand hand1 = Hand.createHandFromString("3H 7H 6S KC JS");
        Hand hand2 = Hand.createHandFromString("QH TD JC 2D 8S");
//        hand.Hand hand2 = hand.Hand.createHandFromString("3H 5D 7S 9C JH"); // Jack High card.Card

        HandComparator comparator = new HandComparator();
        int result = comparator.compare(hand1, hand2);

        assertEquals(-1, result);
    }



    @Test
    public void testCompare_OnePair() {
        Hand hand1 = Hand.createHandFromString("2H 2D 6S 8C TH"); // Pair of Twos
        Hand hand2 = Hand.createHandFromString("3H 3D 7S 9C JH"); // Pair of Threes

        HandComparator comparator = new HandComparator();
        int result = comparator.compare(hand1, hand2);

        assertEquals(-1, result);
    }

    @Test
    public void testCompare_Flush() {
        Hand hand1 = Hand.createHandFromString("2H 4H 6H 8H TH"); // Flush With Ten High (Hearts)
        Hand hand2 = Hand.createHandFromString("3S 5S 7S 9S JS"); // Flush With Jack High (Spades)

        HandComparator comparator = new HandComparator();
        int result = comparator.compare(hand1, hand2);

        assertEquals(-1, result);
    }

    @Test
    public void testCompare_Straight() {
        Hand hand1 = Hand.createHandFromString("2H 3D 4S 5C 6H"); // Straight With Six High
        Hand hand2 = Hand.createHandFromString("3S 4D 5S 6C 7H"); // Straight With Seven High

        HandComparator comparator = new HandComparator();
        int result = comparator.compare(hand1, hand2);

        assertEquals(-1, result);
    }

    @Test
    public void testCompare_FullHouse() {
        Hand hand1 = Hand.createHandFromString("2H 2D 2S 3C 3H"); // Full House With Three Twos
        Hand hand2 = Hand.createHandFromString("3S 3D 3S 4C 4H"); // Full House With Three Threes

        HandComparator comparator = new HandComparator();
        int result = comparator.compare(hand1, hand2);

        assertEquals(-1, result);
    }
}