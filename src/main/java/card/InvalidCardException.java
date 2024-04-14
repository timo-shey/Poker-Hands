package card;

/**
 * Custom exception class for representing an invalid card.
 * <p>
 * This exception is thrown when attempting to create or process a card with invalid attributes.
 * For example, if the card string does not conform to the expected format.
 */
public class InvalidCardException extends RuntimeException{

    /**
     * Constructs a new InvalidCardException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public InvalidCardException(String message) {
        super(message);
    }

}
