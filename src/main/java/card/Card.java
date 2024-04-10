package card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Card {
    public final int rank;
    public final int suit;

    public Card(String str) {
        this.rank = "23456789TJQKA".indexOf(str.charAt(0));
        this.suit = "SHCD".indexOf(str.charAt(1));
    }
}
