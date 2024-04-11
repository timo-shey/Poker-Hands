package hand;

import card.Card;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
public class Hand {
    private final Card[] cards;
    private final HandService handService;

    public Hand(String[] cardStrings, HandService handService) {
        this.cards = Arrays.stream(cardStrings).map(Card::new).toArray(Card[]::new);
        this.handService = handService;
    }

    public int getScore() {
        return handService.getScore(cards);
    }

}


