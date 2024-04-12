package hand;

import card.Card;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandServiceImpl implements HandService{
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

    private static final int MAX_RANK = 13;

    @Override
    public int getScore(Card[] cards) {
        Arrays.sort(cards, CARD_COMPARATOR);
        boolean flush = isFlush(cards);
        int straightHighRank = getStraightHighRank(cards);
        int nOfAKind = getNOfAKind(cards);

        if (isRoyalFlush(cards))                            return ROYAL_FLUSH_SCORE;
        else if (straightHighRank != -1 && flush)           return STRAIGHT_FLUSH_SCORE | straightHighRank;
        else if (nOfAKind == 4)                             return FOUR_OF_A_KIND_SCORE | getNOfAKindRank(cards, 4);
        else if (nOfAKind == 3 && hasNOfAKind(cards, 2)) return FULL_HOUSE_SCORE | getNOfAKindRank(cards, 3);
        else if (flush)                                     return FLUSH_SCORE | getBestCards(cards);
        else if (straightHighRank != -1)                    return STRAIGHT_SCORE | straightHighRank;
        else if (nOfAKind == 3)                             return THREE_OF_A_KIND_SCORE | getNOfAKindRank(cards, 3);
        else if (countPairs(cards) == 2)                    return TWO_PAIR_SCORE | getBestCards(cards);
        else if (countPairs(cards) == 1)                    return ONE_PAIR_SCORE | getNOfAKindRank(cards, 2);
        else                                                return HIGH_CARD_SCORE | getBestCards(cards);
    }

    private boolean isRoyalFlush(Card[] cards) {
        return isFlush(cards) && cards[0].rank == 9;
    }

    private boolean isFlush(Card[] cards) {
        int suit = cards[0].suit;
        return Arrays.stream(cards).allMatch(card -> card.suit == suit);
    }

    private int getStraightHighRank(Card[] cards) {
        int[] ranksCount = new int[MAX_RANK];
        Arrays.stream(cards).mapToInt(card -> card.rank).forEach(rank -> ranksCount[rank]++);

        int startIndex = IntStream.range(0, MAX_RANK - 4)
            .filter(i -> IntStream.range(i, i + 5).allMatch(j -> ranksCount[j] > 0))
            .findFirst().orElse(-1);

        return startIndex >= 0 ? startIndex + 4 : -1;
    }

    private int getNOfAKind(Card[] cards) {
        int[] rankCounts = new int[MAX_RANK];
        Arrays.stream(cards).forEach(card -> rankCounts[card.rank]++);
        return Arrays.stream(rankCounts).max().orElse(0);
    }

    private boolean hasNOfAKind(Card[] cards, int n) {
        return Arrays.stream(cards).mapToInt(card -> card.rank).boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .containsValue((long) n);
    }

    private int getNOfAKindRank(Card[] cards, int n) {
        return Arrays.stream(cards).mapToInt(card -> card.rank).boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream().filter(entry -> entry.getValue() == n)
            .mapToInt(Map.Entry::getKey).max().orElse(-1);
    }

    private int getBestCards(Card[] cards) {
        return Arrays.stream(cards).limit(5).mapToInt(card -> card.rank)
            .reduce(0, (result, rank) -> result << 4 | rank);
    }

    private int countPairs(Card[] cards) {
        int[] rankCounts = new int[MAX_RANK];
        Arrays.stream(cards).forEach(card -> rankCounts[card.rank]++);
        return (int) Arrays.stream(rankCounts).filter(countPerRank -> countPerRank == 2).count();
    }

    private static final Comparator<Card> CARD_COMPARATOR = Comparator.comparingInt((Card c) -> c.rank).reversed();

}
