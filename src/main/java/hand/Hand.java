package hand;

import card.Card;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Hand class that holds a list of Cards.
 */
@Getter
@ToString
public class Hand {
    private final List<Card> cards;
    private final HandService handService;

    /**
     * Constructs a Hand object from a list of card strings and a HandService.
     *
     * @param cardStrings The list of card strings representing the cards in the hand.
     * @param handService The HandService used to calculate the hand score.
     */
    public Hand(List<String> cardStrings, HandService handService) {
        this.cards = cardStrings.stream().map(Card::new).collect(Collectors.toList());
        this.handService = handService;
    }

    /**
     * Gets the score of the hand using the associated HandService.
     *
     * @return The score of the hand.
     */
    public int getScore() {
        return handService.getScore(cards);
    }
}




