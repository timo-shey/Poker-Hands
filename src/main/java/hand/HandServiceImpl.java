package hand;

import card.Card;
import card.Suit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HandServiceImpl is a class that implements the HandService interface.
 * It provides methods for evaluating the strength of a poker hand based on the cards provided.
 * The class defines scoring criteria for various poker hands.
 */
public class HandServiceImpl implements HandService {
    // Scores for different hands
    private static final int ROYAL_FLUSH_SCORE = 9 << 20;
    private static final int STRAIGHT_FLUSH_SCORE = 8 << 20;
    private static final int FOUR_OF_A_KIND_SCORE = 7 << 20;
    private static final int FULL_HOUSE_SCORE = 6 << 20;
    private static final int FLUSH_SCORE = 5 << 20;
    private static final int STRAIGHT_SCORE = 4 << 20;
    private static final int THREE_OF_A_KIND_SCORE = 3 << 20;
    private static final int TWO_PAIR_SCORE = 2 << 20;
    private static final int ONE_PAIR_SCORE = 1 << 20;
    private static final int HIGH_CARD_SCORE = 0;

    // Maximum rank of a card
    private static final int MAX_RANK = 13;

    /**
     * Calculate the score of a hand based on its cards.
     *
     * @param cards List of cards in the hand
     * @return Score of the hand
     */
    @Override
    public int getScore(List<Card> cards) {
        // Sort the cards by rank
        cards.sort(CARD_COMPARATOR);

        // Check for different types of hands and calculate their scores accordingly
        boolean flush = isFlush(cards);
        int straightHighRank = getStraightHighRank(cards);
        int nOfAKind = getNOfAKind(cards);

        if (isRoyalFlush(cards)) return ROYAL_FLUSH_SCORE;
        else if (straightHighRank != -1 && flush) return STRAIGHT_FLUSH_SCORE | straightHighRank;
        else if (nOfAKind == 4) return FOUR_OF_A_KIND_SCORE | getNOfAKindRank(cards, 4);
        else if (nOfAKind == 3 && hasNOfAKind(cards, 2)) return FULL_HOUSE_SCORE | getNOfAKindRank(cards, 3);
        else if (flush) return FLUSH_SCORE | getBestCards(cards);
        else if (straightHighRank != -1) return STRAIGHT_SCORE | straightHighRank;
        else if (nOfAKind == 3) return THREE_OF_A_KIND_SCORE | getNOfAKindRank(cards, 3);
        else if (countPairs(cards) == 2) return TWO_PAIR_SCORE | getBestCards(cards);
        else if (countPairs(cards) == 1) return ONE_PAIR_SCORE | getNOfAKindRank(cards, 2);
        else return HIGH_CARD_SCORE | getBestCards(cards);
    }

    /**
     * Check if the hand is a royal flush.
     *
     * @param cards List of cards in the hand
     * @return True if the hand is a royal flush, false otherwise
     */
    private boolean isRoyalFlush(List<Card> cards) {
        return isFlush(cards) && cards.get(0).getRank() == 9;
    }

    /**
     * Check if the hand is a flush.
     *
     * @param cards List of cards in the hand
     * @return True if the hand is a flush, false otherwise
     */
    private boolean isFlush(List<Card> cards) {
        Suit suit = cards.get(0).getSuit();
        return cards.stream().allMatch(card -> card.getSuit() == suit);
    }

    /**
     * Get the rank of the highest card in a straight.
     *
     * @param cards List of cards in the hand
     * @return Rank of the highest card in the straight, or -1 if not a straight
     */
    private int getStraightHighRank(List<Card> cards) {
        // Count the occurrences of each rank
        Map<Integer, Integer> rankCounts = new HashMap<>();
        cards.forEach(card -> rankCounts.merge(card.getRank(), 1, Integer::sum));

        // Find the starting index of the straight
        int startIndex = IntStream.range(0, MAX_RANK - 4)
            .filter(i -> IntStream.range(i, i + 5).allMatch(j -> rankCounts.getOrDefault(j, 0) > 0))
            .findFirst().orElse(-1);

        return startIndex >= 0 ? startIndex + 4 : -1;
    }

    /**
     * Get the count of the most frequent rank in the hand.
     *
     * @param cards List of cards in the hand
     * @return Count of the most frequent rank
     */
    private int getNOfAKind(List<Card> cards) {
        // Group the cards by rank and count occurrences
        Map<Integer, Long> rankCounts = cards.stream()
            .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCounts.values().stream().mapToInt(Long::intValue).max().orElse(0);
    }

    /**
     * Check if the hand contains N cards of the same rank.
     *
     * @param cards List of cards in the hand
     * @param n     Number of cards to check for
     * @return True if the hand has N cards of the same rank, false otherwise
     */
    private boolean hasNOfAKind(List<Card> cards, int n) {
        // Group the cards by rank and count occurrences
        Map<Integer, Long> rankCounts = cards.stream()
            .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCounts.containsValue((long) n);
    }

    /**
     * Get the rank of the highest N of a kind in the hand.
     *
     * @param cards List of cards in the hand
     * @param n     Number of cards of the same rank
     * @return Rank of the highest N of a kind, or -1 if not found
     */
    private int getNOfAKindRank(List<Card> cards, int n) {
        // Group the cards by rank and count occurrences
        Map<Integer, Long> rankCounts = cards.stream()
            .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCounts.entrySet().stream()
            .filter(entry -> entry.getValue() == n)
            .mapToInt(Map.Entry::getKey)
            .max().orElse(-1);
    }

    /**
     * Get the score of the best 5 cards in the hand.
     *
     * @param cards List of cards in the hand
     * @return Score of the best 5 cards
     */
    private int getBestCards(List<Card> cards) {
        return cards.stream().limit(5).mapToInt(Card::getRank)
            .reduce(0, (result, rank) -> result << 4 | rank);
    }

    /**
     * Count the number of pairs in the hand.
     *
     * @param cards List of cards in the hand
     * @return Number of pairs
     */
    private int countPairs(List<Card> cards) {
        // Group the cards by rank and count occurrences
        Map<Integer, Long> rankCounts = cards.stream()
            .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return (int) rankCounts.values().stream().filter(count -> count == 2).count();
    }

    // Comparator to sort cards by rank in descending order
    private static final Comparator<Card> CARD_COMPARATOR = Comparator.comparingInt(Card::getRank).reversed();
}
