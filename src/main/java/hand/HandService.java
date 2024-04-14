package hand;

import card.Card;

import java.util.List;

/**
 * Defines the contract for a service that calculates the score of a hand in a card game.
 */
public interface HandService {
    /**
     * Calculates the score of the given hand of cards.
     *
     * @param cards The list of cards representing the hand.
     * @return The score of the hand.
     */
    int getScore(List<Card> cards);
}

