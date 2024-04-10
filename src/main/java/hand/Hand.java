package hand;

import card.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@AllArgsConstructor
public class Hand {
    private Card[] cards;

    public Hand(String[] cardStrings) {
        cards = Arrays.stream(cardStrings).map(Card::new).toArray(Card[]::new);
    }

    public int getScore() {
        HandService handService = new HandServiceImpl();
        return handService.getScore(cards);
    }

}


