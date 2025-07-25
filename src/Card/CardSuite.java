package Card;

public enum CardSuite {
    CLUBS,
    SPADES,
    DIAMONDS,
    HEARTS;

    public String str() {
        return switch (this) {
            case CLUBS -> "♣";
            case SPADES -> "♠";
            case DIAMONDS -> "♦";
            case HEARTS -> "♥";
        };
    }
}
