package card;

import lombok.Getter;
import lombok.ToString;

/**
 * Represents a Card class that holds the rank and suit details.
 */
@Getter
@ToString
public class Card {
    public final int rank;
    public final Suit suit;

    /**
     * Constructor to create a Card object from a string representation.
     * @param str String representation of the card, e.g., "2S" for 2 of Spades.
     */
    public Card(String str) {
        this.rank = "23456789TJQKA".indexOf(str.charAt(0));
        this.suit = parseSuit(str.charAt(1));
    }

    /**
     * Parses the suit character and returns the corresponding Suit enum value.
     * @param suitChar Character representing the suit ('S', 'H', 'C', or 'D').
     * @return Suit enum value corresponding to the suit character.
     * @throws InvalidCardException If the suit character is invalid.
     */
    private Suit parseSuit(char suitChar) {
        // Use a switch statement to map the character to the corresponding Suit enum
        return switch (suitChar) {
            case 'S' -> Suit.SPADES;
            case 'H' -> Suit.HEARTS;
            case 'C' -> Suit.CLUBS;
            case 'D' -> Suit.DIAMONDS;
            default -> throw new InvalidCardException("Invalid suit character: " + suitChar);
        };
    }
}

