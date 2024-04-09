import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Getter
@ToString
@AllArgsConstructor
public class Hand {
    private List<Card> cards;

    public static Hand createHandFromString(String handString) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < handString.length(); i += 3) {
            char rank = handString.charAt(i);
            char suit = handString.charAt(i + 1);
            cards.add(new Card(rank, suit));
        }
        return new Hand(cards);
    }
}
