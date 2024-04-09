import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandComparator implements Comparator<Hand> {

    @Override
    public int compare(Hand o1, Hand o2) {
        int rank1 = rankHand(o1);
        int rank2 = rankHand(o2);

        if (rank1 != rank2) {
            return Integer.compare(rank1, rank2);
        } return compareHighCards(o1, o2);
    }

    private int rankHand(Hand hand) {
        List<Card> cards = hand.getCards();
        cards.sort(Comparator.comparing(Card::getRank));

        Map<Predicate<List<Card>>, Integer> handRanks = createHandRanksMap();

        for (Map.Entry<Predicate<List<Card>>, Integer> entry : handRanks.entrySet()) {
            if (entry.getKey().test(cards)) {
                return entry.getValue();
            }
        }
        return 1;
    }

    private boolean isRoyalFlush(List<Card> cards) {
        return isStraightFlush(cards) && cards.get(0).getRank() == 'T';
    }

    private boolean isStraightFlush(List<Card> cards) {
        return isFlush(cards) && isStraight(cards);
    }

    private boolean isFourOfAKind(List<Card> cards) {
        return hasNOfAKind(cards, 4);
    }

    private boolean isFullHouse(List<Card> cards) {
        return hasNOfAKind(cards, 3) && hasNOfAKind(cards, 2);
    }

    private boolean isFlush(List<Card> cards) {
        char suit = cards.get(0).getSuit();
        return cards.stream().allMatch(card -> card.getSuit() == suit);
    }

    private boolean isStraight(List<Card> cards) {
        return IntStream.range(1, cards.size())
            .allMatch(i -> cards.get(i).getRank() - cards.get(i - 1).getRank() == 1);
    }

    private boolean isThreeOfAKind(List<Card> cards) {
        return hasNOfAKind(cards, 3);
    }

    private boolean isTwoPair(List<Card> cards) {
        long countPairs = IntStream.range(0, cards.size() - 1)
            .filter(i -> cards.get(i).getRank() == cards.get(i + 1).getRank())
            .count();

        return countPairs == 2;
    }


    private boolean isOnePair(List<Card> cards) {
        return hasNOfAKind(cards, 2);
    }

    private boolean hasNOfAKind(List<Card> cards, int n) {
        Map<Character, Long> rankCounts = cards.stream()
            .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

        return rankCounts.values().stream().anyMatch(count -> count == n);
    }

    private int compareHighCards(Hand hand1, Hand hand2) {
        List<Card> cards1 = hand1.getCards();
        List<Card> cards2 = hand2.getCards();

        cards1.sort(Comparator.comparing(Card::getRank).reversed());
        cards2.sort(Comparator.comparing(Card::getRank).reversed());

        return IntStream.range(0, Math.min(cards1.size(), cards2.size()))
            .mapToObj(i -> {
                char rank1 = cards1.get(i).getRank();
                char rank2 = cards2.get(i).getRank();
                return Character.compare(rank1, rank2);
            })
            .filter(result -> result != 0)
            .findFirst()
            .orElse(0);
    }

    private Map<Predicate<List<Card>>, Integer> createHandRanksMap() {
        Map<Predicate<List<Card>>, Integer> handRanks = new LinkedHashMap<>();
        handRanks.put(this::isRoyalFlush, 10);
        handRanks.put(this::isStraightFlush, 9);
        handRanks.put(this::isFourOfAKind, 8);
        handRanks.put(this::isFullHouse, 7);
        handRanks.put(this::isFlush, 6);
        handRanks.put(this::isStraight, 5);
        handRanks.put(this::isThreeOfAKind, 4);
        handRanks.put(this::isTwoPair, 3);
        handRanks.put(this::isOnePair, 2);

        return handRanks;
    }
}
