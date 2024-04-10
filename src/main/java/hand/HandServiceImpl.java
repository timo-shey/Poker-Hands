package hand;

import card.Card;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandServiceImpl implements HandService{

    @Override
    public int getScore(Card[] cards) {
        Arrays.sort(cards, CARD_COMPARATOR);
        boolean flush = isFlush(cards);
        int straightHighRank = getStraightHighRank(cards);

        if (straightHighRank != -1 && flush) return 8 << 20 | straightHighRank;
        else if (hasNOfAKind(cards, 4)) return 7 << 20 | getNOfAKindRank(cards, 4);
        else if (hasNOfAKind(cards, 3) && hasNOfAKind(cards, 2)) return 6 << 20 | getNOfAKindRank(cards, 3);
        else if (flush) return 5 << 20 | getBestCards(cards);
        else if (straightHighRank != -1) return 4 << 20 | straightHighRank;
        else if (hasNOfAKind(cards, 3)) return 3 << 20 | getNOfAKindRank(cards, 3);
        else if (countPairs(cards) == 2) return 2 << 20 | getBestCards(cards);
        else if (countPairs(cards) == 1) return 1 << 20 | getNOfAKindRank(cards, 2);
        else return getBestCards(cards);
    }

    private boolean isFlush(Card[] cards) {
        int suit = cards[0].suit;
        return Arrays.stream(cards).allMatch(card -> card.suit == suit);
    }

    private int getStraightHighRank(Card[] cards) {
        int[] ranksCount = new int[13];
        Arrays.stream(cards)
            .mapToInt(card -> card.rank)
            .forEach(rank -> ranksCount[rank]++);

        return IntStream.rangeClosed(0, ranksCount.length - 5)
            .map(i -> i + 4)
            .filter(i -> IntStream.range(i - 4, i + 1).allMatch(j -> ranksCount[(j + 13) % 13] > 0))
            .findFirst()
            .orElse(-1);
    }

    private boolean hasNOfAKind(Card[] cards, int n) {
        int[] rankCounts = new int[13];
        Arrays.stream(cards).forEach(card -> rankCounts[card.rank]++);
        return Arrays.stream(rankCounts).anyMatch(count -> count == n);
    }

    private int getNOfAKindRank(Card[] cards, int n) {
        Map<Integer, Long> rankCounts = Arrays.stream(cards)
            .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCounts.entrySet().stream()
            .filter(entry -> entry.getValue() == n)
            .mapToInt(Map.Entry::getKey)
            .max()
            .orElse(-1);
    }

    private int getBestCards(Card[] cards) {
        return IntStream.range(0, 5)
            .map(i -> cards[i].rank)
            .reduce(0, (result, rank) -> result << 4 | rank);
    }

    private int countPairs(Card[] cards) {
        int[] rankCounts = new int[13];
        Arrays.stream(cards).forEach(card -> rankCounts[card.rank]++);
        return (int) Arrays.stream(rankCounts).filter(countPerRank -> countPerRank == 2).count();
    }

    private static final Comparator<Card> CARD_COMPARATOR = Comparator.comparingInt((Card c) -> c.rank).reversed();

}
