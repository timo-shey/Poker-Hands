package card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Suit {
    CLUBS('C'),
    DIAMONDS('D'),
    HEARTS('H'),
    SPADES('S');

    private final char symbol;

    public static Suit getBySymbol(char symbol) {
        for (Suit suit : Suit.values()) {
            if (suit.symbol == symbol) {
                return suit;
            }
        }
        throw new InvalidCardException("No suit found with symbol: " + symbol);
    }
}
