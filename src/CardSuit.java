// This enum is used to identify the suit of a card.
public enum CardSuit {
    CLUBS,
    SPADES,
    DIAMONDS,
    HEARTS;

    // This method returns the string representation of the suit.
    public String str() {
        return switch (this) {
            case CLUBS ->    "♣";
            case SPADES ->   "♠";
            case DIAMONDS -> "♦";
            case HEARTS ->   "♥";
        };
    }
}
