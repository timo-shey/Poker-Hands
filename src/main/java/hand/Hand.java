package hand;

import card.Card;
import card.InvalidCardException;
import card.Rank;
import card.Suit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
@AllArgsConstructor
public class Hand {
    private final List<Card> cards;

    public static Hand createHandFromString(String handString) {
        List<Card> cards = IntStream.range(0, handString.length() / 3)
            .mapToObj(i -> new Card(Rank.getByValue(getRankValue(handString.charAt(i * 3))),
                Suit.getBySymbol(handString.charAt(i * 3 + 1))))
            .collect(Collectors.toList());
        return new Hand(cards);
    }

    private static int getRankValue(char rankChar) {
        if (Character.isDigit(rankChar)) {
            return Character.getNumericValue(rankChar);
        } else {
            return switch (rankChar) {
                case 'T' -> Rank.TEN.getValue();
                case 'J' -> Rank.JACK.getValue();
                case 'Q' -> Rank.QUEEN.getValue();
                case 'K' -> Rank.KING.getValue();
                case 'A' -> Rank.ACE.getValue();
                default -> throw new InvalidCardException("Invalid rank character: " + rankChar);
            };
        }
    }

}
